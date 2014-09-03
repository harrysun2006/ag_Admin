/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.agloco.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.DateFormatInvalidException;
import com.agloco.report.util.AGReportResultList;
import com.agloco.service.util.ReportServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

/**
 * 
 * @author Erick Kong
 * 
 */
public class ReportQueryAction extends PortletAction
{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		return super.execute(mapping, form, req, res);
	}

	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig config, ActionRequest req, ActionResponse res)
			throws Exception
	{
		try
		{
			reportQuery(req, res);
		}
		catch (Exception e)
		{
			_log.error(e.toString());
			if (e instanceof DateFormatInvalidException)
			{
				SessionErrors.add(req, e.getClass().getName(), e);
			}
			else
			{
				SessionErrors.add(req, CannotCatchedException.class
						.getName());
			}
		}
	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception
	{
		List queryList = ReportServiceUtil.getAGQueryByType(0);
		req.setAttribute("queryList", queryList);
		
		return mapping.findForward("portlet.report.query");
	}

	protected void reportQuery(ActionRequest req, ActionResponse res)
			throws Exception
	{
		int queryId = ParamUtil.getShort(req, "querySelect");

		Map paramsMap = new HashMap();
		Enumeration paramsKey = req.getParameterNames();
		while(paramsKey.hasMoreElements())
		{
			Object tmp = paramsKey.nextElement();
			paramsMap.put(tmp, req.getParameter((String)tmp));
		}

//		String beginDate = ParamUtil.getString(req, "beginDate");
//		String endDate = ParamUtil.getString(req, "endDate");
//		String timezone = ParamUtil.getString(req, "timezone");
		
		
		AGReportResultList result = ReportServiceUtil.getReportResultList(queryId, paramsMap);
		
		req.setAttribute("QueryResult", result);
	}

	private static Log _log = LogFactory.getLog(ReportQueryAction.class);
}
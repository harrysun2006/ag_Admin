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

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.DateFormatInvalidException;
import com.agloco.exception.MemberCodeNullException;
import com.agloco.form.MemberQueryForm;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.StringUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.util.servlet.SessionErrors;

/**
 * 
 * @author Erick Kong
 * 
 */
public class MemberManagementAction extends PortletAction
{
	private boolean isLoad = true;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		if (!isLoad)
		{
			try
			{
				executeCommand(req, res, form);
			}
			catch (Exception e)
			{
				_log.error(e.toString());
				req.getSession().setAttribute("memberQueryForm", form);
				if (e instanceof DateFormatInvalidException
					|| e instanceof MemberCodeNullException)
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
		req.getSession().setAttribute("memberQueryForm", form);
		return super.execute(mapping, form, req, res);
	}

	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig config, ActionRequest req, ActionResponse res)
			throws Exception
	{
		isLoad = false;
	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception
	{
		return mapping.findForward("portlet.membermanagement.query");
	}

	protected void executeCommand(HttpServletRequest req, HttpServletResponse res,
			ActionForm form) throws Exception
	{
		MemberQueryForm mqf = (MemberQueryForm) form;
		boolean isTempMember = mqf.getMemberType().equals("AGMember")?false:true;
		List list = null;
		if(mqf.getCMD().equals("query"))
		{
			list = MemberServiceUtil.listAgMember(mqf);
			mqf.setSelectMembers("");
		}
		else if(mqf.getCMD().equals("flip"))
		{
			MemberQueryForm mqf2 = (MemberQueryForm)req.getSession().getAttribute("memberQueryForm");
			mqf2.setSelectMembers(mqf.getSelectMembers());
			mqf2.setPageNum(mqf.getPageNum());
			BeanUtils.copyProperties(mqf, mqf2);
			list = MemberServiceUtil.listAgMember(mqf);
		}
		else if(mqf.getCMD().equals("suspend") || mqf.getCMD().equals("activate"))
		{
			if(!StringUtil.isNotEmpty(mqf.getSelectMembers()))
				throw new MemberCodeNullException(); 
			String status = mqf.getCMD().equals("suspend")?"S":"N";
			
			MemberQueryForm mqf2 = (MemberQueryForm)req.getSession().getAttribute("memberQueryForm");
			mqf2.setSelectMembers(mqf.getSelectMembers());
			BeanUtils.copyProperties(mqf, mqf2);
			if(mqf.getSelectMembers()== null && mqf.getSelectMembers().length()<1)
				return;
			String[] idStr = mqf.getSelectMembers().split(",");
			
			MemberServiceUtil.updateMemberStatus(idStr, status, !isTempMember);
			list = MemberServiceUtil.listAgMember(mqf);
			mqf.setSelectMembers("");
		}
		
		req.getSession().setAttribute("memberList", list);
	}
	
	private static Log _log = LogFactory.getLog(MemberManagementAction.class);
}
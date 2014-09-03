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

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.agloco.util.ValidateUtil;
import com.liferay.portal.lastmodified.LastModifiedCSS;
import com.liferay.portal.lastmodified.LastModifiedJavaScript;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.spring.hibernate.CacheRegistry;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebCachePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

/**
 * <a href="EditServerAction.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class EditServerAction extends PortletAction {

	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig config, ActionRequest req, ActionResponse res)
			throws Exception {

		if (!ValidateUtil.isClearCacheAdmin(req.getRemoteUser())) {
			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.clearcache.error");

			return;
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("cacheDb")) {
			cacheDb();
		} else if (cmd.equals("cacheMulti")) {
			cacheMulti();
		} else if (cmd.equals("cacheSingle")) {
			cacheSingle();
		} else if (cmd.equals("gc")) {
			gc();
		}
//		else if (cmd.equals("cacheTomcat")) {
//			try {
//				ClearTomcatServerUtil.clearTomcatServ();
//			} catch (Exception e) {
//				// TODO: handle exception
//				SessionErrors.add(req, e.getClass().getName(), e);
//				setForward(req, "portlet.clearcache.error");
//				return;
//			}
//		}

		sendRedirect(req, res);
	}

	protected void cacheDb() throws Exception {
		CacheRegistry.clear();
	}

	protected void cacheMulti() throws Exception {
//		ClusterPool.clear();
		Calendar defaultCalendar=Calendar.getInstance();
		defaultCalendar.add(Calendar.MINUTE,10);
		ClusterPool.getCache().flushAll(defaultCalendar.getTime());
	}

	protected void cacheSingle() throws Exception {
		LastModifiedCSS.clear();
		LastModifiedJavaScript.clear();
		WebCachePool.clear();
	}

	protected void gc() throws Exception {
		Runtime.getRuntime().gc();
	}

	private static Log _log = LogFactory.getLog(EditServerAction.class);

}
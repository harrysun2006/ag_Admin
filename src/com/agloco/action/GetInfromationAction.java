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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.Constants;
import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.CountryInvalidException;
import com.agloco.exception.CountryIsNullExcetion;
import com.agloco.exception.EmailAddressCodeNullException;
import com.agloco.exception.MemberCodeNullException;
import com.agloco.exception.NoMemberMatchesEamilAddressException;
import com.agloco.exception.NoMemberMatchesMembercodeException;
import com.agloco.exception.NoPostCodeException;
import com.agloco.exception.NoSuchUserExistsException;
import com.agloco.exception.PostCodeInvalidException;
import com.agloco.service.util.AGCaptchaUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

/**
 * 
 * @author Erick Kong
 * 
 */
public class GetInfromationAction extends PortletAction
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
			sendInformation(req, res);
		}
		catch (Exception e)
		{
			if (e instanceof CaptchaTextException
					|| e instanceof ContactFirstNameException
					|| e instanceof NoMemberMatchesMembercodeException
					|| e instanceof NoMemberMatchesEamilAddressException
					|| e instanceof NoSuchUserExistsException
					|| e instanceof NoSuchUserException
					|| e instanceof CountryInvalidException
					|| e instanceof PostCodeInvalidException
					|| e instanceof CountryIsNullExcetion
					|| e instanceof NoPostCodeException
					|| e instanceof MemberCodeNullException
					|| e instanceof EmailAddressCodeNullException
					|| e instanceof DuplicateUserEmailAddressException
					|| e instanceof UserEmailAddressException)
			{
				SessionErrors.add(req, e.getClass().getName(), e);
			}
			else
			{
				//IF the excepiton is stranger, add CannotCatchedException into SessionErrors
				SessionErrors.add(req, CannotCatchedException.class.getName());
			}
		}
	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception
	{
		String forgetType = (String)req.getAttribute(Constants.FORGETINFO);
		if(forgetType == null || "".equals(forgetType))
			return mapping.findForward("portlet.forgetinfo.forgetinfo");
		else if(forgetType != null && Constants.PASSWORD.equals(forgetType))
			return mapping.findForward("portlet.forgetinfo.sendpassword");
		else if(forgetType != null && Constants.MEMBERCODE.equals(forgetType))
			return mapping.findForward("portlet.forgetinfo.sendmembercode");
		return mapping.findForward("portlet.forgetinfo.forgetinfo");
	}

	protected void sendInformation(ActionRequest req, ActionResponse res)
			throws Exception
	{
		AGCaptchaUtil.check(req);
		ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		
		String forgetTypeValue = ParamUtil.getString(req,"forgetTypeValue");
		String forgetType = ParamUtil.getString(req,"forgetType");
		
		validate(forgetType, forgetTypeValue);
		if(forgetTypeValue.indexOf('@') > 0) {
			forgetTypeValue = forgetTypeValue.toLowerCase();
		}
		else
		{
			if(Constants.MEMBERCODE.equals(forgetType))
				throw new UserEmailAddressException();
			forgetTypeValue = forgetTypeValue.toUpperCase();
		}
		
		MemberServiceUtil.getInfo(forgetType, forgetTypeValue, themeDisplay.getLocale());
		
		req.setAttribute(Constants.FORGETINFO, forgetType);
	}

	protected void validate(String forgetType,
			String forgetTypeValue) throws Exception
	{
		if(forgetTypeValue == null || "".equals(forgetTypeValue))
		{
			if(Constants.PASSWORD.equals(forgetType))
				throw new MemberCodeNullException();
			if(Constants.MEMBERCODE.equals(forgetType))
				throw new EmailAddressCodeNullException();
		}
	}
}
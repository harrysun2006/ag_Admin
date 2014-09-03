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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.UserPasswordAuthenticateException;
import com.agloco.exception.UserPasswordConfirmException;
import com.agloco.model.AGMember;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.ValidateUtil;
import com.liferay.util.servlet.SessionErrors;

/**
 * <a href="UpdateTermsOfUseAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Erick Kong
 *
 */
public class UpdateTermsOfUseAction extends Action {
	
	private static Log _log = LogFactory.getLog(UpdateTermsOfUseAction.class);

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res){

		try{
			String password = req.getParameter("newPassword").trim();
			String cfmPassword = req.getParameter("newPasswordCfm").trim();
			if(ValidateUtil.isPassword(password) && ValidateUtil.isPassword(cfmPassword))
			{
				if(!password.equals(cfmPassword))
					throw new UserPasswordConfirmException();
			}
			else
				throw new UserPasswordAuthenticateException();
			
			AGMember member = MemberServiceUtil.updateAgreedToTermsOfUse(req.getRemoteUser(), true ,password);
			MailServiceUtil.sendFirstSigninMail(member);
			req.setAttribute("memberCode", member.getMemberCode());
			//top.jsp use for referralcenter
			req.getSession().setAttribute(com.agloco.Constants.MEMBERCODE,member.getMemberCode());
			return mapping.findForward("agloco.sign_up.sign-up-email-link.jsp");
		}
		catch(Exception e){
			if(e instanceof UserPasswordAuthenticateException
					|| e instanceof UserPasswordConfirmException)
				SessionErrors.add(req, e.getClass().getName() , e);
			else
			{
				_log.error(e.getMessage());
				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
			}
			return mapping.findForward("portal.terms_of_use");
		}

		
		
		//remove this to MemberServiceImpl at 12/09/06
//		AGMemberCount agmc = new AGMemberCount();
//		agmc.setMemberId(member.getMemberId());
//		MemberServiceUtil.addAGMemberCount(agmc);
		
		
		
//		AGMemberTemp member = MemberServiceUtil.getAGMemberTempByUserId(req.getRemoteUser());
		
//		return mapping.findForward(Constants.COMMON_REFERER);
	}

}
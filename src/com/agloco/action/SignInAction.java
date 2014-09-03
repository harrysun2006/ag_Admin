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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.Constants;
import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.LoginIsNullException;
import com.agloco.exception.MemberHasBeenLoginException;
import com.agloco.exception.MemberIsSuspendException;
import com.agloco.exception.OmniadminCannotLogin;
import com.agloco.log4j.LogUtil;
import com.agloco.model.AGMember;
import com.agloco.model.MessageObject;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.MiscUtil;
import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.PrincipalFinder;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.XSSUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionParameters;

/**
 *  
 * @author Erick Kong
 * 
 */
public class SignInAction extends Action
{

	public static String getLogin(HttpServletRequest req, String paramName,
			Company company) throws PortalException, SystemException
	{

		String login = req.getParameter(paramName);

		if ((login == null) || (login.equals(StringPool.NULL)))
		{
			login = GetterUtil.getString(CookieUtil.get(req.getCookies(),
					CookieKeys.LOGIN));
		}

		login = XSSUtil.strip(login);
		return login;
	}

//	public static void login(HttpServletRequest req, HttpServletResponse res,
//			String login, String password, boolean rememberMe) throws Exception
//	{
//
//		CookieKeys.validateSupportCookie(req);
//
//		HttpSession ses = req.getSession();
//
//		AGMember agMember = MemberServiceUtil.authenticate(login, password);
//
//		String userId = agMember.getUserId();
//		
//		if("false".equalsIgnoreCase(PropsUtil.get(com.agloco.Constants.CANOMNIADMINLOGIN)) && OmniadminUtil.isOmniadmin(userId) )
//		{
//			throw new OmniadminCannotLogin();
//		}
//		
//		try
//		{
//			PrincipalFinder principalFinder = (PrincipalFinder) InstancePool
//					.get(PropsUtil.get(PropsUtil.PRINCIPAL_FINDER));
//
//			userId = principalFinder.fromLiferay(userId);
//		}
//		catch (Exception e)
//		{
//			throw e;
//		}
//
//		if (agMember != null)
//		{
//			//Get the member code, it will be use in top.jsp.
//			//There is a JS varible in top.jsp named memberCode 
//			String memberCode = agMember.getMemberCode();
//			String emailAddress = agMember.getEmailAddress();
//			//If member's status is not 'N', the member must contact admin to active passport!
//			if(!("N".equals(agMember.getStatus().trim()) 
//					|| StringUtils.EMPTY.equals(agMember.getStatus().trim())))
//				throw new MemberIsSuspendException();
//			
//			//If the member is login in other place, forbid he/she to login
////			if(SSLFilterUtil.isMemberLogin(userId))
////				throw new MemberHasBeenLoginException();
//			
//			LastPath lastPath = (LastPath) ses.getAttribute(WebKeys.LAST_PATH);
//
//			// Invalidate the previous session to prevent phishing
////			ses.invalidate();
//			ses = req.getSession();
//
//			if (lastPath != null)
//			{
//				ses.setAttribute(WebKeys.LAST_PATH, lastPath);
//			}
//			
//			ses.setAttribute(com.agloco.Constants.MEMBERCODE, memberCode);
//			
//			// Set cookies
//			ses.setAttribute("j_username", userId);
//			ses.setAttribute("j_password", password);
//			ses.setAttribute("j_remoteuser", userId);
//
//			Cookie loginCookie = null;
//
//			if(rememberMe)
//			{
//				loginCookie = new Cookie(CookieKeys.LOGIN, login);
//				loginCookie.setPath("/");
//
//				loginCookie.setMaxAge(CookieKeys.MAX_AGE);
//			}
//			else
//			{
//				loginCookie = new Cookie(CookieKeys.LOGIN, null);
//
//				loginCookie.setMaxAge(0);
//			}
//
//			CookieKeys.addCookie(res, loginCookie);
//			
//			MessageObject msg = new MessageObject();
//			
//			msg.setIp(req.getRemoteAddr());
//			msg.setOperate(Constants.OPERATE_SINGIN);
//			msg.setUserId(userId);
//			msg.setMemberCode(memberCode);
//			msg.setEmailAddress(emailAddress);
//			msg.setSessionId(req.getSession().getId());
//			msg.setUserAgent(req.getHeader("user-agent"));
//			msg.setServerIp(MiscUtil.getLocalIp());
//			try
//			{
//				_log.info(msg);	
//			}
//			catch (Exception e)
//			{
//				LogUtil.error(msg.toString(),e);
//				// TODO: handle exception
//			}
//		}
//		else
//		{
//			throw new AuthException();
//		}
//	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception
	{

		HttpSession ses = req.getSession();
		AGMember agMember = null;
		String login = "";
		String password = "";
		if (ses.getAttribute("j_username") != null
				&& ses.getAttribute("j_password") != null)
		{
			return mapping.findForward("/portal/touch_protected.jsp");
		}

		String isLoad = ParamUtil.getString(req, "isLoad");

		if(isLoad == null || "true".equals(isLoad) || "".equals(isLoad))
			return mapping.findForward("portal.login");

		try
		{
			// _login(req, res);
			login = getLogin(req);
			password = ParamUtil.getString(req, SessionParameters.get(req,
					com.agloco.Constants.PASSWORD));
			boolean rememberMe = ParamUtil.getBoolean(req,
					com.agloco.Constants.REMEMBERME);

			// login(req, res, login, password, rememberMe);
			
			CookieKeys.validateSupportCookie(req);

			agMember = MemberServiceUtil.authenticate(login, password);
			//validator the password; 20070607 locker
			 if (!agMember.getPassword().equals(password)) {
				throw new UserPasswordException(
						UserPasswordException.PASSWORDS_DO_NOT_MATCH);
			}
			String userId = agMember.getUserId();
			if("false".equalsIgnoreCase(PropsUtil.get(com.agloco.Constants.CANOMNIADMINLOGIN)) && OmniadminUtil.isOmniadmin(userId) )
			{
				throw new OmniadminCannotLogin();
			}
			
			try
			{
				PrincipalFinder principalFinder = (PrincipalFinder) InstancePool
						.get(PropsUtil.get(PropsUtil.PRINCIPAL_FINDER));

				userId = principalFinder.fromLiferay(userId);
			}
			catch (Exception e)
			{
				throw e;
			}

			if (agMember != null)
			{
				//Get the member code, it will be use in top.jsp.
				//There is a JS varible in top.jsp named memberCode 
				String memberCode = agMember.getMemberCode();
				String emailAddress = agMember.getEmailAddress();
				//If member's status is not 'N', the member must contact admin to active passport!
				if(!("N".equals(agMember.getStatus().trim()) 
						|| StringUtils.EMPTY.equals(agMember.getStatus().trim())))
					throw new MemberIsSuspendException();
				
				// If the member is login in other place, forbid he/she to login
				// if(SSLFilterUtil.isMemberLogin(userId))
				// throw new MemberHasBeenLoginException();
				
				LastPath lastPath = (LastPath) ses.getAttribute(WebKeys.LAST_PATH);

				// Invalidate the previous session to prevent phishing
				// ses.invalidate();
				if (lastPath != null)
				{
					ses.setAttribute(WebKeys.LAST_PATH, lastPath);
				}
				
				ses.setAttribute(com.agloco.Constants.MEMBERCODE, memberCode);
				
				// Set cookies
				ses.setAttribute("j_username", userId);
				ses.setAttribute("j_password", password);
				ses.setAttribute("j_remoteuser", userId);

				Cookie loginCookie = getLoginCookie(rememberMe, login);
				CookieKeys.addCookie(res, loginCookie);
				
				MessageObject msg = new MessageObject();
				
				msg.setIp(req.getRemoteAddr());
				msg.setOperate(Constants.OPERATE_SINGIN);
				msg.setUserId(userId);
				msg.setMemberCode(memberCode);
				msg.setEmailAddress(emailAddress);
				msg.setSessionId(req.getSession().getId());
				msg.setUserAgent(req.getHeader("user-agent"));
				msg.setServerIp(MiscUtil.getLocalIp());
				try
				{
					_log.info(msg);	
				}
				catch (Exception e)
				{
					LogUtil.error(msg.toString(),e);
				}
			}
			else
			{
				throw new AuthException();
			}
			
			return mapping.findForward("/portal/touch_protected.jsp");
		}
		catch (Exception e)
		{
			if (e instanceof AuthException
					|| e instanceof CookieNotSupportedException
					|| e instanceof NoSuchUserException
					|| e instanceof UserEmailAddressException
					|| e instanceof UserIdException
					|| e instanceof OmniadminCannotLogin
					|| e instanceof UserPasswordException
					|| e instanceof LoginIsNullException
					|| e instanceof MemberIsSuspendException
					|| e instanceof MemberHasBeenLoginException)
			{
				SessionErrors.add(req, e.getClass().getName());

				MessageObject msg = new MessageObject("", "", "","", Constants.OPERATE_SINGIN, req.getRemoteAddr());
				StringBuffer sb = new StringBuffer();
				if(login.indexOf('@') > 0) {
					msg.setEmailAddress(login.toLowerCase());
					sb.append("Use emailAddress login,password is:").append(password);
					if (agMember != null) {
						msg.setMemberCode(agMember.getMemberCode());
					}else{
						sb.append(",This member does not exist");
					}
					msg.setMessage(sb.toString());	
				} else {
					msg.setMemberCode(login.toUpperCase());
					sb.append("Use memberCode login,password is:").append(password);
					if (agMember != null) {
						msg.setEmailAddress(agMember.getEmailAddress());
					}else{
						sb.append(",This member does not exist");
					}
					msg.setMessage(sb.toString());
				}
				msg.setSessionId(req.getSession().getId());
				msg.setUserAgent(req.getHeader("user-agent"));
				msg.setServerIp(MiscUtil.getLocalIp());
				if (agMember != null){
					msg.setUserId(agMember.getUserId());
					msg.setMemberCode(agMember.getMemberCode());
					msg.setEmailAddress(agMember.getEmailAddress());
				}
			
				try
				{
					_log.error(msg);
				}
				catch (Exception e2)
				{
					LogUtil.error(msg.toString(),e2);
					// TODO: handle exception
				}
				
				return mapping.findForward("portal.login");
			}
			else
			{
				//IF the excepiton is stranger, add CannotCatchedException into SessionErrors
				SessionErrors.add(req, CannotCatchedException.class.getName());
				return mapping.findForward("portal.login");
			}
		}
	}

	// private void _login(HttpServletRequest req, HttpServletResponse res)
	// throws Exception {
	// String login = ParamUtil.getString(req, "login");
	// if (login == null || "".equals(login))
	// throw new LoginIsNullException();
	// if (login.indexOf('@') > 0) {
	// login = login.toLowerCase();
	// } else {
	// login = login.toUpperCase();
	// login = login.replaceAll("-", "");
	// }
	//
	// String password = ParamUtil.getString(req, SessionParameters.get(req,
	// com.agloco.Constants.PASSWORD));
	// boolean rememberMe = ParamUtil.getBoolean(req,
	// com.agloco.Constants.REMEMBERME);
	// login(req, res, login, password, rememberMe);
	//	}
	
	
	//get the loginString
	private String getLogin(HttpServletRequest req) throws LoginIsNullException{
		String login = ParamUtil.getString(req, "login");
		if(login==null || "".equals(login))
			throw new LoginIsNullException();
		if(login.indexOf('@') > 0) {
			login = login.toLowerCase();
		} else {
			login = login.toUpperCase();
			login = login.replaceAll("-", "");
		}
		return login;
	}
	
	//create the login Cookie
	private Cookie getLoginCookie(boolean rememberMe,String login){
		Cookie loginCookie=null;
		if(rememberMe)
		{
			loginCookie = new Cookie(CookieKeys.LOGIN, login);
			loginCookie.setPath("/");
			loginCookie.setMaxAge(CookieKeys.MAX_AGE);
		}
		else
		{
			loginCookie = new Cookie(CookieKeys.LOGIN, null);
			loginCookie.setMaxAge(0);
		}
		return loginCookie;
	}

	private static Log _log = LogFactory.getLog(SignInAction.class);

}
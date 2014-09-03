package com.agloco.action;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.Constants;
import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.DuplicateEmailAddressException;
import com.agloco.exception.UserEmailAddressConfirmException;
import com.agloco.form.ChangeEmailForm;
import com.agloco.model.AGMemberTemp;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.ConfigUtil;
import com.agloco.util.Validator;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.servlet.SessionErrors;

/**
 * 
 * @author terry_zhao
 *
 */
public class ChangeEmailAddressAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			ActionRequest req, 
			ActionResponse res)
		throws Exception {
		
		try{
			
			ChangeEmailForm f = (ChangeEmailForm)form;
			if(validateFormHasError(req,f)){
				req.setAttribute("hasError", Boolean.TRUE);
				return;
			}
			AGMemberTemp mt = MemberServiceUtil.getAGMemberTempByUserId(f.getUserId());
			if(mt == null){
//				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
				req.setAttribute("redirectToHome", Boolean.TRUE);
				return;
			}
			User user = UserLocalServiceUtil.getUserById(f.getUserId());
			if(user == null){
//				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
				req.setAttribute("redirectToHome", Boolean.TRUE);
				return;
			}
			
			mt.setEmailAddress(f.getEmailAddress());
			mt.setModifiedDate(Calendar.getInstance());
			MemberServiceUtil.updateAGMemberTemp(user.getUserId(),mt);
			MailServiceUtil.sendSignupMail(mt,user.getLocale());
			
			req.setAttribute("memberPassword", mt.getPassword());
			req.setAttribute("change-email-success", "change-email-success");
		}
		catch(Exception e){
			if(e instanceof DuplicateEmailAddressException){
				SessionErrors.add(req, DuplicateEmailAddressException.class.getName() , new DuplicateEmailAddressException());
			}
			else{
				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());	
			}
			
		}
		

		
	}
	
	private boolean validateFormHasError(ActionRequest req, ChangeEmailForm f){
		 
		boolean hasError = false;
		if(!Validator.isValidEmailAddress(f.getEmailAddress())){
			hasError = true;
			SessionErrors.add(req, UserEmailAddressException.class.getName(), new UserEmailAddressException());
		}
		if(!f.getEmailAddress().equals(f.getEmailAddressCfm())){
			hasError = true;
			SessionErrors.add(req, UserEmailAddressConfirmException.class.getName(), new UserEmailAddressConfirmException());
		}
		return hasError;
	}
	
	public ActionForward render(
			ActionMapping mapping, 
			ActionForm form, 
			PortletConfig config,
			RenderRequest req,
			RenderResponse res)
		throws Exception {
		
		Company company = PortalUtil.getCompany(req);
		if (!company.isStrangers()){
			throw new PrincipalException();
		}
		
		ChangeEmailForm f = (ChangeEmailForm)form;
		RenderRequestImpl reqImpl = (RenderRequestImpl) req;
		HttpServletRequest httpReq = reqImpl.getHttpServletRequest();
		
		if("change-email-success".equals(req.getAttribute("change-email-success"))){
			return changeEmailSuccess(mapping,req,res,company,f,httpReq);
		}
		
		Boolean hasError = (Boolean) req.getAttribute("hasError");
		if((hasError == Boolean.TRUE) || !SessionErrors.isEmpty(req)){
			if(SessionErrors.isEmpty(req)){
				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
			}
			return mapping.findForward("portlet.sign-up.change.email.address");
		}
		return initChangeEmail(mapping, req, res, f, httpReq);
		
	}

	private ActionForward initChangeEmail(ActionMapping mapping, 
										  RenderRequest req, 
										  RenderResponse res, 
										  ChangeEmailForm f, 
										  HttpServletRequest httpReq) throws LanguageException {

		ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		res.setTitle(LanguageUtil.get(user, "change email"));
	
		String userId = CookieUtil.get(httpReq.getCookies(), Constants.COOKIE_SIGN_UP_USER_ID);
		if(StringUtils.isBlank(userId)){
			userId = (String)httpReq.getSession().getAttribute(Constants.SESSION_SIGN_UP_USER_ID);
		}
		
		if(StringUtils.isBlank(userId)){
			//sessionerror add: browser do not support session or cookie 
			req.setAttribute("redirectToHome", Boolean.TRUE);
		}
		if(themeDisplay.isSignedIn()){
			req.setAttribute("redirectToHome", Boolean.TRUE);
		}
		
		f.setUserId(userId);
		f.setEmailAddress("");
		f.setEmailAddressCfm("");
		return mapping.findForward("portlet.sign-up.change.email.address");
		
	}		
	
	private ActionForward changeEmailSuccess(ActionMapping mapping, 
											 RenderRequest req, 
											 RenderResponse res, 
											 Company company, 
											 ChangeEmailForm f, 
											 HttpServletRequest httpReq) {
		
		checkEmailAddress(req, res, company,f, httpReq);
		
		boolean b = false;
		String emailAddressExclude = ConfigUtil.getConfig(Constants.EMAIL_ADDRESS_EXCLUDE, company.getCompanyId());
		if(emailAddressExclude == null) emailAddressExclude = "";
		Pattern p = Pattern.compile(emailAddressExclude, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(f.getEmailAddress());
		b = b || m.matches();
		
		if(b){	
			req.setAttribute("memberPassword", req.getAttribute("memberPassword"));
			return	mapping.findForward("portlet.sign-up.sign-up.step2.temp");
		}
		

		return mapping.findForward("portlet.sign-up.sign-up.finished");
	}
	
	/**
	 * display email address  
	 * offer button to browse email service home page 
	 * offer link to correct email address
	 * @param req
	 * @param res
	 * @param company
	 * @param httpReq
	 */
	private void checkEmailAddress(RenderRequest req, 
								   RenderResponse res, 
								   Company company, 
								   ChangeEmailForm f, 
								   HttpServletRequest httpReq) {
		
		RenderResponseImpl resImpl = (RenderResponseImpl)res;
		HttpServletResponse httpRes = resImpl.getHttpServletResponse();
		String userId = (String)req.getAttribute("sign-up-userId");
		String emailAddress = f.getEmailAddress();
		if(StringUtils.isBlank(emailAddress)){
			return;
		}
		
		//set cookie or session for correcting email address
		Cookie userIdCookie = new Cookie(Constants.COOKIE_SIGN_UP_USER_ID,userId);
		userIdCookie.setPath("/");
		if(GetterUtil.getBoolean(PropsUtil.get(PropsUtil.SESSION_ENABLE_PERSISTENT_COOKIES))){
			if(!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.TCK_URL))){
				httpRes.addCookie(userIdCookie);
			}
			else{
				httpReq.getSession().setAttribute(Constants.SESSION_SIGN_UP_USER_ID, userId);
			}
		}
		else{
			httpReq.getSession().setAttribute(Constants.SESSION_SIGN_UP_USER_ID, userId);
		}
		
		//according config judge whether page need button to go to email service home page.
		String needEmailServiceURL = ConfigUtil.getConfig(Constants.CONFIG_EMAIL_SERVICE_DOMAIN_URL_NEED, company.getCompanyId());
		if(needEmailServiceURL == null){
			needEmailServiceURL = StringUtils.EMPTY;
		}
		
		Pattern p = Pattern.compile(needEmailServiceURL,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(emailAddress);
		if(m.matches()){
			req.setAttribute("displayButton", Boolean.TRUE);
			String emailServiceDomain = emailAddress.substring(emailAddress.lastIndexOf(Constants.SYMBOL_AT)+1);
			String buttonURL = ConfigUtil.getConfig(Constants.CONFIG_EMAIL_SERVICE_DOMAIN_URL_PRE+emailServiceDomain, company.getCompanyId());
			if(StringUtils.isBlank(buttonURL)){
				req.setAttribute("displayButton", Boolean.FALSE);	
			}
			req.setAttribute("buttonURL", buttonURL);
			
			String buttonValue = emailServiceDomain.substring(0, emailServiceDomain.indexOf(StringPool.PERIOD));			
			buttonValue = buttonValue.substring(0,1).toUpperCase()+ buttonValue.substring(1);
			req.setAttribute("buttonValue", buttonValue);
		}
		
		req.setAttribute("emailAddress", emailAddress);
	}
}

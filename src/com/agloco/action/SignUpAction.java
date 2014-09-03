package com.agloco.action;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.Constants;
import com.agloco.exception.CannotCatchedException;
import com.agloco.exception.DateFormatInvalidException;
import com.agloco.exception.DuplicateEmailAddressException;
import com.agloco.exception.ReferralCodeException;
import com.agloco.exception.UserBirthdayDateException;
import com.agloco.exception.UserCityException;
import com.agloco.exception.UserCountryException;
import com.agloco.exception.UserEmailAddressConfirmException;
import com.agloco.exception.UserNotAgreeException;
import com.agloco.exception.UserPostCodeException;
import com.agloco.exception.UserStateException;
import com.agloco.form.SignUpForm;
import com.agloco.log4j.LogUtil;
import com.agloco.model.AGMember;
import com.agloco.model.AGMemberTemp;
import com.agloco.model.MessageObject;
import com.agloco.service.util.AGSingleCaptchaUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.ConfigUtil;
import com.agloco.util.Generator;
import com.agloco.util.MiscUtil;
import com.agloco.util.ValidateUtil;
import com.agloco.util.Validator;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

/**
 * 
 * @author terry_zhao
 *
 */

public class SignUpAction extends PortletAction {
	
	private static Log _log = LogFactory.getLog(SignUpAction.class);
	


//	private String getReferralCode(String enReferralCode) {
//		byte[] b = Base64.decode(enReferralCode);	
//		String s = CryptUtil.AESDecrypt(b, Constants.COMMON_AESKEY, Constants.DATABASE_CHARSET); 
//		return s;
//	}

	public void processAction(ActionMapping mapping, ActionForm form,
			PortletConfig config, ActionRequest req, ActionResponse res)
			throws Exception
	{
		SignUpForm f = (SignUpForm)form;
		AGMemberTemp agMemberTemp = null;
		ActionRequestImpl reqImpl = (ActionRequestImpl) req;
		HttpServletRequest httpReq = reqImpl.getHttpServletRequest();
		boolean hasError = false;
		try
		{
			
//			validateForm(f);
			if(f.isAgree() == false){
				hasError = true;
				SessionErrors.add(req, UserNotAgreeException.class.getName(), new UserNotAgreeException());
//				throw new UserNotAgreeException();
			}
			
			if(StringUtils.isBlank(f.getFirstName())){
				hasError = true;
				SessionErrors.add(req, ContactFirstNameException.class.getName(), new ContactFirstNameException());
				
//				throw new ContactFirstNameException();
			}
			if(StringUtils.isBlank(f.getLastName())){
				hasError = true;
				SessionErrors.add(req, ContactLastNameException.class.getName(), new ContactLastNameException());
//				throw new ContactLastNameException();
			}	
			if(!Validator.isValidEmailAddress(f.getEmailAddress())){
				hasError = true;
				SessionErrors.add(req, UserEmailAddressException.class.getName(), new UserEmailAddressException());
//				throw new UserEmailAddressException();
			}
			if(!f.getEmailAddress().equals(f.getEmailAddressCfm())){
				hasError = true;
				SessionErrors.add(req, UserEmailAddressConfirmException.class.getName(), new UserEmailAddressConfirmException());
//				throw new UserEmailAddressConfirmException();
			}

			if(StringUtils.isBlank(f.getCity())){
				hasError = true;
				SessionErrors.add(req, UserCityException.class.getName(), new UserCityException());
//				throw new UserCityException();
			}

			if(StringUtils.isBlank(f.getState())){
				hasError = true;
				SessionErrors.add(req, UserStateException.class.getName(), new UserStateException());
//				throw new UserStateException();
			}

			if(StringUtils.isBlank(f.getPostCode())){
				hasError = true;
				SessionErrors.add(req, UserPostCodeException.class.getName(), new UserPostCodeException());
//				throw new UserPostCodeException();
			}
			if(StringUtils.isBlank(f.getCountry())){
				hasError = true;
				SessionErrors.add(req, UserCountryException.class.getName(), new UserCountryException());
//				throw new UserCountryException();
			}
			
			if(StringUtils.isNotBlank(f.getBirthDate())){
				if(!ValidateUtil.isValidateDate(f.getBirthDate())){
					hasError = true;
					SessionErrors.add(req, DateFormatInvalidException.class.getName(), new DateFormatInvalidException());
				}
//				try {
//					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//					df.parse(f.getBirthDate());
//				} catch(ParseException pe) {
//					hasError = true;
//					SessionErrors.add(req, DateFormatInvalidException.class.getName(), new DateFormatInvalidException());
////					throw new DateFormatInvalidException();
//				}
			}
			
			if(hasError == true){
				if(_log.isErrorEnabled()){
					MessageObject mo = new MessageObject();
					mo.setOperate(Constants.OPERATE_SINGUP);
					mo.setIp(httpReq.getRemoteAddr());
					mo.setSessionId(httpReq.getSession().getId());
					mo.setUserAgent(httpReq.getHeader("User-Agent"));
					String referralFlag = (String) httpReq.getSession().getAttribute("referralFlag");
					if(StringUtils.isNotBlank(referralFlag) && referralFlag.equalsIgnoreCase("true")){
//						mo.setReferralCode(getReferralCode(f.getReferralCode()).toUpperCase());	
						mo.setReferralCode(f.getReferralCode());//modified at 2007-03-09
					}
					else{
						mo.setReferralCode(f.getReferralCode2());	
					}
					
					mo.setEmailAddress(f.getEmailAddress());
					mo.setDescription("input error!");
					mo.setServerIp(MiscUtil.getLocalIp());
					try
					{
						_log.error(mo);
					}
					catch (Exception e2)
					{
						LogUtil.error(mo.toString(),e2);
						// TODO: handle exception
					}
				}
			}
			
			hasError = hasError || validateCaptchaHasError(req,f);
			
			if(hasError == true){
				if(SessionErrors.isEmpty(req)){
					SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
				}
				req.setAttribute("hasError", Boolean.TRUE);
				return;
			}
			
			AGMember agMember = null; 
			String referralCode = f.getReferralCode();
			if(StringUtils.isNotBlank(referralCode)){
//				referralCode = getReferralCode(referralCode).toUpperCase();//modified at 2007-03-09
				agMember = MemberServiceUtil.getAGMemberByCode(referralCode);
				if(agMember == null){
//					throw new ReferralCodeException();
					SessionErrors.add(req, ReferralCodeException.class.getName(), new ReferralCodeException());
					req.setAttribute("hasError", Boolean.TRUE);
					return;
				}
				
			}
			
			String referralCode2 = f.getReferralCode2();
			if(StringUtils.isBlank(referralCode) && StringUtils.isNotBlank(referralCode2)){
				referralCode2 = referralCode2.toUpperCase().replaceAll("-", "");
				agMember = MemberServiceUtil.getAGMemberByCode(referralCode2);
				if(agMember == null){
//					throw new ReferralCodeException();
					SessionErrors.add(req, ReferralCodeException.class.getName(), new ReferralCodeException());
					req.setAttribute("hasError", Boolean.TRUE);
					return;
				}
				
			}
			
			ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
			Company company = themeDisplay.getCompany();
			
			User user = new User();
			user.setCompanyId(company.getCompanyId());
			Locale locale = themeDisplay.getLocale();
			
			
			agMemberTemp = new AGMemberTemp();
			BeanUtils.copyProperties(agMemberTemp, f);
			
			if(MemberServiceUtil.getAGMemberByEmail(f.getEmailAddress()) != null  || MemberServiceUtil.getAGMemberTempByEmail(f.getEmailAddress()) != null){
//				throw new DuplicateUserEmailAddressException();
				SessionErrors.add(req, DuplicateUserEmailAddressException.class.getName(), new DuplicateUserEmailAddressException());
				req.setAttribute("hasError", Boolean.TRUE);
				return;
			}
			
			agMemberTemp.setLastMailTime(Calendar.getInstance());
			agMemberTemp.setPassword(Generator.generatePassword());

			agMemberTemp.setCreateDate(Calendar.getInstance());
//			agMemberTemp.setModifiedDate(Calendar.getInstance());
//			agMemberTemp.setMailCount(new Integer(1));//modified in mailservice
			
			if(agMember != null){
				agMemberTemp.setReferralCode(agMember.getMemberCode());
			}
			
			user.setUserId(Generator.generateUserId(user.getCompanyId()));
			user = MemberServiceUtil.addUserMemberTemp(user.getCompanyId(), false,
					user.getUserId(), false, "agloco", "agloco", false,
					Constants.DEFAULT_MAIL, locale, f.getFirstName(), f.getMiddleName(),
					f.getLastName(), "", "", "", true, 0, 0, 0, "", "", "",true,agMemberTemp);
			
			// Session messages

			SessionMessages.add(httpReq, "user_added", agMemberTemp.getEmailAddress());

			req.setAttribute("sign-up-finished", "sign-up-finished");
			req.setAttribute("sign-up-finished-password", agMemberTemp.getPassword());
			req.setAttribute("sign-up-userId", agMemberTemp.getUserId());
			req.setAttribute("sign-up-emailAddress", agMemberTemp.getEmailAddress());
			
			if(_log.isInfoEnabled()){
				MessageObject mo = new MessageObject();
				mo.setOperate(Constants.OPERATE_SINGUP);
				mo.setIp(httpReq.getRemoteAddr());
				mo.setSessionId(httpReq.getSession().getId());
				mo.setUserAgent(httpReq.getHeader("User-Agent"));
				mo.setReferralCode(agMemberTemp.getReferralCode());
				mo.setEmailAddress(agMemberTemp.getEmailAddress());
				mo.setUserId(agMemberTemp.getUserId());
				mo.setServerIp(MiscUtil.getLocalIp());
				try
				{
					_log.info(mo);
				}
				catch (Exception e2)
				{
					LogUtil.error(mo.toString(),e2);
					// TODO: handle exception
				}
			}
			httpReq.getSession().setAttribute("SignupSuccess", "True");
			

			
		}
		catch (Exception e) {
			
			if(agMemberTemp != null){
				if(_log.isErrorEnabled()){
					MessageObject mo = new MessageObject();
					mo.setOperate(Constants.OPERATE_SINGUP);
					mo.setIp(httpReq.getRemoteAddr());
					mo.setSessionId(httpReq.getSession().getId());
					mo.setUserAgent(httpReq.getHeader("User-Agent"));
					mo.setReferralCode(agMemberTemp.getReferralCode());
					mo.setEmailAddress(agMemberTemp.getEmailAddress());
					mo.setUserId(agMemberTemp.getUserId());
					mo.setServerIp(MiscUtil.getLocalIp());
					try
					{
						_log.error(mo);
					}catch (Exception e2) {
						// TODO: handle exception
						LogUtil.error(mo.toString(),e2);
					}
				}
			}
			
			if (e instanceof CaptchaTextException ||
				e instanceof ContactFirstNameException ||
				e instanceof ContactLastNameException ||
				e instanceof DuplicateUserEmailAddressException ||
				e instanceof NoSuchOrganizationException ||
				e instanceof OrganizationParentException ||
				e instanceof RequiredUserException ||
				e instanceof ReservedUserEmailAddressException ||
				e instanceof UserEmailAddressException ||
				e instanceof UserIdException ||
				e instanceof UserPasswordException ||
				e instanceof UserSmsException || 
				e instanceof UserBirthdayDateException || 
				e instanceof UserCityException || 
				e instanceof UserCountryException || 
				e instanceof UserPostCodeException || 
				e instanceof UserStateException || 
				e instanceof ReferralCodeException || 
				e instanceof DuplicateEmailAddressException || 
				e instanceof UserEmailAddressConfirmException || 
				e instanceof UserNotAgreeException || 
				e instanceof DateFormatInvalidException) {

				SessionErrors.add(req, e.getClass().getName(), e);

			}
			else {
				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());	
			}
		}	
	}

	private void validateCaptcha(ActionRequest req) throws CaptchaTextException {
		try
		{
			AGSingleCaptchaUtil.check(req);
		}
		catch (CaptchaTextException cte)
		{
			throw cte;
		}
	}

	private boolean validateCaptchaHasError(ActionRequest req,ActionForm form){
		
		try
		{
			AGSingleCaptchaUtil.check(req);
			return false;
		}
		catch (CaptchaTextException cte)
		{
			SignUpForm f = (SignUpForm)form;
			ActionRequestImpl reqImpl = (ActionRequestImpl) req;
			HttpServletRequest httpReq = reqImpl.getHttpServletRequest();
			
			if(_log.isErrorEnabled()){
				MessageObject mo = new MessageObject();
				mo.setOperate(Constants.OPERATE_SINGUP);
				mo.setIp(httpReq.getRemoteAddr());
				mo.setSessionId(httpReq.getSession().getId());
				mo.setUserAgent(httpReq.getHeader("User-Agent"));
				String referralFlag = (String) httpReq.getSession().getAttribute("referralFlag");
				if(StringUtils.isNotBlank(referralFlag) && referralFlag.equalsIgnoreCase("true")){
//					mo.setReferralCode(getReferralCode(f.getReferralCode()).toUpperCase());
					mo.setReferralCode(f.getReferralCode());//modified at 2007-03-09
				}
				else{
					mo.setReferralCode(f.getReferralCode2());	
				}
				
				mo.setEmailAddress(f.getEmailAddress());
				mo.setDescription("captcha error!");
				mo.setServerIp(MiscUtil.getLocalIp());
				try
				{
					_log.error(mo);
				}catch (Exception e2) {
					// TODO: handle exception
					LogUtil.error(mo.toString(),e2);
				}
			}
			
			SessionErrors.add(req, CaptchaTextException.class.getName(), new CaptchaTextException());
			return true;
//			throw cte;
		}
	}
	
	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception
	{
	
		Company company = PortalUtil.getCompany(req);

		if (!company.isStrangers())
		{
			throw new PrincipalException();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) req
				.getAttribute(WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		res.setTitle(LanguageUtil.get(user, "sign up"));
	
		SignUpForm f = (SignUpForm)form;
		

		RenderRequestImpl reqImpl = (RenderRequestImpl) req;
		RenderResponseImpl resImpl = (RenderResponseImpl) res;
		HttpServletRequest httpReq = reqImpl.getHttpServletRequest();
		HttpServletResponse httpRes = resImpl.getHttpServletResponse();
		
		// sign up success
		if("sign-up-finished".equals(req.getAttribute("sign-up-finished"))){
		
			return signUpSuccess(mapping, req, res, company, f, httpReq);	
		}
		
		
		//sign up check error
		Boolean hasError = (Boolean) req.getAttribute("hasError");
		if((hasError == Boolean.TRUE) || !SessionErrors.isEmpty(req)){
			if(SessionErrors.isEmpty(req)){
				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
			}
			String referralFlag = (String) httpReq.getSession().getAttribute("referralFlag");
			if(StringUtils.isNotBlank(referralFlag) && referralFlag.equalsIgnoreCase("true")){
				return mapping.findForward("portlet.sign.up.referral");	
			}

			return mapping.findForward("portlet.sign-up.sign-up");
		}
		
		return initSignUp(mapping, f, httpReq, httpRes);	
		

	}

	private ActionForward initSignUp(ActionMapping mapping, 
									 SignUpForm f, 
									 HttpServletRequest req,
									 HttpServletResponse res) {
		
		//process link by /r/...
		//modified at 2007-03-13
		HttpSession session = req.getSession();
		String referralCode = null;
		referralCode = CookieUtil.get(req.getCookies(), Constants.COOKIE_SIGN_UP);

		if(StringUtils.isBlank(referralCode)) {
			referralCode = (String) session.getAttribute(Constants.SESSION_REFERRAL_CODE);
		}	
		if(StringUtils.isBlank(referralCode)) {
			referralCode = req.getParameter("referralCode");
		}
		if(StringUtils.isBlank(referralCode)) {
			LastPath lastPath = (LastPath) req.getAttribute(WebKeys.LAST_PATH);
			Map parameters = lastPath.getParameterMap();
			if(parameters != null) {
				String[] values = (String[]) parameters.get("referralCode");
				if(values != null && values.length > 0) {
					referralCode = values[0];
					session.setAttribute(Constants.SESSION_REFERRAL_CODE, referralCode);	
				}
			}
		}

		//referral code is null
		if(StringUtils.isBlank(referralCode)){
			session.setAttribute("referralFlag", "false");
			return mapping.findForward("portlet.sign-up.sign-up");
		}
		
			
		AGMember member = MemberServiceUtil.getAGMemberByCode(referralCode);
		
		//referral code is not correct
		if(member == null){
			f.setReferralCode("");
			req.setAttribute("referralCode2", referralCode);
			if(StringUtils.isBlank(f.getReferralCode2())){
				f.setReferralCode2(referralCode);
				SessionErrors.add(req, ReferralCodeException.class.getName(), new ReferralCodeException());	
			}
			session.setAttribute("referralFlag", "false");
			return mapping.findForward("portlet.sign-up.sign-up");
		}
		
		// referral code is right
		f.setReferralCode(referralCode);//modified at 2007-03-09
//		f.setReferralCode(enReferralCode);
		session.setAttribute("referralFlag", "true");
		req.setAttribute("referralCode", referralCode);//modified at 2007-03-09
//		httpReq.setAttribute("referralCode", enReferralCode);
		return mapping.findForward("portlet.sign.up.referral");
	}

	private ActionForward signUpSuccess(ActionMapping mapping, 
										RenderRequest req, 
										RenderResponse res, 
										Company company, 
										SignUpForm f, 
										HttpServletRequest httpReq) {
		
		
		checkEmailAddress(req, res, company, httpReq);
		
		boolean b = false;
		String emailAddressExclude = ConfigUtil.getConfig(Constants.EMAIL_ADDRESS_EXCLUDE, company.getCompanyId());
		if(emailAddressExclude == null) emailAddressExclude = "";
		Pattern p = Pattern.compile(emailAddressExclude, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(f.getEmailAddress());
		b = b || m.matches();
		
		if(b){	
			req.setAttribute("memberPassword", req.getAttribute("sign-up-finished-password"));
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
								   HttpServletRequest httpReq) {
		
		RenderResponseImpl resImpl = (RenderResponseImpl)res;
		HttpServletResponse httpRes = resImpl.getHttpServletResponse();
		String userId = (String)req.getAttribute("sign-up-userId");
		String emailAddress = (String)req.getAttribute("sign-up-emailAddress");
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
		httpReq.getSession().setAttribute(Constants.SESSION_SIGN_UP_USER_ID, userId);
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

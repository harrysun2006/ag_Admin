package com.agloco.action;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
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
import com.agloco.exception.UserNewPasswordException;
import com.agloco.exception.UserNotAgreeException;
import com.agloco.exception.UserPasswordAuthenticateException;
import com.agloco.exception.UserPasswordConfirmException;
import com.agloco.exception.UserPostCodeException;
import com.agloco.exception.UserStateException;
import com.agloco.form.MyProfileChangePasswordForm;
import com.agloco.model.AGMember;
import com.agloco.service.util.AGSingleCaptchaUtil;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.ValidateUtil;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.SessionErrors;

public class MyProfileChangePasswordAction extends PortletAction {
	
	public void processAction(ActionMapping mapping,
							  ActionForm form,
							  PortletConfig config,
							  ActionRequest request,
							  ActionResponse response) throws Exception{
		
		MyProfileChangePasswordForm f = (MyProfileChangePasswordForm)form;
		
		try{
			
			validateForm(f);
			validateCaptcha(request);

			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			User user = themeDisplay.getUser();
			String userId = user.getUserId();

			if(StringUtils.isBlank(userId)){
				throw new UserIdException();
			}


			AGMember member = MemberServiceUtil.getAGMemberByUserId(userId);
			
			if(StringUtils.isBlank(f.getPreviousPassword()) || !f.getPreviousPassword().equals(member.getPassword().trim())){
				throw new UserPasswordAuthenticateException();
			}
			
			member.setPassword(f.getNewPassword());
			member.setModifiedDate(Calendar.getInstance());
			MemberServiceUtil.updateAGMember(userId,member);
			
			MailServiceUtil.sendMail(member, Constants.ARTICLEID_AGS_CHANGE_PASSWORD_EMAIL);
			
			request.setAttribute("my_profile_saved", "my_profile_saved");
			
		}
		catch (Exception e){
//			request.setAttribute("myProfileForm", f);
			request.setAttribute("hasError", Boolean.TRUE);
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
				e instanceof UserNewPasswordException || 
				e instanceof UserPasswordConfirmException || 
				e instanceof UserPasswordAuthenticateException || 
				e instanceof DateFormatInvalidException) {

				SessionErrors.add(request, e.getClass().getName(), e);
			}
			else {
				SessionErrors.add(request, CannotCatchedException.class.getName());
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
	
	private void validateForm(MyProfileChangePasswordForm f) throws Exception {
		// TODO Auto-generated method stub
	
		if(!ValidateUtil.isPassword(f.getNewPassword())){
			throw new UserNewPasswordException();
		}
		if(!f.getNewPassword().equals(f.getNewPasswordCfm())){
			throw new UserPasswordConfirmException();
		}

	}

	public ActionForward render(ActionMapping mapping, ActionForm form,
			PortletConfig config, RenderRequest req, RenderResponse res)
			throws Exception
	{

		
		if("my_profile_saved".equals(req.getAttribute("my_profile_saved"))){
			return mapping.findForward("portlet.my_profile.my_profile_saved");
		}
		
		Boolean hasError = (Boolean) req.getAttribute("hasError");
		if((hasError == Boolean.TRUE) || !SessionErrors.isEmpty(req)){
			if(SessionErrors.isEmpty(req)){
				SessionErrors.add(req, CannotCatchedException.class.getName() , new CannotCatchedException());
			}
			return mapping.findForward("portlet.my_profile.my_profile_change_password");
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		
		User user = themeDisplay.getUser();
		String userId = user.getUserId();
		
//		if(!themeDisplay.isSignedIn()){
//			return mapping.findForward("portlet.my_profile.my_profile_change_password");
//
//		}
		
		MyProfileChangePasswordForm f = (MyProfileChangePasswordForm)form;
		if(f != null && StringUtils.isBlank(f.getMemberCode())){
			AGMember member = MemberServiceUtil.getAGMemberByUserId(userId);
			f.setMemberCode(member.getMemberCode());
		}

//		res.setTitle(LanguageUtil.get(user, "my profile"));
		return mapping.findForward("portlet.my_profile.my_profile_change_password");
		
	}
	

}

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
import com.agloco.form.MyProfileForm;
import com.agloco.model.AGMember;
import com.agloco.service.util.AGSingleCaptchaUtil;
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

public class MyProfileAction extends PortletAction {
	
	public void processAction(ActionMapping mapping,
							  ActionForm form,
							  PortletConfig config,
							  ActionRequest request,
							  ActionResponse response) throws Exception{
		
		MyProfileForm f = (MyProfileForm)form;
		
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
			
//			if(StringUtils.isNotBlank(f.getPreviousPassword()) && !f.getPreviousPassword().equals(member.getPassword())){
//				throw new UserPasswordAuthenticateException();
//			}
			
//			BeanUtils.copyProperties(member, f);
//			if(StringUtils.isNotBlank(f.getPreviousPassword()) && StringUtils.isNotBlank(f.getNewPassword())){
//				member.setPassword(f.getNewPassword());
//			}
			//modified at 2007-03-07 ,because bean copy may have some problem ,which may cause member code be modified.
			member.setFirstName(f.getFirstName());
			member.setMiddleName(f.getMiddleName());
			member.setLastName(f.getLastName());
			member.setBirthDate(f.getBirthDate());
			member.setCity(f.getCity());
			member.setState(f.getState());
			member.setCountry(f.getCountry());
			member.setPostCode(f.getPostCode());
			member.setModifiedDate(Calendar.getInstance());
			member.setAddress1(f.getStreet1());
			member.setAddress2(f.getStreet2());
			
			MemberServiceUtil.updateAGMember(userId,member);	
			
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
	
	private void validateForm(MyProfileForm f) throws Exception {
		// TODO Auto-generated method stub
		
		if(StringUtils.isBlank(f.getFirstName())){
			throw new ContactFirstNameException();
		}
		if(StringUtils.isBlank(f.getLastName())){
			throw new ContactLastNameException();
		}
//		if(!Validator.isEmailAddress(f.getEmailAddress())){
//			throw new UserEmailAddressException();
//		}
//		if(!f.getEmailAddress().equals(f.getEmailAddressCfm())){
//			throw new UserEmailAddressConfirmException();
//		}
//		if(StringUtils.isBlank(f.getBirthDate()) || f.getBirthDate().length() != 4){
//			throw new UserBirthdayDateException();
//		}
//		if(!StringUtils.isNumeric(f.getBirthDate())){
//			throw new UserBirthdayDateException();
//		}
//		int year = Integer.parseInt(f.getBirthDate());
//		if(year < 1900 || year > Calendar.getInstance().get(Calendar.YEAR)){
//			throw new UserBirthdayDateException();
//		}
		
//		if(StringUtils.isBlank(f.getStreet1()) && StringUtils.isBlank(f.getStreet2())){
//			throw new UserStreetException();
//		}
		if(StringUtils.isBlank(f.getCity())){
			throw new UserCityException();
		}
		if(StringUtils.isBlank(f.getState())){
			throw new UserStateException();
		}
		if(StringUtils.isBlank(f.getPostCode())){
			throw new UserPostCodeException();
		}
		if(StringUtils.isBlank(f.getCountry())){
			throw new UserCountryException();
		}
//		if(StringUtils.isNotBlank(f.getPreviousPassword()) && StringUtils.isNotBlank(f.getNewPassword()) && !ValidateUtil.isPassword(f.getNewPassword())){
//			throw new UserNewPasswordException();
//		}
//		if(StringUtils.isNotBlank(f.getPreviousPassword()) && StringUtils.isNotBlank(f.getNewPassword()) && !f.getNewPassword().equals(f.getNewPasswordCfm())){
//			throw new UserPasswordConfirmException();
//		}
		if(StringUtils.isNotBlank(f.getBirthDate())){
			if(!ValidateUtil.isValidateDate(f.getBirthDate())){
				throw new DateFormatInvalidException();
			}
//			try {
//				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//				df.parse(f.getBirthDate());
//			} catch(ParseException pe) {
//				throw new DateFormatInvalidException();
//			}
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
			return mapping.findForward("portlet.my_profile.my_profile");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		
		User user = themeDisplay.getUser();
		String userId = user.getUserId();
		
//		if(StringUtils.isBlank(userId) || userId.endsWith(Constants.DEFAULT_VIEW_USER_ID_SUFFIX) || userId.endsWith(Constants.DEFAULT_COMPANY_ID)){
//			SessionErrors.add(req, PrincipalException.class.getName(), new PrincipalException());
//			return mapping.findForward("portal.login");
//			
//		}
//		if(!themeDisplay.isSignedIn()){
//			return mapping.findForward("portlet.my_profile.my_profile");
//			SessionErrors.add(req, PrincipalException.class.getName(), new PrincipalException());
//			return mapping.findForward("portal.login");
//		}
		MyProfileForm f = (MyProfileForm)form;
		if(f != null && StringUtils.isBlank(f.getMemberCode())){
			AGMember member = MemberServiceUtil.getAGMemberByUserId(userId);
			//modified at 2007-03-13
			if(member != null){
				f.setBirthDate(member.getBirthDate());
				f.setCity(member.getCity());
				f.setCountry(member.getCountry());
				f.setFirstName(member.getFirstName());
				f.setLastName(member.getLastName());
				f.setMemberCode(member.getMemberCode());
				f.setMiddleName(member.getMiddleName());
				f.setPostCode(member.getPostCode());
				f.setState(member.getState());
				f.setStreet1(member.getAddress1());
				f.setStreet2(member.getAddress2());
			}
//			BeanUtils.copyProperties(f, member);
//			f.setAddress1(f.getStreet1());
//			f.setAddress2(f.getStreet2());
		}


		//res.setTitle(LanguageUtil.get(user, "my profile"));
		return mapping.findForward("portlet.my_profile.my_profile");
		
	}

}

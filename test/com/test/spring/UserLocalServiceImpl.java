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

package com.test.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.agloco.model.AGMember;
import com.agloco.model.AGMemberTemp;
import com.agloco.service.dao.util.MemberDaoUtil;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.service.persistence.ContactUtil;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.UserIdGenerator;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.Time;
import com.liferay.util.Validator;
/**
 * <a href="UserServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 *
 */
public class UserLocalServiceImpl extends com.liferay.portal.service.impl.UserLocalServiceImpl {

	public User addUser(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId)
		throws PortalException, SystemException {

		return addUser(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, true);
	}

	
	public User addUserMemberTemp(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			AGMemberTemp agMemberTemp)
		throws PortalException, SystemException {

		return addUserMemberTemp(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, true,agMemberTemp);
	}
	
	public User addUser(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			boolean sendEmail)
		throws PortalException, SystemException {

		// User

		userId = userId.trim().toLowerCase();
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		boolean alwaysAutoUserId = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.USERS_ID_ALWAYS_AUTOGENERATE));

		if (alwaysAutoUserId) {
			autoUserId = true;
		}

		validate(
			companyId, autoUserId, userId, autoPassword, password1, password2,
			emailAddress, firstName, lastName, organizationId, locationId);

		validateOrganizations(companyId, organizationId, locationId);

		if (autoUserId) {
			UserIdGenerator userIdGenerator = (UserIdGenerator)InstancePool.get(
				PropsUtil.get(PropsUtil.USERS_ID_GENERATOR));

			try {
				userId = userIdGenerator.generate(companyId);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		if (autoPassword) {
			password1 = PwdToolkitUtil.generate();
		}

		int passwordsLifespan = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.PASSWORDS_LIFESPAN));

		Date expirationDate = null;

		if (passwordsLifespan > 0) {
			expirationDate = new Date(
				System.currentTimeMillis() + Time.DAY * passwordsLifespan);
		}

		User defaultUser = getDefaultUser(companyId);

		String fullName = User.getFullName(firstName, middleName, lastName);

		String greeting = null;

		try {
			greeting =
				LanguageUtil.get(companyId, locale, "welcome") + ", " +
					fullName + "!";
		}
		catch (LanguageException le) {
			greeting = "Welcome, " + fullName + "!";
		}

		User user = UserUtil.create(userId);

		user.setCompanyId(companyId);
		user.setCreateDate(now);
		user.setModifiedDate(now);
		user.setPassword(PwdEncryptor.encrypt(password1));
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordExpirationDate(expirationDate);
		user.setPasswordReset(passwordReset);
		user.setEmailAddress(emailAddress);
		user.setLanguageId(locale.toString());
		user.setTimeZoneId(defaultUser.getTimeZoneId());
		user.setGreeting(greeting);
		user.setResolution(defaultUser.getResolution());
		user.setActive(true);

		UserUtil.update(user);

		// Resources

		if (creatorUserId == null) {
			creatorUserId = user.getUserId();
		}

		ResourceLocalServiceUtil.addResources(
			companyId, null, creatorUserId, User.class.getName(),
			user.getPrimaryKey().toString(), false, false, false);

		// Mail

		if (user.hasCompanyMx()) {
			com.liferay.mail.service.spring.MailServiceUtil.addUser(
				userId, password1, firstName, middleName, lastName,
				emailAddress);
		}

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		String contactId = userId;

		Contact contact = ContactUtil.create(contactId);

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(user.getUserId());
		contact.setUserName(User.getFullName(firstName, middleName, lastName));
		contact.setCreateDate(now);
		contact.setModifiedDate(now);
		contact.setAccountId(user.getCompanyId());
		contact.setParentContactId(Contact.DEFAULT_PARENT_CONTACT_ID);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setNickName(nickName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setJobTitle(jobTitle);

		ContactUtil.update(contact);

		// Organization and location

		UserUtil.clearOrganizations(userId);

		if (Validator.isNotNull(organizationId)) {
			UserUtil.addOrganization(userId, organizationId);
		}

		if (Validator.isNotNull(locationId)) {
			UserUtil.addOrganization(userId, locationId);
		}

		// Group

		GroupLocalServiceUtil.addGroup(
			user.getUserId(), User.class.getName(),
			user.getPrimaryKey().toString(), null, null, null, null);

		// Default groups

		List groups = new ArrayList();

		String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_GROUP_NAMES);

		for (int i = 0; i < defaultGroupNames.length; i++) {
			try {
				Group group = GroupFinder.findByC_N(
					companyId, defaultGroupNames[i]);

				groups.add(group);
			}
			catch (NoSuchGroupException nsge) {
			}
		}

		UserUtil.setGroups(userId, groups);

		// Default roles

		List roles = new ArrayList();

		String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_ROLE_NAMES);

		for (int i = 0; i < defaultRoleNames.length; i++) {
			try {
				Role role = RoleFinder.findByC_N(
					companyId, defaultRoleNames[i]);

				roles.add(role);
			}
			catch (NoSuchRoleException nsge) {
			}
		}

		UserUtil.setRoles(userId, roles);

		// Email
//
//		if (sendEmail) {
//			sendEmail(user, password1);
//		}

		
		return user;
	}

	public User addUserMemberTemp(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			boolean sendEmail,AGMemberTemp agMemberTemp)
		throws PortalException, SystemException {

		// User

		userId = userId.trim().toLowerCase();
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		boolean alwaysAutoUserId = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.USERS_ID_ALWAYS_AUTOGENERATE));

		if (alwaysAutoUserId) {
			autoUserId = true;
		}

		validate(
			companyId, autoUserId, userId, autoPassword, password1, password2,
			emailAddress, firstName, lastName, organizationId, locationId);

		validateOrganizations(companyId, organizationId, locationId);

		if (autoUserId) {
			UserIdGenerator userIdGenerator = (UserIdGenerator)InstancePool.get(
				PropsUtil.get(PropsUtil.USERS_ID_GENERATOR));

			try {
				userId = userIdGenerator.generate(companyId);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		if (autoPassword) {
			password1 = PwdToolkitUtil.generate();
		}

		int passwordsLifespan = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.PASSWORDS_LIFESPAN));

		Date expirationDate = null;

		if (passwordsLifespan > 0) {
			expirationDate = new Date(
				System.currentTimeMillis() + Time.DAY * passwordsLifespan);
		}

		User defaultUser = getDefaultUser(companyId);

		String fullName = User.getFullName(firstName, middleName, lastName);

		String greeting = null;

		try {
			greeting =
				LanguageUtil.get(companyId, locale, "welcome") + ", " +
					fullName + "!";
		}
		catch (LanguageException le) {
			greeting = "Welcome, " + fullName + "!";
		}

		User user = UserUtil.create(userId);

		user.setCompanyId(companyId);
		user.setCreateDate(now);
		user.setModifiedDate(now);
		user.setPassword(PwdEncryptor.encrypt(password1));
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordExpirationDate(expirationDate);
		user.setPasswordReset(passwordReset);
		user.setEmailAddress(emailAddress);
		user.setLanguageId(locale.toString());
		user.setTimeZoneId(defaultUser.getTimeZoneId());
		user.setGreeting(greeting);
		user.setResolution(defaultUser.getResolution());
		user.setActive(true);

		UserUtil.update(user);

		// Resources

		if (creatorUserId == null) {
			creatorUserId = user.getUserId();
		}

		ResourceLocalServiceUtil.addResources(
			companyId, null, creatorUserId, User.class.getName(),
			user.getPrimaryKey().toString(), false, false, false);

		// Mail

		if (user.hasCompanyMx()) {
			com.liferay.mail.service.spring.MailServiceUtil.addUser(
				userId, password1, firstName, middleName, lastName,
				emailAddress);
		}

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		String contactId = userId;

		Contact contact = ContactUtil.create(contactId);

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(user.getUserId());
		contact.setUserName(User.getFullName(firstName, middleName, lastName));
		contact.setCreateDate(now);
		contact.setModifiedDate(now);
		contact.setAccountId(user.getCompanyId());
		contact.setParentContactId(Contact.DEFAULT_PARENT_CONTACT_ID);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setNickName(nickName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setJobTitle(jobTitle);

		ContactUtil.update(contact);

		// Organization and location

		UserUtil.clearOrganizations(userId);

		if (Validator.isNotNull(organizationId)) {
			UserUtil.addOrganization(userId, organizationId);
		}

		if (Validator.isNotNull(locationId)) {
			UserUtil.addOrganization(userId, locationId);
		}

		// Group

		GroupLocalServiceUtil.addGroup(
			user.getUserId(), User.class.getName(),
			user.getPrimaryKey().toString(), null, null, null, null);

		// Default groups

		List groups = new ArrayList();

		String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_GROUP_NAMES);

		for (int i = 0; i < defaultGroupNames.length; i++) {
			try {
				Group group = GroupFinder.findByC_N(
					companyId, defaultGroupNames[i]);

				groups.add(group);
			}
			catch (NoSuchGroupException nsge) {
			}
		}

		UserUtil.setGroups(userId, groups);

		// Default roles

		List roles = new ArrayList();

		String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_ROLE_NAMES);

		for (int i = 0; i < defaultRoleNames.length; i++) {
			try {
				Role role = RoleFinder.findByC_N(
					companyId, defaultRoleNames[i]);

				roles.add(role);
			}
			catch (NoSuchRoleException nsge) {
			}
		}

		UserUtil.setRoles(userId, roles);

		//add memberTemp
		agMemberTemp.setUserId(user.getUserId());
//		agMemberTemp.getAgUser().setCompanyId(user.getCompanyId());
//		agMemberTemp.getAgUser().setUserId(user.getUserId());
//		agMemberTemp.getAgUser().setPassword(user.getPassword());
//		agMemberTemp.getAgUser().setActive(user.getActive() == true ? 1:0);
//		agMemberTemp.getAgUser().setAgreedToTermsOfUse(user.getAgreedToTermsOfUse() == true ? 1:0);
		MemberDaoUtil.addAGMemberTemp(agMemberTemp);
		MailServiceUtil.sendSignupMail(agMemberTemp);
		
		return user;
	}

	public User authenticate(String login, String password)
		throws Exception {
		AGMember member = MemberServiceUtil.authenticate(login, password);
		return super.getUserById(member.getUserId());
	}

}
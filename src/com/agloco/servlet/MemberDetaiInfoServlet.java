package com.agloco.servlet;

import javax.mail.internet.InternetAddress;

import org.apache.commons.beanutils.BeanUtils;

import com.agloco.mail.MailMessage;
import com.agloco.model.AGMember;
import com.agloco.model.AGMemberTemp;
import com.agloco.model.MemberDetailInfo;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;

public class MemberDetaiInfoServlet {
	private final static String COMPANY_ID = "agloco.com";

	public static MemberDetailInfo getMemberDetailInfo(String userId)
			throws Exception {

		MemberDetailInfo memberDetailInfo = new MemberDetailInfo();
		try {
			User user = UserLocalServiceUtil.getUserById(userId);
			//			
			// AGUser u = MemberServiceUtil.getAGUser(userId);
//			BeanUtils.copyProperties(memberDetailInfo, user);

			memberDetailInfo.setLoginDate(user.getLoginDate());
			memberDetailInfo.setLastLoginDate(user.getLastLoginDate());
			memberDetailInfo.setLastLoginIP(user.getLastLoginIP());
			memberDetailInfo.setLoginIP(user.getLoginIP());

			AGMemberTemp memberTemp = MemberServiceUtil
					.getAGMemberTempByUserId(userId);

			if (memberTemp == null) {
				AGMember member = MemberServiceUtil.getAGMemberByUserId(userId);
				BeanUtils.copyProperties(memberDetailInfo, member);
				memberDetailInfo.setTempMember(false);
			} else {
				BeanUtils.copyProperties(memberDetailInfo, memberTemp);
				memberDetailInfo.setTempMember(true);
			}
		} catch (Exception e) {
			
		}
		return memberDetailInfo;
	}

	public static boolean sendMail(String mailSubject, String mailBody,
			String userId) {

		String toAddress = "";

		String fromName = "";
		String fromAddress = "";
		try {
			fromName = PrefsPropsUtil.getString(COMPANY_ID,
					PropsUtil.ADMIN_EMAIL_FROM_NAME);
			fromAddress = PrefsPropsUtil.getString(COMPANY_ID,
					PropsUtil.ADMIN_EMAIL_FROM_ADDRESS);
			AGMemberTemp memberTemp = MemberServiceUtil
					.getAGMemberTempByUserId(userId);
			if (memberTemp == null) {
				AGMember member = MemberServiceUtil.getAGMemberByUserId(userId);
				toAddress = member.getEmailAddress();
			} else {
				toAddress = memberTemp.getEmailAddress();
			}
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			MailMessage message = new MailMessage(from, null, mailSubject,
					mailBody, false);
			message.setRecipient(toAddress);
			MailServiceUtil.sendMail(message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}

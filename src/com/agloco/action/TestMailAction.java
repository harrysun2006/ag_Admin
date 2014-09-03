package com.agloco.action;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.agloco.Constants;
import com.agloco.exception.CannotCatchedException;
import com.agloco.form.TestMailForm;
import com.agloco.mail.MailEngine;
import com.agloco.mail.MailMessage;
import com.agloco.model.AGMember;
import com.agloco.service.MailService;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.MailUtil;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.spring.JournalArticleLocalServiceUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

public class TestMailAction extends PortletAction {

	private final static String[] ALL_EMAIL_ARTICLES = {
		Constants.ARTICLEID_AGS_CHANGE_EMAIL_ADDRESS_EMAIL,
		Constants.ARTICLEID_AGS_CHANGE_PASSWORD_EMAIL,
		Constants.ARTICLEID_AGS_FIRST_SIGNIN_EMAIL,
		Constants.ARTICLEID_AGS_FORGOT_MEMBERCODE_EMAIL,
		Constants.ARTICLEID_AGS_FORGOT_PASSWORD_EMAIL,
		Constants.ARTICLEID_AGS_SIGNUP_EMAIL,
		Constants.ARTICLEID_AGS_SIGNUP_EMAIL_TEMP1,
		"AGS_SIGNUP_EMAIL_TEMP2",
		"AGS_SIGNUP_EMAIL_TEMP3",
		"AGS_SIGNUP_EMAIL_TEMP4",
		Constants.ARTICLEID_AGS_SIGNUP_EMAIL_TIMING,
		"AGS_TEST_EMAIL",
	};

	private final static String[] ALL_CHARSETS = {
		"utf-8",
		"gb2312",
	};

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {
		TestMailForm tm = (TestMailForm) form;
		try {
			MailService mailService = MailServiceUtil.getMailService();
			mailService.setExcluder(null);
			List result = new ArrayList();
			if(tm.getTab() == 1) {
				String toAddress = MailUtil.getAddressText(new InternetAddress(tm.getTo()));
				AGMember m = MemberServiceUtil.getAGMemberByEmail(toAddress);
				if(m == null) m = MemberServiceUtil.getAGMemberByCode("AGLOTEST");
				if(m == null) {
					m = new AGMember();
					m.setUserId("agloco.com.99");
					m.setMemberCode("AMAXTEST");
					m.setPassword("AGLOCO");
					m.setEmailAddress(toAddress);
					m.setFirstName("Receiver");
					m.setLastName("Test");
				}
				RenderRequestImpl renderReqImpl = (RenderRequestImpl)req;
				HttpServletRequest httpReq = (HttpServletRequest) renderReqImpl.getHttpServletRequest();
				TestMailForm temp;
				String[] articleIds = tm.getArticles();
				String[] names = {"SEND_IP",};
				String[] values = {httpReq.getLocalAddr(),};
				for(int i = 0; i < articleIds.length; i++) {
					temp = new TestMailForm();
					temp.setArticleId(articleIds[i]);
					try {
						mailService.sendMail(m, null, articleIds[i], names, values);
						temp.setSuccess(Boolean.TRUE);
					} catch(Exception e) {
						temp.setSuccess(Boolean.FALSE);
					}
					result.add(temp);
				}
			} else if(tm.getTab() == 2) {
				try {
					MailMessage message = new MailMessage();
					message.setFrom(tm.getFrom());
					message.setRecipient(tm.getTo());
					message.setCarbonCopy(tm.getCc());
					message.setBackgroundCopy(tm.getBcc());
					message.setSubject(tm.getSubject());
					message.setContent(tm.getContent());
					message.setCharset(tm.getCharset());
					mailService.sendMail(message);
					tm.setSuccess(Boolean.TRUE);
				} catch(Exception e) {
					tm.setSuccess(Boolean.FALSE);
				}
				result.add(tm);
			}
			req.setAttribute("result", result);
			SessionMessages.add(req, "request_processed");
		} catch(Exception e) {
			if (e instanceof AddressException) {
				e = new UserEmailAddressException(e);
				SessionErrors.add(req, e.getClass().getName(), e);
			} else {
				SessionErrors.add(req, CannotCatchedException.class.getName());
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		String companyId = themeDisplay.getCompanyId();
		List articles = new ArrayList();
		JournalArticle article;
		for(int i = 0; i < ALL_EMAIL_ARTICLES.length; i++) {
			try {
				article = JournalArticleLocalServiceUtil.getArticle(companyId, ALL_EMAIL_ARTICLES[i]);
				if(article == null || article.isExpired() || !article.isApproved()) continue;
				articles.add(article);
			} catch(Exception e) {
			} 
		}
		loadTestMail(mapping, form, req, res);
		req.setAttribute("allArticles", articles);
		req.setAttribute("allCharsets", ALL_CHARSETS);
		return mapping.findForward("portlet.testmail.view");
	}


	private void loadTestMail(ActionMapping mapping, ActionForm form, RenderRequest req, RenderResponse res) throws Exception {
		TestMailForm tm = (TestMailForm) form;
		ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		String companyId = themeDisplay.getCompanyId();
		String fromName = PrefsPropsUtil.getString(companyId, PropsUtil.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(companyId, PropsUtil.ADMIN_EMAIL_FROM_ADDRESS);
		tm.setFrom(MailUtil.getAddressText(new InternetAddress(fromAddress, fromName)));
		Session session = MailEngine.getSession();
		String protocol = session.getProperty("mail.transport.protocol");
		String host = session.getProperty("mail." + protocol + ".host");
		tm.setProtocol(protocol);
		tm.setHost(host);
	}
}

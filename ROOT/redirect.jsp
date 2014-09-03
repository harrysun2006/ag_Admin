<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Locale"%>
<%@page import="org.apache.struts.Globals" %>
<%@page import="com.agloco.AglocoURL"%>
<%@page import="com.agloco.Constants"%>
<%@page import="com.liferay.portal.language.LanguageUtil"%>
<%@page import="com.liferay.portal.util.PropsUtil"%>
<%@page import="com.liferay.util.GetterUtil"%>
<%@page import="com.liferay.util.Http"%>
<%@page import="com.liferay.util.ListUtil"%>
<%@page import="com.liferay.util.LocaleUtil"%>
<%@page import="com.liferay.util.StringPool"%>
<%@page import="com.liferay.util.Validator"%>
<%
	String referralCode = request.getParameter("referralCode");
	String languageId = request.getParameter("lang");
	if(languageId == null) languageId = "";
	StringBuffer redirectURL = new StringBuffer();
	if(Validator.isNotNull(referralCode)) {
		try {
			Cookie signupCookie = new Cookie(Constants.COOKIE_SIGN_UP, referralCode);
			signupCookie.setPath("/");
			if (GetterUtil.getBoolean(PropsUtil.get(PropsUtil.SESSION_ENABLE_PERSISTENT_COOKIES))) {
				if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.TCK_URL))) {
					response.addCookie(signupCookie);
				} else {
					session.setAttribute(Constants.SESSION_REFERRAL_CODE, referralCode);
				}
			} else {
				session.setAttribute(Constants.SESSION_REFERRAL_CODE, referralCode);	
			}
	
			Locale locale = LocaleUtil.fromLanguageId(languageId);
			List availableLocales = ListUtil.fromArray(LanguageUtil.getAvailableLocales());
	
			if (availableLocales.contains(locale)) {
				session.setAttribute(Globals.LOCALE_KEY, locale);
				LanguageUtil.updateCookie(response, locale);
			}
			
			//add at 2007-03-12 by Terry
			StringBuffer servletPath = new StringBuffer();
			servletPath.append(request.getServletPath() == null ? "" : request.getServletPath());
			servletPath.append(request.getPathInfo() == null ? "" : request.getPathInfo());
			session.setAttribute("servletPath", servletPath.toString());

			String serverName = request.getServerName();
	
			redirectURL.append(Http.HTTPS_WITH_SLASH);
			redirectURL.append(serverName);
	
			if (request.getServerPort() == 8080) {
				redirectURL.append(StringPool.COLON);
				redirectURL.append("8443");
			}

			redirectURL.append(AglocoURL.SIGNUP);

			redirectURL
				.append(StringPool.QUESTION)
				.append("referralCode=").append(URLEncoder.encode(referralCode, "UTF-8"))
				.append(StringPool.AMPERSAND).append("lang=").append(URLEncoder.encode(languageId, "UTF-8"));
		} catch(Exception e) {
			redirectURL.append(AglocoURL.SIGNUP);
		}
	} else {
		redirectURL.append("/");
	}
	// System.out.println(redirectURL.toString());
%>
<html>
<body>
<script language="javascript">
<!--
self.location.replace("<%= redirectURL.toString() %>");
//-->
</script>
</body>
</html>
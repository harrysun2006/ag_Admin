
<%@ include file="/html/portal/init.jsp" %>
	
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://easyconf.sourceforge.net/tags-easyconf" prefix="easyconf" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portlet.LiferayWindowState" %>
<%@ page import="javax.portlet.WindowState" %>
<%@ page import="com.liferay.portal.language.LanguageUtil" %>
<%@ page import="com.liferay.portlet.PortletURLUtil" %>

<%@ page import="com.agloco.form.MyProfileChangePasswordForm" %>


<%@ page import="com.agloco.exception.NoSuchUserExistsException" %>
<%@ page import="com.liferay.portal.captcha.CaptchaTextException" %>
<%@ page import="com.liferay.portal.ContactFirstNameException" %>
<%@ page import="com.liferay.portal.ContactLastNameException" %>
<%@ page import="com.liferay.portal.DuplicateUserEmailAddressException" %>
<%@ page import="com.liferay.portal.DuplicateUserIdException" %>
<%@ page import="com.liferay.portal.ReservedUserEmailAddressException" %>
<%@ page import="com.liferay.portal.ReservedUserIdException" %>
<%@ page import="com.liferay.portal.UserEmailAddressException" %>
<%@ page import="com.liferay.portal.UserIdException" %>
<%@ page import="com.agloco.exception.UserBirthdayDateException" %>
<%@ page import="com.agloco.exception.UserCityException" %>
<%@ page import="com.agloco.exception.UserCountryException" %>
<%@ page import="com.agloco.exception.UserPostCodeException" %>
<%@ page import="com.agloco.exception.UserStateException" %>
<%@ page import="com.agloco.exception.ReferralCodeException" %>
<%@ page import="com.agloco.exception.DuplicateEmailAddressException" %>
<%@ page import="com.agloco.exception.UserEmailAddressConfirmException" %>
<%@ page import="com.agloco.exception.UserNewPasswordException" %>
<%@ page import="com.agloco.exception.UserPasswordConfirmException" %>
<%@ page import="com.agloco.exception.UserPasswordAuthenticateException" %>
<%@page import="com.agloco.exception.CannotCatchedException"%>
<%@ page import="com.agloco.AglocoURL" %>

<link rel="stylesheet" type="text/css" media="all" href="/html/agloco/css/calendar/calendar-win2k-cold-2.css" />
<script type="text/javascript" src="/html/agloco/js/calendar/calendar.js"></script>
<script type="text/javascript" src="/html/agloco/js/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="/html/agloco/js/calendar/lang/calendar-en.js"></script>

<script type="text/javascript">
<!--
	redirectToLogin();
//-->
</script>
<%
	MyProfileChangePasswordForm agMember = (MyProfileChangePasswordForm)request.getAttribute("myProfileChangePasswordForm");
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/my_profile/my_profile_change_password" /></portlet:actionURL>" method="post" name="myProfileChangePasswordForm">


 <center> 
  <table width="790"  border="0" cellpadding="0" cellspacing="0" bgcolor="white">
   <tr>
   	<td colspan="3">
	   	<liferay-ui:error exception="<%= NoSuchUserExistsException.class %>" message="ag-referral-is-not-existed" />
	   	<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="ag-text-verification-failed" />
		<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="ag-please-enter-a-valid-first-name" />
		<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="ag-please-enter-a-valid-last-name" />
		<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="ag-the-user-id-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="ag-the-user-id-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="ag-please-enter-a-valid-email-address" />
		<liferay-ui:error exception="<%= UserIdException.class %>" message="ag-please-enter-a-valid-user-id" />
		<liferay-ui:error exception="<%= UserBirthdayDateException.class %>" message="ag-please-enter-a-valid-birthday-date" />
		<liferay-ui:error exception="<%= UserCityException.class %>" message="ag-please-enter-a-valid-city" />
		<liferay-ui:error exception="<%= UserCountryException.class %>" message="ag-please-enter-a-valid-country" />
		<liferay-ui:error exception="<%= UserPostCodeException.class %>" message="ag-please-enter-a-valid-post-code" />
		<liferay-ui:error exception="<%= UserStateException.class %>" message="ag-please-enter-a-valid-state" />
		<liferay-ui:error exception="<%= ReferralCodeException.class %>" message="ag-please-enter-a-valid-referral-code" />
		<liferay-ui:error exception="<%= DuplicateEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= UserEmailAddressConfirmException.class %>" message="ag-please-enter-a-valid-confirm-email-address" />
		<liferay-ui:error exception="<%= UserNewPasswordException.class %>" message="ag-my-profile-please-input-validate-new-password" />
		<liferay-ui:error exception="<%= UserPasswordConfirmException.class %>" message="ag-my-profile-please-input-validate-new-password-confirm" />
		<liferay-ui:error exception="<%= UserPasswordAuthenticateException.class %>" message="ag-my-profile-please-input-validate-previous-password" />
   	</td>
   </tr>
    <tr bgcolor="#FFFFFF">
      <td align="center" valign="top">&nbsp; </td>
      <td align="center" valign="top"><br>
        <table width="760" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="8"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-spacer") %>" width="1" height="1"></td>
          </tr>
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td height="45" valign="top"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-my-profile-title-my-profile") %>" width="682" height="31"></td>
                </tr>
                <tr>
                  <td valign="top"><table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" class="k11">
                      <tr>
                        <td colspan="5" class="ag_r11">&nbsp;&nbsp;* <%=LanguageUtil.get(pageContext,"ag-required-fields") %></td>
                      </tr>
                       <tr>
                        <td class="ag_k12" align="right" width="230"><%=LanguageUtil.get(pageContext,"ag-my-profile-member-code") %>:</td>
                        <td colspan="4" class="ag_k12"> <%=agMember.getMemberCode()%>
                        	<input name="<portlet:namespace />memberCode" type="hidden"  value="<bean:write name="myProfileChangePasswordForm" property="memberCode"/>">
                        </td>
                      
                      </tr>
                      
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-my-profile-previous-password") %></span><span class="ag_r11">*</span></div></div>
                        </td>
                        <td colspan="4" class="ag_r10b">
                        	<input name="<portlet:namespace />previousPassword" type="password"  size="30" value="<bean:write name="myProfileChangePasswordForm" property="previousPassword"/>"/>
                        </td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-my-profile-new-password") %></span><span class="ag_r11">*</span></div></div>
                        </td>
                        <td colspan="4" class="ag_r10b">
                        	<input name="<portlet:namespace />newPassword" type="password"  size="30" value="<bean:write name="myProfileChangePasswordForm" property="newPassword"/>"/>
                        </td>
                      </tr>
                      
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-my-profile-new-password-confirm") %></span><span class="ag_r11">*</span></div></div>
                        </td>
                        <td colspan="4" class="ag_r10b">
                        	<input name="<portlet:namespace />newPasswordCfm" type="password"  size="30" value="<bean:write name="myProfileChangePasswordForm" property="newPasswordCfm"/>"/>
                        </td>
                      </tr>
                      
                      <tr>
                        <td class="ag_k12"><div align="right"></div></td>
                        <td colspan="1" class="ag_r10b">	
	                        <div align="left">
						        <portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
									<portlet:param name="struts_action" value="/my_profile/captcha" />
								</portlet:actionURL>
								<liferay-ui:captcha page="/html/agloco/taglib/ui/captcha/page.jsp" url="<%= captchaURL %>" />
							</div></td>
                          <td align="left">
					        <div>
					        <div class="ag_or11"><!-- webbot bot="HTMLMarkup" startspan -->
					            <!-- GeoTrust QuickSSL [tm] Smart Icon tag. Do not edit. -->
								<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="//smarticon.geotrust.com/si.js"></SCRIPT>
								<!-- end GeoTrust Smart Icon tag -->
								<!-- webbot bot="HTMLMarkup" endspan -->
					        </div>
					       </div>
					     </td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-enter-validate-code") %></span><span class="ag_r11">*</span></div>
                        </div></td>
                        <td colspan="4" class="ag_r10b"><input name="<portlet:namespace />captchaText" size="30" type="text" value=""></td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right"></div></td>
                        <td colspan="4"><a href="<%=AglocoURL.TERMS_OF_USE %>" class="ag_b11"><%= LanguageUtil.get(pageContext, "ag-terms-of-service") %></a></td>
                      </tr>
                      <tr>
            
                        <td colspan="5" class="ag_r10b"><div align="center">
                            <input type="button" class="ag_bt0" name="btn_submit" id="btn_submit" onClick="javascript:formSubmit();" value="<%= LanguageUtil.get(pageContext,"ag-my-profile-save") %>">
                        </div></td>
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
      <br></td>
      <td align="center" valign="top">&nbsp;</td>
    </tr>
    
  </table>
</center>

</form>
<script language="javascript">
<!--
function formSubmit()
{
	var f = document.forms["myProfileChangePasswordForm"];
	f.elements["btn_submit"].disabled = true;
	f.submit();
}
//-->
</script>


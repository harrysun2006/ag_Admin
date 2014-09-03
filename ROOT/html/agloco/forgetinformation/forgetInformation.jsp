
<%
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
%>

<%@ include file="/html/common/init.jsp"%>
<%@ page import="com.agloco.exception.NoSuchUserExistsException"%>
<%@ page import="com.agloco.exception.CountryInvalidException"%>
<%@ page import="com.agloco.exception.PostCodeInvalidException"%>
<%@ page import="com.agloco.exception.CountryIsNullExcetion"%>
<%@ page import="com.agloco.exception.NoPostCodeException"%>
<%@ page import="com.agloco.exception.MemberCodeNullException"%>
<%@ page import="com.agloco.exception.EmailAddressCodeNullException"%>
<%@ page import="com.liferay.portal.captcha.CaptchaTextException"%>
<%@ page import="com.agloco.action.SignInAction"%>
<%@ page import="com.liferay.portal.SendPasswordException"%>
<%@ page import="com.liferay.portal.UserEmailAddressException"%>
<%@ page import="com.agloco.exception.NoMemberMatchesMembercodeException"%>
<%@ page import="com.agloco.exception.NoMemberMatchesEamilAddressException"%>
<%@ page import="com.liferay.portal.NoSuchUserException"%>
<%@page import="com.agloco.exception.CannotCatchedException"%>
<%@ taglib uri="/WEB-INF/tld/agloco-util.tld" prefix="agloco-util" %>

<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<%
	String forgetType = ParamUtil.getString(request, "forgetType");
	String forgetTypeValue = ParamUtil.getString(request, "forgetTypeValue");
%>
<form
	action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/forgetinfo/forgetinfo" /></portlet:actionURL>"
	method="post" name="forgetPwdForm">
	<input name="<portlet:namespace />captchaText" type="hidden" value="">
	<input name="<portlet:namespace />forgetTypeValue" type="hidden" value="">
	<input name="<portlet:namespace />forgetType" type="hidden" value="memberCode">
<table width="790" border="0" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
		<td><br>
		<liferay-ui:error exception="<%= CaptchaTextException.class %>"
			message="ag-text-verification-failed" /> <liferay-ui:error exception="<%= CannotCatchedException.class %>"
			message="ag-can-not-catched-exception" /> <liferay-ui:error exception="<%= NoMemberMatchesMembercodeException.class %>"
			message="ag-no-member-matches-membercode" /> <liferay-ui:error exception="<%= NoMemberMatchesEamilAddressException.class %>"
			message="ag-no-member-matches-eamil-address" /> <liferay-ui:error
			exception="<%= NoSuchUserExistsException.class %>"
			message="ag-member-not-register" /> <liferay-ui:error
			exception="<%= MemberCodeNullException.class %>"
			message="ag-please-enter-membercode" /> <liferay-ui:error
			exception="<%= EmailAddressCodeNullException.class %>"
			message="ag-please-enter-emailaddress" /> <liferay-ui:error
			exception="<%= NoSuchUserException.class %>"
			message="the-email-address-you-requested-is-not-registered-in-our-database" /> <liferay-ui:error
			exception="<%= SendPasswordException.class %>"
			message="your-password-can-only-be-sent-to-an-external-email-address" /> <liferay-ui:error
			exception="<%= UserEmailAddressException.class %>"
			message="please-enter-a-valid-email-address" />
			 
		</td>
	</tr>
	<tr>
		<td align="center" valign="top" bgcolor="#FFFFFF"><br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="8"><img src="/html/agloco/images/spacer.gif"
					width="1" height="1"></td>
			</tr>
			<tr>
				<td valign="top" bgcolor="#c9cccf">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					bgcolor="#FFFFFF">
					<tr>
						<td height="38" align="center"><img
							src="<%=LanguageUtil.get(pageContext, "ag-pic-forget-info-title")%>" width="682"
							height="31"></td>
					</tr>
					<tr>
						<td valign="top">
						<table width="85%" border="0" align="center" cellpadding="2"
							cellspacing="0" class="ag_k11">
							<tr>
								<td height="30" colspan="4" class="ag_r11">
								<div><b><%=LanguageUtil.get(pageContext,
							"ag-choose-one-of-these-options")%></b></div>
								</td>
							</tr>
							<tr>
								<td colspan="3" class="ag_k11b">
								<table width="98%" border="0" align="center" cellpadding="1"
									cellspacing="1" bgcolor="#C2ECAE" class="ag_k11">
									<tr>
										<td bgcolor="#E1F6D7" class="ag_k14b">&nbsp;<%=LanguageUtil.get(pageContext, "ag-forgot-your-password")%>?</td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF" class="ag_k12">
										<table width="100%" border="0" cellspacing="0" cellpadding="3">
											<tr>
												<td>
												<div class="ag_k11">
												<div align="center"><%=LanguageUtil.get(pageContext,
							"ag-enter-your-account-number-or-emailaddress")%>
												:</div>
												</div>
												</td>
											</tr>
											<tr>
												<td>
												<div align="center"><input
													name="<portlet:namespace />forgetPwdValue" type="text" onclick="javascript:this.value=''"
													size="40" value="<%=com.agloco.Constants.MEMBERCODE.equals(forgetType) || forgetTypeValue.equals("")?LanguageUtil.get(pageContext, "ag-for-example")+": AGLO0034":forgetTypeValue%>"></div>
												</td>
											</tr>
											<tr>
												<td>
													<div align="center" class="ag_r11"><%= LanguageUtil.get(pageContext, "ag-enter-validate-code") %> *</div>
												</td>
											</tr>
											<tr>
												<td class="ag_k11b">
												<div align="center"><portlet:actionURL
													windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"
													var="captchaURL">
													<portlet:param name="struts_action"
														value="/forgetpassword/agcaptcha" />
												</portlet:actionURL> <agloco-util:captcha name="1"
													url="<%= captchaURL+"&captchaTextName=captchaText_1" %>" />
												</div>
												</td>
											</tr>
											<tr>
												<td class="ag_r10b" align="center"><input name="<portlet:namespace />captchaText_1" size="20" type="text" value=""></td>
											</tr>
											<tr>
												<td class="ag_or11">
												<div align="center"><a href="#"
													onMouseOut="MM_swapImgRestore()"
													onMouseOver="MM_swapImage('Image27','','<%=LanguageUtil.get(pageContext, "ag-pic-forget-info-send-password-b")%>',1)"
													onFocus="if(this.blur)this.blur()"> <img
													src="<%=LanguageUtil.get(pageContext, "ag-pic-forget-info-send-password-a")%>"
													name="Image27" id="Image27" width="210" height="35" border="0"
													onclick="javascript:submitForm('password')"> </a></div>
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
								<td width="50%" class="ag_k11b">
								<table width="98%" border="0" align="center" cellpadding="1"
									cellspacing="1" bgcolor="#C2ECAE" class="ag_k11">
									<tr>
										<td bgcolor="#E1F6D7" class="ag_k14b">&nbsp;<%=LanguageUtil.get(pageContext,
							"ag-forgot-your-account-number")%>?</td>
									</tr>
									<tr>
										<td bgcolor="#FFFFFF" class="ag_k12">
										<table width="100%" border="0" cellspacing="0" cellpadding="3">
											<tr>
												<td>
												<div class="ag_k11">
												<div align="center"><%=LanguageUtil.get(pageContext,
							"ag-enter-your-email-address")%>
												:</div>
												</div>
												</td>
											</tr>
											<tr>
												<td>
												<div align="center"><input value="<%=com.agloco.Constants.PASSWORD.equals(forgetType) || forgetTypeValue.equals("")?LanguageUtil.get(pageContext, "ag-for-example")+": yourname@abc.com":forgetTypeValue %>"
													name="<portlet:namespace />forgetMCValue" type="text" onclick="javascript:this.value=''"
													size="40"></div>
												</td>
											</tr>
											<tr>
												<td>
													<div align="center" class="ag_r11"><%= LanguageUtil.get(pageContext, "ag-enter-validate-code") %> *</div>
												</td>
											</tr>
											<tr>
												<td class="ag_k11b">
												<div align="center"><portlet:actionURL
													windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"
													var="captchaURL">
													<portlet:param name="struts_action"
														value="/forgetpassword/agcaptcha" />
												</portlet:actionURL> <agloco-util:captcha name="2"
													url="<%= captchaURL+"&captchaTextName=captchaText_2"  %>" /></div>
												</td>
											</tr>
											<tr>
												<td class="ag_r10b" align="center"><input name="<portlet:namespace />captchaText_2" size="20" type="text" value=""></td>
											</tr>
											<tr>
												<td class="ag_or11">
												<div align="center"><a href="#"
													onMouseOut="MM_swapImgRestore()"
													onMouseOver="MM_swapImage('Image28','','<%=LanguageUtil.get(pageContext, "ag-pic-forget-info-send-account-b")%>',1)"
													onFocus="if(this.blur)this.blur()"> <img
													src="<%=LanguageUtil.get(pageContext, "ag-pic-forget-info-send-account-a")%>"
													name="Image28" id="Image28" width="210" height="35" border="0"
													onclick="javascript:submitForm('memberCode')"> </a></div>
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td colspan="4" class="ag_k11b">&nbsp;</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<script>
function submitForm(forgetType)
{
	document.getElementById("Image27").disabled = true;
	document.getElementById("Image28").disabled = true;
	with(document.forgetPwdForm)
	{
		<portlet:namespace />forgetType.value=forgetType;
		if(forgetType=="memberCode")
		{
			<portlet:namespace />forgetTypeValue.value=<portlet:namespace />forgetMCValue.value;
			<portlet:namespace />captchaText.value="captchaText_2";
		}
		if(forgetType=="password")
		{
			<portlet:namespace />forgetTypeValue.value=<portlet:namespace />forgetPwdValue.value;
			<portlet:namespace />captchaText.value="captchaText_1";
		}
		submit();
	}
}
</script>

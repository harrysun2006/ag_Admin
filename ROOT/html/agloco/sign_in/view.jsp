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

<%@ include file="/html/agloco/sign_in/init.jsp" %>
<%@page import="com.agloco.action.SignInAction" %>
<%@page import="com.agloco.AglocoURL" %>
<%@page import="com.liferay.portal.model.Layout" %>
<%@page import="com.liferay.portal.util.WebKeys" %>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<form action="<%= themeDisplay.getPathMain() %>/portal/login" method="post" name="fm1">
	<%
		String login = SignInAction.getLogin(request, "login", company);
		String rememberMe = "".equals(login)? "false" : "true";
	%>
	<table width="790" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<input name="rememberMe" type="hidden" value="<%=rememberMe %>">
	<input name="isLoad" type="hidden" value="true">
		<tr>
			<td align="center" valign="top" bgcolor="#FFFFFF">
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="2" height="8" align="center">
							<img src="/html/agloco/images/title_signin.gif" width="682" height="31" border="0">
						</td>
					</tr>
					<tr>
						<td width="78%">
							<br>
							<table width="450" border="0" align="center" cellpadding="2" cellspacing="0" class="ag_k11">
								<tr>
									<td class="ag_k11b">
										<div align="right"><%=LanguageUtil.get(pageContext, "ag-loginname")%>:</div>
									</td>
									<td>
										<div align="right"><input name="login" size="25" type="text" value="<%= login %>"></div>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="ag_k11b">
										<div align="right"><%=LanguageUtil.get(pageContext, "password")%>:</div>
									</td>
									<td>
										<div align="right"><input name="<%= SessionParameters.get(request, "password") %>" size="25" type="password"></div>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div align="right">
											<%=LanguageUtil.get(pageContext, "remember-me")%>
											<label>
												<input type="checkbox" <% if(com.agloco.Constants.TRUE.equals(rememberMe)) out.print("checked"); %> onClick="
								if (this.checked) {
									document.fm1.rememberMe.value = 'true';
								}
								else {
									document.fm1.rememberMe.value = 'false';
								}">
											</label>
										</div>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<div align="right">
											<a href="<%= AglocoURL.FORGETINFO %>" class="ag_b11"><%=LanguageUtil.get(pageContext, "ag-forgot-your-info")%>?</a>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<div align="right">
										<%
										String languageName= locale.getLanguage();
										%>
										<%
												if (languageName.equals("zh")) {
													%>
												<a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image20','','/html/agloco/images/button_signinb_cn.jpg',1)"> <img src="/html/agloco/images/button_signina_cn.jpg" name="Image20" width="150" height="35" border="0"
													onclick="javascript:formSubmit();"> </a>												
													<%
												}else{
													%>
												<a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image20','','/html/agloco/images/button_signinb.jpg',1)"> <img src="/html/agloco/images/button_signina.jpg" name="Image20" width="150" height="35" border="0"
													onclick="javascript:formSubmit();"> </a>
											<%	}%>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<hr size=1 color=efefef>
									</td>
								</tr>
								<tr>
									<td>
										<div align="right" class="ag_k11b">
											<%=LanguageUtil.get(pageContext, "ag-not-a-member")%>
										</div>
									</td>
									<td>
										<div align="center">
											<a href="<%=AglocoURL.SIGNUP %>" class="ag_r11"><font color=red><u><%=LanguageUtil.get(pageContext, "ag-sign-up")%></u></font></a>
										</div>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="3">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
<script>
	function formSubmit()
	{
		document.fm1.isLoad.value='false'
		document.fm1.submit();
	}

	function onkey()
	{
		if (window.event.keyCode==13)
		{
			formSubmit();
		}
	}
</script>

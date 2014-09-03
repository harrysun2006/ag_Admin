<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.apache.struts.taglib.TagUtils" %>
<%@ page import="com.agloco.AglocoURL" %>
<%@ include file="/html/common/init.jsp"%>
<%@ taglib uri="/WEB-INF/tld/agloco-util.tld" prefix="agloco-util" %>
<SCRIPT language=JavaScript>
<!--
redirectToLogin();
//-->
</SCRIPT>
<bean:define id="member" name="member" type="com.agloco.model.AGMember"/>
<bean:define id="firstDate" name="firstDate" type="java.util.Date"/>
<bean:define id="rank" name="rank" type="java.lang.Double"/>
<%
	TagUtils tagUtils = TagUtils.getInstance();
	Date createDate = member.getCreateDateValue();
	DateFormat df = new SimpleDateFormat("MMM. dd, yyyy");
	String createDateString = (createDate == null) ? "" : df.format(createDate);
	df = new SimpleDateFormat("MMM. dd");
	String firstDateString = (firstDate == null) ? "" : df.format(firstDate);
	NumberFormat nf = new DecimalFormat("0.00%");
	String rankString = (rank.compareTo(new Double(0)) == 0) ? "0%" : nf.format(rank);
	String firstName = (member.getFirstName() == null) ? "" : member.getFirstName();
	String middleName = (member.getMiddleName() == null) ? "" : member.getMiddleName();
	String lastName = (member.getLastName() == null) ? "" : member.getLastName();
	String referralText = company.getPortalURL() + "/r/" + member.getMemberCode();
	String referralLink = AglocoURL.REFERRALCENTER + "#dd";
	Boolean displayDownloadLink = (Boolean)request.getAttribute("displayDownloadLink");
%>
<table width="790" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
<tr><td align="center">
<br>
<table width="760" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td width="100%" height="40" colspan="2" align="center"><img src="<%= LanguageUtil.get(pageContext, "ag-pic-my-account-title-my-account")%>" width="682" height="31"></td>
	</tr>
</table>
<table width="760" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
	<tr>
		<td width="25"></td>
		<td height="14"><img src="/html/agloco/images/061107_tb01.gif" width="600" height="14"></td>
		<td width="25"></td>
	</tr>
	<tr>
		<td><img src="/html/agloco/images/061107_tb02_0.gif" width="25" height="26"></td>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="40"><img src="/html/agloco/images/061107_tb02.gif" width="40" height="26"></td>
					<td width="132" align="center" class="ag_k14b">
						<%= tagUtils.message(pageContext, null, null, "ag-my-account-name", new Object[]{firstName, middleName, lastName}) %>
					</td>
					<td width="403" align="center" class="ag_k14b">
						<font color="#999999">[ <b><bean:write name="member" property="memberCode"/> </b>]</font>&nbsp;&nbsp;<%= tagUtils.message(pageContext, null, null, "ag-my-account-ms", new Object[]{createDateString}) %> 

					</td>
					<td><img src="/html/agloco/images/061107_tb03.gif" width="135" height="26"></td>
				</tr>
			</table>
		</td>
		<td><img src="/html/agloco/images/061107_tb04.gif" width="25" height="26"></td>
	</tr>
	<tr>
		<td valign="top" background="/html/agloco/images/061107_tb05b.gif"><img src="/html/agloco/images/061107_tb05.gif" width="25" height="378"></td>
		<td valign="top" background="/html/agloco/images/061107_tb06.gif" style="background-repeat:repeat-x" bgcolor="#e6e6e6">
			<table width="710" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="341">
						<br>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="9"><img src="/html/agloco/images/061107_tb11.gif" width="9" height="9"></td>
								<td bgcolor="#253A59"><img src="/html/agloco/images/061107_tb12.gif" width="9" height="9"></td>
								<td width="9"><img src="/html/agloco/images/061107_tb13.gif" width="9" height="9"></td>
							</tr>
							<tr>
								<td background="/html/agloco/images/061107_tb12.gif"><img src="/html/agloco/images/061107_tb12.gif" width="9" height="9"></td>
								<td bgcolor="#253A59">
									<table  width="352" border="0" cellspacing="4" cellpadding="0">
										<tr align="center">
											<td width="60" height="50">
												&nbsp;
											</td>
											<td width="64" class="ag_w11b">
												<!--img src="/html/agloco/images/061107_tb_members.gif" width="77" height="50"-->
												<%= LanguageUtil.get(pageContext, "ag-my-account-members") %>
											</td>
											<td  width="62" class="ag_w11b">
												<!--img src="/html/agloco/images/061107_tb_newsince.gif" width="77" height="50"-->
												<%= tagUtils.message(pageContext, null, null, "ag-my-account-newsince", new Object[]{firstDateString}) %>
											</td>
											<td width="64" class="ag_w11b">
												<!--img src="/html/agloco/images/061107_tb_hours.gif" width="77" height="50"-->
												<%= LanguageUtil.get(pageContext, "ag-my-account-hours") %>
											</td>
											<td width="78" class="ag_w11b">
												<%= LanguageUtil.get(pageContext, "ag-my-account-total-hours") %>
											</td>
										</tr>
										<tr align="center">
											<td><img src="<%= LanguageUtil.get(pageContext, "ag-pic-my-account-button-you")%>" width="60" height="50"></td>
											<logic:iterate id="row0" name="ROWS0">
											<td background="/html/agloco/images/061107_tb16.gif" class="ag_k14b">
												<logic:present name="row0"><bean:write name="row0"/></logic:present>
												<logic:notPresent name="row0">&nbsp;</logic:notPresent>
											</td>
											</logic:iterate>
										</tr>
										<tr align="center">
											<td><img src="<%= LanguageUtil.get(pageContext, "ag-pic-my-account-button-direct")%>" width="60" height="50"></td>
											<logic:iterate id="row1" name="ROWS1">
											<td background="/html/agloco/images/061107_tb16.gif" class="ag_k14b">
												<logic:present name="row1"><bean:write name="row1"/></logic:present>
												<logic:notPresent name="row0">&nbsp;</logic:notPresent>
											</td>
											</logic:iterate>
										</tr>
										<tr align="center">
											<td><img src="<%= LanguageUtil.get(pageContext, "ag-pic-my-account-button-extended")%>" width="60" height="50"></td>
											<logic:iterate id="row2" name="ROWS2">
											<td background="/html/agloco/images/061107_tb16.gif" class="ag_k14b"><bean:write name="row2"/></td>
											</logic:iterate>
										</tr>
										<tr align="center">
											<td><img src="<%= LanguageUtil.get(pageContext, "ag-pic-my-account-button-total")%>" width="60" height="50"></td>
											<logic:iterate id="row3" name="ROWS3">
											<td background="/html/agloco/images/061107_tb16.gif" class="ag_k14b"><bean:write name="row3"/></td>
											</logic:iterate>
										</tr>
									</table>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="5" colspan="3"><img width="1" height="1"></td>
										</tr>
										<tr>
											<td width="9%"><img src="/html/agloco/images/061107_tb17.gif" width="30" height="32"></td>
											<td width="90%" align="center" background="/html/agloco/images/061107_tb18.gif" class="ag_k11">
												<marquee scrollAmount=2 onmouseover=stop() onmouseout=start()>
													<%= tagUtils.message(pageContext, null, null, "ag-my-account-rank", new Object[]{rankString})%>
												</marquee>
											</td>
											<td width="1%"><img src="/html/agloco/images/061107_tb19.gif" width="12" height="32"></td>
										</tr>
										<!--
										 <tr>
											<td width="9%">&nbsp;</td>
											<td width="90%" align="center" class="ag_k11">&nbsp;</td>
											<td width="1%">&nbsp;</td>
										</tr>
										 -->
									</table>
								</td>
								<td background="/html/agloco/images/061107_tb12.gif"><img src="/html/agloco/images/061107_tb12.gif" width="9" height="9"></td>
							</tr>
							<tr>
								<td><img src="/html/agloco/images/061107_tb14.gif" width="9" height="9"></td>
								<td background="/html/agloco/images/061107_tb12.gif"><img src="/html/agloco/images/061107_tb12.gif" width="9" height="9"></td>
								<td><img src="/html/agloco/images/061107_tb15.gif" width="9" height="9"></td>
							</tr>
						</table>
						<div align="left" class="ag_k11"><%= LanguageUtil.get(pageContext, "ag-my-account-remark1") %></div>
						
						<!-- download viewbar link add at 2007-04-30-->
						<!-- <div align="left" class="ag_k11" style="height:10px;"></div>-->
					</td>
					<td width="15">
						&nbsp;
					</td>
					<td width="330" valign="top">
						<br><agloco-util:article-content article="<%= com.agloco.Constants.ARTICLEID_AG_AGLOCO_UPDATE%>"/>
					</td>
				</tr>
				<tr>
					<td>
					<!-- download viewbar link add at 2007-06-18-->
					<div align="left" class="ag_k11">
							<c:if test="<%=displayDownloadLink == true%>">
							<!-- <strong><%= LanguageUtil.get(pageContext, "viewbar-download-message") %></strong> -->
								<span style="text-align: left"><strong>For fast downloads use this:</strong></span><br>
								<div align="center"><nobr>
								<input type="button" class="ag_bt1" name="fastdownload" id="fastdownload" onClick="javascript:fastDownLoadViewbar();" value="<%= LanguageUtil.get(pageContext,"ag-my-account-download-viewbar-new") %>">
								</nobr>
								</div>
								<strong>When prompted, select a server in the country closest to you for fastest processing.</strong><br><br>
								 
								 
								 If you cannot access the fast download, use this:<br>
								 <div align="center"><nobr>
								 <input type="button" class="ag_bt0" name="btn_submit" id="btn_submit" onClick="javascript:downloadViewbar();" value="<%= LanguageUtil.get(pageContext,"ag-my-account-download-viewbar") %>">
								</nobr>
								</div>
							</c:if>
						</div>
					
					</td>
					<td>&nbsp;</td>
					<td><div align="left" class="ag_k11"><%= tagUtils.message(pageContext, null, null, "ag-my-account-referral-link", new Object[]{referralText}) %></div></td>
				</tr>
			</table>
		</td>
		<td valign="top" background="/html/agloco/images/061107_tb07b.gif"><img src="/html/agloco/images/061107_tb07.gif" width="25" height="378"></td>
	</tr>
	<tr>
		<td><img src="/html/agloco/images/061107_tb08.gif" width="25" height="20"></td>
		<td background="/html/agloco/images/061107_tb09.gif"><img src="/html/agloco/images/061107_tb09.gif" width="25" height="20"></td>
		<td><img src="/html/agloco/images/061107_tb10.gif" width="25" height="20"></td>
	</tr>
</table>
<br>
</td></tr>
</table>
<script language="javascript">
<!--
function downloadViewbar()
{
	self.location = '/web/guest/viewbaragreement';
}
function fastDownLoadViewbar()
{
	window.open("http://www.tucows.com/preview/515079");
}
//-->
</script>

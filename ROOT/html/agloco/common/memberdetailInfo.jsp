<html>
	<link href="/html/themes/agloco/css/en_lightblue.css" rel="stylesheet" type="text/css">
	<%@ include file="/html/portal/init.jsp"%>
	<%@ page import="com.agloco.model.MemberDetailInfo"%>
	<%@ page import="com.agloco.servlet.MemberDetaiInfoServlet"%>
	<%@ page import="java.text.*"%>
	<%@ page import="java.util.*"%>
	<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<script src="/html/agloco/js/common.js" type="text/javascript"></script>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Detail Info</title>
	</head>

	<body>
		<%String mailSubject = request.getParameter("mailSubject");
			String mailBody = request.getParameter("mailBody");
			String userId = request.getParameter("userId");
			boolean preResult = Boolean.FALSE;
			boolean postResult = Boolean.FALSE;
			if (mailSubject != null) {
				postResult = MemberDetaiInfoServlet.sendMail(mailSubject,
						mailBody, userId);
				preResult = Boolean.TRUE;
			} 
			MemberDetailInfo memberdetail = MemberDetaiInfoServlet
					.getMemberDetailInfo(userId);
			request.setAttribute("memberdetail", memberdetail);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			%>
		<form action="" method="post" name="testForm">
			<table width="600" border="0" cellpadding="2" cellspacing="2" bgcolor="#FFFFFF" align="center">
				<tr><td colspan="4">
				<div id="resultMessage" class="ag_r18b">
				<c:if test="<%= preResult == Boolean.TRUE %>">
					<c:if test="<%= postResult == Boolean.FALSE %>">
							<font color="red">The Error Happened,Please contact the Administrator!</font>
					</c:if>
					<c:if test="<%= postResult == Boolean.TRUE %>">
							<font color="green">You request have successed!</font>
					</c:if>
				</c:if>
				</div>
				</td>
				</tr>
				<tr>
					<td class="ag_k11b">
						User ID:
					</td>
					<td class="ag_k11" width="120">
						<bean:write name="memberdetail" property="userId" />
					</td>
					<td class="ag_k11b" width="100">
						Member Code:
					</td>
					<td class="ag_k11" width="80">
						<bean:write name="memberdetail" property="memberCode" />
					</td>
					
				</tr>
				<tr>
					<td class="ag_k11b">
						EmailAddress:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="emailAddress" />
					</td>
					<td class="ag_k11b">
						BirthDate:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="birthDate" />
					</td>
				</tr>
				<tr>
					<td class="ag_k11b">
						FirstName:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="firstName" />
					</td>
					<td class="ag_k11b">
						MiddleName:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="middleName" />
					</td>
					<td class="ag_k11b">
						LastName:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="lastName" />
					</td>

				</tr>
				<tr>
					<td class="ag_k11b">
						Country:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="country" />
					</td>
					<td class="ag_k11b">
						City:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="city" />
					</td>

				</tr>
				<tr>
					<td class="ag_k11b">
						State:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="state" />
					</td>
					<td class="ag_k11b">
						PostCode:
					</td>
					<td class="ag_k11">
						<bean:write name="memberdetail" property="postCode" />
					</td>

				</tr>
				<tr>
					<td class="ag_k11b">
						CreateDate:
					</td>
					<td class="ag_k11">
						<%=memberdetail.getCreateDate() == null ? ""
							: dateformat.format(memberdetail.getCreateDate()
									.getTime())%>
					</td>
					<td class="ag_k11b">
						ModifiedDate:
					</td>
					<td class="ag_k11">
						<%=memberdetail.getModifiedDate() == null ? ""
					: dateformat.format(memberdetail.getModifiedDate()
							.getTime())%>
					</td>

				</tr>
				<td class="ag_k11b">
					LoginDate:
				</td>
				<td width="50" class="ag_k11">
					<%=memberdetail.getLoginDate() == null ? ""
					: dateformat.format(memberdetail.getLoginDate().getTime())%>
				</td>
				<td class="ag_k11b">
					LastLoginDate:
				</td>
				<td width="50" class="ag_k11">
					<%=memberdetail.getLastLoginDate() == null ? ""
					: dateformat.format(memberdetail.getLastLoginDate()
							.getTime())%>
				</td>

				<tr>
				<tr>
					<td bgcolor="#FFE0E0" class="ag_k11b">
						LoginIP:
					</td>
					<td width="50" class="ag_k11">
						<bean:write name="memberdetail" property="loginIP" />
					</td>
					<td bgcolor="#FFE0E0" class="ag_k11b">
						LastLoginIP:
					</td>
					<td width="50" class="ag_k11">
						<bean:write name="memberdetail" property="lastLoginIP" />
					</td>
				</tr>
				<tr>
					<td class="ag_k11b">
						Status:
					</td>
					<td width="50" class="ag_k11">
						<bean:write name="memberdetail" property="status" />
					</td>
					<td class="ag_k11b">
						Temp or Member:
					</td>
					<td width="50" class="ag_k11">
						<%= memberdetail.isTempMember()?"Temp":"Member"%>
					</td>
				</tr>
				</tr>
				<c:if test="<%=memberdetail.isTempMember() == Boolean.TRUE %>">
					<tr>
						<td class="ag_k11b">
							MailCount:
						</td>
						<td width="50" class="ag_k11">
							<bean:write name="memberdetail" property="mailCount" />
						</td>
						<td class="ag_k11b">
							LastMailTime:
						</td>
						<td width="50" class="ag_k11">
							<%=memberdetail.getLastMailTime() == null ? ""
					: dateformat.format(memberdetail.getLastMailTime()
							.getTime())%>
						</td>
						<td class="ag_k11b">
							MailResult:
						</td>
						<td width="50" class="ag_k11">
							<bean:write name="memberdetail" property="mailResult" />
						</td>
					</tr>
				</c:if>
				<tr>
					<table width="600" border="0" cellpadding="2" cellspacing="2" bgcolor="#FFFFFF" align="center">
						<tr style="font:14px Verdana;text-decoration:none;line-height:18px;font-weight:bold" bgcolor="#C2ECAE">
							<b>Send The Email:</b>
						</tr>
						<div>
						<tr>
							<td class="ag_k11b">
								Mail Subject:
							</td>
							<td align="left" class="ag_k10b">
								<input size="70" type="text" name="mailSubject" id="mailSubject" value="<%= mailSubject==null?"":mailSubject %>">
							</td>
						</tr>
						<tr>
							<td class="ag_k11b">
								Mail Body:
							</td>

							<td colspan="1" width="100" class="ag_k10b">
								<TEXTAREA rows="4" cols="60" name="mailBody" id="mailBody"><%=mailBody == null ? "" : mailBody%></TEXTAREA>
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td class="ag_k11" align="center" colspan="3">
								<input type="button" class="ag_bt0" id="btn_submit" value="sendMail" onClick="javascript:sendMail();" />
							</td>
						</tr>
						</div>
					</table>
				</tr>
			</table>
		</form>
	</body>
	<SCRIPT LANGUAGE="JavaScript">
	function sendMail()
	{
		var resultMessage=document.getElementById('resultMessage');
  
  		if(document.getElementById("mailSubject").value == null
  			|| document.getElementById("mailSubject").value.length <1)
		{
			resultMessage.innerHTML = 'Please enter the mail subject!';
			return;
		}
		else
			resultMessage.innerHTML = '';
		
		
  		if(document.getElementById("mailBody").value == null
  			|| document.getElementById("mailBody").value.length <1)
		{
			resultMessage.innerHTML = 'Please enter the mail content!';
			return;
		}
		else
			resultMessage.innerHTML = '';
			
		document.testForm.submit();
	}
	</SCRIPT>
</html>
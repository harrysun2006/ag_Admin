<%@ include file="/html/portlet/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="javax.portlet.WindowState" %>
<%@page import="com.agloco.exception.FileFormatException"%>
<%@page import="com.agloco.exception.CannotCatchedException"%>
<%@page import="java.io.IOException" %>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.agloco.model.AGMember"%>
<%@page import="com.agloco.model.AGMemberTemp"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<script src="/html/agloco/js/viewpage.js" type="text/javascript"></script>

<script type="text/javascript">
<!--
	redirectToLogin();
//-->
</script>
<%
	//Set memberSet = (Set) request.getAttribute("memberSet");
	//Set memberTempSet = (Set) request.getAttribute("memberTempSet");
	List memberSet = (List) request.getAttribute("memberList");
	List memberTempSet = (List) request.getAttribute("memberTempList");
	Boolean displayMember = (Boolean)request.getAttribute("displayMember");
	Boolean suspendResult = (Boolean)request.getAttribute("suspendResult");
	Boolean enableResult = (Boolean)request.getAttribute("enableResult");
	Boolean cancelResult = (Boolean)request.getAttribute("cancelResult");
	Integer suspendNumber = (Integer)request.getAttribute("suspendNumber");
	Integer enableNumber = (Integer)request.getAttribute("enableNumber");
	String actionName = (String)request.getAttribute("actionName");

	
	if(memberSet == null || memberSet.size() < 1){
		memberSet = null;
	}
	if(memberTempSet == null || memberTempSet.size() < 1){
		memberTempSet = null;
	}
	if(StringUtils.isBlank(actionName) || "null".equalsIgnoreCase(actionName)){
		actionName = null;
	}
	
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/suspend_member/suspend" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="suspendMemberForm">
	<table border="0" width="100%">
		<tr>
			<td>
				<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
				<liferay-ui:error exception="<%= FileFormatException.class %>" message="ag-suspend-member-file-format-exception" />
			</td>
		</tr>
	
		<tr>
			<td align="left">
				<input class="form-text" name="<portlet:namespace />uploadFileName" size="100" type="file">			
			</td>
			<td class="ag_r10b" align="left">
                 <input type="button" class="ag_bt0" name="btn_upload" id="btn_upload" onClick="javascript:upload();" value="Upload File">
             </td>
		</tr>
	</table>
		
		
		
		<c:if test="<%=displayMember == Boolean.TRUE %>">
			<div style="width:100%;height:360px;overflow-x:hidden;overflow-y:auto;">
				<table border="0" width="100%">
					<tr>
						<td>
							<div>
								<table border="0" width="100%" cellspacing="1" cellpadding="1" bgcolor="green">
									<c:if test="<%=memberSet == null %>">
										<tr>
											<td colspan="5" align="left" bgcolor="#e4d8e0">
												there is no members &nbsp;
											</td>
										</tr>
									</c:if>
									<c:if test="<%=memberSet != null%>">
										<tr bgcolor="#e889">
											<td align="left" width="15%">
												First Name
											</td>
											<td align="left" width="15%">
												Last Name
											</td>
											<td align="left" width="20%">
												Member ID
											</td>
											<td align="left" width="40%">
												Email Address
											</td>
											<td align="left" width="10%">
												Status
											</td>
										</tr>
									<%
										AGMember m = null;
										for(Iterator it = memberSet.iterator(); it.hasNext();){
											m = (AGMember) it.next();
									%>		
									
										<tr bgcolor="#FFFFFF">
											<td bgcolor="#FFFFFF">
												<a href="javascript:memberInfo('<%=m.getUserId() %>')"><%=m.getFirstName() %></a>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<a href="javascript:memberInfo('<%=m.getUserId() %>')"><%=m.getLastName() %></a>&nbsp;
											</td>
											<td bgcolor="#FFFFFF"> 
												<%=m.getMemberCode() %>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<%=m.getEmailAddress() %>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<c:if test="<%=m.getStatus().equals("S") %>">
													Suspend&nbsp;
												</c:if>
												<c:if test="<%=!m.getStatus().equals("S") %>">
													Normal&nbsp;
												</c:if>
											</td>
										</tr>
									<%		
										}
									%>
										<tr>
											<td align="left" colspan="5" bgcolor="#e4d8e0">
												there are <%=memberSet.size() %> members &nbsp;
											</td>
										</tr>
									</c:if>	
									
								</table>
							</div>		
						</td>
					</tr>
					
					<tr>
						<td>
							<div>
								<table border="0" width="100%" cellspacing="1" cellpadding="1" bgcolor="green">
									<c:if test="<%=memberTempSet == null %>">
										<tr>
											<td colspan="5" align="left" bgcolor="#e4d8e0">
												there is no temp members &nbsp;
											</td>
										</tr>
									</c:if>
									<c:if test="<%=memberTempSet != null%>">
										<tr bgcolor="#e889">
											<td align="left" width="15%">
												First Name
											</td>
											<td align="left" width="15%">
												Last Name
											</td>
											<td align="left" width="20%">
												Member ID
											</td>
											<td align="left" width="40%">
												Email Address
											</td>
											<td align="left" width="10%">
												Status
											</td>
										</tr>
		
									<%
										AGMemberTemp mt = null;
										for(Iterator it = memberTempSet.iterator(); it.hasNext();){
											mt = (AGMemberTemp) it.next();
									%>		
									
										<tr bgcolor="#FFFFFF">
											<td bgcolor="#FFFFFF">
												<a href="javascript:memberInfo('<%=mt.getUserId() %>')"><%=mt.getFirstName() %></a>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<a href="javascript:memberInfo('<%=mt.getUserId() %>')"><%=mt.getLastName() %></a>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<%=mt.getMemberCode() %>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<%=mt.getEmailAddress() %>&nbsp;
											</td>
											<td bgcolor="#FFFFFF">
												<c:if test="<%=mt.getStatus().equals("S") %>">
													Suspend&nbsp;
												</c:if>
												<c:if test="<%=!mt.getStatus().equals("S") %>">
													Normal&nbsp;
												</c:if>
											</td>
										</tr>
									<%		
										}
									%>
										<tr>
											<td align="left" colspan="5" bgcolor="#e4d8e0">
												there are <%=memberTempSet.size() %> temp members &nbsp;
											</td>
										</tr>
									</c:if>	
									
								</table>
							</div>		
						</td>
					</tr>
				</table>
			</div>
			<table width="100%">
				<tr>
					<td colspan="3">
						<div align="center">
	                 		<input type="button" class="ag_bt0" name="btn_suspend" id="btn_suspend" onClick="javascript:suspend();" value="Suspend Member">
	                 		<input type="button" class="ag_bt0" name="btn_enable" id="btn_enable" onClick="javascript:enable();" value="Enable Member">
	                 		<input type="button" class="ag_bt0" name="btn_cancel" id="btn_cancel" onClick="javascript:cacel();" value="Cancel">	
	             		</div>
					</td>
				</tr>
			</table>	
		</c:if>

		<c:if test="<%=displayMember != Boolean.TRUE %>">
			<div style="width:100%;height:360px;overflow-x:hidden;overflow-y:auto;">
				<table border="0" width="100%">
					<c:if test="<%=suspendResult == Boolean.TRUE %>">
						<tr>
							<td>
									suspend member success, total number:<%=suspendNumber %>
							</td>
						</tr>
					</c:if>
					
					<c:if test="<%=enableResult == Boolean.TRUE %>">
						<tr>
							<td>
									enable member success, total number:<%=enableNumber %>
							</td>
						</tr>
					</c:if>
					
					<c:if test="<%=cancelResult == Boolean.TRUE %>">
						<tr>
							<td>
									cancel operation success.
							</td>
						</tr>
					</c:if>
					<c:if test="<%=actionName == null %>">
						<tr>
							<td>
									The upload file support *.txt only. 
							</td>
						</tr>
						<tr>
							<td>
									Txt file one line only accept one email address	
							</td>
						</tr>
						<tr>
							<td>
									Don't input invalid data,it only recognize email address.
							</td>
						</tr>
					</c:if>
					
				</table>
			</div>
		</c:if>
		
		
	<table border="0" width="100%">	

		<tr>
			<td colspan="2">
				<input name="<portlet:namespace />actionName" type="hidden"/>		
			</td>
		</tr>
	</table>
</form>

<script language="javascript">
	function upload() {
		var f = document.forms["suspendMemberForm"];
		f.elements["<portlet:namespace />actionName"].value = "upload";
		f.submit();
	}
	
	function suspend() {
	
		var ok = confirm("are you sure suspend all the members?");
		if(ok != true ){
			return;
		}
		
		var f = document.forms["suspendMemberForm"];
		f.elements["<portlet:namespace />actionName"].value = "suspend";
		f.submit();
	}
	
	function enable() {
	
		var ok = confirm("are you sure enable all the members?");
		if(ok != true ){
			return;
		}
		
		var f = document.forms["suspendMemberForm"];
		f.elements["<portlet:namespace />actionName"].value = "enable";
		f.submit();
	}
	
	function cacel() {
		var f = document.forms["suspendMemberForm"];
		f.elements["<portlet:namespace />actionName"].value = "cancel";
		f.submit();
	}
	
	function memberInfo(userId)
	{
		document.getElementById("displayTD").innerHTML = "<iframe frameborder=0 width=610 height=450 scrolling=no src='/html/agloco/common/memberdetailInfo.jsp?userId="+userId+"' name='detailInfo'></iframe>";
		document.all.detail.style.display = 'block';
	}
	
</script>

<table align=center border="1" cellpadding="0" cellspacing="0" rules=none style="position:absolute;left:150;top:250;display:none" name="detail" id="detail" bgcolor="#FFFFFF">
	<tr onmousedown="MDown(detail)" style="cursor:move" height=22>
		<td bgcolor="#20c0c0" align="left">&nbsp;&nbsp;Detail Infomation</td>
		<td bgcolor="#20c0c0" align="right">
			<img src="/html/agloco/images/close.gif" onclick="javascript:document.getElementById('detail').style.display='none';" style="cursor: pointer">
		</td>
	</tr>
	<tr>
		<td align=center height=30 id="displayTD">
		</td>
	</tr>
</table>
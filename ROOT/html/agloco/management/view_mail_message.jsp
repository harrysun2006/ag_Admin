<%@ include file="/html/portlet/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="javax.portlet.WindowState" %>
<%@page import="com.agloco.form.MailMessageForm"%>
<%@ page import="com.agloco.exception.CannotCatchedException"%>


<%@page import="com.agloco.mail.Part"%>
<%@page import="com.agloco.mail.Part.Type"%>
<%@page import="java.util.Collection"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.agloco.mail.MailPart"%>
<%@page import="java.util.Iterator"%>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<script src="/html/agloco/js/viewpage.js" type="text/javascript"></script>

<portlet:defineObjects />

<script type="text/javascript">
<!--
	redirectToLogin();
//-->
</script>


<bean:define id="mailMessageForm" name="mailMessageForm" type="com.agloco.form.MailMessageForm"/>
<%
	MailMessageForm mf =(MailMessageForm)request.getAttribute("mailMessageForm");

	String message = "There is no content in this letter !";
	Part.Type type = mf.getMessage().getType();
	Part p = mf.getMessage().getPart(type);
	if(p != null){
		message = p.getText();
	}
	
	Collection list = null;
	list = mf.getMessage().getAttachments();
	if(list != null && list.size() < 1){
		list = null;
	}

%>

<form
	action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/manage_mail_queue/list" />
	</portlet:actionURL>" method="post" name="<portlet:namespace />listQueueForm">
	
	<table border="0" width="100%">
		<tr>
			<td>
				<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
			</td>
		</tr>
		<tr>
			<td>
				<input name="<portlet:namespace />actionName" type="hidden"/>
				<input name="<portlet:namespace />pageNumber" type="hidden"/>		
			</td>
		</tr>
	</table>
	
	<div>
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			
			<c:if test="<%=mailMessageForm == null %>">
				<tr>
					<td colspan="5" align="left" bgcolor="#FFFFFF">
						can not get the message,it may be sent &nbsp;
					</td>
				</tr>
			</c:if>	
			
			<c:if test="<%=mailMessageForm != null%>">	
				<tr>
					<td class="ag_k12b" align="right" width="10%">From:&nbsp; </td><td class="ag_k12"><bean:write name="mailMessageForm" property="from"/></td>
				</tr>
				
				<tr>
					<td class="ag_k12b" align="right" width="10%">To:&nbsp; </td><td class="ag_k12"><bean:write name="mailMessageForm" property="to"/></td>
				</tr>
				
				<tr>
					<td class="ag_k12b" align="right" width="10%">CC:&nbsp; </td><td class="ag_k12"><bean:write name="mailMessageForm" property="cc"/></td>
				</tr>
				
				<tr>
					<td class="ag_k12b" align="right" width="10%">Date:&nbsp; </td><td class="ag_k12"><bean:write name="mailMessageForm" property="sendDateString"/></td>
				</tr>
				
				<tr>
					<td class="ag_k12b" align="right" width="10%">Subject:&nbsp; </td><td class="ag_k12"><%=mf.getMessage().getSubject() %></td>
				</tr>
				
				<tr>
					<td class="ag_k12" colspan="2"><hr width="100%" size="1"/></td>
				</tr>
		
				<tr>
					<td class="ag_k12" colspan="2"><%=message %></td>
				</tr>
				
				<c:if test="<%= list != null %>">
					<tr>
						<td class="ag_k12" colspan="2">There are <%=list.size() %> attachments.</td>
					</tr>
				</c:if>
				
				<tr>
					<td class="ag_k12" colspan="2" align="center"><input type="button" value="back" onclick="javascript:history.go(-1)"/></td>
				</tr>
			</c:if>

		</table>
	</div>		
	
</form>	


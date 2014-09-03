<%@ include file="/html/portlet/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="javax.portlet.WindowState" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.agloco.form.MailMessageForm"%>
<%@ page import="com.agloco.exception.CannotCatchedException"%>

<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.Message.RecipientType"%>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<script src="/html/agloco/js/viewpage.js" type="text/javascript"></script>

<portlet:defineObjects />

<script type="text/javascript">
<!--
	redirectToLogin();
//-->
</script>

<script type="text/javascript">
	//alert('aaaaaa');
</script>

<%

	int totalCount = 0;
	int currentPage = 1;
	int maxPage = 0;
	List list = null;
	list = (List) request.getAttribute("list");
	Integer tc = (Integer)request.getAttribute("totalCount");
	Integer cp = (Integer)request.getAttribute("currentPage");
	Integer mp = (Integer)request.getAttribute("maxPage");
	if(tc != null){
		totalCount = tc.intValue();
	}
	if(cp != null){
		currentPage = cp.intValue();
	}
	if(mp != null){
		maxPage = mp.intValue();
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
				<input name="<portlet:namespace />messageId" type="hidden"/>
				<input name="<portlet:namespace />messageIds" type="hidden"/>		
			</td>
		</tr>
	</table>
	
	<div>
		<table border="0" width="100%" cellspacing="1" cellpadding="1" bgcolor="green">
			<tr bgcolor="#E1F6D7">
				<td align="left" width="10">
					<input type="checkbox" name="selectAll" onclick="javascript:selectAllMail('<portlet:namespace />listQueueForm')">
				</td>
				<td align="left" width="20%">
					Email Address
				</td>
				<td align="left" width="25%">
					Subject
				</td>
				<td align="left" width="30%">
					Send Date
				</td>
				<td align="left" width="20%">
					Charset
				</td>
			</tr>
			
			<c:if test="<%=list == null %>">
				<tr>
					<td colspan="5" align="left" bgcolor="#FFFFFF">
						there is no mail message &nbsp;
					</td>
				</tr>
			</c:if>	
			
			<c:if test="<%=list != null%>">
			<%
				MailMessageForm f = null;
				for(Iterator it = list.iterator(); it.hasNext();){
					f = (MailMessageForm)it.next();
			%>		
			
				<tr bgcolor="#FFFFFF">
					<td>
						<input type="checkbox" name="<portlet:namespace />ids" value="<%=f.getId() %>">
					</td>
					<td bgcolor="#FFFFFF">
						<a href="javascript:viewMessageInfo('<%=f.getId() %>')"><%=f.getRecipientAddress() %></a>&nbsp;
					</td>
					<td bgcolor="#FFFFFF">
						<a href="javascript:viewMessageInfo('<%=f.getId() %>')"><%=f.getMessage().getSubject() %></a>&nbsp;
					</td>
					<td bgcolor="#FFFFFF"> 
						<%=f.getSendDateString() %>&nbsp;
					</td>
					<td bgcolor="#FFFFFF">
						<%=f.getMessage().getCharset() %>&nbsp;
					</td>
				</tr>
			<%		
				}
			%>
			</c:if>
			<%
				if(maxPage > 1){
			%>
					<tr bgcolor="#E1F6D7">
						<td colspan="5" align="right" nowrap="true">
							<div>
								current page/total page: &nbsp;<%=currentPage %>/<%=maxPage %>&nbsp;&nbsp;
								<a <% if(currentPage > 1){ %>href="javascript:listQueue(1)"<%} %>>First</a> | 
								<a <% if(currentPage-1 >= 1){ %>href="javascript:listQueue(<%=currentPage-1 %>)"<%} %>>Previous</a> | 
								<a <% if(currentPage+1 <= maxPage){ %>href="javascript:listQueue(<%=currentPage+1 %>)"<%} %>>Next</a> | 
								<a <% if(currentPage < maxPage){ %>href="javascript:listQueue(<%=maxPage %>)"<%} %>>End</a>
							</div>	
						</td>
					</tr>
			<%
				}
			%>
		</table>
		<div style="text-align:center;">
			<table>
				<tr>
					<td class="ag_k12" align="center">
						<input type="button" value="delete" onclick="javascript:deleteMessage('<portlet:namespace />listQueueForm')"/>
					</td>
				</tr>
			</table>
		</div>
	</div>		
	
</form>	

<script type="text/javascript">

	function selectAllMail(formName)
	{
		var form = document.forms[formName];
		var elements = form.elements;
		var checked = form.selectAll.checked;	
			
		for (var i = 0; i < elements.length; i++) {
			var element = elements[i];
			
			if (element.type == "checkbox" &&
				element.name != "selectAll") {
				element.checked = checked;
			}
		}
		
	}
	
	function deleteMessage(formName)
	{
		var ok = confirm("are you sure delete the selected mail?");
		if(ok != true ){
			return;
		}
		var form = document.forms[formName];
		var elements = form.elements;
		var checked = form.selectAll.checked;	
		var selected = '';	
		for (var i = 0; i < elements.length; i++) {
			var element = elements[i];
			
			if (element.type == "checkbox" &&
				element.name != "selectAll" &&
				element.checked == true ) {
				selected += element.value + ','; 
			}
		}
		
		document.all['<portlet:namespace />messageIds'].value = selected;
		document.all['<portlet:namespace />pageNumber'].value = '<%=currentPage%>';
		document.all['<portlet:namespace />actionName'].value = "delete";
		document.all['<portlet:namespace />listQueueForm'].submit();
	}
	
	function listQueue(pageNumValue)
	{
		document.all['<portlet:namespace />pageNumber'].value = pageNumValue;
		document.all['<portlet:namespace />actionName'].value = "list";
		document.all['<portlet:namespace />listQueueForm'].submit();
	}
	
	function viewMessageInfo(messageId)
	{
		document.all['<portlet:namespace />messageId'].value = messageId;
		document.all['<portlet:namespace />actionName'].value = "view";
		document.all['<portlet:namespace />listQueueForm'].submit();
	}
	
</script>

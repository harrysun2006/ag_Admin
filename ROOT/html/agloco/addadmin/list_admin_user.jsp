<%@ include file="/html/portlet/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="javax.portlet.WindowState" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.agloco.model.AGMember"%>
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

<%

	int totalCount = 0;
	int currentPage = 1;
	int maxPage = 0;
	//List list = null;
	//list = (List) request.getAttribute("list");
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
	<portlet:param name="struts_action" value="/admin_user/list" />
	</portlet:actionURL>" method="post" name="<portlet:namespace />listAdminUserForm">
	
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
				<input name="<portlet:namespace />userId" type="hidden"/>
				<input name="<portlet:namespace />userIds" type="hidden"/>		
			</td>
		</tr>
	</table>
	
	<div>
		<table border="0" width="100%" cellspacing="1" cellpadding="1" bgcolor="green">
			<tr bgcolor="#E1F6D7">
				<td align="left" width="10">
					<input type="checkbox" name="selectAll" onclick="javascript:<portlet:namespace />selectAllUser('<portlet:namespace />listAdminUserForm')">
				</td>
				<td align="left" width="20%">
					Member Code
				</td>
				<td align="left" width="18%">
					First Name
				</td>
				<td align="left" width="17%">
					Last Name
				</td>
				<td align="left" width="35%">
					Email Address
				</td>
				<td align="left" width="5%">
					Status
				</td>
			</tr>
			
			<c:if test="${empty list}">
				<tr>
					<td colspan="6" align="left" bgcolor="#FFFFFF" class="ag_k12">
						there is no admin user &nbsp;
					</td>
				</tr>
			</c:if>	
			
			<c:if test="${!empty list}">
				<c:forEach var="member" items="${list}" varStatus="loopStatus">
					<tr bgcolor="#FFFFFF">
						<td>
							<input type="checkbox" name="<portlet:namespace />ids" value="${member.userId}">
						</td>
						<td bgcolor="#FFFFFF" class="ag_k12">
							<a href="javascript:<portlet:namespace />editMember('${member.userId}','${currentPage}')">${member.memberCode}</a>&nbsp;
						</td>
						<td bgcolor="#FFFFFF" class="ag_k12">
							<a href="javascript:<portlet:namespace />editMember('${member.userId}','${currentPage}')">${member.firstName}</a>&nbsp;
						</td>
						<td bgcolor="#FFFFFF" class="ag_k12"> 
							${member.lastName}&nbsp;
						</td>
						<td bgcolor="#FFFFFF" class="ag_k12">
							${member.emailAddress}&nbsp;
						</td>
						<td bgcolor="#FFFFFF" class="ag_k12">
							${member.status}&nbsp;
						</td>
					</tr>
				</c:forEach>		
			</c:if>
			<c:if test="${maxPage > 1}">
				<tr bgcolor="#E1F6D7">
					<td colspan="6" align="right" nowrap="true" class="ag_k12">
						<div>
							current page/total page: &nbsp;${currentPage}/${maxPage}&nbsp;&nbsp;
							
							<a <% if(currentPage > 1){ %>href="javascript:<portlet:namespace />listMember(1)"<%} %>>First</a> | 
							<a <% if(currentPage-1 >= 1){ %>href="javascript:<portlet:namespace />listMember(<%=currentPage-1 %>)"<%} %>>Previous</a> | 
							<a <% if(currentPage+1 <= maxPage){ %>href="javascript:<portlet:namespace />listMember(<%=currentPage+1 %>)"<%} %>>Next</a> | 
							<a <% if(currentPage < maxPage){ %>href="javascript:<portlet:namespace />listMember(<%=maxPage %>)"<%} %>>End</a>
						</div>	
					</td>
				</tr>
			</c:if>
			
		</table>
		<div align="center">
			<table>
				<tr>
					<td class="ag_k12" align="center">
						<input type="button" class="ag_bt0" value="add"     onclick="javascript:<portlet:namespace />addMember()"/>
						<input type="button" class="ag_bt0" value="suspend" onclick="javascript:<portlet:namespace />suspendMember('<portlet:namespace />listAdminUserForm')"/>
						<input type="button" class="ag_bt0" value="active"  onclick="javascript:<portlet:namespace />activeMember('<portlet:namespace />listAdminUserForm')"/>
					</td>
				</tr>
			</table>
		</div>
	</div>		
	
</form>	

<script type="text/javascript">

	function <portlet:namespace />selectAllUser(formName){
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
	
	function <portlet:namespace />suspendMember(formName){
		<portlet:namespace />updateMemberStatus(formName,"suspend");
	}
	
	function <portlet:namespace />activeMember(formName){
		<portlet:namespace />updateMemberStatus(formName,"active");
	}
	
	function <portlet:namespace />updateMemberStatus(formName,actionName){
		var ok = confirm("are you sure "+ actionName + " the selected users?");
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
		
		document.all['<portlet:namespace />userIds'].value = selected;
		document.all['<portlet:namespace />pageNumber'].value = '${currentPage}';
		document.all['<portlet:namespace />actionName'].value = actionName;
		document.all['<portlet:namespace />listAdminUserForm'].submit();
	}
	
	function <portlet:namespace />listMember(pageNumValue){
		document.all['<portlet:namespace />pageNumber'].value = pageNumValue;
		document.all['<portlet:namespace />actionName'].value = "list";
		document.all['<portlet:namespace />listAdminUserForm'].submit();
	}
	
	function <portlet:namespace />addMember(){
		document.all['<portlet:namespace />actionName'].value = "add";
		document.all['<portlet:namespace />listAdminUserForm'].submit();
	}
	
	function <portlet:namespace />editMember(userId,pageNumValue){
		document.all['<portlet:namespace />pageNumber'].value = pageNumValue;
		document.all['<portlet:namespace />userId'].value = userId;
		document.all['<portlet:namespace />actionName'].value = "edit";
		document.all['<portlet:namespace />listAdminUserForm'].submit();
	}
	
</script>

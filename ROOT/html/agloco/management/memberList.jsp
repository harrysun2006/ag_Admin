
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.util.ParamUtil"%>
<%@ page import="com.agloco.form.MemberQueryForm" %>
<%@ page import="com.agloco.exception.DateFormatInvalidException"%>
<%@ page import="com.agloco.exception.CannotCatchedException"%>
<%@ page import="com.agloco.exception.MemberCodeNullException"%>
<%@ include file="/html/common/init.jsp"%>

<%@page import="com.agloco.model.MemberDetailInfo"%>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<script src="/html/agloco/js/viewpage.js" type="text/javascript"></script>

<portlet:defineObjects />

<%
	int pageNum = 0;
	String memberCode = "";
	String memberCodeOp = "";	
	String emailAddress = "";
	String emailAddressOp = "";
	
	String beginDateStr = "";
	String endDateStr = "";
	String country = "";
	String selectMembers = "";
	String status = "";
	String memberType = "";
	String timezone = "";
	int pageSize = 20;
	int totalNum = 0;
	int maxPage = 1;
	
	MemberQueryForm memberQueryForm = (MemberQueryForm)request.getSession().getAttribute("memberQueryForm");
	if(memberQueryForm!=null)
	{
		pageNum = memberQueryForm.getPageNum().intValue();
		memberCode = memberQueryForm.getMemberCode();
		emailAddress = memberQueryForm.getEmailAddress();
		beginDateStr = memberQueryForm.getBeginDate();
		endDateStr = memberQueryForm.getEndDate();
		country = memberQueryForm.getCountry();
		pageSize = memberQueryForm.getPageSize().intValue();
		totalNum = memberQueryForm.getTotalResult();
		selectMembers = memberQueryForm.getSelectMembers();
		status = memberQueryForm.getStatus();
		memberType = memberQueryForm.getMemberType();
		emailAddressOp = memberQueryForm.getEmailAddressOp();
		memberCodeOp = memberQueryForm.getMemberCodeOp();
		timezone = memberQueryForm.getTimezone();
	}
	if(pageSize !=0)
		maxPage = (totalNum-1) / pageSize;
%>
<form
	action="<portlet:actionURL><portlet:param name="struts_action" value="/membermanagement/query" /></portlet:actionURL>"
	method="post" name="<portlet:namespace />memberQueryForm" id="<portlet:namespace />memberQueryForm">
	<input name="<portlet:namespace />pageNum" id="<portlet:namespace />pageNum" value="0" type="hidden">
	<input name="<portlet:namespace />CMD" id="<portlet:namespace />CMD" value="query" type="hidden">
	<input name="<portlet:namespace />selectMembers" id="<portlet:namespace />selectMembers" value="<%=selectMembers %>" type="hidden">
<table width="760" border="0" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
	<c:if test="<%= SessionErrors.contains(request, DateFormatInvalidException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-please-enter-a-valid-date")%> </span>
	</c:if>
	</tr>
	<tr>
	<c:if test="<%= SessionErrors.contains(request, CannotCatchedException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-can-not-catched-exception")%> </span>
	</c:if>
	</tr>
	<tr>
	<c:if test="<%= SessionErrors.contains(request, MemberCodeNullException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-please-select-a-member-or-more")%> </span>
	</c:if>
	</tr>
	<tr>
		<td>
		<table  width="98%" border="0" align="center" cellpadding="1"
									cellspacing="1" bgcolor="#C2ECAE" class="ag_k11">
		<tr>
			<td bgcolor="#E1F6D7" class="ag_k14b" align="center" >
				<table width="98%" cellpadding="0" cellspacing="0" >
				<tr>
					<td align="left" class="ag_k14b">Query statement</td>
					<td align="right" >
					<button class="ag_bt0" value="Query" id="<portlet:namespace />query" name="<portlet:namespace />query" onclick="javascript:<portlet:namespace />memberQueryForm.submit()">Query Member</button>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF" class="ag_k12">
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr class="ag_k12">
				<td colspan="2">
					<span class="ag_k11b">Member Type: </span>
					<select name="<portlet:namespace />memberType" id="<portlet:namespace />memberType">
					<option value="AGMember" selected>Member</option>
					<option value="AGMemberTemp">Temp Member</option>
					</select>
				</td>
			</tr>
			<tr class="ag_k12">
				<td>
					<span class="ag_k11b">Member code: </span>
					<select name="<portlet:namespace />memberCodeOp" id="<portlet:namespace />memberCodeOp">
					<option value="=">equal</option>
					<option value="like">like</option>
					</select>
					<input name="<portlet:namespace />memberCode" id="<portlet:namespace />memberCode" value="<%=memberCode %>">
				</td>
				<td>
					<span class="ag_k11b">Email address: </span>
					<select name="<portlet:namespace />emailAddressOp" id="<portlet:namespace />emailAddressOp">
					<option value="=">equal</option>
					<option value="like">like</option>
					</select>
					<input name="<portlet:namespace />emailAddress" id="<portlet:namespace />emailAddress" value="<%=emailAddress %>">
				</td>
			</tr>
			<tr class="ag_k12">
				<td colspan="2">
					<span class="ag_k11b">Create Date: </span>
					<span>between </span>
					<input name="<portlet:namespace />beginDate" id="<portlet:namespace />beginDate" value="<%=beginDateStr %>">
					<span>and </span>
					<input name="<portlet:namespace />endDate" id="<portlet:namespace />endDate" value="<%=endDateStr %>">
					<span class="ag_r11">Date Style: YYYY-MM-DD hh:mm:ss</span>
				</td>
			</tr>
			<tr class="ag_k12">
				<td class="ag_k11b">Time Zone:
					<select name="<portlet:namespace />timezone" id="<portlet:namespace />timezone">
					<%
					String[] tzIds = TimeZone.getAvailableIDs();
					for(int i=0;i<tzIds.length;i++)
					{
						if(tzIds[i].equals("PST8PDT"))
							out.println("<option selected value='"+tzIds[i]+"'>"+tzIds[i]+"</option>");
						else
							out.println("<option value='"+tzIds[i]+"'>"+tzIds[i]+"</option>");
					}
					%>
					</select>
				</td>
				<td>
					<span class="ag_k11b">Status: </span>
					<select name="<portlet:namespace />status" id="<portlet:namespace />status">
						<option value="">ALL</option>
						<option value="N">Active</option>
						<option value="S">Suspend</option>
					</select>
				</td>
			</tr>
			<tr class="ag_k12">
				<td>
					<span class="ag_k11b">Country: </span>
					<select name="<portlet:namespace />country" id="<portlet:namespace />country">
					<jsp:include flush="true" page="/html/agloco/common/country.jsp"></jsp:include>
					</select>
				</td>
				<td>
					<span class="ag_k11b">Rows per page: </span>
					<input name="<portlet:namespace />pageSize" id="<portlet:namespace />pageSize" value="<%=pageSize %>">
				</td>
			</tr>
			</table>	
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table  width="98%" border="0" align="center" cellpadding="1"
									cellspacing="1" bgcolor="#C2ECAE" class="ag_k11">
		<tr>
			<td bgcolor="#E1F6D7" class="ag_k14b">Result</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF" class="ag_k12">
			<div style="height: 520;overflow: auto;" align="center" id="resultDispaly">
			<table width="98%" border="0" cellpadding="1" cellspacing="1">
			<tr class="ag_th">
				<td width="40"></td>
				<td width="95">Member Code</td>
				<td width="95">First Name</td>
				<td width="95">Middle Name</td>
				<td width="95">Last Name</td>
				<td width="250">Email</td>
				<td width="40">Status</td>
				<td width="40">
				<input type="checkbox" name="selectAll" id="selectAll" onclick="javascript:selectAllMember(this,<%=pageSize %>)">
				</td>
			</tr>

			<% int index=0; %>		
		<logic:notEmpty name="memberList">
		<logic:iterate id="member" name="memberList" scope="session">
			<tr class="ag_tr<%=index%2 %>">
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><%=pageNum*pageSize + index+1 %> </a></td>
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><bean:write name="member" property="memberCode"/> </a></td>
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><bean:write name="member" property="firstName"/> </a></td>
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><bean:write name="member" property="middleName"/> </a></td>
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><bean:write name="member" property="lastName"/> </a></td>
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><bean:write name="member" property="emailAddress"/> </a></td>
				<td><a href="javascript:seeDetail('<bean:write name="member" property="userId"/>')"><bean:write name="member" property="status"/> </a></td>
				<td><input type="checkbox" name="<portlet:namespace />selectMember<%=index %>" id="<portlet:namespace />selectMember<%=index %>" value="<bean:write name="member" property="userId"/>"></td>
			</tr>
			<% index++; %>		
		</logic:iterate>
		</logic:notEmpty>
		<c:if test="<%=index<pageSize %>">
		<c:forEach begin="<%=index %>" end="<%=pageSize-1 %>" >
			<tr class="ag_tr<%=index++%2 %>">
				<td>&nbsp;</td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
			</tr>
		</c:forEach>
		</c:if>


<%		
	session.removeAttribute("memberList");
%>
		<c:if test="<%=maxPage>0 %>">
			<tr class="ag_th">
				<td colspan="8" align="right">
				<a <% if(pageNum>0){ %>href="javascript:submitForm(0)"<%} %>>First</a> | 
				<a <% if(pageNum-1>=0){ %>href="javascript:submitForm(<%=pageNum-1 %>)"<%} %>>Previous</a> | 
				<a <% if(pageNum+1<=maxPage){ %>href="javascript:submitForm(<%=pageNum+1 %>)"<%} %>>Next</a> | 
				<a <% if(pageNum<maxPage){ %>href="javascript:submitForm(<%=maxPage %>)"<%} %>>End</a>
				</td>
			</tr>
		</c:if>
			</table>
			</div>
			</td>
		</tr>
		</table>
		<br>
		
		</td>
	</tr>
	<tr>
		<td align="right">
			<button class="ag_bt0" value="S" name="Suspend" id="Suspend" onclick="javascript:changeStatus('suspend')">Suspend</button>
			<button class="ag_bt0" value="N" name="Activate" id="Activate" onclick="javascript:changeStatus('activate')">Activate</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<p></p>
		</td>
	</tr>
</table>
</form>
<script>
	var memberQueryForm = document.forms["<portlet:namespace />memberQueryForm"];
	
	function submitForm(pageNumValue)
	{
		memberQueryForm.elements['<portlet:namespace />selectMembers'].value = selectedMembers();
		memberQueryForm.elements['<portlet:namespace />pageNum'].value = pageNumValue;
		memberQueryForm.elements['<portlet:namespace />CMD'].value = "flip";
		memberQueryForm.submit();
	}
	
	function seeDetail(userId)
	{
		document.getElementById("detailInfo").src = "/html/agloco/common/memberdetailInfo.jsp?userId="+userId+"";
		document.getElementById("detail").style.display = '';
	}
	
	function selectAllMember(obj,pageSize)
	{
		for(idx=0;idx<pageSize;idx++)
		{
			if(memberQueryForm.elements['<portlet:namespace />selectMember'+idx]!=null)
				memberQueryForm.elements['<portlet:namespace />selectMember'+idx].checked = obj.checked
		}	
	}
	
	function selectedMembers()
	{
		var pageSize = <%=pageSize %>;
		var selectMembers = "<%=selectMembers %>";
		for(idx=0;idx<pageSize;idx++)
		{
			var obj = memberQueryForm.elements['<portlet:namespace />selectMember'+idx];
			if(obj!=null)
			{
				if(obj.checked == true)
				{
					if(selectMembers.indexOf(obj.value+',') == 0)
						selectMembers = selectMembers.substring(obj.value.length+1);
					selectMembers = selectMembers.replace(','+obj.value+',',',');
					selectMembers = selectMembers + obj.value+',';
				}
				else
				{
					if(selectMembers.indexOf(obj.value+',') == 0)
						selectMembers = selectMembers.substring(obj.value.length+1);
					selectMembers = selectMembers.replace(','+obj.value+',',',');
				}
			}
		}	
		return selectMembers;
	}
	
	function changeStatus(status)
	{
		memberQueryForm.elements['<portlet:namespace />CMD'].value = status;
		memberQueryForm.elements['<portlet:namespace />selectMembers'].value = selectedMembers();
		memberQueryForm.submit();
	}
	
	function initpage()
	{
		initSelect(memberQueryForm.elements['<portlet:namespace />memberType'],'<%=memberType %>');
		initSelect(memberQueryForm.elements['<portlet:namespace />status'],'<%=status %>');
		initSelect(memberQueryForm.elements['<portlet:namespace />memberCodeOp'],'<%=memberCodeOp %>');
		initSelect(memberQueryForm.elements['<portlet:namespace />emailAddressOp'],'<%=emailAddressOp %>');
		initSelect(memberQueryForm.elements['<portlet:namespace />country'],'<%=country %>');
		initSelect(memberQueryForm.elements['<portlet:namespace />timezone'],'<%=timezone %>');
		initCheckbox();
	}
	
	function initCheckbox()
	{
		var pageSize = <%=pageSize %>;
		var selectMembers = '<%=selectMembers %>'.split(',');
		for(idx=0;idx<pageSize;idx++)
		{
			var obj = memberQueryForm.elements['<portlet:namespace />selectMember'+idx];
			if(obj!=null)
			{
				for(var i=0;i<selectMembers.length;i++)
				{
					if(selectMembers[i] == obj.value)
						obj.checked = true;
				}
			}
		}	
	}
	
	function initSelect(obj,valueStr)
	{
		for(var i=0;i<obj.options.length;i++)
		{
			if(obj[i].value == valueStr)
			{
				obj.selectedIndex = i;
				return;
			}
		}
	}
	
	initpage();
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
		<iframe frameborder=0 width=610 height=450 scrolling=no src='' name='detailInfo' id='detailInfo'></iframe>
		</td>
	</tr>
</table>


<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="java.util.List" %>
<%@ page import="com.agloco.exception.CannotCatchedException"%>
<%@ page import="com.agloco.exception.TableNotExistException"%>
<%@ page import="com.agloco.form.LogMessageForm" %>

<%@ include file="/html/common/init.jsp"%>

<%@page import="com.agloco.model.MemberDetailInfo"%>

<%
	int pageNum = 0;
	String userId = "";
	String memberCode = "";
	String memberCodeType = "";
	String emailAddress = "";
	String emailAddressType = "";
	String referralCode = "";
	String referralCodeType = "";
	String operate = "";
	String ip = "";
	String priority = "";
	String logDateStr = "";
	int pageSize = 20;
	int totalNum = 0;
	int maxPage = 1;
	
	int year = 2006,month = 11,day = 21;
	Date today = new Date();
	year = today.getYear()+1900;
	month = today.getMonth();
	day = today.getDate();
	
	LogMessageForm logMessageForm = (LogMessageForm)request.getSession().getAttribute("logMessageForm");
	request.getSession().removeAttribute("logMessageForm");
	if(logMessageForm!=null)
	{
		pageNum = logMessageForm.getPageNum().intValue();
		memberCode = logMessageForm.getMemberCode();
		emailAddress = logMessageForm.getEmailAddress();
		userId = logMessageForm.getUserId();
		pageSize = logMessageForm.getPageSize().intValue();
		totalNum = logMessageForm.getTotalResult();
		referralCode = logMessageForm.getReferralCode();
		referralCodeType = logMessageForm.getReferralCodeType();
		operate = logMessageForm.getOperate();
		emailAddressType = logMessageForm.getEmailAddressType();
		memberCodeType = logMessageForm.getMemberCodeType();
		ip = logMessageForm.getIp();
		priority = logMessageForm.getPriority();
		logDateStr = logMessageForm.getLogDate();
		if(logDateStr !=null)
		{
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date logDate = df.parse(logDateStr);
			year = logDate.getYear()+1900;
			month = logDate.getMonth();
			day = logDate.getDate();
		}
	}
	
	if(pageSize !=0)
		maxPage = (totalNum-1) / pageSize;
	
%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<form
	action="<portlet:actionURL><portlet:param name="struts_action" value="/log/query" /></portlet:actionURL>"
	method="post" name="<portlet:namespace />logMessageForm" id="<portlet:namespace />logMessageForm">
	<input name="<portlet:namespace />pageNum" id="<portlet:namespace />pageNum" value="0" type="hidden">
	<input name="<portlet:namespace />logDate" id="<portlet:namespace />logDate" value="<%=logDateStr %>" type="hidden">
<table width="760" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
	<c:if test="<%= SessionErrors.contains(request, CannotCatchedException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-can-not-catched-exception")%> </span>
	</c:if>
	</tr>
	<tr>
	<c:if test="<%= SessionErrors.contains(request, TableNotExistException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-thers-is-no-log")%> </span>
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
					<input class="ag_bt0" type="button" value="Query" id="<portlet:namespace />query" name="<portlet:namespace />query" onclick="javascript:formSubmit()" />
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF" class="ag_k12">
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr class="ag_k12">
				<td>
					<span class="ag_k11b">Priority: </span>
					<select name="<portlet:namespace />priority" id="<portlet:namespace />priority">
					<option value="" selected>ALL</option>
					<option value="INFO">INFO</option>
					<option value="ERROR">ERROR</option>
					</select>
				</td>
				<td>
					<span class="ag_k11b">Operate: </span>
					<select name="<portlet:namespace />operate" id="<portlet:namespace />operate">
					<option value="" selected>ALL</option>
					<option value="SIGNUP">SIGNUP</option>
					<option value="SIGNIN">SIGNIN</option>
					<option value="REFERRAL">REFERRAL</option>
					</select>
				</td>
			</tr>
			<tr class="ag_k12">
				<td>
					<span class="ag_k11b">Log Date: </span>
					<liferay-ui:input-date formName="logMessageForm" yearRangeEnd="<%=today.getYear()+1900 %>" yearRangeStart="2006" dayParam="day" yearParam="year" monthParam="month"
					page="/html/agloco/taglib/ui/input_date/page.jsp"></liferay-ui:input-date>
				</td>
				<td>
					<span class="ag_k11b">Email address: </span>
					<select name="<portlet:namespace />emailAddressType" id="<portlet:namespace />emailAddressOp">
					<option value="=">equal</option>
					<option value="like">like</option>
					</select>
					<input name="<portlet:namespace />emailAddress" id="<portlet:namespace />emailAddress" value="<%=emailAddress %>">
				</td>
			</tr>
			<tr class="ag_k12">
				<td>
					<span class="ag_k11b">Member code: </span>
					<select name="<portlet:namespace />memberCodeType" id="<portlet:namespace />memberCodeOp">
					<option value="=">equal</option>
					<option value="like">like</option>
					</select>
					<input name="<portlet:namespace />memberCode" id="<portlet:namespace />memberCode" value="<%=memberCode %>">
				</td>
				<td>
					<span class="ag_k11b">Referral Code: </span>
					<select name="<portlet:namespace />referralCodeType" id="<portlet:namespace />referralCodeOp">
					<option value="=">equal</option>
					<option value="like">like</option>
					</select>
					<input name="<portlet:namespace />referralCode" id="<portlet:namespace />referralCode" value="<%=referralCode %>">
				</td>
			</tr>
			<tr class="ag_k12">
				<td>
					<span class="ag_k11b">IP: </span>
					<input name="<portlet:namespace />ip" id="<portlet:namespace />ip" value="<%=ip %>">
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
			<td bgcolor="#E1F6D7" class="ag_k14b">Results <%=totalNum==0?0:pageNum*pageSize+1 %>-<%=(pageNum+1)*pageSize<totalNum?(pageNum+1)*pageSize:totalNum %> of <%=totalNum %> records</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF" align="center" class="ag_k12">
			<div style="height: 500;width=730; overflow: auto;" align="center" id="resultDispaly">
			<table width="1000" border="0" cellpadding="1" cellspacing="1">
			<tr class="ag_th">
				<td width=20> </td>
				<td width=170>Create Date</td>
				<td width=60>Priority</td>
				<td width=80>User Id</td>
				<td width=250>Email</td>
				<td width=70>MC</td>
				<td width=70>RC</td>
				<td width=120>IP</td>
				<td width=60>Operate</td>
			</tr>

			<% int index=0; %>		
		<logic:notEmpty name="logInfoList">
		<logic:iterate id="logInfo" name="logInfoList" scope="session">
			<tr class="ag_tr<%=index%2 %>">
				<td><%=pageNum*pageSize + index+1 %></td>
				<td><bean:write name="logInfo" property="logDate"/></td>
				<td><bean:write name="logInfo" property="priority"/></td>
				<td><bean:write name="logInfo" property="userId"/></td>
				<td><bean:write name="logInfo" property="emailAddress"/></td>
				<td><bean:write name="logInfo" property="memberCode"/></td>
				<td><bean:write name="logInfo" property="referralCode"/></td>
				<td><bean:write name="logInfo" property="ip"/></td>
				<td><bean:write name="logInfo" property="operate"/></td>
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
				<td> </td>
			</tr>
		</c:forEach>
		</c:if>


<%		
	session.removeAttribute("logInfoList");
%>
		<c:if test="<%=maxPage>0 %>">
			<tr class="ag_th">
				<td colspan="9" align="left">
				<a <% if(pageNum>0){ %>href="javascript:<portlet:namespace />submitForm(0)"<%} %>>First</a> | 
				<a <% if(pageNum-1>=0){ %>href="javascript:<portlet:namespace />submitForm(<%=pageNum-1 %>)"<%} %>>Previous</a> | 
				<a <% if(pageNum+1<=maxPage){ %>href="javascript:<portlet:namespace />submitForm(<%=pageNum+1 %>)"<%} %>>Next</a> | 
				<a <% if(pageNum<maxPage){ %>href="javascript:<portlet:namespace />submitForm(<%=maxPage %>)"<%} %>>End</a>
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
</table>
</form>
<script>
	var logMessageForm = <portlet:namespace />logMessageForm;
	
	function <portlet:namespace />submitForm(pageNumValue)
	{
		logMessageForm.elements['<portlet:namespace />pageNum'].value = pageNumValue;
		formSubmit();
	}
	
	function formSubmit()
	{
		logMessageForm.<portlet:namespace />logDate.value = logMessageForm.<portlet:namespace />year.value;
		var month = parseInt(logMessageForm.<portlet:namespace />month.value)+1;
		if(month<10)
			logMessageForm.<portlet:namespace />logDate.value += "0"+month;
		else
			logMessageForm.<portlet:namespace />logDate.value += ""+month;
		if(parseInt(logMessageForm.<portlet:namespace />day.value)<10)
			logMessageForm.<portlet:namespace />logDate.value += "0"+logMessageForm.<portlet:namespace />day.value;
		else
			logMessageForm.<portlet:namespace />logDate.value += ""+logMessageForm.<portlet:namespace />day.value;
		
		logMessageForm.submit();
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
	
	function initpage()
	{
		initSelect(logMessageForm.elements['<portlet:namespace />memberCodeType'],'<%=memberCodeType %>');
		initSelect(logMessageForm.elements['<portlet:namespace />emailAddressType'],'<%=emailAddressType %>');
		initSelect(logMessageForm.elements['<portlet:namespace />referralCodeType'],'<%=referralCodeType %>');
		initSelect(logMessageForm.elements['<portlet:namespace />operate'],'<%=operate %>');
		initSelect(logMessageForm.elements['<portlet:namespace />priority'],'<%=priority %>');
		initSelect(logMessageForm.elements['<portlet:namespace />year'],'<%=year %>');
		initSelect(logMessageForm.elements['<portlet:namespace />month'],'<%=month %>');
		initSelect(logMessageForm.elements['<portlet:namespace />day'],'<%=day %>');
	}
	initpage();
	
	//The method changeDate() is in page.jsp of input_date taglib
	changeDate();
	
</script>

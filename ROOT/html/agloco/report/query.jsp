
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
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.agloco.model.AGQuery" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.liferay.util.ParamUtil"%>
<%@ page import="com.agloco.exception.DateFormatInvalidException"%>
<%@ page import="com.agloco.exception.CannotCatchedException"%>

<%
	List queryList = (ArrayList)request.getAttribute("queryList");
	String timezone = ParamUtil.getString(request,"timezone");
	String querySelect = ParamUtil.getString(request,"querySelect");
	String beginDate = ParamUtil.getString(request,"beginDate");
	String endDate = ParamUtil.getString(request,"endDate");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	now.setHours(0);
	now.setMinutes(0);
	now.setSeconds(0);
	
	if(timezone != null && timezone.length()>0)
		df.setTimeZone(TimeZone.getTimeZone(timezone));
	if(endDate == null || endDate.length()<1)
		endDate = df.format(now);
	now.setHours(-24);
	if(beginDate == null || beginDate.length()<1)
		beginDate = df.format(now);
	
%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.agloco.report.util.AGReportResultList"%>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>

<form
	action="<portlet:actionURL><portlet:param name="struts_action" value="/report/query" /></portlet:actionURL>"
	method="post" name="<portlet:namespace />querydefine">
	<liferay-ui:error exception="<%= DateFormatInvalidException.class %>"
			message="ag-please-enter-a-valid-date" />
	<liferay-ui:error exception="<%= CannotCatchedException.class %>"
			message="ag-can-not-catched-exception" />
<table width="100%" border="0" cellpadding="5" cellspacing="5"
	bgcolor="#FFFFFF" align="center">
	<tr>
		<td>
		<span class="ag_k11b">Select a query:</span>
		<select name="<portlet:namespace />querySelect">
<%
	for(int i=0;i<queryList.size();i++)
	{
		AGQuery agQuery = (AGQuery)queryList.get(i);
%>
		<option value="<%=agQuery.getQueryId() %>"><%=agQuery.getQueryName() %></option>
<%
	}
%>
		</select></td>
		<td>
		<button class="ag_bt0" value="Query" name="queryButton" onclick="javascript:<portlet:namespace />querydefine.submit();">Query</button>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="ag_k11b">Time Zone:
			<select name="<portlet:namespace />timezone">
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
	</tr>
	<tr>
		<td colspan="2">
		<table>
			<tr class="ag_k12">
				<td colspan="2">
					<span class="ag_k11b">Create Date: </span>
					<span>between </span>
					<input name="<portlet:namespace />beginDate" value="<%=beginDate %>">
					<span>and </span>
					<input name="<portlet:namespace />endDate" value="<%=endDate %>">
					<span class="ag_r11">Date Style: YYYY-MM-DD hh:mm:ss</span>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div style="max-height: 400;overflow: auto;" align="center" id="resultDispaly">
		<%
			AGReportResultList result = (AGReportResultList)request.getAttribute("QueryResult");
			if(result!=null)
			{
				request.getSession().removeAttribute("QueryResult");
				out.println(result.displayAsHTML("ag_tab"));
			}
		%>
			</div>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
<!--
	function initpage()
	{
		initSelect(document.all['<portlet:namespace />timezone'],'<%=timezone %>');
		initSelect(document.all['<portlet:namespace />querySelect'],'<%=querySelect %>');
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
//-->
</script>

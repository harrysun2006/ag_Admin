
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

<script src="/html/agloco/js/common.js" type="text/javascript"></script>

<form
	action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/report/newquery" /></portlet:actionURL>"
	method="post" name="newquery">
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td><select name="querySelect">
			<option value="0">select</option>
			<option>test1</option>
			<option>test2</option>
			<option>test3</option>
			<option>test4</option>
			<option>test5</option>
		</select></td>
		<td>
		<button class="ag_bt0" value="Query" name="queryButton">
		</td>
	</tr>
	<tr>
		<td colspan="2">Query Name:<input type="text" name="queryName">
		</td>
	</tr>
	<tr>
		<td colspan="2">Query:<textarea name="query" rows="5" cols="100"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">Query dercripe:<textarea name="descripe" rows="5" cols="100"></textarea>
		</td>
	</tr>
		<td>
		<button class="ag_bt0" value="View" name="queryButton">
		<button class="ag_bt0" value="Save" name="queryButton">
		</td>
	<tr>
		<td>
		<table>
			<tr>
				<td>View query result here!</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>

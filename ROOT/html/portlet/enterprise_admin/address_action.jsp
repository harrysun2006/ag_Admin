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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Address address = (Address)row.getObject();
%>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_address" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="addressId" value="<%= address.getAddressId() %>" />
</portlet:renderURL>

<liferay-ui:icon image="edit" url="<%= portletURL %>" />

<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_address" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="addressId" value="<%= address.getAddressId() %>" />
</portlet:actionURL>

<liferay-ui:icon-delete url="<%= portletURL %>" />
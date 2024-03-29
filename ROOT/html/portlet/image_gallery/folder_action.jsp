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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

IGFolder folder = (IGFolder)row.getObject();
%>

<c:if test="<%= IGFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/image_gallery/edit_folder" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="folderId" value="<%= folder.getFolderId() %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= IGFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS) %>">
	<liferay-security:permissionsURL
		modelResource="<%= IGFolder.class.getName() %>"
		modelResourceDescription="<%= folder.getName() %>"
		resourcePrimKey="<%= folder.getPrimaryKey().toString() %>"
		var="portletURL"
	/>

	<liferay-ui:icon image="permissions" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= IGFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/image_gallery/edit_folder" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="folderId" value="<%= folder.getFolderId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= portletURL %>" />
</c:if>
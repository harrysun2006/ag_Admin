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

Website website = (Website)request.getAttribute(WebKeys.WEBSITE);

String websiteId = BeanParamUtil.getString(website, request, "websiteId");

String className = BeanParamUtil.getString(website, request, "className");
String classPK = BeanParamUtil.getString(website, request, "classPK");

String typeId = BeanParamUtil.getString(website, request, "typeId");
%>

<script type="text/javascript">
	function <portlet:namespace />saveWebsite() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= website == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_website" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveWebsite(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />websiteId" type="hidden" value="<%= websiteId %>">
<input name="<portlet:namespace />className" type="hidden" value="<%= className %>">
<input name="<portlet:namespace />classPK" type="hidden" value="<%= classPK %>">

<liferay-ui:tabs names="website" />

<liferay-ui:error exception="<%= NoSuchListTypeException.class %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-url" />

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "url") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= Website.class %>" bean="<%= website %>" field="url" />
	</td>
	<td style="padding-left: 30px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, "type") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<select name="<portlet:namespace />typeId">
			<option value=""></option>

			<%
			List websiteTypes = ListTypeServiceUtil.getListTypes(className + ListType.WEBSITE);

			for (int i = 0; i < websiteTypes.size(); i++) {
				ListType suffix = (ListType)websiteTypes.get(i);
			%>

				<option <%= suffix.getListTypeId().equals(typeId) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><%= LanguageUtil.get(pageContext, suffix.getName()) %></option>

			<%
			}
			%>

		</select>
	</td>
	<td style="padding-left: 30px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, "primary") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= Website.class %>" bean="<%= website %>" field="primary" />
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />url.focus();
</script>
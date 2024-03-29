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

<%@ include file="/html/common/init.jsp" %>

<%@ include file="/html/common/themes/top_meta.jsp" %>
<%@ include file="/html/common/themes/top_meta-ext.jsp" %>

<link href="<%= themeDisplay.getPathMain() %>/portal/css_cached?themeId=<%= themeDisplay.getTheme().getThemeId() %>&colorSchemeId=<%= themeDisplay.getColorScheme().getColorSchemeId() %>" type="text/css" rel="stylesheet" />
<link href="<%= themeDisplay.getPathJavaScript() %>/calendar/skins/aqua/theme.css" rel="stylesheet" type="text/css" />

<style type="text/css">
	<liferay-theme:include page="css.jsp" />
</style>

<%@ include file="/html/common/themes/top_js.jsp" %>
<%@ include file="/html/common/themes/top_js-ext.jsp" %>

<script language="JavaScript" src="<%= themeDisplay.getPathMain() %>/portal/javascript_cached?themeId=<%= themeDisplay.getTheme().getThemeId() %>&languageId=<%= themeDisplay.getLocale().toString() %>&colorSchemeId=<%= themeDisplay.getColorScheme().getColorSchemeId() %>"></script>

<script type="text/javascript">
	<liferay-theme:include page="javascript.jsp" />
</script>
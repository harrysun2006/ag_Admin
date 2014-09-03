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

<%@ include file="portal_init.jsp" %>
<link href="/html/themes/agloco/css/en_lightblue.css" rel="stylesheet" type="text/css">
<script src="/html/agloco/js/common.js"></script>
<script src="/html/agloco/js/mm_menu.js"></script>
<html dir="<bean:message key="lang.dir" />">

<head>
	<title><bean:message key="ag_title" /></title>
	<meta name="description"  content='<%=LanguageUtil.get(pageContext,"ag-description").toString()%>'>
	<meta name="keywords"  content='<%=LanguageUtil.get(pageContext,"ag-keywords").toString()%>'>
	<liferay-util:include page='<%= Constants.TEXT_HTML_DIR + "/common/themes/top_head.jsp" %>' />
</head>

<body bgcolor="#F6FBF4" leftmargin="0" topmargin="5" marginwidth="0" marginheight="0">
<liferay-util:include page="/html/common/themes/top_warning.jsp" />

<%@ include file="top.jsp" %>
<div id="layout-outer-side-decoration">
<c:choose>
	<c:when test="<%= selectable %>">
		<liferay-util:include page="<%= Constants.TEXT_HTML_DIR + tilesContent %>" />
	</c:when>
	<c:otherwise>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>
					<liferay-util:include page="<%= Constants.TEXT_HTML_DIR + tilesContent %>" />
				</td>
			</tr>
			</table>
	</c:otherwise>
</c:choose>
</div>
<%@ include file="bottom.jsp" %>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />
<liferay-util:include page="/html/common/themes/session_timeout.jsp" />
<liferay-util:include page="/html/common/themes/sound_alerts.jsp" />

</body>

</html>
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String formName = (String)request.getAttribute(WebKeys.FORM_NAME);

String portletResource = ParamUtil.getString(request, "portletResource");
String previewWidth = ParamUtil.getString(request, "previewWidth");

PortletPreferences portletSetup = PortletPreferencesFactory.getPortletSetup(request, portletResource, true, true);

String title = portletSetup.getValue("portlet-setup-title", StringPool.BLANK);
boolean showBorders = GetterUtil.getBoolean(portletSetup.getValue("portlet-setup-show-borders", "true"));

PortletURL lookAndFeelRedirect = new PortletURLImpl(request, PortletKeys.PORTLET_CONFIGURATION, plid, false);

lookAndFeelRedirect.setWindowState(WindowState.MAXIMIZED);
lookAndFeelRedirect.setPortletMode(PortletMode.VIEW);

lookAndFeelRedirect.setParameter("struts_action", "/portlet_configuration/edit_look_and_feel");
lookAndFeelRedirect.setParameter("redirect", redirect);
lookAndFeelRedirect.setParameter("portletResource", portletResource);
lookAndFeelRedirect.setParameter("previewWidth", previewWidth);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="look-and-feel" />
</liferay-util:include>

<div style="height: 150px; margin: auto; overflow: auto; width: 95%">
	<div style="margin: 3px; width: <%= Validator.isNotNull(previewWidth) ? ((GetterUtil.getInteger(previewWidth) + 20) + "px") : "100%" %>;">
		<liferay-portlet:runtime portletName="<%= portletResource %>" />
	</div>
</div>

<br><div class="beta-separator"></div><br>

<html:form action="/portlet_configuration/edit_look_and_feel?actionURL=true">

<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />lookAndFeelRedirect" type="hidden" value="<%= lookAndFeelRedirect %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= portletResource %>" />

<div style="text-align: left;">
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "title") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<input class="form-text" name="<portlet:namespace />title" size="30" type="text" value="<%= title %>">
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "show-borders") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-checkbox param="showBorders" defaultValue="<%= showBorders %>" />
		</td>
	</tr>
	</table>
</div>

<br>

<input type="button" class="portlet-form-button" value="<%= LanguageUtil.get(pageContext, "save") %>" onclick="submitForm(document.<%= formName %>);">

<input type="button" class="portlet-form-button" value='<%= LanguageUtil.get(pageContext, "reset") %>' onclick="document.<%= formName %>.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.RESET %>'; submitForm(document.<%= formName %>);">

<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td valign="top">
		<liferay-util:include page="/html/portlet/portlet_configuration/styles_background.jsp">
			<liferay-util:param name="propertyPrefix" value="portlet" />
			<liferay-util:param name="previewObject" value="container" />
		</liferay-util:include>

		<br>

		<liferay-util:include page="/html/portlet/portlet_configuration/styles_border.jsp">
			<liferay-util:param name="propertyPrefix" value="portlet" />
			<liferay-util:param name="previewObject" value="container" />
		</liferay-util:include>

		<br>

		<liferay-util:include page="/html/portlet/portlet_configuration/styles_background.jsp">
			<liferay-util:param name="propertyPrefix" value="header" />
			<liferay-util:param name="previewObject" value="header" />
		</liferay-util:include>

		<br>

		<liferay-util:include page="/html/portlet/portlet_configuration/styles_border.jsp">
			<liferay-util:param name="propertyPrefix" value="header" />
			<liferay-util:param name="previewObject" value="header" />
		</liferay-util:include>
	</td>
	<td style="padding-left: 10px;"></td>
	<td valign="top">
		<liferay-util:include page="/html/portlet/portlet_configuration/styles_text.jsp">
			<liferay-util:param name="propertyPrefix" value="header" />
			<liferay-util:param name="previewObject" value="title" />
		</liferay-util:include>

		<br>

		<liferay-util:include page="/html/portlet/portlet_configuration/styles_background.jsp">
			<liferay-util:param name="propertyPrefix" value="content" />
			<liferay-util:param name="previewObject" value="content" />
		</liferay-util:include>

		<br>

		<liferay-util:include page="/html/portlet/portlet_configuration/styles_border.jsp">
			<liferay-util:param name="propertyPrefix" value="content" />
			<liferay-util:param name="previewObject" value="content" />
		</liferay-util:include>

		<br>

		<liferay-util:include page="/html/portlet/portlet_configuration/styles_text.jsp">
			<liferay-util:param name="propertyPrefix" value="content" />
			<liferay-util:param name="previewObject" value="content" />
		</liferay-util:include>
	</td>
	<td style="padding-left: 10px;"></td>
	<td valign="top">
		<liferay-util:include page="/html/portlet/portlet_configuration/styles_link.jsp">
			<liferay-util:param name="propertyPrefix" value="content" />
		</liferay-util:include>
	</td>
</tr>
</table>

</html:form>

<div id="default-preview-portlet" style="display: none;"></div>

<script type="text/javascript">
	stylePortlet = {
		lastSection : null,
		lastSectionTitle : null,

		toggle : function (obj, id) {
			var section = document.getElementById(id);

			if (this.lastSection != section) {
				if (this.lastSection != null) {
					this.lastSection.style.display = "none";
					this.lastSectionTitle.style.fontStyle = "normal";
					this.lastSectionTitle.style.textDecoration= "none";
				}

				section.style.display = "block";
				obj.style.fontStyle = "italic";
				obj.style.textDecoration = "underline";
				this.lastSection = section;
				this.lastSectionTitle = obj;
			}
		}
	}

	function Preview(portletId) {
		var portlet = document.getElementById("p_p_id_" + portletId + "_");
		var self = this;
		var defaultPreview = document.getElementById("default-preview-portlet");

		this.hoverStyles = new Array();

		var list = portlet.getElementsByTagName("a");
		var anchors = list;

		for (var i = 0; i < list.length; i++) {
			list[i].href = "javascript: void(0);";
			list[i].style.cursor = "default";

			list[i].onmouseover = function () {
				if (preview.hoverStyles["color"] && "" != preview.hoverStyles["color"]) {
					this.style.color = preview.hoverStyles["color"];
				}

				if (preview.hoverStyles["decor"] && "" != preview.hoverStyles["decor"]) {
					this.style.textDecoration = preview.hoverStyles["decor"];
				}

				if (preview.hoverStyles["font"] && "" != preview.hoverStyles["font"]) {
					this.style.fontFamily = preview.hoverStyles["font"];
				}

				if (preview.hoverStyles["size"] && "" != preview.hoverStyles["size"]) {
					this.style.fontSize = preview.hoverStyles["size"];
				}

				if (preview.hoverStyles["weight"] && "" != preview.hoverStyles["weight"]) {
					this.style.fontWeight = preview.hoverStyles["weight"] ? "bold" : "normal";
				}

				if (preview.hoverStyles["style"] && "" != preview.hoverStyles["style"]) {
					this.style.fontStyle = preview.hoverStyles["style"] ? "italic" : "normal";
				}
			};

			list[i].onmouseout = function () {
				this.style.color = "";
				this.style.textDecoration = "";
				this.style.fontFamily = "";
				this.style.fontSize = "";
				this.style.fontWeight = "";
				this.style.fontStyle = "";
			};
		}

		list = portlet.getElementsByTagName("input");

		for (var i = 0; i < list.length; i++) {
			list[i].disabled = "true";
			list[i].style.cursor = "default";
		}

		list = portlet.getElementsByTagName("script");

		for (var i = 0; i < list.length; i++) {
			list[i].disabled = "true";
		}

		list = portlet.getElementsByTagName("div");

		// Initialize to empty preview div

		this.container = defaultPreview.style;
		this.content = defaultPreview.style;
		this.header = defaultPreview.style;
		this.title = defaultPreview.style;

		for (var i = 0; i < list.length; i++) {
			if (list[i].className.match("portlet-borderless-container")) {
				this.container = list[i].style;
				this.content = list[i].style;

				break;
			}

			if (list[i].className.match("portlet-container")) {
				this.container = list[i].style;
			}

			if (list[i].className.match("portlet-box")) {
				this.content = list[i].style;
			}

			if (list[i].className.match("portlet-header-bar")) {
				this.header = list[i].style;
			}

			if (list[i].className.match("portlet-title")) {
				this.title = list[i].style;
			}
		}

		this.aColor = function (value) {
			for (var i = 0; i < anchors.length; i++) {
				anchors[i].style.color = value;
			}
		};

		this.aDecor = function (value) {
			for (var i = 0; i < anchors.length; i++) {
				anchors[i].style.textDecoration = value;
			}
		};

		this.aFont = function (value) {
			for (var i = 0; i < anchors.length; i++) {
				anchors[i].style.fontFamily = value;
			}
		};

		this.aSize = function (value) {
			for (var i = 0; i < anchors.length; i++) {
				anchors[i].style.fontSize = value;
			}
		};

		this.aStyle = function (value) {
			for (var i = 0; i < anchors.length; i++) {
				anchors[i].style.fontStyle = value ? "italic" : "normal";
			}
		};

		this.aWeight = function (value) {
			for (var i = 0; i < anchors.length; i++) {
				anchors[i].style.fontWeight = value ? "bold" : "normal";
			}
		};

		this.setHoverStyle = function (key, value) {
			self.hoverStyles[key] = value;
		};
	}

	var colorPicker = new ColorPicker("<%= themeDisplay.getPathJavaScript() %>/colorpicker/colorscale.png");
	var preview = new Preview("<%= portletResource %>");
</script>
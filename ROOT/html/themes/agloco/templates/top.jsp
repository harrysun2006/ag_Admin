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
<%@ page import="com.agloco.AglocoURL" %>
<%@ page import="com.liferay.portlet.admin.util.OmniadminUtil" %>
<%@ taglib uri="/WEB-INF/tld/agloco-util.tld" prefix="agloco-util" %>

<%@page import="com.agloco.action.ServicePreAction"%>
<script src="/html/agloco/js/mm_menu.js"></script>

<SCRIPT language=JavaScript>
<!--
var signinFlag = <%=themeDisplay.isSignedIn() %> ;
var userId = '<%=themeDisplay.getUserId() %>';

//This attribute is setted in SignInAction.class
var memberCode ="<%=session.getAttribute(com.agloco.Constants.MEMBERCODE) %>";

function redirectToLogin(){
	if (!signinFlag) 
	{
		window.location="/web/guest/signin";
	}
}

function mmLoadMenus() {
if (window.ag_mamenu) return;
window.ag_mamenu = new Menu("root",120,20,"Tahoma",11,"#FFFFFF","#000000","#B1B0B5","#F6FBF4","left","middle",3,0,100,-5,7,true,true,true,0,true,true);
ag_mamenu.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;<%=LanguageUtil.get(pageContext,"ag-myprofile").toString()%>", "location='/web/guest/myprofile'");
ag_mamenu.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;<%=LanguageUtil.get(pageContext,"ag-changepassword").toString()%>", "location='/web/guest/changepassword'");  
ag_mamenu.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;<%=LanguageUtil.get(pageContext,"ag-changeemail").toString()%>", "location='/web/guest/changeemail'");    
ag_mamenu.hideOnMouseOut=true;
ag_mamenu.childMenuIcon="arrows.gif";
ag_mamenu.bgColor='#FFFFFF';
ag_mamenu.menuBorder=1;
ag_mamenu.menuLiteBgColor='#FFFFFF';
ag_mamenu.menuBorderBgColor='';
ag_mamenu.writeMenus();
}

mmLoadMenus();
//-->											
</SCRIPT>

<%
	//difine the language local
	String languageName = locale.getLanguage();
	String changeToLanguage;

	String formAction = (String) request
			.getAttribute("liferay-ui:language:formAction");
	if (Validator.isNull(formAction)) {
		PortletURLImpl portletURLImpl = new PortletURLImpl(request,
		PortletKeys.LANGUAGE, plid, true);

		portletURLImpl.setWindowState(WindowState.NORMAL);
		portletURLImpl.setPortletMode(PortletMode.VIEW);
		portletURLImpl.setAnchor(false);

		portletURLImpl.setParameter("struts_action", "/language/view");

		formAction = portletURLImpl.toString();
	}

	//control the blog show or hidden
	boolean showblog;
	boolean ischinese;
	boolean showAdminControl = false;
	String servletPath = "";
	if (request.getSession().getAttribute("servletPath") != null) {
		servletPath = request.getSession().getAttribute("servletPath").toString();
	}

	if (languageName.equals("zh")) {
		changeToLanguage="en";
		ischinese = true;
		showblog = true;
	} else {
		changeToLanguage="zh_CN";
		ischinese = false;
		if (servletPath.endsWith("/web/guest/signup")) {
			showblog = false;
		} else {
			showblog = true;
		}
	}
	if (ServicePreAction.isAdministrator(user)){
		showAdminControl = true;
	}
	StringBuffer sb = new StringBuffer();
	if (themeDisplay.isSignedIn()){

		//get the private layouts view
		List prelayouts = null;
		Layout prelayout = null;
		
		if (OmniadminUtil.isOmniadmin(user.getUserId())){
			//System.out.println("Omniadmin layout id is :" + layout.getOwnerId());
			
			if (layout.getOwnerId().equals("PRI.1002")){
				layouts=LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE + "1002",Layout.DEFAULT_PARENT_LAYOUT_ID);
			}else if (layout.getOwnerId().equals("PUB.1007")){
				
			}else{
				Layout tempLayout = (Layout) layouts.get(0);
				layouts= LayoutLocalServiceUtil.getLayouts(tempLayout
						.getOwnerId(), tempLayout.getLayoutId());
			}
			//prelayouts=LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE + "1002",Layout.DEFAULT_PARENT_LAYOUT_ID);
			prelayouts=layouts;
			
			
		}else if (ServicePreAction.isAdministrator(user)){
			if (layout.getOwnerId().equals("PUB.1007")){
				layouts=LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
						+ "1007", Layout.DEFAULT_PARENT_LAYOUT_ID);
			}else{
				Layout tempLayout = (Layout) layouts.get(0);
				layouts= LayoutLocalServiceUtil.getLayouts(tempLayout
						.getOwnerId(), tempLayout.getLayoutId());
			}
			
			
			
			prelayouts = layouts;
		}else if (ServicePreAction.isPowerUser(user)){
			Group userGroup = user.getGroup();
			prelayouts = LayoutLocalServiceUtil.getLayouts(Layout.PRIVATE
					+ userGroup.getGroupId(),
					Layout.DEFAULT_PARENT_LAYOUT_ID);
		}else {
			Group guestGroup = GroupLocalServiceUtil.getGroup(user
					.getActualCompanyId(), Group.GUEST);
			layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
					+ guestGroup.getGroupId(),
					Layout.DEFAULT_PARENT_LAYOUT_ID);

			layout = (Layout) layouts.get(1);

			layouts = LayoutLocalServiceUtil.getLayouts(Layout.PUBLIC
					+ guestGroup.getGroupId(), layout.getLayoutId());
			prelayouts=layouts;
			
		}
		_buildSiteMapS(prelayouts, themeDisplay, sb);

	}else{
		//get the public layouts view
		String layoutId="";
		try{
			layoutId=request.getParameter("p_l_id");
		}catch (Exception e){
			e.printStackTrace();
			layoutId="";
		}		
		
		List prelayouts = LayoutLocalServiceUtil.getLayouts(layout.getOwnerId(), Layout.DEFAULT_PARENT_LAYOUT_ID);		
		Layout prelayout = (Layout) prelayouts.get(0);		
		prelayouts = LayoutLocalServiceUtil.getLayouts(layout.getOwnerId(), prelayout.getLayoutId());

		_buildSiteMap(prelayouts, themeDisplay, sb,layoutId);
}
%>
<%!private void _buildSiteMapS(List layouts, ThemeDisplay themeDisplay,
			StringBuffer sb) throws Exception {

	sb.append("<td width=\"5\"><img src='");
	sb.append(themeDisplay.getPathThemeImage());
	sb.append("/top/agloco_dh_01.gif' width=\"5\" height=\"23\"></td>");
	sb.append("<td background='");
	sb.append(themeDisplay.getPathThemeImage());
	sb.append("/top/agloco_dh_02.gif'>");
	sb.append(" <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	sb.append(" <tr align=\"center\">");
	if (layouts.size()>0){
		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = (Layout) layouts.get(i);
			if ( i == 0 ){
				String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
				String target = PortalUtil.getLayoutTarget(layout);
				if (themeDisplay.getUserId().equals("agloco.com.1") || themeDisplay.getUserId().equals("agloco.com.2")){
					sb.append("<td id=link1>");
				}else{
					sb.append("<td id=link1 onMouseOver=\"MM_showMenu(window.ag_mamenu,-6,21,null,'link1')\" onmouseout=MM_startTimeout();  name=link1>");
				}
				sb.append("<a class=\"ag_K11\" href=\"");
				sb.append(layoutURL);
				sb.append("\" ");
					sb.append(target);
					sb.append("> ");
					sb.append(layout.getName(themeDisplay.getLocale()));
					sb.append("</a>");
					sb.append("</td>");
					if (layouts.size() != 1){
					sb.append("<td width=\"1\" bgcolor=\"#C0C0C0\"><img src='");
					sb.append(themeDisplay.getPathThemeImage());
					sb.append("/top/agloco_dh_04.gif' width=\"2\"></td>");
					}
				}else if (i != layouts.size() - 1) {
					String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
					String target = PortalUtil.getLayoutTarget(layout);
	
					sb.append("<td>");
					sb.append("<a class=\"ag_K11\" href=\"");
					sb.append(layoutURL);
					sb.append("\" ");
					sb.append(target);
					sb.append("> ");
					sb.append(layout.getName(themeDisplay.getLocale()));
					sb.append("</a>");
					sb.append("</td>");
					sb.append("<td width=\"1\" bgcolor=\"#C0C0C0\"><img src='");
					sb.append(themeDisplay.getPathThemeImage());
					sb.append("/top/agloco_dh_04.gif' width=\"2\"></td>");
	
				} else {
					
					String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
					String target = PortalUtil.getLayoutTarget(layout);
					sb.append("<td>");
					sb.append("<a class=\"ag_K11\" href=\"");
					sb.append(layoutURL);
					sb.append("\" ");
					sb.append(target);
					sb.append("> ");
					sb.append(layout.getName(themeDisplay.getLocale()));
					sb.append("</a>");
					sb.append("</td>");
				}
			}
			}
			sb.append(" </tr></table></td>");
			sb.append("<td width=\"5\" background='");
			sb.append(themeDisplay.getPathThemeImage());
			sb.append("/top/agloco_dh_02.gif'>");
			sb.append("<img src='");
			sb.append(themeDisplay.getPathThemeImage());
			sb.append("/top/agloco_dh_03.gif' width=\"5\" height=\"23\"></td>");
}%>

<%!private void _buildSiteMap(List layouts, ThemeDisplay themeDisplay,StringBuffer sb,String layoutId) throws Exception {			

		if ( (layoutId==null) || !layoutId.equals("PUB.1001.23")) {
			sb.append("<td width=\"5\"><img src='");
			sb.append(themeDisplay.getPathThemeImage());
			sb.append("/top/agloco_dh_01.gif' width=\"5\" height=\"23\"></td>");
			sb.append("<td background='");
			sb.append(themeDisplay.getPathThemeImage());
			sb.append("/top/agloco_dh_02.gif'>");
			sb.append(" <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			sb.append(" <tr align=\"center\">");
	
			
			for (int i = 0; i < layouts.size(); i++) {
				Layout layout = (Layout) layouts.get(i);
				if (i != layouts.size() - 1) {
					String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
					String target = PortalUtil.getLayoutTarget(layout);
	
					sb.append("<td background='");
					sb.append(themeDisplay.getPathThemeImage());
					sb.append("/top/agloco_dh_02.gif'>");
					sb.append("<a class=\"ag_K11\" href=\"");
					sb.append(layoutURL);
					sb.append("\" ");
					sb.append(target);
					sb.append("> ");
					sb.append(layout.getName(themeDisplay.getLocale()));
					sb.append("</a>");
					sb.append("</td>");
					sb.append("<td width=\"1\" bgcolor=\"#C0C0C0\"><img src='");
					sb.append(themeDisplay.getPathThemeImage());
					sb.append("/top/agloco_dh_04.gif' width=\"2\"></td>");
	
				} else {
					String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
					String target = PortalUtil.getLayoutTarget(layout);
					sb.append("<td>");
					sb.append("<a class=\"ag_K11\" href=\"");
					sb.append(layoutURL);
					sb.append("\" ");
					sb.append(target);
					sb.append("> ");
					sb.append(layout.getName(themeDisplay.getLocale()));
					sb.append("</a>");
					sb.append("</td>");
				}
			}
			
			sb.append(" </tr></table></td>");
			sb.append("<td width=\"5\" background='");
			sb.append(themeDisplay.getPathThemeImage());
			sb.append("/top/agloco_dh_02.gif'>");
			sb.append("<img src='");
			sb.append(themeDisplay.getPathThemeImage());
			sb.append("/top/agloco_dh_03.gif' width=\"5\" height=\"23\"></td>");
		
			
		}
	}%>
	
<div id="layout-outer-side-decoration">
<table width="790" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" align="center" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
		<td valign="top" align="center" bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="24%"><a href="<%= AglocoURL.HOME %>"> <img
					src="<%= themeDisplay.getPathThemeImage() %>/top/agloco_logo01.gif"
					width="210" height="71" border="0"></a></td>
				<td width="85%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<c:choose>
						<c:when test="<%= themeDisplay.isSignedIn() %>">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
											<c:if test="<%= showAdminControl == Boolean.TRUE%>">
												<div id="layout-my-places"><liferay-portlet:runtime portletName="<%= PortletKeys.MY_PLACES %>" /></div>
											</c:if>&nbsp;
											</td>
											<td align="right"><a
												href="<%= themeDisplay.getURLSignOut() %>"><img
												src="<%= themeDisplay.getPathThemeImage() %>/top/agloco_signout.gif"
												width="109" height="20" border="0"></a>&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right">
											<c:if test="<%= showAdminControl == Boolean.TRUE%>">
												<a href="javascript: void(0);"
															onClick="<%= themeDisplay.getURLAddContent() %>"><bean:message
															key="add-content" /></a> - 
												<a href="<%= themeDisplay.getURLPageSettings().toString() %>">
												<bean:message key="page-settings" /></a>&nbsp;&nbsp;
												<a href="/web/guest/sitemap"><font color="Blue">Guest Site Map</font></a>
											</c:if>&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:when>
					<c:otherwise>
						<tr>
							<td align="right" class="ag_K10">
								<a	href="<%= themeDisplay.getURLSignIn() %>"> 
									<img src="<%= themeDisplay.getPathThemeImage() %><bean:message	key="ag-signin-pic-path" />" width="109" height="20" border="0"></a>&nbsp;&nbsp;
								<a	href="/web/guest/home?p_p_id=82&p_p_action=1&p_p_state=normal&p_p_mode=view&p_p_col_id=null&p_p_col_pos=0&p_p_col_count=1&_82_struts_action=%2Flanguage%2Fview&languageId=<%=changeToLanguage %>">
									<c:if test="<%= ischinese == Boolean.TRUE %>">
										<img src="<%= themeDisplay.getPathThemeImage() %>/button/english.gif" width="102" height="20" border="0">													
									</c:if>
									<c:if test="<%= ischinese == Boolean.FALSE %>">
										<img src="<%= themeDisplay.getPathThemeImage() %>/button/chinese.gif" width="102" height="20" border="0">
									</c:if>
								</a>
							</td>
						</tr>
						<tr>
							<td>
								<img src="images/spacer.gif" width="1" height="20" border="0">
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
					<tr>
						<td align="right" class="ag_K11">
						<c:if test="<%= showblog == Boolean.TRUE %>">
							<a href="/web/guest/blogarticle">
							<img src="<%= themeDisplay.getPathThemeImage() %>/top/agloco_ball.gif" width="32" height="25" align="absmiddle"> 
							<font color="#1B649B">
								<bean:message key="blogs-title" />
								</a>:
							</font> 
							<agloco-util:article-content article="<%= com.agloco.Constants.ARTICLEID_AG_BLOGLINK%>"/>
						</c:if>
						</td>
					</tr>
				</table>
				</td>
				<td width="1%"><img width="4" height="1"></td>
			</tr>
			<tr>
				<td colspan="2"	background="<%= themeDisplay.getPathThemeImage() %>/top/agloco_topbg01.gif">
				<img src="<%= themeDisplay.getPathThemeImage() %><bean:message	key="agloco_logo02-pic-path" />" width="257" height="19"></td>
				<td width="1%" align="right"
					background="<%= themeDisplay.getPathThemeImage() %>/top/agloco_topbg02.gif"><img
					src="<%= themeDisplay.getPathThemeImage() %>/top/agloco_topbg02.gif"
					width="5" height="19"></td>
			</tr>
			<tr>
				<td colspan="3" background="<%= themeDisplay.getPathThemeImage() %>/top/agloco_topbg03.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="257">
						<img src="<%= themeDisplay.getPathThemeImage() %><bean:message	key="agloco_logo03-pic-path" />" width="257" height="23"></td>
						<td align="right">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<%= sb.toString() %>		
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="5" align="center" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
	</tr>
</table>
</div>
<%@ include file="/html/common/referer_common.jsp" %>
<%@ include file="/html/common/init.jsp" %>
<%@ include file="/html/common/init-ext.jsp" %>
<%@page import="com.agloco.exception.CannotCatchedException"%>

<link href="/html/themes/agloco/css/en_lightblue.css" rel="stylesheet" type="text/css">
<script src="/html/agloco/js/common.js"></script>
<script src="/html/agloco/js/mm_menu.js"></script>
<html dir="<bean:message key="lang.dir" />">

<head>
	<title><%=LanguageUtil.get(pageContext,"ag-my-profile-email-link-title") %></title>
	<meta name="description"  content='<%=LanguageUtil.get(pageContext,"ag-description").toString()%>'>
	<meta name="keywords"  content='<%=LanguageUtil.get(pageContext,"ag-keywords").toString()%>'>
	<liferay-util:include page='<%= Constants.TEXT_HTML_DIR + "/common/themes/top_head.jsp" %>' />
</head>

<STYLE type=text/css>
.headbg{
	BACKGROUND-ATTACHMENT: fixed;
	BACKGROUND-IMAGE: url(images/newhead_02.jpg);
	background-repeat: no-repeat;
	background-position: left top;
}
</STYLE>

<body bgcolor="#F6FBF4" leftmargin="0" topmargin="5" marginwidth="0" marginheight="0">


<center>
<%@ include file="/html/themes/agloco/templates/top.jsp" %>
<% String memberCode = (String)request.getAttribute("memberCode"); %>
<div id="layout-outer-side-decoration">
  <table width="790"  border="0" cellpadding="0" cellspacing="0">
    
    <tr bgcolor="#FFFFFF">
      <td align="center" valign="top">&nbsp; </td>
      <td align="center" valign="top"><br>
      <%
      	if(memberCode == null || "".equalsIgnoreCase(memberCode) || "null".equalsIgnoreCase(memberCode)){
      %>
      	<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" rowBreak="<br/>"/>
      <%		
      	}
      	else{
      %>
        <table width="760" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="8"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-spacer") %>" width="1" height="1"></td>
          </tr>
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td height="38" align="center"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-email-link-title-congratulation") %>" width="682" height="31"></td>
                </tr>
                <tr>
                  <td height="300" align="right" valign="top"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-email-link-step3") %>" width="430" height="70" hspace="20">
                    <table width="100%" border="0" align="center" cellpadding="20" cellspacing="0" class="ag_k11">
                    <tr>
                      <td colspan="2" class="ag_k14b"><div align="center"><%=LanguageUtil.get(pageContext,"ag-sign-up-email-link-you-are-member") %></div></td>
                    </tr>
                    <tr>
                      <td colspan="2" class="ag_k14b"><div align="center"><%=LanguageUtil.get(pageContext,"ag-sign-up-email-link-your-member-id") %> <span class="ag_r18b"><%=memberCode %></span></div></td>
                    </tr>
                    <tr>
                      <td class="ag_k14b"><div align="right"><a href="<%=AglocoURL.REFERRALCENTER %>"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-email-link-button-i-am-ready") %>" width="180" height="35" border="0"></a></div></td>
                      <td class="ag_k14b"><div ><a href="<%=AglocoURL.MYACCOUNT %>"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-email-link-button-show-me") %>" width="180" height="35" border="0"></a></div></td>
                    </tr>
                    <tr>
						<td colspan="2" class="ag_k14b">
							<div align="center">
								<%=LanguageUtil.get(pageContext,"ag-sign-up-email-link-new-comment") %>
							</div>
						</td>
					<tr>
                  </table></td>
                </tr>
            </table>
            <%
      			}
            %>
            </td>
          </tr>
        </table>
        <br></td>
      <td align="center" valign="top">&nbsp;</td>
    </tr>
   
  </table>
 </div> 
<%@ include file="/html/themes/agloco/templates/bottom.jsp" %>
</center>
</body>

</html>
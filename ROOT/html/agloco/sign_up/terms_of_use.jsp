<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://easyconf.sourceforge.net/tags-easyconf" prefix="easyconf" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portlet.LiferayWindowState" %>
<%@ page import="javax.portlet.WindowState" %>
<%@ page import="com.liferay.portal.language.LanguageUtil" %>
<%@ page import="com.liferay.portlet.PortletURLUtil" %>
<%@ page import="com.agloco.exception.CannotCatchedException"%>
<%@ page import="com.agloco.exception.UserPasswordConfirmException" %>
<%@ page import="com.agloco.exception.UserPasswordAuthenticateException" %>

<%@ include file="/html/portal/init.jsp" %>

<form action="<%= themeDisplay.getPathMain() %>/portal/update_terms_of_use" name="update_terms_of_use_fm" method="post">
 <center> 
  <table width="790"  border="0" cellpadding="0" cellspacing="0" bgcolor="white">
	<tr>
	<c:if test="<%= SessionErrors.contains(request, CannotCatchedException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-can-not-catched-exception")%> </span>
	</c:if>
	</tr>
	<tr>
	<c:if test="<%= SessionErrors.contains(request, UserPasswordConfirmException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-my-profile-please-input-validate-new-password-confirm")%> </span>
	</c:if>
	</tr>
	<tr>
	<c:if test="<%= SessionErrors.contains(request, UserPasswordAuthenticateException.class.getName()) %>">
		<span class="portlet-msg-error" style="font-size: small;"> <%=LanguageUtil.get(pageContext, "ag-my-profile-please-input-validate-new-password")%> </span>
	</c:if>
	</tr>
    <tr bgcolor="#FFFFFF">
      <td align="center" valign="top">&nbsp; </td>
      <td align="center" valign="top"><br>
        <table width="760" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td valign="top">
                  <table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" class="k11">
                  	  <tr>
                  	  	<td colspan=5 align="center" >
                  	  		<div><span class="ag_r11"><%= LanguageUtil.get(pageContext, "ag-tips-when-member-first-login") %></span></div>
                  	  		<br>
                  	  	</td>                  	  	
                  	  </tr>
                      <tr align="center">
                        <td align="right" width="300"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-my-profile-new-password") %></span><span class="ag_r11">*</span></div></div>
                        </td>
                        <td colspan="4" class="ag_r10b" align="left">
                        	<input name="newPassword" type="password"  size="30"/>
                        </td>
                      </tr>
                      
                      <tr align="center">
                        <td align="right"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-my-profile-new-password-confirm") %></span><span class="ag_r11">*</span></div></div>
                        </td>
                        <td colspan="4" class="ag_r10b" align="left">
                        	<input name="newPasswordCfm" type="password"  size="30"/>
                        </td>
                      </tr>
                       <tr>
            
                        <td colspan="5" class="ag_r10b" align="center">
                            <input class="ag_bt0" type="button" name="btn_submit" value="<%= LanguageUtil.get(pageContext,"ag-my-profile-save") %>" id="btn_submit" onClick="formSubmit(this)"/>
                        </td>
                      </tr>
                  </table>
                  </td>
                </tr>
            </table></td>
          </tr>
        </table>
      <br></td>
      <td align="center" valign="top">&nbsp;</td>
    </tr>
    
  </table>
</center>
</form>

<script>
	function formSubmit(obj)
	{
		obj.disabled=true;
		document.forms["update_terms_of_use_fm"].submit();
	}
</script>
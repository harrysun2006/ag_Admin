<%@ include file="/html/portlet/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="javax.portlet.WindowState" %>
<%@ page import="com.agloco.exception.CannotCatchedException"%>
<%@ page import="com.agloco.exception.DuplicateEmailAddressException" %>
<%@ page import="com.agloco.exception.UserEmailAddressConfirmException" %>
<%@ page import="com.liferay.portal.UserEmailAddressException" %>
<%@ page import="com.agloco.AglocoURL" %>

<%@page import="com.liferay.portal.language.LanguageUtil"%>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>
<script src="/html/agloco/js/viewpage.js" type="text/javascript"></script>

<portlet:defineObjects />



<%

	Boolean redirectToHome = (Boolean)request.getAttribute("redirectToHome");
	
	Boolean displayButton = (Boolean)request.getAttribute("displayButton");
	String buttonURL = (String)request.getAttribute("buttonURL");
	
%>

<script type="text/javascript">
<!--
	if(<%=redirectToHome %> == true){
		self.location='<%=AglocoURL.HOME%>';
	}
		
//-->
</script>

<form
	action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/sign_up_change_email/view" />
	</portlet:actionURL>" method="post" name="<portlet:namespace />changeEmailForm">
	
	 <table width="790"  border="0" cellpadding="0" cellspacing="0" bgcolor="white">
		<tr>
			<td colspan="3">
				<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
				<liferay-ui:error exception="<%= DuplicateEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" />
				<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="ag-please-enter-a-valid-email-address" />
				<liferay-ui:error exception="<%= UserEmailAddressConfirmException.class %>" message="ag-please-enter-a-valid-confirm-email-address" />
			</td>
		</tr>
		<tr>
			<td colspan="3">	
				<input type="hidden" name="userId" value="<bean:write name="changeEmailForm" property="userId"/>">
			</td>
		</tr>
		
		<tr bgcolor="#FFFFFF">
	      <td align="center" valign="top">&nbsp; </td>
	      <td align="center" valign="top"><br>
	     	 <table width="760" border="0" cellpadding="0" cellspacing="0">
	     	 	<tr>	
	     	 		<td>
	     	 			<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" class="k11">
	     	 				<tr>
				                  <td colspan="2" class="ag_r11">&nbsp;&nbsp;* <%=LanguageUtil.get(pageContext,"ag-required-fields") %></td>
				            </tr>
							<tr>
				                <td class="ag_k12" width="33%">
				                 	<div align="right">
				                      <div>
				                      	<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-email") %></span>
				                      	<span class="ag_r11">*</span>
				                      </div>
				                  	</div>
				                 </td>
								<td><input name="<portlet:namespace />emailAddress"   type="text"  style="width:260px" value="<bean:write name="changeEmailForm" property="emailAddress"/>"   maxlength="50"/></td>
							</tr>
							<tr>
								<td class="ag_k12">
				                 	<div align="right">
				                      <div>
				                      	<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-confirm-email") %></span>
				                      	<span class="ag_r11">*</span>
				                      </div>
				                  	</div>
				                 </td>
								<td><input name="<portlet:namespace />emailAddressCfm" type="text"  style="width:260px" value="<bean:write name="changeEmailForm" property="emailAddressCfm"/>" maxlength="50"/></td>
							</tr>
							
							<tr>
								<td class="ag_k12" colspan="2">
									<div  align="center">
										<input type="button" class="ag_bt0" value="<%= LanguageUtil.get(pageContext,"ag-my-profile-save") %>" id="btn_submit" onclick="javascript:<portlet:namespace />submitForm()"/>
									</div>
								</td>
							</tr>
	     	 			</table>
	     	 		</td>
	     	 	</tr>
	     	 </table>
	      	 <br>
	      </td>
     	 <td align="center" valign="top">&nbsp;</td>	 
      
	</table>
	
</form>	

<script type="text/javascript">

	function <portlet:namespace />submitForm(){
		var f = document.forms["<portlet:namespace />changeEmailForm"];
		f.elements["btn_submit"].disabled = true;
		f.submit();

	}
	
</script>

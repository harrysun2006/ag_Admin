<%@ include file="/html/portlet/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.agloco.exception.EmptyMemberCodeException"%>
<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/decryptMemberInfo/decrypt" /></portlet:actionURL>" method="post" name="decryptMemberInfoForm">
	<table border="0" width="100%">
		<tr>
			<liferay-ui:error exception="<%= EmptyMemberCodeException.class %>" message="ag-member-info-empty" />
		</tr>

		<tr>
			<table bgcolor="#FFFFFF" width="100%">
				<tr>
					<td class="ag_k11" nowrap>Encrypted Member Code: </td>
					<td class="ag_k11">
						<input name="<portlet:namespace />encryptMemberCode" type="text" size="20" value="<bean:write name="decryptMemberInfoForm" property="encryptMemberCode"/>" />
					</td>
				</tr>
				<tr>
					<td class="ag_k11" nowrap>Decrypted Member Code: </td>
					<td class="ag_k11">
						<bean:write name="decryptMemberInfoForm" property="decryptMemberCode" />
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" class="ag_bt0" id="btn_submit" value="Submit" />
					</td>
				</tr>
			</table>
		</tr>
	</table>
	<table>
</form>

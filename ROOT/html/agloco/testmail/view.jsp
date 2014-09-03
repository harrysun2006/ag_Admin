<%@ page import="com.agloco.AglocoURL" %>
<%@ page import="com.liferay.portal.UserEmailAddressException" %>
<%@ page import="com.agloco.exception.CannotCatchedException"%>
<%@ taglib uri="/WEB-INF/tld/agloco-util.tld" prefix="agloco-util" %>
<%@ include file="/html/common/init.jsp"%>
<portlet:defineObjects />
<%
String checked = "";
String selected = "";
List articles = new ArrayList();
%>
<logic:iterate id="article" name="testMailForm" property="articles" indexId="articleIdx">
<%articles.add(article); %>
</logic:iterate>
<script language="javascript">
<!--
NS4 = (document.layers) ? 1 : 0;
IE4 = (document.all) ? 1 : 0;
ver4 = (NS4 || IE4) ? 1 : 0;
function initTab() {
	if(IE4) {
		divColl = document.all.tags("DIV");
		for(i = 0; i < divColl.length; i++) {
			whichEl = divColl(i);
			if(whichEl.id.indexOf("Child") != -1) whichEl.style.display = "none";
		}
	}	else {
		divColl = document.getElementsByName("testDiv");
		for(i = 0; i < divColl.length; i++) {
			whichEl = divColl[i];
			if(whichEl.id.indexOf("Child") != -1) whichEl.style.display = "none";
		}
	}
}

function expandTab(el) {
	initTab();
	whichEl = document.getElementById("TAB" + el + "Child");
	if(whichEl.style.display == "none") {
		whichEl.style.display = "block";
		whichEl.style.visibility = "visible";
	} else {
		whichEl.style.display = "none";
		whichEl.style.visibility = "hidden";
	}
	for(i = 1; i <= 2; i++) {
		whichEl = document.getElementById("TAB" + i);
		if(el == i) {
			whichEl.style.backgroundImage = "url(/html/agloco/images/" + "frame_bg_b.jpg)";
		} else	{
			whichEl.style.backgroundImage ="url(/html/agloco/images/" + "frame_bg_g.jpg)";
		}
	}
	var dispaly1 = document.getElementById("CORN1");
	dispaly1.src = "/html/agloco/images/frame_L_G.jpg"
	if(el == 1) dispaly1.src = "/html/agloco/images/frame_L_B.jpg"
	var dispaly2 = document.getElementById("CORN2");
	dispaly2.src = "/html/agloco/images/frame_g_g.jpg"
	if(el == 1) dispaly2.src = "/html/agloco/images/frame_b_g.jpg"
	if(el == 2) dispaly2.src = "/html/agloco/images/frame_g_b.jpg"
	var dispaly3 = document.getElementById("CORN3");
	dispaly3.src = "/html/agloco/images/frame_r_g2.jpg"
	if(el == 2) dispaly3.src = "/html/agloco/images/frame_r_b.jpg"
}
//-->
</script>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
	<tbody>
		<tr>
			<td colspan="6">
				<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
				<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="ag-please-enter-a-valid-email-address" />
			</td>
		</tr>
		<tr>
			<td width="6"><img id="CORN1" height="22" alt="" width="6" src="/html/agloco/images/frame_L_B.jpg" /></td>
			<td id="TAB1" width="180" background="/html/agloco/images/frame_bg_b.jpg">
				<div align="center"><span class="k12b"><a class="text" onclick="expandTab(1); return false" href="#">Selectable Articles</a></span></div>
			</td>
			<td width="18"><img id="CORN2" height="22" alt="" width="18" src="/html/agloco/images/frame_g_g.jpg" /></td>
			<td id="TAB2" width="180" background="/html/agloco/images/frame_bg_g.jpg">
			<div align="center"><span class="k12b"><a class="text" onclick="expandTab(2); return false" href="#">Customized Mail</a></span></div>
			</td>
			<td width="18"><img id="CORN3" height="22" alt="" width="18" src="/html/agloco/images/frame_r_g2.jpg" /></td>
			<td background="/html/agloco/images/frame_r.jpg">&nbsp;</td>
		</tr>
	</tbody>
</table>
<bean:define id="selectedCharset" name="testMailForm" property="charset" type="java.lang.String"/>
<form action="<portlet:actionURL><portlet:param name="struts_action" value="/testmail/view" /></portlet:actionURL>" method="post" name="<portlet:namespace />form">
<input type="hidden" name="tab"/>
<input type="hidden" name="from" value="<bean:write name="testMailForm" property="from"/>">
<input type="hidden" name="protocol" value="<bean:write name="testMailForm" property="protocol"/>">
<input type="hidden" name="host" value="<bean:write name="testMailForm" property="host"/>">
<input type="hidden" name="to" value="<bean:write name="testMailForm" property="to"/>">
<div id="TAB1Child" style="DISPLAY: none" name="testDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
<tr>
	<td class="ag_k12"><table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#FFFFFF">
		<tr class="portlet-section-body" style="font-weight: normal;">
			<td class="ag_k12" width="100">From: </td>
			<td class="ag_k12"><bean:write name="testMailForm" property="from"/>(<bean:write name="testMailForm" property="protocol"/>, <bean:write name="testMailForm" property="host"/>)</td>
		</tr>
		<tr class="portlet-section-alternate" style="font-weight: normal;">
			<td class="ag_k12">TO: </td>
			<td class="ag_k12"><input type="text" name="to1" style="width:95%" value="<bean:write name="testMailForm" property="to"/>"></td>
		</tr>
	</table></td>
</tr>
<tr>
	<td class="ag_k12"><table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#FFFFFF">
		<tr class="portlet-section-header" style="font-weight: bold;">
			<td class="ag_k13b" align="center">Article ID</td>
			<td class="ag_k13b" align="center">Title</td>
		</tr>
		<logic:iterate id="article" name="allArticles" indexId="articleIdx">
		<bean:define id="articleId" name="article" property="articleId"/>
<%
	if(articles.contains(articleId)) checked = " checked";
	else checked = "";
	if (articleIdx.longValue() % 2 == 0) {%> 
		<tr class="portlet-section-body" 
			style="font-weight: normal;" 
			onMouseEnter="this.className = 'portlet-section-body-hover';" 
			onMouseLeave="this.className = 'portlet-section-body';">
<%} else { %>
		<tr class="portlet-section-alternate" 
			style="font-weight: normal;" 
			onMouseEnter="this.className = 'portlet-section-alternate-hover';" 
			onMouseLeave="this.className = 'portlet-section-alternate';">
<%}

			%>
			<td class="ag_k12"><input type="checkbox" name="articles" value="<bean:write name="article" property="articleId"/>"<%=checked%>>&nbsp;<bean:write name="article" property="articleId"/></td>
			<td class="ag_k12"><bean:write name="article" property="title"/></td>
		</tr>
		</logic:iterate>
	</table></td>
</tr>
<tr class="portlet-section-header" style="font-weight: bold;">
	<td align="right" class="ag_k12">
		<input type="button" class="ag_bt0" onclick="javascript:selectAll()" value="Select All">
		<input type="button" class="ag_bt0" onclick="javascript:selectNone()" value="Select None">
		<input type="button" class="ag_bt0" onclick="javascript:selectReverse()" value="Select Reverse">
		<input type="button" class="ag_bt0" id="btn_send" name="btn_send" value="Send" onclick="javascript:send(1)">&nbsp;&nbsp;
	</td>
</tr>
</table>
</div>
<div id="TAB2Child" style="DISPLAY: none" name="testDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
<tr>
	<td class="ag_k12"><table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#FFFFFF">
		<tr class="portlet-section-alternate" style="font-weight: normal;">
			<td class="ag_k12" width="100">From: </td>
			<td class="ag_k12"><bean:write name="testMailForm" property="from"/>(<bean:write name="testMailForm" property="protocol"/>, <bean:write name="testMailForm" property="host"/>)</td>
		</tr>
		<tr class="portlet-section-body" style="font-weight: normal;">
			<td class="ag_k12">TO: </td>
			<td><input type="text" name="to2" style="width:95%" value="<bean:write name="testMailForm" property="to"/>"></td>
		</tr>
		<tr class="portlet-section-alternate" style="font-weight: normal;">
			<td class="ag_k12">CC: </td>
			<td><input type="text" name="cc" style="width:95%" value="<bean:write name="testMailForm" property="cc"/>"></td>
		</tr>
		<tr class="portlet-section-body" style="font-weight: bold;">
			<td class="ag_k12">BCC: </td>
			<td><input type="text" name="bcc" style="width:95%" value="<bean:write name="testMailForm" property="bcc"/>"></td>
		</tr>
		<tr class="portlet-section-alternate" style="font-weight: bold;">
			<td class="ag_k12">Subject: </td>
			<td><input type="text" name="subject" style="width:95%" value="<bean:write name="testMailForm" property="subject"/>"></td>
		</tr>
		<tr class="portlet-section-body" style="font-weight: bold;">
			<td class="ag_k12">Charset: </td>
			<td class="ag_k12">
				<select name="charset" style="width:95%">
				<logic:iterate id="charset" name="allCharsets" indexId="charsetIdx" type="java.lang.String">
				<%
					if(charset.equalsIgnoreCase(selectedCharset)) selected = " selected";
					else selected = "";
				%>
					<option value="<bean:write name="charset"/>"<%= selected %>><%= charset.toUpperCase() %>
				</logic:iterate>
				</select>
			</td>
		</tr>
		<tr class="portlet-section-alternate" style="font-weight: bold;" valign="top" height="100%">
			<td class="ag_k12">Content: </td>
			<td><textarea name="content" rows="6" style="width:95%"><bean:write name="testMailForm" property="content"/></textarea></td>
		</tr>
	</table></td>
</tr>
<tr class="portlet-section-header" style="font-weight: bold;">
	<td align="right" class="ag_k11">
		<input type="button" class="ag_bt0" id="btn_send" name="btn_send" value="Send" onclick="javascript:send(2)">&nbsp;&nbsp;
	</td>
</tr>
</table>
</div>
</form>
<script language="javascript">
<!--
expandTab(<bean:write name="testMailForm" property="tab"/>);

function selectAll() {
	var f = document.forms["<portlet:namespace />form1"];
	var articles = f.elements["articles"];
	var i;
	if(articles && (articles.length == undefined)) articles.checked = true;
	for(i = 0; i < articles.length; i++) {
		articles[i].checked = true;
	}
}

function selectNone() {
	var f = document.forms["<portlet:namespace />form1"];
	var articles = f.elements["articles"];
	var i;
	if(articles && (articles.length == undefined)) articles.checked = false;
	for(i = 0; i < articles.length; i++) {
		articles[i].checked = false;
	}
}

function selectReverse() {
	var f = document.forms["<portlet:namespace />form1"];
	var articles = f.elements["articles"];
	var i;
	if(articles && (articles.length == undefined)) articles.checked = !articles.checked;
	for(i = 0; i < articles.length; i++) {
		articles[i].checked = !articles[i].checked;
	}
}

function validate() {
	var f = document.forms["<portlet:namespace />form"];
	var to_ = f.elements["to"].value;
	if(to_ == "") {
		alert("please input the TO emailAddress!");
		f.elements["to"].focus();
		return false;
	}
	return true;
}

function send(i) {
	var f = document.forms["<portlet:namespace />form"];
	f.elements["tab"].value = i;
	f.elements["to"].value = f.elements["to" + i].value;
	if(!validate()) return;
	var sends = f.elements["btn_send"];
	if(sends && (sends.length == undefined)) sends.disabled = true;
	for(i = 0; i < sends.length; i++) {
		sends[i].disabled = true;
	}
	//submitForm(f, '<portlet:actionURL><portlet:param name="struts_action" value="/testmail/view" /></portlet:actionURL>');
	//submitForm(f, '<liferay-portlet:renderURL anchor="false"><portlet:param name="struts_action" value="/testmail/view" /></liferay-portlet:renderURL>');
	//loadForm(document.<portlet:namespace />form, '<liferay-portlet:renderURL anchor="false"><portlet:param name="struts_action" value="/testmail/view" /></liferay-portlet:renderURL>', 'p_p_id<portlet:namespace />');
	f.submit();
}
//-->
</script>

<%@ include file="/html/portlet/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="javax.portlet.WindowState" %>
<%@page import="com.agloco.exception.CannotCatchedException"%>

<%@ page import="com.liferay.portal.language.LanguageUtil" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.portlet.*"%>
<%@page import="com.agloco.model.AGConfig"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<script type="text/javascript" src='<%=renderResponse.encodeURL(renderRequest.getContextPath() + "dwr/engine.js")%>'></script>
<script type="text/javascript" src='<%=renderResponse.encodeURL(renderRequest.getContextPath() + "dwr/util.js")%>'></script>
<script type="text/javascript" src='<%=renderResponse.encodeURL(renderRequest.getContextPath() + "dwr/interface/ConfigAjax.js")%>'></script>

<script type="text/javascript">
<!--
	redirectToLogin();
//-->
</script>
<%
	List list = (List) request.getAttribute("list");
	if(list == null || list.size() < 1){
		list = null;
	}
%>
<script language="javascript">

	function configTypeSelect(selectId,selectIndex) {
		var f = document.forms["manageConfigForm"];
		var s = f.elements[selectId];
		s[selectIndex].selected = true;
	}
	
</script>
<div id="layout-outer-side-decoration">
<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/manage_config/list" /></portlet:actionURL>"  method="post" name="manageConfigForm">
	<table border="0" width="100%">
		<tr>
			<td>
				<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
			</td>
		</tr>
		<tr>
			<td>
				<div id="<portlet:namespace />msg" name="<portlet:namespace />msg" style="color:red;"></div>
			</td>
		</tr>
	</table>
		
	<div>
		<table border="0" width="100%" cellspacing="1" cellpadding="1" bgcolor="green">
			<tr bgcolor="#E1F6D7">
				<td align="left" width="20%">
					Config Name
				</td>
				<td align="left" width="30%">
					Config Value
				</td>
				<td align="left" width="10%">
					Config Type
				</td>
				<td align="left" width="20%">
					Config Description
				</td>
				<td align="left" nowrap>
					Operation
				</td>
			</tr>
			<c:if test="<%=list == null %>">
				<tr>
					<td colspan="5" align="left" bgcolor="#FFFFFF">
						there is no configs &nbsp;
					</td>
				</tr>
			</c:if>
			
			<c:if test="<%=list != null%>">
			<%
				AGConfig cfg = null;
				int i = 0;
				String operationText = "";
				for(Iterator it = list.iterator(); it.hasNext();){
					cfg = (AGConfig) it.next();
					operationText = LanguageUtil.get(pageContext, "ag-config-" + cfg.getPrimaryKey().getName());
			%>		
			
				<tr bgcolor="#FFFFFF">
					<td bgcolor="#FFFFFF" title="<%=cfg.getPrimaryKey().getName() %>">
						<c:if test="<%=cfg.getPrimaryKey().getName().length()>=20 %>">
							<%=cfg.getPrimaryKey().getName().substring(0,20) %>&nbsp;
						</c:if>
						<c:if test="<%=cfg.getPrimaryKey().getName().length()<20 %>">
							<%=cfg.getPrimaryKey().getName() %>&nbsp;
						</c:if>
						
						<input name="<portlet:namespace />configName<%=i %>" type="hidden" value="<%=cfg.getPrimaryKey().getName() %>"/>
						<input name="<portlet:namespace />companyId<%=i %>"  type="hidden" value="<%=cfg.getPrimaryKey().getCompanyId() %>"/>
					</td>
					<td>
						<c:if test="<%=StringUtils.isNotBlank(cfg.getValue()) %>">
							 <input name="<portlet:namespace />configValue<%=i %>" type="text"  style="width:220px;"  value="<%=cfg.getValue() %>" property="configValue<%=i %>" readOnly="true"/>
						</c:if>
						<c:if test="<%=StringUtils.isNotBlank(cfg.getContent()) %>">
							 <input name="<portlet:namespace />configValue<%=i %>" type="text"  style="width:220px;" value="<%=cfg.getContent() %>" property="configValue<%=i %>" readOnly="true"/>
						</c:if>
						<c:if test="<%=StringUtils.isBlank(cfg.getValue()) && StringUtils.isBlank(cfg.getContent())  %>">
							 <input name="<portlet:namespace />configValue<%=i %>" type="text"  style="width:220px;"  value="" property="configValue<%=i %>" readOnly="true"/>
						</c:if>
					</td>
					<td bgcolor="#FFFFFF">
						<select id ="<portlet:namespace />configType<%=i %>" onchange="javascript:resetText('<%=i %>');" name="<portlet:namespace />configType<%=i %>" disabled="true">
							<option value="E">enable</option>
							<option value="<%=AGConfig.AG_CONFIG_TYPE_DISABLE %>">disable</option>
						</select>
						<c:if test="<%=cfg.getType().equalsIgnoreCase("X") %>">
							<script language="javascript">
								configTypeSelect("<portlet:namespace />configType<%=i %>", 1);
							</script>
						</c:if>

					</td>
					<td bgcolor="#FFFFFF" title="<%=operationText %>">
						<c:if test="<%=operationText.length() >= 18 %>">
							<%=operationText.substring(0,18) %>
						</c:if>
						<c:if test="<%=operationText.length() < 18 %>">
							<%=operationText %>
						</c:if>
						&nbsp;
					</td>
					<td bgcolor="#FFFFFF" nowrap>
						<table>
							<tr>
								<td><input type="button" style="width:50px;" class="ag_bt0" name="<portlet:namespace />btn_modify<%=i %>" id="<portlet:namespace />btn_modify<%=i %>" onClick="javascript:modifyConfig('<%=i %>')" value="Modify"></td>
								<td><input type="button" style="width:50px;display:none;" class="ag_bt0" name="<portlet:namespace />btn_save<%=i %>"   id="<portlet:namespace />btn_save<%=i %>"   onClick="javascript:saveConfig('<%=i %>')"   value="Save"></td>
								<td><input type="button" style="width:50px;display:none;" class="ag_bt0" name="<portlet:namespace />btn_cancel<%=i %>" id="<portlet:namespace />btn_cancel<%=i %>" onClick="javascript:cancelConfig('<%=i %>')" value="Cancel"></td>
							</tr>
						</table>	
					</td>
				</tr>
			<%	
					i++;
				}
			%>

			</c:if>	
			
		</table>
	</div>		
		
	<table border="0" width="100%">	

		<tr>
			<td>
				<input name="<portlet:namespace />actionName" type="hidden"/>		
			</td>
		</tr>
	</table>
</form>
</div>
<script language="javascript">

	function displayButton(btnId,btnDisplay) {
		var f = document.forms["manageConfigForm"];
		var b = f.elements[selectId];
		b.style.display = btnDisplay;
	}
	
	function refreshMsg(msg){
		var e_msg = document.getElementById("<portlet:namespace />msg");
		if(e_msg){
			e_msg.innerHTML = msg;
			if(msg != ""){
				e_msg.scrollIntoView(true);
			}
			
		}
		
	}
	
	function modifyConfig(selectIndex) {
		var f = document.forms["manageConfigForm"];
		var btn_modify = f.elements["<portlet:namespace />btn_modify"+selectIndex];
		var btn_save   = f.elements["<portlet:namespace />btn_save"  +selectIndex];
		var btn_cancel = f.elements["<portlet:namespace />btn_cancel"+selectIndex];
		btn_modify.style.display = "none";
		btn_save.style.display   = "block";
		btn_cancel.style.display = "block";
		refreshSelect(selectIndex,false);
		resetText(selectIndex); 
		refreshMsg("");
		//refreshText(selectIndex,false);
	}
	
	function saveConfig(selectIndex) {
	
		var f = document.forms["manageConfigForm"];
		var configName  = f.elements["<portlet:namespace />configName" +selectIndex].value;
		var companyId   = f.elements["<portlet:namespace />companyId"  +selectIndex].value;
		var configValue = f.elements["<portlet:namespace />configValue"+selectIndex].value;
		var element     = f.elements["<portlet:namespace />configType" +selectIndex];
		var configType  = element[element.selectedIndex].value;
		ConfigAjax.updateConfig(configName,companyId,configValue,configType,{callback:function(data){
			if(data == 'success'){
				var btn_modify = f.elements["<portlet:namespace />btn_modify"+selectIndex];
				var btn_save   = f.elements["<portlet:namespace />btn_save"  +selectIndex];
				var btn_cancel = f.elements["<portlet:namespace />btn_cancel"+selectIndex];
				btn_modify.style.display = "block";
				btn_save.style.display   = "none";
				btn_cancel.style.display = "none";
				refreshSelect(selectIndex,true);
				resetText(selectIndex);
				refreshMsg("save success");
				return; 
			}
			else if(data == 'empty'){
				refreshMsg("config value can't be empty!");
				return; 
			}
			else if(data == 'permission.deny'){
				refreshMsg("permission deny!please sign in with admin user...");
				return;
			}	
			
			refreshMsg("system busy, please try again later");
			return;		
		}});
		
		//refreshText(selectIndex,true);
	}
	
	function cancelConfig(selectIndex) {
		var f = document.forms["manageConfigForm"];
		var configName    = f.elements["<portlet:namespace />configName" +selectIndex].value;
		var companyId     = f.elements["<portlet:namespace />companyId"  +selectIndex].value;
		var e_configValue = f.elements["<portlet:namespace />configValue"+selectIndex];
		var e_configType  = f.elements["<portlet:namespace />configType" +selectIndex];
		ConfigAjax.cancelConfig(configName,companyId,{callback:function(data){
			
			if(data[0] == 'success'){
				e_configValue.value = data[2];
				if('X' == data[1]){
					e_configType[1].selected = true;
				}
				else{
					e_configType[0].selected = true;
				}
				var btn_modify = f.elements["<portlet:namespace />btn_modify"+selectIndex];
				var btn_save   = f.elements["<portlet:namespace />btn_save"  +selectIndex];
				var btn_cancel = f.elements["<portlet:namespace />btn_cancel"+selectIndex];
				btn_modify.style.display = "block";
				btn_save.style.display   = "none";
				btn_cancel.style.display = "none";
				refreshSelect(selectIndex,true);
				resetText(selectIndex);
				refreshMsg("cancel success");
				return; 
			}
			else if(data[0] == 'permission.deny'){
				refreshMsg("permission deny!please sign in with admin user...");
				return;
			}	
			
			refreshMsg("system busy, please try again later");
			return;		
		}});
		var btn_modify = f.elements["<portlet:namespace />btn_modify"+selectIndex];
		var btn_save   = f.elements["<portlet:namespace />btn_save"  +selectIndex];
		var btn_cancel = f.elements["<portlet:namespace />btn_cancel"+selectIndex];
		btn_modify.style.display = "block";
		btn_save.style.display   = "none";
		btn_cancel.style.display = "none";
		refreshSelect(selectIndex,true);
		resetText(selectIndex); 
		//refreshText(selectIndex,true);
	}
	
	function resetText(index){
		var f = document.forms["manageConfigForm"];
		var s = f.elements["<portlet:namespace />configType"+index];
		if(s.disabled == true){
			refreshText(index,true);
			return;
		}
		if(s[0].selected == true){
			refreshText(index,false);
			return;		
		}
		refreshText(index,true);
	}
	
	function refreshText(index,status){
		var f = document.forms["manageConfigForm"];
		var t = f.elements["<portlet:namespace />configValue"+index];
		t.readOnly = status;
	}
	
	function refreshSelect(index,status){
		var f = document.forms["manageConfigForm"];
		var s = f.elements["<portlet:namespace />configType"+index];
		s.disabled = status;	
	}
	
</script>

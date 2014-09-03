<%@ include file="/html/portal/init.jsp" %>
<%@ include file="/html/common/referer_common.jsp" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@page import="com.liferay.portal.language.LanguageUtil"%>
<%@page import="com.agloco.AglocoURL" %>

<STYLE type=text/css>
.headbg{
	BACKGROUND-ATTACHMENT: fixed;
	BACKGROUND-IMAGE: url(images/newhead_02.jpg);
	background-repeat: no-repeat;
	background-position: left top;
}

A.sign_up2:LINK{color:blue;font:12px Verdana;text-decoration:none;line-height:18px;}
A.sign_up2:VISITED{color:blue;font:12px Verdana;text-decoration:none;line-height:18px;}
A.sign_up2:HOVER{color:blue;font:12px Verdana;text-decoration:underline; line-height:18px;}

</STYLE>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>

<%
	Boolean displayButton = (Boolean)request.getAttribute("displayButton");
	String buttonURL = (String)request.getAttribute("buttonURL");

%>

<body bgcolor="#F6FBF4" leftmargin="0" topmargin="5" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-finished-chn2") %>')">
<center>
  <table width="790"  border="0" cellpadding="0" cellspacing="0">
    
    <tr bgcolor="#FFFFFF">
      <td align="center" valign="top">&nbsp; </td>
      <td align="center" valign="top"><br>
        <table width="760" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="8"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-spacer") %>" width="1" height="1"></td>
          </tr>
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td height="38" align="center"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-finished-title-thank-you") %>" width="682" height="31"></td>
                </tr>
                <tr>
                  <td height="300" align="right" valign="top"><p><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-finished-step2") %>" width="430" height="70" hspace="20"></p>
                      <table width="90%" border="0" align="center" cellpadding="5" cellspacing="0" class="ag_k11">
                        <tr>
                          <td class="ag_r11"><div>
                              <div class="ag_k14b">
	                                <div align="center">
	                                	
	                                	<%=LanguageUtil.get(pageContext,"ag-sign-up-finished-sent-email") %> </br>
	                                	
	                                	<bean:write name="emailAddress" />
	                                	
	                                	<a class="sign_up2" href="<%=AglocoURL.CORRECT_EMAIL%>">(<%=LanguageUtil.get(pageContext,"ag-sign-up-finished-change-email-address") %>)</a>
	                                </div>
                              </div>
                              <div class="ag_k14b" align="center">
                              		<c:if test="<%=displayButton == Boolean.TRUE %>">
                              			<input type="button" class="ag_bt0" value="<%=LanguageUtil.get(pageContext,"ag-sign-up-finished-continue-to") %> <bean:write name="buttonValue"/> <%=LanguageUtil.get(pageContext,"ag-sign-up-finished-email-post") %>" onclick="javascript:openEmail('<%=buttonURL %>')" />
                              		</c:if>
                              </div>
                              <div class="ag_k14b"></div>
                              <div></div>
                          </div></td>
                        </tr>
                        <tr>
                          <td>
                          	<div>
                              <div align="center"><span class="ag_k12b"><%=LanguageUtil.get(pageContext,"ag-sign-up-finished-confirm-email") %></span></div>
                            </div>
                              <div>
                                <div align="center"></div>
                              </div>
                              <div align="left"></div>
                              <div align="left"></div>
                              <div align="left"></div>
                            </td>
                        </tr>
                        
                        <tr>
                          <td class="ag_k12b"><div align="center"><span class="ag_k12b"><%=LanguageUtil.get(pageContext,"ag-sign-up-finished-thank-you") %></span></div></td>
                        </tr>
                        
                         <tr>
                          <td class=ag_k12b><div align="center"><span class="ag_k12b"><%=LanguageUtil.get(pageContext,"ag-sign-up-finished-email") %></span></div></td>
                        </tr>
                        
                        <tr>
                          <td class="ag_k12b"><div align="left"><span class="ag_k12b"><%=LanguageUtil.get(pageContext,"ag-sign-up-finished-can-not-find-email") %></span></div></td>
                        </tr>
                       <tr>
                          <td class="ag_k12">
                     		<div align="left">
                          		<span class="ag_k12">
                          			<li><%=LanguageUtil.get(pageContext,"ag-sign-up-finished-review-spelling") %><b><bean:write name="emailAddress" /></b> <%=LanguageUtil.get(pageContext,"ag-sign-up-finished-if-it-is-incorrect") %><a class="sign_up2" href="<%=AglocoURL.CORRECT_EMAIL%>">&nbsp;<%=LanguageUtil.get(pageContext,"ag-sign-up-finished-correct-your-email") %></a></li>
                          			<li><%=LanguageUtil.get(pageContext,"ag-sign-up-finished-use-email-can-check-immediately") %><a class="sign_up2" href="<%=AglocoURL.CORRECT_EMAIL%>">&nbsp;<%=LanguageUtil.get(pageContext,"ag-sign-up-finished-change-your-email-address") %></a></li>
                          		</span>
                          	</div>
                          </td>
                        </tr>
                        
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
      <br></td>
      <td align="center" valign="top">&nbsp;</td>
    </tr>
  </table>
</center>

<script type="text/javascript">
	function openEmail(emailURL){
		window.open(emailURL);
	}
</script>

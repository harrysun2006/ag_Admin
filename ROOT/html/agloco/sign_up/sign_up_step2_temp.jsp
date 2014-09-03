

<%@ include file="/html/portal/init.jsp" %>
<%@ include file="/html/common/referer_common.jsp" %>

<%@page import="com.liferay.portal.language.LanguageUtil"%>
<%@page import="com.agloco.form.SignUpForm"%>
<STYLE type=text/css>
.headbg{
	BACKGROUND-ATTACHMENT: fixed;
	BACKGROUND-IMAGE: url(images/newhead_02.jpg);
	background-repeat: no-repeat;
	background-position: left top;
}
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
	String password = (String)request.getAttribute("memberPassword");	
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
                          <td>
                              <div class="ag_k14b">
                                <div align="center"><font color="red">
                                <strong>
	                                A confirmation email will be sent to your<br/>
	                                Hotmail / MSN account within the next few days.<br/>
	                                When that message arrives, you will be required to  <br/>
	                                log in and verify your membership.<br/>
                                </strong>
                                </font></div>
                              </div>
                          </td>
                        </tr>
                        
            
                        <tr>
                          <td class="ag_k11"><div align="center"><span class="ag_k11">In the meantime, here is your temporary password:</span></div></td>
                        </tr>
                        
                        <tr>
                          <td class="ag_k12b"><div align="center"><span class="ag_k12b"><%=password %> </span></div></td>
                        </tr>
	                  		
	                	 
	                	<tr>
                          <td class="ag_k11"><div align="center"><span class="ag_k11">
                          You can log into your AGLOCO account using this link:<br/>
                          <a href="https://www.agloco.com/c/portal/login">https://www.agloco.com/c/portal/login</a><br/>
                          </span></div></td>
                        </tr>
                        	
                        
                        <tr>
                          <td class="ag_k11"><div align="center"><span class="ag_k11">
                          <i>
	                          Please note that some mail services may put AGLOCO email into your junk or <br/>
	                          spam folder, so please check there. You can add <a target="_blank" href="mailto:emailservice@agloco.com">emailservice@agloco.com</a> to <br/>
	                          your email address book to reduce the chance of the email being marked as spam. <br/>
	                      </i>    
                          </span></div></td>
                        </tr>

                        
                        <tr>
                          <td class="ag_k11"><div align="center"><span class="ag_k11">
                          If you have further questions, please feel free to contact our<br/>
                          Member Services team at <a target="_blank" href="mailto:support@agloco.com">support@agloco.com</a>.<br/>  
                          </span></div></td>
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

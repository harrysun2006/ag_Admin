
<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.portal.language.LanguageUtil"%>
<%@ page import="com.agloco.AglocoURL" %>

<script src="/html/agloco/js/common.js" type="text/javascript"></script>

<center>
  <table width="790"  border="0" cellpadding="0" cellspacing="0">

    <tr bgcolor="#FFFFFF">
      <td align="center" valign="top">&nbsp; </td>
      <td align="center" valign="top"><br>
        <table width="760" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="8"><img src="/html/agloco/images/spacer.gif" width="1" height="1"></td>
          </tr>
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td height="38" align="center"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-my-profile-title-my-profile") %>" width="682" height="31"></td>
                </tr>
                <tr>
                  <td height="300" valign="top"><br>
                      <table width="90%" border="0" align="center" cellpadding="5" cellspacing="0" class="ag_k11">
                        <tr>
                          <td class="ag_k14b"><div align="center">
                              <div>
                                <div>
                                  <div><b><%=LanguageUtil.get(pageContext,"ag-my-profile-saved-your-profile-has-been-saved") %></b></div>
                                </div>
                                <div></div>
                              </div>
                          </div></td>
                        </tr>
                        <tr>
                          <td class="ag_k14b"><div align="center"><a href="<%=AglocoURL.MYACCOUNT %>" class="ag_b12d"><%=LanguageUtil.get(pageContext,"ag-my-profile-saved-go-to-my-home-page") %></a></div></td>
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
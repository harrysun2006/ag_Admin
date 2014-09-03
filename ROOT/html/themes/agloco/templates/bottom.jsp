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
<div id="layout-outer-side-decoration">
 <table width="790"  border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="108" valign="bottom" bgcolor="#FFFFFF">&nbsp;</td>
      <td height="108" valign="bottom">
      	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="108" align="center" background="<%= themeDisplay.getPathThemeImage() %>/bottom/agloco_bottom_bg.gif">&nbsp;</td>
          <td width="780" align="center" valign="top" background="<%= themeDisplay.getPathThemeImage() %>/bottom/agloco_bottom_bg.gif">
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" colspan="3">&nbsp;</td>
              </tr>
              <tr>
                <td width="14" height="30">&nbsp;</td>
                <td align="center" class="ag_K11">
                <a href="<%= AglocoURL.MEMBERSHIP_AGREEMENT %>" class="ag_K11"><bean:message key="ag-bootom-membership" /></a>&nbsp;-&nbsp;
                <a href="<%= AglocoURL.TERMS_OF_USE %>" class="ag_K11"><bean:message key="ag-bootom-terms" /></a>&nbsp;-&nbsp;
                <a href="<%= AglocoURL.PRIVACY %>" class="ag_K11"><bean:message key="ag-bootom-privacy" /></a>&nbsp;-&nbsp;
                <a href="<%= AglocoURL.ANTI_SPAM %>" class="ag_K11"><bean:message key="ag-bootom-anti" /></a>&nbsp;-&nbsp;
                <a href="<%= AglocoURL.PARTNERS %>" class="ag_K11"><bean:message key="ag-bootom-partners" /></a>&nbsp;-&nbsp;
                <a href="<%= AglocoURL.ABOUTUS %>" class="ag_K11"><bean:message key="ag-bootom-aboutus" /></a>&nbsp;-&nbsp;
                <a target="_blank" href="<%= AglocoURL.COMPANYBLOG %>" class="ag_K11"><bean:message key="ag-bootom-companyblog" /></a>&nbsp;-&nbsp;
                <a href="<%= AglocoURL.HELP_FAQ %>" class="ag_K11"><bean:message key="ag-bootom-help" /></a>
                </td>
                <td width="14">&nbsp;</td>
              </tr>
            </table>
              <div align="center" class="ag_K11" title="<%=request.getLocalName()%>"><bean:message key="ag-company-info" /></div>
              <div align="center" class="ag_K12"></div></td>
          <td align="center" background="<%= themeDisplay.getPathThemeImage() %>/bottom/agloco_bottom_bg.gif">&nbsp;</td>
        </tr>
      </table></td>
      <td height="108" valign="bottom" bgcolor="#FFFFFF">&nbsp;</td>
    </tr>
</table>
</div>

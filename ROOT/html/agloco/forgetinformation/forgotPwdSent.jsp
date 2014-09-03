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

<%@ include file="/html/portal/init.jsp" %>
<script src="/html/agloco/js/common.js" type="text/javascript"></script>
        <table width="790" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
          <tr>
            <td height="8"><br><img src="/html/agloco/images/spacer.gif" width="1" height="1"></td>
          </tr>
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td height="38" align="center"><img src="<%=LanguageUtil.get(pageContext, "ag-pic-forget-info-title")%>" width="682" height="31"></td>
                </tr>
                <tr>
                  <td height="400" valign="top"><p>&nbsp;</p>
                      <table width="90%" border="0" align="center" cellpadding="5" cellspacing="0" class="ag_k11">
                        <tr>
                          <td class="ag_k14b"><div align="center">
                              <SCRIPT 
      src="/html/agloco/js/flash.js" 
      type=text/javascript></SCRIPT>
                              <DIV id=johndesignflash></DIV>
                              <SCRIPT type=text/javascript>
// <![CDATA[
insertFlash('johndesignflash', '/html/agloco/images/sending.swf', 600, 30);
// ]]>
                    </SCRIPT>
                          </div></td>
                        </tr>
                        <tr>
                          <td class="ag_k14b"><div align="center">
                              <div>
                                <div>
                                  <div>
                                    <div><%=LanguageUtil.get(pageContext, "ag-a-temporary-password-has-been-sent")%></div>
                                    <div></div>
                                  </div>
                                </div>
                                <div></div>
                              </div>
                          </div></td>
                        </tr>
                        <tr>
                          <td class="ag_k14b"><div align="center"><a href="/" class="ag_b12d"><%=LanguageUtil.get(pageContext, "ag-homepage")%></a></div></td>
                        </tr>
                    </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
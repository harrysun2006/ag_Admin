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

<%@ include file="/html/portlet/polls/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "vote");

PollsQuestion question = (PollsQuestion)request.getAttribute(WebKeys.POLLS_QUESTION);

List choices = PollsChoiceLocalServiceUtil.getChoices(question.getQuestionId());

boolean hasVoted = PollsUtil.hasVoted(request, question.getQuestionId());
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/polls/view_question" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/polls/view_question" /><portlet:param name="questionId" value="<%= question.getQuestionId() %>" /></portlet:renderURL>">
<input name="<portlet:namespace />questionId" type="hidden" value="<%= question.getQuestionId() %>">

<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

<span style="font-size: small;"><b>
<%= question.getTitle() %>
</b></span><br>

<span style="font-size: x-small;">
<%= question.getDescription() %>
</span>

<br><br>

<c:choose>
	<c:when test='<%= tabs1.equals("vote") && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>'>
		<table border="0" cellpadding="0" cellspacing="0">

		<%
		Iterator itr = choices.iterator();

		while (itr.hasNext()) {
			PollsChoice choice = (PollsChoice)itr.next();
		%>

			<tr>
				<td>
					<input name="<portlet:namespace />choiceId" type="radio" value="<%= choice.getChoiceId() %>">
				</td>
				<td style="padding-left: 10px;"></td>
				<td>
					<b><%= choice.getChoiceId() %>.</b>
				</td>
				<td style="padding-left: 10px;"></td>
				<td>
					<%= choice.getDescription() %>
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "vote") %>' onClick="submitForm(document.<portlet:namespace />fm);">

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "see-current-results") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/polls/view_question" /><portlet:param name="tabs1" value="results" /><portlet:param name="questionId" value="<%= question.getQuestionId() %>" /></portlet:renderURL>';">
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/polls/view_question_results.jsp" %>

		<br><br>

		<b><%= LanguageUtil.get(pageContext, "charts") %>:</b>
		<a href="javascript: var viewChartWindow = window.open('<%= themeDisplay.getPathMain() %>/polls/view_chart?questionId=<%= question.getQuestionId() %>&chartType=area', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();"><%= LanguageUtil.get(pageContext, "area") %></a>,
		<a href="javascript: var viewChartWindow = window.open('<%= themeDisplay.getPathMain() %>/polls/view_chart?questionId=<%= question.getQuestionId() %>&chartType=horizontal_bar', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();"><%= LanguageUtil.get(pageContext, "horizontal-bar") %></a>,
		<a href="javascript: var viewChartWindow = window.open('<%= themeDisplay.getPathMain() %>/polls/view_chart?questionId=<%= question.getQuestionId() %>&chartType=line', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();"><%= LanguageUtil.get(pageContext, "line") %></a>,
		<a href="javascript: var viewChartWindow = window.open('<%= themeDisplay.getPathMain() %>/polls/view_chart?questionId=<%= question.getQuestionId() %>&chartType=pie', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();"><%= LanguageUtil.get(pageContext, "pie") %></a>,
		<a href="javascript: var viewChartWindow = window.open('<%= themeDisplay.getPathMain() %>/polls/view_chart?questionId=<%= question.getQuestionId() %>&chartType=vertical_bar', 'viewChart', 'directories=no,height=430,location=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,width=420'); void(''); viewChartWindow.focus();"><%= LanguageUtil.get(pageContext, "vertical-bar") %></a>

		<c:if test="<%= !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
			<br><br>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "back-to-vote") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/polls/view_question" /><portlet:param name="tabs1" value="vote" /><portlet:param name="questionId" value="<%= question.getQuestionId() %>" /></portlet:renderURL>';">
		</c:if>
	</c:otherwise>
</c:choose>

</form>
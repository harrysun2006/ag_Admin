package com.agloco.taglib.util;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.spring.JournalArticleLocalServiceUtil;

public class ArticleTitleTag extends TagSupport {

	private String _article;
	private static final Log _log = LogFactory.getLog(ArticleTitleTag.class);

	public String getArticle() {
		return _article;
	}

	public void setArticle(String article) {
		_article = article;
	}

	/**
	 * Process the start tag.
	 *
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		evaluate();
		return EVAL_PAGE;
	}

	private void evaluate() throws JspException {
		String r = "";
		try {
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			String companyId = themeDisplay.getCompanyId();
			Locale locale = themeDisplay.getLocale();
			String pathRoot = themeDisplay.getPathRoot();
			JournalArticle article = JournalArticleLocalServiceUtil.getArticle(companyId, _article);
			r = article.getTitle();
			JspWriter out = pageContext.getOut();
			out.write(r);
		} catch(Exception e) {
			// throw new JspException(e);
			_log.error(e, e);
		}
		JspWriter out = pageContext.getOut();
		try {
			out.write(r);
		} catch(IOException ioe) {
		}
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {
		super.release();
	}
}

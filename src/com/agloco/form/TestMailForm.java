package com.agloco.form;

import org.apache.struts.action.ActionForm;

public class TestMailForm extends ActionForm {

	private final static String[] EMPTY_STRINGS = {};

	private String from;
	private String protocol;
	private String host;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String content;
	private String charset;
	private String articleId;
	private Boolean success;
	private String[] articles;
	private String dispatch;
	private int tab = 1;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String[] getArticles() {
		return (articles == null) ? EMPTY_STRINGS : articles;
	}

	public void setArticles(String[] articles) {
		this.articles = articles;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getCharset() {
		return (charset == null) ? "" : charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

}

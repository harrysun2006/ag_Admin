package com.agloco.form;

import org.apache.struts.action.ActionForm;

import com.agloco.mail.MailMessage;
import com.agloco.util.DateUtil;

/**
 * 
 * @author terry_zhao
 *
 */
public class MailMessageForm extends ActionForm {

	private Long id;
	private MailMessage message;
	private String recipientAddress = "";
	private String recipientName = "";
	private String sendDateString;
	private String from;
	private String to;
	private String cc;
	
	public String getSendDateString() {
		return sendDateString;
	}

	public void setSendDateString(String strSendDate) {
		this.sendDateString = strSendDate;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}
	
	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}
	public String getRecipientName() {
		return recipientName;
	}
	
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MailMessage getMessage() {
		return message;
	}
	public void setMessage(MailMessage message) {
		this.message = message;
		
		try{
			String recipient = message.getRecipient();
			int start = recipient.indexOf("<");
			int end = recipient.indexOf(">");
			setRecipientName(recipient.substring(0, start));
			setRecipientAddress(recipient.substring(start+1, end));	
		}
		catch(Exception e){
			
		}
		
		setSendDateString(DateUtil.getDate2String(message.getSentDate(),DateUtil.DATE_PATTERN_ALL));
		setFrom(message.getSender());
		setTo(message.getRecipient());
		setCc(message.getCarbonCopy());
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}

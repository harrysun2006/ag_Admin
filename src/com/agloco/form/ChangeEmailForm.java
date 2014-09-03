package com.agloco.form;

import org.apache.struts.action.ActionForm;
/**
 * 
 * @author terry_zhao
 *
 */
public class ChangeEmailForm extends ActionForm {

	private String userId;
	private String emailAddress;
	private String emailAddressCfm;
	
	public String getEmailAddress() {
		return (emailAddress == null) ? "" : emailAddress.trim().toLowerCase();
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddressCfm() {
		return (emailAddressCfm == null) ? "" : emailAddressCfm.trim().toLowerCase();
	}
	public void setEmailAddressCfm(String emailAddressCfm) {
		this.emailAddressCfm = emailAddressCfm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

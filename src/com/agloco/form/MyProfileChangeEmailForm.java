package com.agloco.form;

import org.apache.struts.action.ActionForm;

/**
 * 
 * @author terry_zhao
 *
 */
public class MyProfileChangeEmailForm extends ActionForm {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5051059142362194230L;

	private String previousEmailAddress = "";
	
	private String newEmailAddress = "";
	
	private String newEmailAddressCfm = "";
	
	private String memberCode = "";

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getNewEmailAddress() {
		return (newEmailAddress == null) ? "" : newEmailAddress.trim().toLowerCase();
	}

	public void setNewEmailAddress(String newEmailAddress) {
		this.newEmailAddress = (newEmailAddress == null) ? "" : newEmailAddress.trim().toLowerCase();
	}

	public String getNewEmailAddressCfm() {
		return (newEmailAddressCfm == null) ? "" : newEmailAddressCfm.trim().toLowerCase();
	}

	public void setNewEmailAddressCfm(String newEmailAddressCfm) {
		this.newEmailAddressCfm = (newEmailAddressCfm == null) ? "" : newEmailAddressCfm.trim().toLowerCase();
	}

	public String getPreviousEmailAddress() {
		return (previousEmailAddress == null) ? "" : previousEmailAddress.trim().toLowerCase();
	}

	public void setPreviousEmailAddress(String previousPassword) {
		this.previousEmailAddress = (previousPassword == null) ? "" : previousPassword.trim().toLowerCase();
	}

	

}

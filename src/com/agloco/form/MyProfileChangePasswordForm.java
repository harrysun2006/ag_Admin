package com.agloco.form;

import org.apache.struts.action.ActionForm;

/**
 * 
 * @author terry_zhao
 *
 */
public class MyProfileChangePasswordForm extends ActionForm {


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6064532546526315737L;

	private String previousPassword = "";
	
	private String newPassword = "";
	
	private String newPasswordCfm = "";
	
	private String memberCode = "";

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getNewPassword() {
		return newPassword == null ? "" : newPassword.trim();
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordCfm() {
		return newPasswordCfm == null ? "" : newPasswordCfm.trim();
	}

	public void setNewPasswordCfm(String newPasswordCfm) {
		this.newPasswordCfm = newPasswordCfm;
	}

	public String getPreviousPassword() {
		return previousPassword == null ? "" : previousPassword.trim();
	}

	public void setPreviousPassword(String previousPassword) {
		this.previousPassword = previousPassword;
	}


}

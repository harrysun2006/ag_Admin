package com.agloco.form;

import org.apache.struts.action.ActionForm;

public class DecryptMemberInfoForm extends ActionForm {
	private String encryptMemberCode;

	private String decryptMemberCode;

	public String getDecryptMemberCode() {
		return decryptMemberCode;
	}

	public void setDecryptMemberCode(String decryptMemberCode) {
		this.decryptMemberCode = decryptMemberCode;
	}

	public String getEncryptMemberCode() {
		return encryptMemberCode;
	}

	public void setEncryptMemberCode(String encryptMemberCode) {
		this.encryptMemberCode = encryptMemberCode;
	}

}

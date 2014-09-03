package com.agloco.form;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

/**
 * 
 * @author terry_zhao
 *
 */
public class MyProfileForm extends ActionForm {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2189643725958972262L;

	private String firstName = "";

	private String middleName = "";

	private String lastName = "";

	private String emailAddress = "";
	
	private String emailAddressCfm = "";

	private String birthDate = "";

	private String address1 = "";

	private String address2 = "";

	private String city = "";

	private String state = "";

	private String postCode = "";

	private String country = "";
	
	private String previousPassword = "";
	
	private String newPassword = "";
	
	private String newPasswordCfm = "";
	
	private String memberCode = "";

	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName == null ? "":middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getPostCode()
	{
		return postCode;
	}

	public void setPostCode(String postCode)
	{
		this.postCode = postCode;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getFullName()
	{
		StringBuffer sb = new StringBuffer(32);
		
		if(StringUtils.isNotEmpty(getMiddleName())){
		  return sb.append(getFirstName()).append(" ").append(getMiddleName()).append(" ").append(getLastName()).toString();
		}		
		return sb.append(getFirstName()).append(" ").append(getLastName()).toString();

	}

	public String getEmailAddressCfm() {
		return emailAddressCfm;
	}

	public void setEmailAddressCfm(String emailAddressCfm) {
		this.emailAddressCfm = emailAddressCfm;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPreviousPassword() {
		return previousPassword;
	}

	public void setPreviousPassword(String previousPassword) {
		this.previousPassword = previousPassword;
	}

	public String getStreet1() {
		return address1;
	}

	public void setStreet1(String street1) {
		this.address1 = street1;
	}

	public String getStreet2() {
		return address2;
	}

	public void setStreet2(String street2) {
		this.address2 = street2;
	}

	public String getNewPasswordCfm() {
		return newPasswordCfm;
	}

	public void setNewPasswordCfm(String newPasswordCmf) {
		this.newPasswordCfm = newPasswordCmf;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberId) {
		this.memberCode = memberId;
	}


}

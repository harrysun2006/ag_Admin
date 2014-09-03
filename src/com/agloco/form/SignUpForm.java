package com.agloco.form;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

/**
 * 
 * @author terry_zhao
 *
 */
public class SignUpForm extends ActionForm {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2189643725958972262L;

	private Long memberId = null;

	private String memberCode = "";

	private Calendar createDate = null;

	private Calendar modifiedDate = null;

	private String password = "";

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

	private Integer mailCount = null;

	private Calendar lastMailTime = null;

	private String mailResult = "";
	
	private String referralCode = "";
	
	private String referralCode2 = "";
	
	private boolean agree = false;

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

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
		return (emailAddress == null) ? "" : emailAddress.trim().toLowerCase();
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = (emailAddress == null) ? "" : emailAddress.trim().toLowerCase();
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

	public Integer getMailCount()
	{
		return mailCount;
	}

	public void setMailCount(Integer mailCount)
	{
		this.mailCount = mailCount;
	}

	public String getMailResult()
	{
		return mailResult;
	}

	public void setMailResult(String mailResult)
	{
		this.mailResult = mailResult;
	}

	public String getMemberCode()
	{
		return memberCode;
	}

	public void setMemberCode(String memberCode)
	{
		this.memberCode = memberCode;
	}

	public Long getMemberId()
	{
		return memberId;
	}

	public void setMemberId(Long memberId)
	{
		this.memberId = memberId;
	}

	public String getMiddleName()
	{
		return middleName == null ? "":middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public Calendar getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Calendar createDate)
	{
		this.createDate = createDate;
	}

	public Calendar getLastMailTime()
	{
		return lastMailTime;
	}

	public void setLastMailTime(Calendar lastMailTime)
	{
		this.lastMailTime = lastMailTime;
	}

	public Calendar getModifiedDate()
	{
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate)
	{
		this.modifiedDate = modifiedDate;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getEmailAddressCfm() {
		return (emailAddressCfm == null) ? "" : emailAddressCfm.trim().toLowerCase();
	}

	public void setEmailAddressCfm(String emailAddressCfm) {
		this.emailAddressCfm = (emailAddressCfm == null) ? "" : emailAddressCfm.trim().toLowerCase();
	}

	public String getReferralCode2() {
		return (referralCode2 == null) ? "" : referralCode2.toUpperCase();
	}

	public void setReferralCode2(String referralCode2) {
		this.referralCode2 = (referralCode2 == null) ? "" : referralCode2.toUpperCase();
	}


}

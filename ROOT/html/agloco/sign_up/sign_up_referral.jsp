
<%@ include file="/html/common/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://easyconf.sourceforge.net/tags-easyconf" prefix="easyconf" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portlet.LiferayWindowState" %>
<%@ page import="javax.portlet.WindowState" %>
<%@ page import="com.liferay.portal.language.LanguageUtil" %>
<%@ page import="com.liferay.portlet.PortletURLUtil" %>

<%@ page import="com.agloco.exception.NoSuchUserExistsException" %>
<%@ page import="com.liferay.portal.captcha.CaptchaTextException" %>
<%@ page import="com.liferay.portal.ContactFirstNameException" %>
<%@ page import="com.liferay.portal.ContactLastNameException" %>
<%@ page import="com.liferay.portal.DuplicateUserEmailAddressException" %>
<%@ page import="com.liferay.portal.DuplicateUserIdException" %>
<%@ page import="com.liferay.portal.ReservedUserEmailAddressException" %>
<%@ page import="com.liferay.portal.ReservedUserIdException" %>
<%@ page import="com.liferay.portal.UserEmailAddressException" %>
<%@ page import="com.liferay.portal.UserIdException" %>
<%@ page import="com.agloco.exception.UserBirthdayDateException" %>
<%@ page import="com.agloco.exception.UserCityException" %>
<%@ page import="com.agloco.exception.UserCountryException" %>
<%@ page import="com.agloco.exception.UserPostCodeException" %>
<%@ page import="com.agloco.exception.UserStateException" %>
<%@ page import="com.agloco.exception.ReferralCodeException" %>
<%@ page import="com.agloco.exception.DuplicateEmailAddressException" %>
<%@ page import="com.agloco.exception.UserEmailAddressConfirmException" %>
<%@ page import="com.agloco.exception.UserNotAgreeException" %>
<%@ page import="com.agloco.exception.DateFormatInvalidException" %>
<%@ page import="com.agloco.form.SignUpForm" %>
<%@ page import="com.agloco.AglocoURL" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@ page import="com.liferay.portlet.journal.service.spring.JournalArticleLocalServiceUtil" %>
<%@ page import="java.util.Locale" %>
<%@page import="com.agloco.exception.CannotCatchedException"%>

<%@ page import="org.apache.struts.taglib.tiles.GetAttributeTag" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@page import="com.liferay.util.Http"%>
<script src="/html/agloco/js/sign-up.js" type="text/javascript"></script>
<STYLE type=text/css>
.headbg{
	BACKGROUND-ATTACHMENT: fixed;
	BACKGROUND-IMAGE: url(images/newhead_02.jpg);
	background-repeat: no-repeat;
	background-position: left top;
}

.STYLE1 {	font-family: Verdana;
	font-size: 11px;
}
</STYLE>

<script language="javascript">
	var submitCount = 0;
</script>


<portlet:defineObjects />

<%

	SignUpForm agMember = (SignUpForm)request.getAttribute("signUpFrm");
	String referralCode = (String)request.getAttribute("referralCode");

	String articleId = com.agloco.Constants.ARTICLEID_AG_SIGNUP_LEFT_ARTICE;
	JournalArticle article = JournalArticleLocalServiceUtil.getArticle(themeDisplay.getCompanyId(),articleId );
	String subject = article.getTitle();
	String articleBody = JournalArticleLocalServiceUtil.getArticleContent(themeDisplay.getCompanyId(), articleId, themeDisplay.getLocale().toString(), "");
	
%>


<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/sign-up/sign-up" /></portlet:actionURL>" method="post" name="signUpFrm">
<bean:define id = "signUpFrm" name="signUpFrm" type="com.agloco.form.SignUpForm"/>

<table width="790" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tbody>
    <tr>
      <td height="16" align="center"></td>
    </tr>
    <tr>
      <td align="middle"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-referral-landingpage") %>" width="790" height="62" /></td>
    </tr>
    <tr>
      <td height="509" align="middle" valign="top">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      		<tr bgcolor="#FFFFFF">
	      		<td width="1" height="10" bgcolor="#CCCCCC"></td>
					<td colspan="2">
						<liferay-ui:error exception="<%= NoSuchUserExistsException.class %>" message="ag-referral-is-not-existed" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="ag-text-verification-failed" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="ag-please-enter-a-valid-first-name" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="ag-please-enter-a-valid-last-name" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="ag-the-user-id-you-requested-is-already-taken" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-reserved" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="ag-the-user-id-you-requested-is-reserved" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="ag-please-enter-a-valid-email-address" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserIdException.class %>" message="ag-please-enter-a-valid-user-id" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserBirthdayDateException.class %>" message="ag-please-enter-a-valid-birthday-date" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserCityException.class %>" message="ag-please-enter-a-valid-city" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserCountryException.class %>" message="ag-please-enter-a-valid-country" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserPostCodeException.class %>" message="ag-please-enter-a-valid-post-code" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserStateException.class %>" message="ag-please-enter-a-valid-state" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= ReferralCodeException.class %>" message="ag-please-enter-a-valid-referral-code" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= DuplicateEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserEmailAddressConfirmException.class %>" message="ag-please-enter-a-valid-confirm-email-address" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= UserNotAgreeException.class %>" message="ag-please-confirm-you-agree-service-and-privacy" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= DateFormatInvalidException.class %>" message="ag-please-enter-a-valid-birthday-date" rowBreak="<br/>"/>
						<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" rowBreak="<br/>"/>
						<br/>
					</td>
					<td width="1" height="10" bgcolor="#CCCCCC"></td>
				</tr>    
		        <tr>
		          <td width="1" height="509" bgcolor="#CCCCCC"></td>
		          <td width="330" align="right" valign="top" bgcolor="#FFFFFF">
		          		<div style="margin-left:7px;margin-right:7px;"><%=articleBody %></div>
		         	 
		          </td>
		          <td width="458" align="center" valign="middle" bgcolor="#FFFFFF">
					  <table width="454" height="98%" border="0" align="left" cellpadding="0" cellspacing="0">
			            <tr>
			              <td colspan="3"><img src="/html/agloco/images/agloco-referral-landingp-03.gif" width="455" height="12" /></td>
			            </tr>
			            <tr>
			              <td width="6" rowspan="3" valign="top"><img src="/html/agloco/images/agloco-referral-landingp-05.gif" width="10" height="562" /></td>
			              <td width="429" height="30" align="left" valign="top"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-referral-landingp-06") %>" width="181" height="38" />
			              
			              
			              </td>
			              <td width="16" rowspan="3" valign="top"><img src="/html/agloco/images/agloco-referral-landingp-08.gif" width="16" height="562" /></td>
			            </tr>
			            <tr>
			              <td height="343" valign="top" colspan="3">
							  <table width="428" height="394" border="0" cellpadding="0" cellspacing="0">
							  
							  
							  <tr>
				                  <td width="120" height="18" align="center"  class=ag_r11></td>
				                  <td colspan="4" align="left"  class=ag_r11>*&nbsp;<%=LanguageUtil.get(pageContext,"ag-required-fields") %></td>
                  			  </tr>
			                <tr>
				                  <td height="40"></td>
				                  <td width="150" align="left" class="ag_k12"><span class="ag_r10b">*</span><span class="ag_k12">&nbsp;</span><%= LanguageUtil.get(pageContext, "ag-firstname") %> <br />
				                    <input name="<portlet:namespace />firstName" type="text"  width="120px" value="<bean:write name="signUpFrm" property="firstName"/>"/></td>
				                  <td colspan="3" align="left" class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-middlename") %> <br />
				                     <input name="<portlet:namespace />middleName" type="text" style="width:50px" value="<bean:write name="signUpFrm" property="middleName"/>"/></td>
				                    
			                </tr>
			                
			                <tr>
			                  <td height="40"></td>
			                  <td align="left" class="ag_k12"><span class="ag_r10b">*</span><span class="ag_k12">&nbsp;</span><%= LanguageUtil.get(pageContext, "ag-lastname") %> <br />
			                    <input name="<portlet:namespace />lastName" type="text"  size="20" value="<bean:write name="signUpFrm" property="lastName"/>"/></td>
			                  <td colspan="3">&nbsp;</td>
			                </tr>
			                
			                <tr>
			                  <td height="33" align="right" class="ag_k12"><span class="ag_r10b">*</span>&nbsp;<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-email") %>&nbsp;</span> </td>
			                  <td colspan="4" align="left">                <input name="<portlet:namespace />emailAddress" type="text"  style="width:220px" value="<bean:write name="signUpFrm" property="emailAddress"/>" maxlength="50"/></td>
			                </tr>
			                
			                <tr>
			                  <td height="29" align="right" class="ag_k12"><span class="ag_r10b">*</span>&nbsp;<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-confirm-email") %>&nbsp;</span></td>
			                  <td colspan="4" align="left"> <input name="<portlet:namespace />emailAddressCfm" type="text"  style="width:220px" value="<bean:write name="signUpFrm" property="emailAddressCfm"/>" maxlength="50"/></td>
			                </tr>
			                 
			                <tr>
			                  <td height="30" align="right" class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-date-of-birth") %> &nbsp;</td>
			                  <td align="left"><input name="<portlet:namespace />birthDate" type="text"  width="110px" value="<bean:write name="signUpFrm" property="birthDate"/>" maxlength="10"/></td>
			                  <td colspan="3" rowspan="2" align="left" valign="top" class="ag_k10">&nbsp;<%= LanguageUtil.get(pageContext, "ag-date-of-birth-comment") %></td>
			                </tr>
			                
			                <tr>
			                  <td height="29" align="right" class="ag_k12"><span class="ag_r10b">*</span>&nbsp;<span class="ag_k11"><%= LanguageUtil.get(pageContext, "ag-city") %>&nbsp;</span></td>
			                  <td align="left"><input name="<portlet:namespace />city" type="text"  style="width:140px" value="<bean:write name="signUpFrm" property="city"/>"/></td>
			                </tr>
			                
			                <tr>
			                  <td height="31" align="right" class="ag_k12"><span class="ag_r10b">*</span>&nbsp;<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-state") %>&nbsp;</span></td>
			                  <td align="left"><input name="<portlet:namespace />state" id="<portlet:namespace />state" type="text"  value="<% if(agMember.getState() == null || "".equals(agMember.getState().trim())) out.print(LanguageUtil.get(pageContext, "ag-none-if-not-applicable")); else out.print(agMember.getState()); %>" width="110px" onclick="clearContext(this)"/></td>
			                  <td colspan="3">&nbsp;</td>
			                </tr>
			                
			                <tr>
			                  <td  nowrap="true" height="36" align="right" class="ag_k12"><span class="ag_r10b">*</span>&nbsp;<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-zip-post-code") %>&nbsp;</span> </td>
			                  <td align="left"><input name="<portlet:namespace />postCode" id="<portlet:namespace />postCode" type="text"  value="<% if(agMember == null || "".equals(agMember.getState().trim())) out.print(LanguageUtil.get(pageContext, "ag-none-if-not-applicable")); else out.print(agMember.getPostCode());  %>" width="110px" onclick="clearContext(this)"/></td>
			                  <td colspan="3">&nbsp;</td>
			                </tr>
			                
			                <tr>
			                  <td height="23" align="right" class="ag_k12"><span class="ag_r10b">*</span>&nbsp;<span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-country") %>&nbsp;</span></td>
			                  <td colspan="4" align="left" valign="middle"><select id="<portlet:namespace />country" onChange="" style="width:250px" name="country">
			
			                <option value="" selected="selected">SELECT</option>
			                <option value="US">United States</option>
			                <option value="GB">United Kingdom</option>
			                <option value="CA">Canada</option>
			                <option value="AU">Australia</option>
			                <option value=" ">----------</option>
			
			                <option value="AF">Afghanistan</option>
			                <option value="AL">Albania</option>
			                <option value="DZ">Algeria</option>
			                <option value="AD">Andorra</option>
			                <option value="AO">Angola</option>
			                <option value="AI">Anguilla</option>
			
			                <option value="AQ">Antarctica</option>
			                <option value="AG">Antigua and Barbuda</option>
			                <option value="AR">Argentina</option>
			                <option value="AM">Armenia</option>
			                <option value="AW">Aruba</option>
			                <option value="AU">Australia</option>
			
			                <option value="AT">Austria</option>
			                <option value="AZ">Azerbaijan</option>
			                <option value="BS">Bahamas</option>
			                <option value="BH">Bahrain</option>
			                <option value="BD">Bangladesh</option>
			                <option value="BB">Barbados</option>
			
			                <option value="BY">Belarus</option>
			                <option value="BE">Belgium</option>
			                <option value="BZ">Belize</option>
			                <option value="BJ">Benin</option>
			                <option value="BM">Bermuda</option>
			                <option value="BT">Bhutan</option>
			
			                <option value="BO">Bolivia</option>
			                <option value="BA">Bosnia and Herzegovina</option>
			                <option value="BW">Botswana</option>
			                <option value="BR">Brazil</option>
			                <option value="IO">British Indian Ocean</option>
			                <option value="BN">Brunei</option>
			
			                <option value="BG">Bulgaria</option>
			                <option value="BF">Burkina Faso</option>
			                <option value="BI">Burundi</option>
			                <option value="KH">Cambodia</option>
			                <option value="CM">Cameroon</option>
			                <option value="CA">Canada</option>
			
			                <option value="CV">Cape Verde</option>
			                <option value="KY">Cayman Islands</option>
			                <option value="CF">Central African Republic</option>
			                <option value="TD">Chad</option>
			                <option value="CL">Chile</option>
			                <option value="CN">China</option>
			
			                <option value="CX">Christmas Island</option>
			                <option value="CC">Cocos (Keeling) Islands</option>
			                <option value="CO">Colombia</option>
			                <option value="KM">Comoros</option>
			                <option value="CD">Congo, Democratic Republic of the</option>
			                <option value="CG">Congo, Republic of the</option>
			
			                <option value="CK">Cook Islands</option>
			                <option value="CR">Costa Rica</option>
			                <option value="HR">Croatia</option>
			                <option value="CY">Cyprus</option>
			                <option value="CZ">Czech Republic</option>
			                <option value="DK">Denmark</option>
			
			                <option value="DJ">Djibouti</option>
			                <option value="DM">Dominica</option>
			                <option value="DO">Dominican Republic</option>
			                <option value="TL">East Timor</option>
			                <option value="EC">Ecuador</option>
			                <option value="EG">Egypt</option>
			
			                <option value="SV">El Salvador</option>
			                <option value="GQ">Equatorial Guinea</option>
			                <option value="ER">Eritrea</option>
			                <option value="EE">Estonia</option>
			                <option value="ET">Ethiopia</option>
			                <option value="FK">Falkland Islands (Malvinas)</option>
			
			                <option value="FO">Faroe Islands</option>
			                <option value="FJ">Fiji</option>
			                <option value="FI">Finland</option>
			                <option value="FR">France</option>
			                <option value="GF">French Guiana</option>
			                <option value="PF">French Polynesia</option>
			
			                <option value="GA">Gabon</option>
			                <option value="GM">Gambia</option>
			                <option value="GE">Georgia</option>
			                <option value="DE">Germany</option>
			                <option value="GH">Ghana</option>
			                <option value="GI">Gibraltar</option>
			
			                <option value="GR">Greece</option>
			                <option value="GL">Greenland</option>
			                <option value="GD">Grenada</option>
			                <option value="GP">Guadeloupe</option>
			                <option value="GT">Guatemala</option>
			                <option value="GN">Guinea</option>
			
			                <option value="GW">Guinea-Bissau</option>
			                <option value="GY">Guyana</option>
			                <option value="HT">Haiti</option>
			                <option value="HN">Honduras</option>
			                <option value="HK">Hong Kong</option>
			                <option value="HU">Hungary</option>
			
			                <option value="IS">Iceland</option>
			                <option value="IN">India</option>
			                <option value="ID">Indonesia</option>
			                <option value="IE">Ireland</option>
			                <option value="IL">Israel</option>
			                <option value="IT">Italy</option>
			
			                <option value="CI">Ivory Coast (C&ocirc;te d'Ivoire)</option>
			                <option value="JM">Jamaica</option>
			                <option value="JP">Japan</option>
			                <option value="JO">Jordan</option>
			                <option value="KZ">Kazakhstan</option>
			                <option value="KE">Kenya</option>
			
			                <option value="KI">Kiribati</option>
			                <option value="KR">Korea, South</option>
			                <option value="KW">Kuwait</option>
			                <option value="KG">Kyrgyzstan</option>
			                <option value="LA">Laos</option>
			                <option value="LV">Latvia</option>
			
			                <option value="LB">Lebanon</option>
			                <option value="LS">Lesotho</option>
			                <option value="LI">Liechtenstein</option>
			                <option value="LT">Lithuania</option>
			                <option value="LU">Luxembourg</option>
			                <option value="MO">Macau</option>
			
			                <option value="MK">Macedonia, Republic of</option>
			                <option value="MG">Madagascar</option>
			                <option value="MW">Malawi</option>
			                <option value="MY">Malaysia</option>
			                <option value="MV">Maldives</option>
			                <option value="ML">Mali</option>
			
			                <option value="MT">Malta</option>
			                <option value="MH">Marshall Islands</option>
			                <option value="MQ">Martinique</option>
			                <option value="MR">Mauritania</option>
			                <option value="MU">Mauritius</option>
			                <option value="YT">Mayotte</option>
			
			                <option value="MX">Mexico</option>
			                <option value="FM">Micronesia</option>
			                <option value="MD">Moldova</option>
			                <option value="MC">Monaco</option>
			                <option value="MN">Mongolia</option>
			                <option value="MS">Montserrat</option>
			
			                <option value="MA">Morocco</option>
			                <option value="MZ">Mozambique</option>
			                <option value="NA">Namibia</option>
			                <option value="NR">Nauru</option>
			                <option value="NP">Nepal</option>
			                <option value="NL">Netherlands</option>
			
			                <option value="AN">Netherlands Antilles</option>
			                <option value="NC">New Caledonia</option>
			                <option value="NZ">New Zealand</option>
			                <option value="NI">Nicaragua</option>
			                <option value="NE">Niger</option>
			                <option value="NG">Nigeria</option>
			
			                <option value="NU">Niue</option>
			                <option value="NF">Norfolk Island</option>
			                <option value="NO">Norway</option>
			                <option value="OM">Oman</option>
			                <option value="PK">Pakistan</option>
			                <option value="PS">Palestinian Territory</option>
			
			                <option value="PA">Panama</option>
			                <option value="PG">Papua New Guinea</option>
			                <option value="PY">Paraguay</option>
			                <option value="PE">Peru</option>
			                <option value="PH">Philippines</option>
			                <option value="PN">Pitcairn Island</option>
			
			                <option value="PL">Poland</option>
			                <option value="PT">Portugal</option>
			                <option value="QA">Qatar</option>
			                <option value="RE">R&eacute;union</option>
			                <option value="RO">Romania</option>
			                <option value="RU">Russia</option>
			
			                <option value="RW">Rwanda</option>
			                <option value="SH">Saint Helena</option>
			                <option value="KN">Saint Kitts and Nevis</option>
			                <option value="LC">Saint Lucia</option>
			                <option value="PM">Saint Pierre and Miquelon</option>
			                <option value="VC">Saint Vincent and the Grenadines</option>
			
			                <option value="WS">Samoa</option>
			                <option value="SM">San Marino</option>
			                <option value="ST">S&atilde;o Tome and Principe</option>
			                <option value="SA">Saudi Arabia</option>
			                <option value="SN">Senegal</option>
			                <option value="CS">Serbia and Montenegro</option>
			
			                <option value="SC">Seychelles</option>
			                <option value="SL">Sierra Leone</option>
			                <option value="SG">Singapore</option>
			                <option value="SK">Slovakia</option>
			                <option value="SI">Slovenia</option>
			                <option value="SB">Solomon Islands</option>
			
			                <option value="SO">Somalia</option>
			                <option value="ZA">South Africa</option>
			                <option value="GS">South Georgia and the South Sandwich Islands</option>
			                <option value="ES">Spain</option>
			                <option value="LK">Sri Lanka</option>
			                <option value="SR">Suriname</option>
			
			                <option value="SJ">Svalbard and Jan Mayen</option>
			                <option value="SZ">Swaziland</option>
			                <option value="SE">Sweden</option>
			                <option value="CH">Switzerland</option>
			                <option value="TW">Taiwan</option>
			                <option value="TJ">Tajikistan</option>
			
			                <option value="TZ">Tanzania</option>
			                <option value="TH">Thailand</option>
			                <option value="TG">Togo</option>
			                <option value="TK">Tokelau</option>
			                <option value="TO">Tonga</option>
			                <option value="TT">Trinidad and Tobago</option>
			
			                <option value="TN">Tunisia</option>
			                <option value="TR">Turkey</option>
			                <option value="TM">Turkmenistan</option>
			                <option value="TC">Turks and Caicos Islands</option>
			                <option value="TV">Tuvalu</option>
			                <option value="UG">Uganda</option>
			
			                <option value="UA">Ukraine</option>
			                <option value="AE">United Arab Emirates</option>
			                <option value="GB">United Kingdom</option>
			                <option value="US">United States</option>
			                <option value="UM">United States Minor Outlying Islands</option>
			                <option value="UY">Uruguay</option>
			
			                <option value="UZ">Uzbekistan</option>
			                <option value="VU">Vanuatu</option>
			                <option value="VA">Vatican City</option>
			                <option value="VE">Venezuela</option>
			                <option value="VN">Vietnam</option>
			                <option value="VG">Virgin Islands, British</option>
			
			                <option value="WF">Wallis and Futuna</option>
			                <option value="EH">Western Sahara</option>
			                <option value="YE">Yemen</option>
			                <option value="ZM">Zambia</option>
			                <option value="ZW">Zimbabwe</option>
			            </select>   </td>
			                  </tr>
                  
          
            <input name="<portlet:namespace />referralCode" type="hidden" value="<%=signUpFrm.getReferralCode()%>" size="20"/> 
              <tr>
	            <td class="ag_k12"><div align="right">
	                <div><span class="ag_r10b"></span>&nbsp;<%= LanguageUtil.get(pageContext, "ag-regerred-by-with-referral") %></div>
	
	            </div></td>
	            <td colspan="4" class="ag_k10b">&nbsp;&nbsp;<bean:write name="signUpFrm" property="referralCode"/></td>
	          </tr>      
                  
                <tr>
	                  <td height="49" colspan="3" align="right"><div align="left">
							  <portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
							<portlet:param name="struts_action" value="/my_profile/captcha" />
							</portlet:actionURL>
							<liferay-ui:captcha page="/html/agloco/taglib/ui/captcha/page.jsp" url="<%= captchaURL %>" />
							</div></td>
	                  <td width="102" colspan="2">	
	                  	 <div class="ag_or11"><!-- webbot bot="HTMLMarkup" startspan -->
						            <!-- GeoTrust QuickSSL [tm] Smart Icon tag. Do not edit. -->
									<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="//smarticon.geotrust.com/si.js"></SCRIPT>
									<!-- end GeoTrust Smart Icon tag -->
									<!-- webbot bot="HTMLMarkup" endspan -->
						 </div>
					  </td>
                </tr>
                
                <tr>
                  <td height="36" class="ag_k12" align="right"><%= LanguageUtil.get(pageContext, "ag-enter-validate-code") %>&nbsp;<span class="ag_r10b">*</span></td>
                  <td valign="middle"><input name="<portlet:namespace />captchaText" width="150" type="text" value=""></td>
                  <td colspan="3">&nbsp;</td>
                </tr>
                
              <tr>
	            <td class="ag_k10b"><div align="right">
	                <input type=CHECKBOX name="agree" id="agree">
	            </div></td>
	            <td colspan="4" class="ag_or11"><font color="#FF3300"><span class="ag_r10b">*</span> <%= LanguageUtil.get(pageContext, "ag-have-read-and-agree") %> <a href="<%=AglocoURL.TERMS_OF_USE %>" class="ag_k12"><font color=ff3300><u><%= LanguageUtil.get(pageContext, "ag-terms-of-service") %></u></font></a>, <a href="<%=AglocoURL.MEMBERSHIP_AGREEMENT %>" class="ag_k12"><font color=ff3300><u><%=LanguageUtil.get(pageContext,"ag_membership-agreement") %></u></font></a> <%= LanguageUtil.get(pageContext, "ag-and") %> <a href="<%=AglocoURL.PRIVACY %>" class="ag_k12"><font color=ff3300><u><%= LanguageUtil.get(pageContext, "ag-privacy-policy") %></u></font></a></font></td>
        	  </tr>
				                 
				              </table>
				          </td>
			            </tr>
			            <tr>
			              <td height="35" align="center" valign="middle"  colspan="3">
			              	<img style="cursor: hand" src="<%= LanguageUtil.get(pageContext, "ag-pic-sign-up-button-join-now-a-new") %>" name="Image27" id="Image27" width="150" height="35" border="0" onclick="javascript:formSubmit();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image27','','<%= LanguageUtil.get(pageContext, "ag-pic-sign-up-button-join-now-b-new") %>',1)">
			              </td>
			            </tr>
			            
			            <tr>
			              <td height="19" colspan="3"><img src="/html/agloco/images/agloco-referral-landingp-10.gif" width="455" height="19" /></td>
			            </tr>
			          </table>
		         </td>
		         <td width="1" bgcolor="#CCCCCC"></td>
		        </tr>
	      </table>
      </td>
    </tr>
    <tr>
      <td align="middle" valign="top"><img src="/html/agloco/images/agloco-homepage-v4_08.gif" width="790" height="11" /></td>
    </tr>
  </tbody>
</table>

</form>




<script>
	var selectedCountry = "<%=agMember.getCountry() %>";
	var tmpPortletId = "<portlet:namespace />";
	if(selectedCountry != "")
	{
		var countryObj = document.getElementById(tmpPortletId+'country');
		for(var idx=0;idx<countryObj.options.length;idx++)
		{
			if(countryObj[idx].value == selectedCountry)
			{
				countryObj.selectedIndex = idx;
				break;
			}
		}
	}

	function formSubmit()
	{	
	
		if(submitCount > 0){
			return;
		}
		
		var portletId = '<portlet:namespace />';
	
		document.getElementById("Image27").disabled = true;
		if(document.getElementById(portletId+"postCode").value == "<%=LanguageUtil.get(pageContext, "ag-none-if-not-applicable")%>"){
			document.getElementById(portletId+"postCode").value = 'none';

		}
		if(document.getElementById(portletId+"state").value == "<%=LanguageUtil.get(pageContext, "ag-none-if-not-applicable")%>"){
			document.getElementById(portletId+"state").value = 'none';
		}
		
		document.signUpFrm.submit();
		submitCount += 1;

		/*
		setPortletId(tmpPortletId);
		var tips = document.getElementById(tmpPortletId+'tips');
		tips.innerHTML="";
		var flag = checkForm();
		if(flag=="true")
		{
			document.signUpFrm.submit();
		}
		else
		{
			tips.style.color = "red";
			tips.innerHTML=flag;
			return;	
		}
		*/
	}
	
	var selectCountry = '<%=agMember.getCountry()%>';
    for(var i = 0; i < document.signUpFrm.country.length; i++){
    	if(document.signUpFrm.country[i].value == selectCountry){
    		document.signUpFrm.country[i].selected = true;
    	}
    }
    
    var selectCheckBox = '<%=agMember.isAgree() %>';

    if(selectCheckBox == 'true'){
    	document.signUpFrm.agree.checked = true;
    }
    else{
    	document.signUpFrm.agree.checked = false;
    }
    
</script>
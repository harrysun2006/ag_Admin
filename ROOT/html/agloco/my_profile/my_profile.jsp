
<%@ include file="/html/portal/init.jsp" %>
	
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://easyconf.sourceforge.net/tags-easyconf" prefix="easyconf" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portlet.LiferayWindowState" %>
<%@ page import="javax.portlet.WindowState" %>
<%@ page import="com.liferay.portal.language.LanguageUtil" %>
<%@ page import="com.liferay.portlet.PortletURLUtil" %>

<%@ page import="com.agloco.form.MyProfileForm" %>


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
<%@ page import="com.agloco.exception.UserNewPasswordException" %>
<%@ page import="com.agloco.exception.UserPasswordConfirmException" %>
<%@ page import="com.agloco.exception.UserPasswordAuthenticateException" %>
<%@ page import="com.agloco.exception.DateFormatInvalidException" %>
<%@page import="com.agloco.exception.CannotCatchedException"%>

<%@ page import="com.agloco.AglocoURL" %>

<link rel="stylesheet" type="text/css" media="all" href="/html/agloco/css/calendar/calendar-win2k-cold-2.css" />

<script type="text/javascript" src="/html/agloco/js/calendar/lang/calendar-en.js"></script>

<script type="text/javascript">
<!--
	redirectToLogin();
//-->
</script>
<%
	MyProfileForm agMember = (MyProfileForm)request.getAttribute("myProfileForm");
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/my_profile/my_profile" /></portlet:actionURL>" method="post" name="myProfileForm">


 <center> 
  <table width="790"  border="0" cellpadding="0" cellspacing="0" bgcolor="white">
   <tr>
   	<td colspan="3">
	   	<liferay-ui:error exception="<%= NoSuchUserExistsException.class %>" message="ag-referral-is-not-existed" />
	   	<liferay-ui:error exception="<%= CannotCatchedException.class %>" message="ag-can-not-catched-exception" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="ag-text-verification-failed" />
		<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="ag-please-enter-a-valid-first-name" />
		<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="ag-please-enter-a-valid-last-name" />
		<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="ag-the-user-id-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="ag-the-user-id-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="ag-please-enter-a-valid-email-address" />
		<liferay-ui:error exception="<%= UserIdException.class %>" message="ag-please-enter-a-valid-user-id" />
		<liferay-ui:error exception="<%= UserBirthdayDateException.class %>" message="ag-please-enter-a-valid-birthday-date" />
		<liferay-ui:error exception="<%= UserCityException.class %>" message="ag-please-enter-a-valid-city" />
		<liferay-ui:error exception="<%= UserCountryException.class %>" message="ag-please-enter-a-valid-country" />
		<liferay-ui:error exception="<%= UserPostCodeException.class %>" message="ag-please-enter-a-valid-post-code" />
		<liferay-ui:error exception="<%= UserStateException.class %>" message="ag-please-enter-a-valid-state" />
		<liferay-ui:error exception="<%= ReferralCodeException.class %>" message="ag-please-enter-a-valid-referral-code" />
		<liferay-ui:error exception="<%= DuplicateEmailAddressException.class %>" message="ag-the-email-address-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= UserEmailAddressConfirmException.class %>" message="ag-please-enter-a-valid-confirm-email-address" />
		<liferay-ui:error exception="<%= UserNewPasswordException.class %>" message="ag-my-profile-please-input-validate-new-password" />
		<liferay-ui:error exception="<%= UserPasswordConfirmException.class %>" message="ag-my-profile-please-input-validate-new-password-confirm" />
		<liferay-ui:error exception="<%= UserPasswordAuthenticateException.class %>" message="ag-my-profile-please-input-validate-previous-password" />
		<liferay-ui:error exception="<%= DateFormatInvalidException.class %>" message="ag-please-enter-a-valid-birthday-date" />
		
   	</td>
   </tr>
    <tr bgcolor="#FFFFFF">
      <td align="center" valign="top">&nbsp; </td>
      <td align="center" valign="top"><br>
        <table width="760" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="8"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-sign-up-spacer") %>" width="1" height="1"></td>
          </tr>
          <tr>
            <td valign="top" bgcolor="#c9cccf"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                  <td height="45" valign="top"><img src="<%=LanguageUtil.get(pageContext,"ag-pic-my-profile-title-my-profile") %>" width="682" height="31"></td>
                </tr>
                <tr>
                  <td valign="top"><table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" class="k11">
                      <tr>
                        <td colspan="5" class="ag_r11">&nbsp;&nbsp;* <%=LanguageUtil.get(pageContext,"ag-required-fields") %></td>
                      </tr>
                       <tr>
                        <td class="ag_k12" align="right"><%=LanguageUtil.get(pageContext,"ag-my-profile-member-code") %>:</td>
                        <td colspan="4" class="ag_k12"> <%=agMember.getMemberCode()%>
                        	<input name="<portlet:namespace />memberCode"   type="hidden" value="<bean:write name="myProfileForm" property="memberCode"/>"/>
                        	<input name="<portlet:namespace />emailAddress" type="hidden" value="<bean:write name="myProfileForm" property="emailAddress"/>"/>
                        </td>
                        
                      
                      
                      </tr>
                      <tr>
                        <td width="87" class="ag_r10b"><div align="right"></div></td>
                        <td width="140" class="ag_k12"><div align="left"> <span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-firstname") %></span><span class="ag_r11">*</span><br>
                                <input name="<portlet:namespace />firstName" type="text"  size="18" value="<bean:write name="myProfileForm" property="firstName"/>"/>
                        </div></td>
                        <td width="133" class="ag_k12"><div align="left"> <span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-middlename") %></span> <br>
                               <input name="<portlet:namespace />middleName" type="text"  size="18" value="<bean:write name="myProfileForm" property="middleName"/>"/>
                        </div></td>
                        <td width="154" class="ag_k12"><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-lastname") %></span><span class="ag_r11">*</span><br>
                        <input name="<portlet:namespace />lastName" type="text"  size="18" value="<bean:write name="myProfileForm" property="lastName"/>"/></td>
                        <td width="226" class="ag_r10b"><div align="left"></div></td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div>
                              <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-date-of-birth") %></span><span class="ag_r11"></span>&#13;</div>
                              <div></div>
                            </div>
                        </div></td>
                        <td><input name="<portlet:namespace />birthDate" type="text"  size="20" value="<bean:write name="myProfileForm" property="birthDate"/>" id="birthDate" maxlength="10"/>
                        <!--  <button type="reset" id="btn_birthDate" >...</button>--></td>
                        <td colspan="3"><div>
                            <div class="ag_g11"><%= LanguageUtil.get(pageContext, "ag-date-of-birth-comment") %>&#13;</div>
                            <div><!--<liferay-ui:calendar page="/html/taglib/ui/calendar/page.jsp" day="30" month="12" year="2006" />--></div>
                        </div></td>
                      </tr>
					 <tr>
                        <td class="ag_k12"><div align="right">&#13;&nbsp;<span class="ag_k12">&nbsp;<%=LanguageUtil.get(pageContext,"ag-my-profile-street") %><span class="ag_r11"></span></span><br>
                        </div></td>
                        <td colspan="3" class="ag_r10b"><input name="<portlet:namespace />street1" type="text"  size="70" value="<bean:write name="myProfileForm" property="street1"/>"/></td>
                        <td rowspan="4" valign="top"><div class="ag_g11"><%=LanguageUtil.get(pageContext,"ag-my-profile-we-need-your-address") %>.&#13;</div>
                            <div class="ag_or11"><a href="<%=AglocoURL.PRIVACY %>" class="ag_b11"><%=LanguageUtil.get(pageContext,"ag-my-profile-click-here-to-see-our-privacy-policy") %>&#13;</a></div>
                            </td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right"></div></td>
                        <td colspan="3" class="ag_r10b"><input name="<portlet:namespace />street2" type="text"  size="70" value="<bean:write name="myProfileForm" property="street2"/>"/></td>
                      </tr>
                       
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-city") %></span><span class="ag_r11">*</span></div>
                        </div></td>
                        <td colspan="3" class="ag_r10b"><input name="<portlet:namespace />city" type="text"  size="70" value="<bean:write name="myProfileForm" property="city"/>"/></td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div>
                              <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-state") %></span><span class="ag_r11">*</span>&#13;</div>
                              <div></div>
                            </div>
                        </div></td>
                        <td class="ag_r10b"><input name="<portlet:namespace />state" type="text"  value="<% if(agMember.getState() == null || "".equals(agMember.getState().trim())) out.print(LanguageUtil.get(pageContext, "ag-none-if-not-applicable")); else out.print(agMember.getState()); %>" size="18" />
			                    <!--select id="state"  onChange="" name="<portlet:namespace />state">
						                <option value="N/A - Outside U.S." selected="selected">N/A - Outside U.S.</option>
						                <option value="Alabama">Alabama</option>
						                <option value="Alaska">Alaska</option>
						                <option value="Arizona">Arizona</option>
						                <option value="Arkansas">Arkansas</option>
						                <option value="California">California</option>
						                <option value="Colorado">Colorado</option>
						                <option value="Connecticut">Connecticut</option>
						                <option value="Delaware">Delaware</option>
						                <option value="District of Columbia">District of Columbia</option>
						                <option value="Florida">Florida</option>
						                <option value="Georgia">Georgia</option>
						                <option value="Hawaii">Hawaii</option>
						                <option value="Idaho">Idaho</option>
						                <option value="Illinois">Illinois</option>
						                <option value="Indiana">Indiana</option>
						                <option value="Iowa">Iowa</option>
						                <option value="Kansas">Kansas</option>
						                <option value="Kentucky">Kentucky</option>
						                <option value="Louisiana">Louisiana</option>
						                <option value="Maine">Maine</option>
						                <option value="Maryland">Maryland</option>
						                <option value="Massachusetts">Massachusetts</option>
						                <option value="Michigan">Michigan</option>
						                <option value="Minnesota">Minnesota</option>
						                <option value="Mississippi">Mississippi</option>
						                <option value="Missouri">Missouri</option>
						                <option value="Montana">Montana</option>
						                <option value="Nebraska">Nebraska</option>
						                <option value="Nevada">Nevada</option>
						                <option value="New Hampshire">New Hampshire</option>
						                <option value="New Jersey">New Jersey</option>
						                <option value="New Mexico">New Mexico</option>
						                <option value="New York">New York</option>
						                <option value="North Carolina">North Carolina</option>
						                <option value="North Dakota">North Dakota</option>
						                <option value="Ohio">Ohio</option>
						                <option value="Oklahoma">Oklahoma</option>
						                <option value="Oregon">Oregon</option>
						                <option value="Pennsylvania">Pennsylvania</option>
						                <option value="Rhode Island">Rhode Island</option>
						                <option value="South Carolina">South Carolina</option>
						                <option value="South Dakota">South Dakota</option>
						                <option value="Tennessee">Tennessee</option>
						                <option value="Texas">Texas</option>
						                <option value="Utah">Utah</option>
						                <option value="Vermont">Vermont</option>
						                <option value="Virginia">Virginia</option>
						                <option value="Washington">Washington</option>
						                <option value="West Virginia">West Virginia</option>
						                <option value="Wisconsin">Wisconsin</option>
						                <option value="Wyoming">Wyoming</option>                                         
						            	</select//-->
                        </td>
                      
                      <td colspan="2" class="ag_k12"><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-zip-post-code") %></span><span class="ag_r11">*</span>&#13;<span class="ag_r10b">
                        <input name="<portlet:namespace />postCode" type="text"  value="<% if(agMember == null || "".equals(agMember.getState().trim())) out.print(LanguageUtil.get(pageContext, "ag-none-if-not-applicable")); else out.print(agMember.getPostCode());  %>" size="18" />
                      </span></td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-country") %></span><span class="ag_r11">*</span></div>
                        </div></td>
                        <td colspan="4" class="ag_r10b"><select id="country" onChange="" name="<portlet:namespace />country">
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
                        </select></td>
                      </tr>
         
                      <tr>
                        <td class="ag_k12"><div align="right"></div></td>
                        <td colspan="3" class="ag_r10b">	
	                        <div align="left">
						        <portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
									<portlet:param name="struts_action" value="/my_profile/captcha" />
								</portlet:actionURL>
								<liferay-ui:captcha page="/html/agloco/taglib/ui/captcha/page.jsp" url="<%= captchaURL %>" />
							</div>
							</td>
						<td align="left">
					        <div>
					        <div class="ag_or11"><!-- webbot bot="HTMLMarkup" startspan -->
					            <!-- GeoTrust QuickSSL [tm] Smart Icon tag. Do not edit. -->
								<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="//smarticon.geotrust.com/si.js"></SCRIPT>
								<!-- end GeoTrust Smart Icon tag -->
								<!-- webbot bot="HTMLMarkup" endspan -->
					        </div>
					       </div>
					     </td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right">
                            <div><span class="ag_k12"><%= LanguageUtil.get(pageContext, "ag-enter-validate-code") %></span><span class="ag_r11">*</span></div>
                        </div></td>
                        <td colspan="4" class="ag_r10b"><input name="<portlet:namespace />captchaText" size="20" type="text" value=""></td>
                      </tr>
                      <tr>
                        <td class="ag_k12"><div align="right"></div></td>
                        <td colspan="4"><a href="<%=AglocoURL.TERMS_OF_USE %>" class="ag_b11"><%= LanguageUtil.get(pageContext, "ag-terms-of-service") %></a></td>
                      </tr>
                      <tr>
                        <td class="ag_k12">&nbsp;</td>
                        <td colspan="2" class="ag_r10b"><div align="center">
                            <input type="button" class="ag_bt0"  name="btn_submit" id="btn_submit" onClick="javascript:formSubmit();" value="<%= LanguageUtil.get(pageContext,"ag-my-profile-save") %>">
                        </div></td>
                        <td colspan="2" class="ag_k12">&nbsp;</td>
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
      <br></td>
      <td align="center" valign="top">&nbsp;</td>
    </tr>
    
  </table>
</center>

</form>
<script  type="text/javascript">
<!--
/*
    Calendar.setup({
        inputField     :    "birthDate",      // id of the input field
        ifFormat       :    "%m/%d/%Y",       // format of the input field
        showsTime      :    true,            // will display a time selector
        button         :    "btn_birthDate",   // trigger for the calendar (button ID)
        singleClick    :    false,           // double-click mode
        step           :    1                // show all years in drop-down boxes (instead of every other year as default)
    });
*/    
    var selectCountry = '<%=agMember.getCountry()%>';
    for(var i = 0; i < document.myProfileForm.country.length; i++){
    	if(document.myProfileForm.country[i].value == selectCountry){
    		document.myProfileForm.country[i].selected = true;
    	}
    }
/*
		var selectState = '<%=agMember.getState()%>';
    for(var i = 0; i < document.myProfileForm.state.length; i++){
    	if(document.myProfileForm.state[i].value == selectState){
    		document.myProfileForm.state[i].selected = true;
    	}
    }
*/
function formSubmit()
{
	var f = document.forms["myProfileForm"];
	f.elements["btn_submit"].disabled = true;
	f.submit();
}
//-->
</script>
package com.test.db;

import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.Constants;
import com.agloco.model.AGMember;
import com.agloco.model.AGMemberTemp;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.Generator;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.model.User;

public class SignUpReferralThread extends Thread {
	
	private final static Log log = LogFactory.getLog(SignUpReferralThread.class);
	private final static String COMPANY_ID = "agloco.com";
	private int number;
	private String emailSuffix;
	private int t ;//threadNumber;
	
	public SignUpReferralThread(int number,String emailSuffix){
		this.number = number;
		this.emailSuffix = emailSuffix;
		t = Integer.parseInt(emailSuffix.substring(emailSuffix.indexOf("T")+1,emailSuffix.length()));
	}
	
	public void run(){
		int success = 0;
		int fail = 0;
		int i = 0;
		long startTime = Calendar.getInstance().getTimeInMillis();
		while(i < number){
			
			StringBuffer sb = new StringBuffer("");
			AGMember m = null;
			try{
				AGMemberTemp mt = new AGMemberTemp();
				mt.setFirstName("Terry" + t + "-" + i);
				mt.setLastName("Zhao" + t + "-" + i);
				mt.setCity("Su Zhou");
				mt.setState("Jiang Su");
				mt.setCountry("CN");
				mt.setPostCode( t + "-" + i + "");
				mt.setEmailAddress(i + emailSuffix);
				
				//set referral code
				m = MemberServiceUtil.getAGMemberByEmail(i*t+"@terry.zhao.T1");
				if(m != null){
					mt.setReferralCode(m.getMemberCode());
				}
				
				if(MemberServiceUtil.getAGMemberByEmail(mt.getEmailAddress()) != null  || 
				   MemberServiceUtil.getAGMemberTempByEmail(mt.getEmailAddress()) != null){
							throw new DuplicateUserEmailAddressException();
							
				}
				
				User user = new User();
				user.setCompanyId(COMPANY_ID);
				user.setUserId(Generator.generateUserId(user.getCompanyId()));
				user = MemberServiceUtil.addUserMemberTemp(user.getCompanyId(), false,
						user.getUserId(), false, "agloco", "agloco", false,
						Constants.DEFAULT_MAIL, Locale.getDefault(), "firstName", "middleName",
						"lastName", "", "", "", true, 0, 0, 0, "", "", "",true,mt);
				i++;
				success++;
				System.out.println(mt.getEmailAddress()+ " : " + user.getUserId() + " : " + mt.getReferralCode());
			}
			catch(Exception e){
				fail++;
				sb.append("sign up failure:");
				log.error(sb.toString(),e);
			}
		
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		long minutes = millis/(1000*60);
		log.info("thread:" + t+ " total time minutes:" + minutes);
		log.info("thread:" + t+ " total time seconds:" + seconds);
		log.info("thread:" + t+ " sign up with referral total number success/fail: " + success + "/" + fail);
		
	}

	

}

package com.test.db;

import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.Constants;
import com.agloco.model.AGMemberTemp;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.Generator;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.model.User;

public class SignUpThread extends Thread {
	
	private final static Log log = LogFactory.getLog(SignUpThread.class);
	private final static String COMPANY_ID = "agloco.com";
	private int number;// process number per thread
	private String emailSuffix;
	private int t ;//threadNumber;
	
	public SignUpThread(int number,String emailSuffix){
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
			try{
				AGMemberTemp mt = new AGMemberTemp();
				mt.setFirstName("Terry" + t + "-" + i);
				mt.setLastName("Zhao" + t + "-" + i);
				mt.setCity("Su Zhou");
				mt.setState("Jiang Su");
				mt.setCountry("CN");
				mt.setPostCode( t + "-" + i + "");
				mt.setEmailAddress(i + emailSuffix);
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
//				log.info(mt.getEmailAddress()+ " : " + user.getUserId());
				System.out.println(mt.getEmailAddress()+ " : " + user.getUserId());
			}
			catch(Exception e){
				i++;
				fail++;
				sb.append("sign up failure,emailAddress:" + i + emailSuffix);
				log.error(sb.toString(),e);
			}
		
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		long minutes = millis/(1000*60);
		log.info("thread:" + t+ " total time minutes/seconds:" + minutes + "/" + seconds);
		log.info("thread:" + t+ " sign up total number success/fail: " + success + "/" + fail);
		
	}
	

}

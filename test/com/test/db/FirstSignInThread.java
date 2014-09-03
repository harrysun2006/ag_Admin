package com.test.db;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.model.AGMember;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;

public class FirstSignInThread extends Thread{
	
	private final static Log log = LogFactory.getLog(FirstSignInThread.class);
	private String emailSuffix;
	private int number;
	private String password;
	private int t ;//threadNumber;
	public FirstSignInThread(String emailSuffix,int number,String password){
		this.emailSuffix = emailSuffix;
		this.number = number;
		this.password = password;
		t = Integer.parseInt(emailSuffix.substring(emailSuffix.indexOf("T")+1,emailSuffix.length()));
	}
	
	public void run(){
		int success = 0;
		int fail = 0;
		int empty = 0;
		long startTime = Calendar.getInstance().getTimeInMillis();
	
		AGMember user = null;
		for(int i = 0;i < number; i++ ){
			try{	
				user = MemberServiceUtil.authenticate(i + emailSuffix, password);
				if(user != null){
					MemberServiceUtil.updateAgreedToTermsOfUse(user.getUserId(), true);
					UserLocalServiceUtil.updateLastLogin(user.getUserId(), "127.0.0.1");
					success++;
					System.out.println(i+emailSuffix + " : " + user.getUserId());
				}
				else{
					empty++;
				}
			}
			catch(Exception e){
				fail++;
				log.error("first sign in error",e);
			}
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		long minutes = millis/(1000*60);
		log.info("thread:" + t+ " total time minutes/seconds:" + minutes + "/" + seconds);
		log.info("thread:" + t + " first sign in total number success/fail/empty: " + success + "/" + fail + "/" +empty);

	}

	
}

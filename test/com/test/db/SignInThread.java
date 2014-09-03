package com.test.db;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.model.AGMember;
import com.agloco.model.AGMemberCount;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;

public class SignInThread extends Thread {
	
	private final static Log log = LogFactory.getLog(SignInThread.class);
	private String emailSuffix;
	private int number;
	private String password;
	private int t ;//threadNumber;
	
	public SignInThread(String emailSuffix,int number,String password){
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

		AGMember member = null;

		for(int i = 0;i < number; i++ ){
			try{
				member = MemberServiceUtil.authenticate(i + emailSuffix, password);
				if(member != null){
					UserLocalServiceUtil.updateLastLogin(member.getUserId(), "127.0.0.1");
					AGMemberCount agmc = MemberServiceUtil.getAGMemberCount(member.getMemberId());
//					if(i%4 == 0){
						double rank = 0.0;
						String s = String.valueOf(agmc.getCount().longValue());
						int order = MemberServiceUtil.getAGMemberCountOrder(Integer.parseInt(s));
						int totalNum = MemberServiceUtil.getAGMemberCountWithReferrals();
						if(order > 0 && totalNum > 0){
							rank = (double)order/totalNum;	
						}
//					}
					
					success++;
					System.out.println(member.getUserId() + " : " + member.getEmailAddress());
				}
				else{
					empty++;
				}
			}
			catch(Exception e){
				fail++;
				log.error("sign in error",e);
			}
		}

		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		long minutes = millis/(1000*60);
		log.info("thread:" + t+ " total time minutes/seconds:" + minutes + "/" + seconds);
		log.info("sign in total number success/fail/empty: " + success + "/" + fail + "/" +empty);
		
		
	}
}

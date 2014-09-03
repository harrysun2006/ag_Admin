package com.test;

import com.agloco.model.AGMemberTemp;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.Generator;
import com.agloco.util.WordUtil;
import com.liferay.portal.util.UserIdGenerator;

public class TestAddMemberTemp {

	private final static int MAX_SEND_NUMBER = 400;
	private final static String HOTMAIL_SUFFIX = "@hotmail.com";
	private final static String MSN_SUFFIX = "@msn.com";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");
		
//		WordUtil.reloadBadPatterns();
//		WordUtil.reloadReservedPatterns();
		
		int i = 0;
		while(i < MAX_SEND_NUMBER){
			UserIdGenerator userIdGenerator = new UserIdGenerator();
			for(int k =0; k < 30; k++){
				AGMemberTemp mt = new AGMemberTemp();
				mt.setEmailAddress(i+HOTMAIL_SUFFIX);
				String userId = userIdGenerator.generate("agloco.com");
				mt.setUserId(userId);
				MemberServiceUtil.addAGMemberTemp(mt);
				i++;
				System.out.println(i);
				if(i > 400){
					return;
				}
			}
			
			for(int k =0; k < 30; k++){
				AGMemberTemp mt = new AGMemberTemp();
				mt.setEmailAddress(i+HOTMAIL_SUFFIX);
				String userId = userIdGenerator.generate("agloco.com");
				mt.setUserId(userId);
				MemberServiceUtil.addAGMemberTemp(mt);
				i++;
				System.out.println(i);
				if(i > 400){
					return;
				}
			}

		}

		
		
	}

}

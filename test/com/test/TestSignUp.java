package com.test;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.Constants;
import com.agloco.model.AGMemberTemp;
import com.agloco.service.util.MemberServiceUtil;
import com.liferay.portal.model.User;

public class TestSignUp {

	private static Log _log = LogFactory.getLog(TestSignUp.class);
	
	
	
	private final static int MAX_NUMBER = 5000;
	private final static String EMAIL_SUFFIX = "@guohui.zhao";
	private final static String COMPANY_ID = "agloco.com";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");
		
//		WordUtil.reloadBadPatterns();
//		WordUtil.reloadReservedPatterns();
		
		int i = 4990;
		while(i < MAX_NUMBER){
			StringBuffer sb = new StringBuffer("");

			try{
				AGMemberTemp mt = new AGMemberTemp();
				mt.setEmailAddress(i+EMAIL_SUFFIX);
				User user = new User();
				user.setCompanyId(COMPANY_ID);
				user = MemberServiceUtil.addUserMemberTemp(user.getCompanyId(), true,
						"", false, "agloco", "agloco", false,
						Constants.DEFAULT_MAIL, Locale.getDefault(), "firstName", "middleName",
						"lastName", "", "", "", true, 0, 0, 0, "", "", "",true,mt);
				i++;
				System.out.println(i);
				sb.append("save success:"+mt.getUserId());
				_log.info(sb.toString());
			}
			catch(Exception e){
				sb.append("save failure:");
				_log.error(sb.toString(),e);
				
				
			}
		
		}
		
	}
	
	
}

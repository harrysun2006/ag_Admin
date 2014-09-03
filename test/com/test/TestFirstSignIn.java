package com.test;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.model.AGMemberTemp;
import com.agloco.service.util.MemberServiceUtil;
import com.agloco.util.WordUtil;

public class TestFirstSignIn {

	private static Log _log = LogFactory.getLog(TestFirstSignIn.class);
	
	
	
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
		
		try{
			while(true){
				
				List list = MemberServiceUtil.listAGMemberTempByEmailSuffix(EMAIL_SUFFIX);
				if(list == null || list.size() < 1){
					return;
				}
				
				for(Iterator it = list.iterator(); it.hasNext();){
					AGMemberTemp mt = (AGMemberTemp)it.next();
					try{
						MemberServiceUtil.updateAgreedToTermsOfUse(mt.getUserId(), true);
						_log.info("update member success,memberId:"+mt.getMemberId());
					}
					catch(Exception e){
						_log.error("update member failure,memberId:"+mt.getMemberId()+" "+mt.getEmailAddress(),e);
			
					}
				}
				
			}
			
			
		}
		catch(Exception e){
			
		}
		
	}
	
	
}

package com.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.model.AGMemberCount;
import com.agloco.service.util.MemberServiceUtil;

public class TestAGMCountService {

	private static Log _log = LogFactory.getLog(TestAGMCountService.class);
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");
		System.out.println("begin test...");
		for(int i = 100; i <= 200;i++){
			AGMemberCount agmc = new AGMemberCount();
			agmc.setMemberId(new Long(i));
			MemberServiceUtil.addAGMemberCount(agmc);
			_log.info(i + ":" + agmc.getMemberId() + "-" + agmc.getCount());
		}
		System.out.println("test end...");
	}

}

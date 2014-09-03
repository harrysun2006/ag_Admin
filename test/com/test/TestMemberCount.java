package com.test;

import java.io.IOException;

import com.agloco.service.util.MemberServiceUtil;

public class TestMemberCount {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");
		System.out.println("memberId-6:" + MemberServiceUtil.getAGMemberCountOrder(new Long(6)));
		
		MemberServiceUtil.updateAGMemberCountTask();
		
		System.out.println("update successfully ,memberId-6:" + MemberServiceUtil.getAGMemberCountOrder(new Long(6)));

	}

}

package com.test;

import java.io.IOException;

import com.agloco.model.AGMemberTemp;
import com.agloco.util.Generator;

public class TestGenerator {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");
		try {
			System.out.println(Generator.generateMemberCode(AGMemberTemp.class.getName()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.test.db;

import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.test.ContextHelper;

public class TestDataBase {

	private final static Log log = LogFactory.getLog(TestDataBase.class);
	private final static String EMAIL_SUFFIX = "@test.agloco.T";
	private final static String PASSWORD = "test";
	
	private static long totalStartTime;
	private static long totalEndTime;
	public static void testSignUp(int stn,int tn, int pn){
		long startTime = Calendar.getInstance().getTimeInMillis();
		totalStartTime = Calendar.getInstance().getTimeInMillis();
		for(int i = stn; i < stn + tn; i++){
			SignUpThread t = new SignUpThread(pn,EMAIL_SUFFIX + i);
			t.start();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log.error("thread:" + stn + " sleep error",e);
			}
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		log.info("sign up:" + tn + " threads start up  total use  time millis/seconds:" + millis + "/" + seconds);
	}
	
	
	public static void testSignUpWithReferral(int stn,int tn, int pn){
		long startTime = Calendar.getInstance().getTimeInMillis();
		for(int i = stn; i < stn + tn; i++){
			SignUpReferralThread t = new SignUpReferralThread(pn,EMAIL_SUFFIX + i);
			t.start();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log.error("thread:" + stn + " sleep error",e);
			}
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		log.info("sign up with referral:" + tn + " threads start up  total use  time millis/seconds:" + millis + "/" + seconds);
	}
	
	public static void testFirstSignIn(int stn,int tn,int pn){
		long startTime = Calendar.getInstance().getTimeInMillis();
		for(int i = stn; i < stn + tn; i++){
			FirstSignInThread t = new FirstSignInThread(EMAIL_SUFFIX + i,pn,PASSWORD);
			t.start();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log.error("thread:" + stn + " sleep error",e);
			}
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		long millis = endTime - startTime;
		long seconds = millis/1000;
		log.info("first sign in:" + tn + " threads start up  total use  time millis/seconds:" + millis + "/" + seconds);
	}
	
	public static void testSignIn(int stn,int tn,int pn){
		long startTime = Calendar.getInstance().getTimeInMillis();
		for(int i = stn; i < stn + tn; i++){
			SignInThread t = new SignInThread(EMAIL_SUFFIX + i,pn,PASSWORD);
			t.start();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log.error("thread:" + stn + " sleep error",e);
			}
		}
		long endTime = Calendar.getInstance().getTimeInMillis();

		long millis = endTime - startTime;
		long seconds = millis/1000;
		log.info("sign in:" + tn + " threads start up  total use  time millis/seconds:" + millis + "/" + seconds);
		
		totalEndTime = Calendar.getInstance().getTimeInMillis();
		long totalMillis = totalEndTime - totalStartTime;
		long totalSeconds = totalMillis/1000;
		log.info("Simulation web site test" + tn + " threads start up  total use  time totalMillis/totalSeconds:" + totalMillis + "/" + totalSeconds);
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");

//		{
//			int startThreadNumer = 1;
//			int threadNumber  = 1;
//			int processNumber = 2000;//per thread
//			testSignUp(startThreadNumer, threadNumber,processNumber);
//		}
		
//		{
//			int startThreadNumer = 120;
//			int threadNumber  = 5;
//			int processNumber = 50;//per thread
//			testSignUpWithReferral(startThreadNumer, threadNumber,processNumber);
//		}
		{
			int startThreadNumer = 1;
			int threadNumber  = 1;
			int processNumber = 2000;//per thread
			testFirstSignIn(startThreadNumer, threadNumber,processNumber);
		}
//		{
//			int startThreadNumer = 1;
//			int threadNumber  = 20;
//			int processNumber = 50;//per thread
//			testSignIn(startThreadNumer, threadNumber,processNumber);
//		}
//		int startThreadNumer = 101;
//		int threadNumber  = 100;
//		int processNumber = 50;//per thread
//		testSignUp(startThreadNumer, threadNumber,processNumber);
//		testFirstSignIn(startThreadNumer, threadNumber,processNumber);
//		testSignIn(startThreadNumer, threadNumber,processNumber);
//		testSignUpWithReferral(startThreadNumer, threadNumber,processNumber);
		
		
		//
//		{
//			int startThreadNumer = 201;
//			int threadNumber  = 20;
//			int processNumber = 50;//per thread
//			testSignUp(startThreadNumer, threadNumber,processNumber);
//		}
//		{
//			int startThreadNumer = 221;
//			int threadNumber  = 10;
//			int processNumber = 50;//per thread
//			testSignUpWithReferral(startThreadNumer, threadNumber,processNumber);
//		}
//		{
//			int startThreadNumer = 101;
//			int threadNumber  = 30;
//			int processNumber = 50;//per thread
//			testFirstSignIn(startThreadNumer, threadNumber,processNumber);
//		}
//		{
//			int startThreadNumer = 1;
//			int threadNumber  = 40;
//			int processNumber = 50;//per thread
//			testSignIn(startThreadNumer, threadNumber,processNumber);
//		}

	}

}

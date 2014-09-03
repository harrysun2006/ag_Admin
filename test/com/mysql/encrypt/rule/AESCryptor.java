package com.mysql.encrypt.rule;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import com.mysql.util.CryptHelper;

public class AESCryptor {

	private static void test1() throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 */
		String cKey = "liferayagloco123";
		// 需要加密的字串
		String cSrc = "harry";
		// 加密
		long lStart = System.currentTimeMillis();
		String enString = AESCryptor.Encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);
		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");
		// 解密
		lStart = System.currentTimeMillis();
		String DeString = AESCryptor.Decrypt(enString, cKey);
		System.out.println("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");
	}

	private static void test2() throws Exception {
		String text = "test1";
		byte[] plainText = text.getBytes("UTF8");

		// 通过KeyGenerator形成一个key
		System.out.println("\nStart generate AES key");
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		Key key = keyGen.generateKey();
		System.out.println("Finish generating AES key");

		// 获得一个私鈅加密类Cipher，ECB是加密方式，PKCS5Padding是填充方法
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());

		// 使用私鈅加密
		System.out.println("\nStart encryption:");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption:");
		System.out.println(new String(cipherText, "UTF8"));

		System.out.println("\nStart decryption:");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] newPlainText = cipher.doFinal(cipherText);
		System.out.println("Finish decryption:");
		System.out.println(new String(newPlainText, "UTF8"));
	}

	private static void test3() throws Exception {
		String text = "test1";
		String password = "agloco";
		byte[] enc = CryptHelper.AESEncrypt(text, password);
		String encText = new String(enc);
		byte[] dec = CryptHelper.AESDecrypt(enc, password);
		String decText = new String(dec);
		String r = new String(encText.getBytes("UTF-8"));
		System.out.println(text + " ==> " + encText + " ==> " + decText);
	}

	public static void main(String[] args) throws Exception {
		test3();
	}

	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	// 判断Key是否正确
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		return byte2hex(encrypted).toLowerCase();
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}

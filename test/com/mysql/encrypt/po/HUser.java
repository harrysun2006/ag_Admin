/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mysql.encrypt.po;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;

import org.hibernate.lob.BlobImpl;

import com.mysql.util.CryptHelper;

/**
 * <a href="FoodItem.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class HUser implements Serializable {

	private static final String AGLOCO_AESKEY = "agloco";
	private static final String DATABASE_CHARSET = "UTF-8";

	private Long id;
	private String name;
	private String password;

	private Blob encName;
	private Blob encPassword;

	public HUser() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long l) {
		id = l;
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		if(name == null) name = s;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String s) {
		if(password == null) password = s;
	}

	public void encrypt() {
		setEncName(CryptHelper.AESEncrypt(name, AGLOCO_AESKEY, DATABASE_CHARSET));
		setEncPassword(CryptHelper.AESEncrypt(password, AGLOCO_AESKEY, DATABASE_CHARSET));
	}

	protected Blob getEncName() {
		return encName;
	}

	protected void setEncName(Blob b) {
		name = CryptHelper.AESDecrypt(b, AGLOCO_AESKEY, DATABASE_CHARSET);
		encName = b;
	}

	protected void setEncName(byte[] b) {
		encName = (b == null) ? null : new BlobImpl(b);
	}

	protected Blob getEncPassword() {
		return encPassword;
	}

	protected void setEncPassword(Blob b) {
		password = CryptHelper.AESDecrypt(b, AGLOCO_AESKEY, DATABASE_CHARSET);
		encPassword = b;
	}

	protected void setEncPassword(byte[] b) {
		encPassword = (b == null) ? null : new BlobImpl(b);
	}
}
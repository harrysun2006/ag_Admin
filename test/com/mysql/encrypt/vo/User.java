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

package com.mysql.encrypt.vo;

/**
 * <a href="FoodItem.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class User {

	private long id;
	private String name;
	private String password;

	public User() {
	}

	public User(String name, String password) {
		setName(name);
		setPassword(password);
	}

	public long getId() {
		return id;
	}

	public void setId(long l) {
		id = l;
	}

	public void setId(Long l) {
		id = (l == null) ? 0 : l.intValue();
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String s) {
		password = s;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(100);
		sb.append("user[")
			.append(id).append(".")
			.append(name).append("/").append(password)
			.append("]");
		return sb.toString();
	}
}
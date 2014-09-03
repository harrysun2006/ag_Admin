package com.test.kernel;

public class Host {

	private String id;
	private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("host[")
			.append("id=").append(id)
			.append(", name=").append(name)
			.append("]");
		return sb.toString();
	}
}

package com.test.model;

public class DynaTable1 implements DynaTable {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
			.append("DynaItem: [id = ")
			.append((id == null) ? "" : id.toString())
			.append(", name = ")
			.append((name == null) ? "" : name)
			.append("]");
		return sb.toString();
	}
}

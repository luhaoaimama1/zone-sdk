package com.example.mylib_test.activity.db.entity;

public class MbKb {
	private String id;
	private String name;
	private String age;
	private String sj;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		System.out.println("id:"+this.getId() +"\t name"+this.getName()+"\t age"+this.getAge()+"\t sj"+this.getSj());
		return super.toString();
	}

}

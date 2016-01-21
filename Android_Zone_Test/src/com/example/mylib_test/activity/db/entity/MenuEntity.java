package com.example.mylib_test.activity.db.entity;

public class MenuEntity{
	public String info;
	public Class<?> goClass;
	public MenuEntity(String info,Class<?> goClass) {
		this.info=info;
		this.goClass=goClass;
	}
}
package com.playdate.model;

public class DataHolder {
	  private Long userid;
	  public Long getData() {return userid;}
	  public void setData(Long userid) {this.userid = userid;}

	  private static final DataHolder holder = new DataHolder();
	  public static DataHolder getInstance() {return holder;}
	}
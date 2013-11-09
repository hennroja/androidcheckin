package com.fortytwo.discipline;

import java.util.ArrayList;

public class Shop {
	private ArrayList<Sensor> ShopSensors;
	private String Shopname="";
	
	public ArrayList<Sensor> getShopSensors(){
		return ShopSensors;
	}
	
	public String getShopname(){
		return Shopname;
	}
	
	public void setShopname(String shopname){
		this.Shopname=shopname;
	}
}

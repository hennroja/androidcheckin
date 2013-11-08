package com.fortytwo.discipline;

public class Sensor {
	private String BSSID;
	private String Shopname;
	private Integer RSSI;
	
	public Sensor (String BSSID, Integer RSSI){
		this.BSSID=BSSID;
		this.RSSI=RSSI;
	};

	public String getBSSID(){return BSSID;}
	public String getShopname(){return Shopname;}
	public Integer getRSSI(){return RSSI;}

	public void setBSSID(String bssid){this.BSSID=bssid;}
	public void setShopname(String shopname){this.Shopname=shopname;}
	public void setRSSI(Integer rssi){this.RSSI=rssi;}

}

package com.fortytwo.discipline;

public class Sensor {
	private String BSSID;
	private String Shopname;
	private Integer RSSI;
	private String SSID;
	
	public Sensor (String BSSID, Integer RSSI){
		this.BSSID=BSSID;
		this.RSSI=RSSI;
	};

	public String getBSSID(){return BSSID;}
	public String getShopname(){return Shopname;}
	public Integer getRSSI(){return RSSI;}
	public String getSSID(){return SSID;}

	public void setBSSID(String bssid){this.BSSID=bssid;}
	public void setSSID(String ssid){this.SSID=ssid;}
	public void setShopname(String shopname){this.Shopname=shopname;}
	public void setRSSI(Integer rssi){this.RSSI=rssi;}

}

package com.fortytwo.discipline;

import java.util.ArrayList;
import java.util.List;

public class ApiConnector {

	private static class SingletonHolder {
		public static final ApiConnector SINGLETON = new ApiConnector();
	}

	public static ApiConnector getSingleton() {
		return SingletonHolder.SINGLETON;
	}

	public void getShopnames(List<Sensor> SensorList) {
		ArrayList<Sensor> removeList = new ArrayList<Sensor>();
		for (Sensor sensor : SensorList) {
			String returned_Shopname = null;
			if (sensor.getSSID().equals("")) {
				// returnval=callapi(sensor.getBSSID());
				returned_Shopname = "NiceShopname";
				sensor.setShopname(returned_Shopname);
				sensor.setSSID("42-maintenance");
			}
			if (returned_Shopname == null)
				removeList.add(sensor);
		}
		SensorList.removeAll(removeList);
	}

	public boolean initApiConnection() {
		// init api connection sucess
		return true;
	}

	public void stopApiConnection() {
		// stop api connection
	}

}

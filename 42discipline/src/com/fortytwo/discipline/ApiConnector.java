package com.fortytwo.discipline;

import java.util.List;

public class ApiConnector {

	private static class SingletonHolder {
		public static final ApiConnector SINGLETON = new ApiConnector();
	}

	public static ApiConnector getSingleton() {
		return SingletonHolder.SINGLETON;
	}

	public List<Sensor> getShopnames(List<Sensor> asd) {
		// call a
		for (Sensor sensor : asd) {
			String returned_Shopname = null;
			// String returnval=callapi(sensor.getBSSID());
			String returnval="NiceShopname";
			if (returned_Shopname != null)
				sensor.setShopname(returned_Shopname);
			else
				asd.remove(sensor);
		}

		return asd;
	}

}

package com.fortytwo.discipline;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.net.wifi.ScanResult;

public class SSIDCheckerService extends Service {

	private static String TAG = "SSIDCheckerService";
	private final IBinder mBinder = new WifiBinder();
	private WifiManager wifiMan;

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class WifiBinder extends Binder {
		SSIDCheckerService getService() {
			return SSIDCheckerService.this;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		this.registerReceiver(this.NewWifiResults, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}
	
	private BroadcastReceiver NewWifiResults = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
						
			Log.d(TAG,"Call BCR - Action: "+action);
			
			ArrayList<ScanResult> resList = wifiMan.getScanResults();
			
			if (obj != null) {
				if (obj instanceof WifiInfo) {
					obj = (WifiInfo) obj;
					String networkName = ((WifiInfo) obj).getSSID();
					System.out.println(" Info : "+ action + " - "+networkName);
				}
			}

		}
	};


}

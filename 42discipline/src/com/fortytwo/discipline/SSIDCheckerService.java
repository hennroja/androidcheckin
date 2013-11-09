package com.fortytwo.discipline;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.net.wifi.ScanResult;

public class SSIDCheckerService extends Service {

	private static String TAG = "SSIDCheckerService";
	private final IBinder mBinder = new WifiBinder();
	private NotificationManager mNotifyManager;
	private Notification mNotification;
	private WifiManager wifiMan;
	private Shop actualShop;

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


	private void dohapticNotification(String msg) {
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		long[] pattern = { 0, 500, 500, 500, 500, 500, 500, 500 };

		NotificationCompat.Builder mNotification = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher).setSound(soundUri)
				.setContentTitle("Check In").setContentText(msg).addAction(0, "Enter", null);
		// .setVibrate(pattern)

		Intent resultIntent = new Intent(this, MainActivity.class);

		
		
		
		//TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		//stackBuilder.addParentStack(MainActivity.class);
		//stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =PendingIntent.getActivity(this, 0, resultIntent, 0); 
				//stackBuilder.getPendingIntent(0,
				//PendingIntent.FLAG_UPDATE_CURRENT);
		mNotification.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(123, mNotification.build());

	}

	private BroadcastReceiver NewWifiResults = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();

			Log.d(TAG, "Call BCR - Action: " + action);

			List<ScanResult> resList = wifiMan.getScanResults();
			ArrayList<Sensor> senList = new ArrayList<Sensor>();

			for (ScanResult scanResult : resList) {
				senList.add(new Sensor(scanResult.BSSID, scanResult.level,
						scanResult.SSID));
			}

			ApiConnector.getSingleton().getShopnames(senList);

			actualShop = new Shop();

			for (Sensor sensor : senList) {

				Log.d(TAG,
						"BSSID: " + sensor.getBSSID() + " SSID: "
								+ sensor.getSSID() + " ShopName: "
								+ sensor.getShopname());

			}

			if(senList.size()>0){
			if (!actualShop.getShopname().equals(senList.get(0).getShopname())) {
				dohapticNotification(senList.get(0).getShopname());
				actualShop.setShopname(senList.get(0).getShopname());
			}
			}

		}

	};

}

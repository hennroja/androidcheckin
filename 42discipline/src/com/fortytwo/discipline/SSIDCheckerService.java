package com.fortytwo.discipline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
	LocalDatabaseConnector ldc;
	//private LinkedHashMap<String, ArrayList<Sensor>> actualShops;

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
		ldc = new  LocalDatabaseConnector(getApplicationContext());

		demoData();

		
		wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		//actualShops = new LinkedHashMap<String, ArrayList<Sensor>>();

		this.registerReceiver(this.NewWifiResults, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}

	private void dohapticNotification(Sensor sensor) {

		Uri soundUri = RingtoneManager
		 .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		 long[] pattern = { 0, 100, 200, 100, 200, 200, 500, 100 };

		Intent agreeIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingAgreeIntent = PendingIntent.getActivity(this, 0,
				agreeIntent, 0);

        Intent cancelMain = new Intent("action_close_app");
        PendingIntent pendingCancelIntent = PendingIntent.getActivity(this, 0,
        		cancelMain, 0);
		
		NotificationCompat.Builder mNotification = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_launcher)
				 .setSound(soundUri) //this makes me angry
				 .setVibrate(pattern) //this too
				.setContentTitle("Check In").setContentText(sensor.getShopname())
				.addAction(0, "Agree", pendingAgreeIntent)
				.addAction(0, "Cancel", pendingCancelIntent);

		
		mNotification.setContentIntent(null);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(4242, mNotification.build());
	
	}

	private BroadcastReceiver NewWifiResults = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			List<ScanResult> resList = wifiMan.getScanResults();
			ArrayList<Sensor> senList = new ArrayList<Sensor>();

			for (ScanResult scanResult : resList) {
				Sensor ss = new Sensor(scanResult.BSSID, scanResult.level,
						scanResult.SSID);
				ss.setDate(new Date());
				if(ldc.checkSensor(ss)){
					senList.add(ss);
				}
				
			}

			
			//ApiConnector.getSingleton(getApplicationContext()).getShopnames(senList);
			Log.d(TAG,"sensorList after Request"+senList.toString());

			Sensor winner = null;
			for (Sensor sensor : senList) {
				if(winner==null) winner = sensor;
				else if(winner.getRSSI()<sensor.getRSSI()){
					winner = sensor;
				}
			}

			if(winner!=null){
				System.out.println(winner.getShopname());
				dohapticNotification(winner);
			}
		}

	};
	
	private void demoData() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
	    Sensor s1 = new Sensor("f8:1a:67:51:59:18", -44, "42main");
		s1.setShopname("42Kitchen");
		s1.setDate(new Date());
		
	   Sensor s2 = new Sensor("a0:f3:c1:16:5a:58", -44, "42main");
		s2.setShopname("42Desk");
		s2.setDate(new Date());

	   Sensor s3 = new Sensor("a0:f3:c1:76:52:9c", -44, "42main");
		s3.setShopname("42Door");
		s3.setDate(new Date());

		ldc.addSensor(s1);
		ldc.addSensor(s2);
		ldc.addSensor(s3);
		
		ArrayList<Sensor> b = ldc.getAllSensors();
		for (Sensor sensor2 : b) {
			System.out.print(sensor2.getBSSID()+" ");
			System.out.print(sensor2.getShopname()+" ");
			System.out.print(sensor2.getSSID()+" ");
			System.out.print(sensor2.getRSSI()+" ");
			System.out.println(sensor2.getTimestamp()+" ");
		}

	}

}

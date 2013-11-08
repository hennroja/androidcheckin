package com.fortytwo.discipline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.Context; 
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	ArrayList<String> appointment;
	ArrayAdapter<String> aa;
	MainActivity main = this;

	private void addItem(String item) {

		if (item.length() > 0) {
			this.appointment.add(item);
			this.aa.notifyDataSetChanged();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.listView1);

		appointment = new ArrayList<String>();
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, appointment);
		lv.setAdapter(aa);
		
		
		addItem("Desk");
		addItem("Door");
		addItem("Kitchen");
		addItem("Testbed #1");
		addItem("Testbed #2");
		addItem("Testbed #3");
		
		ActivityManager aManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		
		boolean isRunning = false;
        for (RunningServiceInfo service : aManager.getRunningServices(Integer.MAX_VALUE)) {
           
        	
        	if ("com.fortytwo.discipline.SSIDCheckerService"
        			.equals(service.service.getClassName())) {
        		isRunning = true;
            	break; 	
            }
        }
        if(!isRunning) {
        	startService(new Intent( new Intent(this, SSIDCheckerService.class)));
        }
		Button btnScan = (Button) findViewById(R.id.button1);
		btnScan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				WifiManager wifiManager = (WifiManager) 
						main.getSystemService(Context.WIFI_SERVICE);
				wifiManager.startScan();
				
				List<ScanResult> results=wifiManager.getScanResults();
				aa.clear();
				for(int i=0;i<results.size();i++){
					addItem(results.get(i).SSID.toString());
				}
			}
			
		});

		ActivityManager aManager1 = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : aManager1
				.getRunningServices(Integer.MAX_VALUE)) {

			if ("com.fortytwo.discipline.SSIDCheckerService.java"
					.equals(service.service.getClassName())) {

				System.out.println("HHEHEHEHEHE");

				break;

			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

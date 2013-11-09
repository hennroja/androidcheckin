package com.fortytwo.discipline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context; 
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ArrayList<String> appointment;
	private ArrayAdapter<String> aList;
	private MainActivity main = this;
    private SSIDCheckerService mBoundService;
    private boolean isRunning = false;
    private final String TAG = "MAIN ACITIVTY";


	private void addItem(String item) {

		if (item.length() > 0) {
			this.appointment.add(item);
			this.aList.notifyDataSetChanged();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.listView1);

		appointment = new ArrayList<String>();
		aList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, appointment);
		lv.setAdapter(aList);
		

		ActivityManager aManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		
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
        
        bindService(new Intent(MainActivity.this,SSIDCheckerService.class), mServiceConn, Context.BIND_AUTO_CREATE);


	}
	
    
    private ServiceConnection mServiceConn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((SSIDCheckerService.WifiBinder)service).getService();	
    		isRunning = true;
        	
        	Log.d(TAG, "refresh triggerd by onServiceConnected");
        	
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

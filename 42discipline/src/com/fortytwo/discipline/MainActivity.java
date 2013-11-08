package com.fortytwo.discipline;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

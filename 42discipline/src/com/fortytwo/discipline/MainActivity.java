package com.fortytwo.discipline;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	        ActivityManager aManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : aManager.getRunningServices(Integer.MAX_VALUE)) {
           
        	if ("com.fortytwo.discipline.SSIDCheckerService.java"
        			.equals(service.service.getClassName())) {
        		
        		System.out.println("HHEHEHEHEHE");
        		System.out.println("HHEHEHEHEHE");
        		System.out.println("HHEHEHEHEHE");
        		System.out.println("HHEHEHEHEHE");
        		System.out.println("HHEHEHEHEHE");
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

package com.fortytwo.discipline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Autostart extends BroadcastReceiver {

	private final String TAG = "AutostartReceiver";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Start Service");
		Intent serviceIntent = new Intent(context, SSIDCheckerService.class);
		     context.startService(serviceIntent);
		  
	}

}

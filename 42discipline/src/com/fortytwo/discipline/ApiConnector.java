package com.fortytwo.discipline;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

public class ApiConnector {
    private final String TAG = "MAIN ACITIVTY";
	private final String USER_AGENT = "Mozilla/5.0";
	private static Context cont;
	
	private static class SingletonHolder {
		public static final ApiConnector SINGLETON = new ApiConnector();
	}

	public static ApiConnector getSingleton(Context con) {
		cont = con;
		return SingletonHolder.SINGLETON;
	}

	public void getShopnames(List<Sensor> SensorList) {
		ArrayList<Sensor> removeList = new ArrayList<Sensor>();
		ArrayList<String> macStringsList= new ArrayList<String>();
		
		for (Sensor sensor : SensorList) {
			String returned_Shopname = null;
			if (sensor.getSSID().equals("")) {
				macStringsList.add(sensor.getBSSID());
				
				
				DatabaseConnector db = new DatabaseConnector(cont);
				
				if(db.checkSensor(sensor)){
					db.updateContact(sensor);
				}else{
					RequestManager task = new RequestManager();
					try {
						returned_Shopname=task.execute(macStringsList.get(0)).get();
						sensor.setShopname(returned_Shopname);

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}	
				}
				
			
    
    			//returned_Shopname = "NiceShopname";
				sensor.setSSID("42maintenance");
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

	public String httppost(String sensormac)  {
		
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
		String URL="http://192.168.2.173:8181/sensors"	;													// Limit
		HttpResponse response;

		JSONArray json =new JSONArray();
		try {
			HttpPost post = new HttpPost(URL);
//			
//			json.put("a0:f3:c1:76:51:b0");
//			json.put("f8:1a:67:51:75:96");
			json.put(sensormac);
			StringEntity se = new StringEntity(json.toString());
			//Log.d(TAG,getStringFromInputStream(se.getContent()));
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(se);
			response = client.execute(post);

			/* Checking response */
			if (response != null) {
				InputStream in = response.getEntity().getContent(); // Get the
				Log.d(TAG,"answer httprequest"+getStringFromInputStream(in));
				return getStringFromInputStream(in);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {
	 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
		
		  private class RequestManager extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... arg0) {
				return httppost(arg0[0]);
			}
			  
		  }


}

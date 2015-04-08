package com.example.yamba;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.IHttpClient;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class YambaApplication extends Application {
	private static final String TAG=YambaApplication.class.getSimpleName();
	Twitter twitter;
	SharedPreferences prefs;
	private static int dataId = 0;
	private boolean serviceRunning;
	
	public boolean isServiceRunning(){
		return serviceRunning;
	}
	
	public void setServiceRunning(boolean sr){
		serviceRunning = sr;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
			
			@Override
			public synchronized void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {
				// TODO Auto-generated method stub
				twitter = null;
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public synchronized Twitter getTwitter(){
		if(twitter == null){
			String username, password, apiRoot;
			username = prefs.getString("username", "student");
			password = prefs.getString("password", "password");
			apiRoot = prefs.getString("apiRoot", "http://yamba.marakana.com/api");
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl(apiRoot);
	
					
		}
		return twitter;
	} 
	
	public List<TimeLine> getTimeLine(){
		
		List<TimeLine> list = new ArrayList<TimeLine>();
		
		for(int i = 0; i < 20; i++){
			TimeLine t = new TimeLine(
					dataId++,
					"createAt" + new Random().nextInt(100), "source" + new Random().nextInt(100),
					"user"+new Random().nextInt(100), "text"+new Random().nextInt(100));
			list.add(t);
		}
		
		return list;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}

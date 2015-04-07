package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class YambaApplication extends Application {
	private static final String TAG=YambaApplication.class.getSimpleName();
	Twitter twitter;
	SharedPreferences prefs;
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
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}

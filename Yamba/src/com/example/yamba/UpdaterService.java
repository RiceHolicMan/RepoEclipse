package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	static final String TAG = "UpdaterService";
	
	static final int DELAY = 60000;
	private boolean runFlag = false;
	private Updater updater;
	private YambaApplication yamba;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.yamba = (YambaApplication) getApplication();
		this.updater = new Updater();
		Log.d(TAG, "onCreated");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		
		if(!runFlag){
			this.runFlag = true;
			this.updater.start();
		}
		
		this.yamba.setServiceRunning(true);
		Log.d(TAG, "onStarted");
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
		this.runFlag = false;
		this.updater.interrupt();
		this.updater = null;
		this.yamba.setServiceRunning(false);
	}


	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class Updater extends Thread{
		
		List<TimeLine> timeline;
		public Updater(){
			super("UpdaterService-Updater");
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			UpdaterService updaterService = UpdaterService.this;
			while(updaterService.runFlag){
				Log.d(TAG, "Updater running");
				try {
					//Twitter twitter = yamba.getTwitter();
					timeline = yamba.getTimeLine();
					for(TimeLine status : timeline){
						Log.d(TAG, String.format("%s -- %s", status.username, status.text));
					}
						
					Log.d(TAG, "Updater ran");
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					updaterService.runFlag = false;
				}
			}
		}
		
		
	}

}

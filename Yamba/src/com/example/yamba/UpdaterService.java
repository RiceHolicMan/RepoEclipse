package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	static final String TAG = "UpdaterService";
	
	static final int DELAY = 60000;
	private boolean runFlag = false;
	private Updater updater;
	private YambaApplication yamba;
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.yamba = (YambaApplication) getApplication();
		this.updater = new Updater();
		
		dbHelper = new DbHelper(this);
		
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
					
					db = dbHelper.getWritableDatabase();
					ContentValues values = new ContentValues();
					for(TimeLine status : timeline){
						Log.d(TAG, String.format("%s -- %s", status.username, status.text));
						values.clear();
						values.put(DbHelper.C_ID, status.id);
						values.put(DbHelper.C_CREATED_AT, status.createAt);
						values.put(DbHelper.C_SOURCE, status.source);
						values.put(DbHelper.C_TEXT, status.text);
						values.put(DbHelper.C_USER, status.username);
						db.insertOrThrow(DbHelper.TABLE, null, values);
					}
					
					db.close();
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

package com.example.webtest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//i got mad!!!!!!!!!!!!
		//you know!!!!
	}
	
	private void visitJSP(){
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Log.v("AndroidWeb", "T T!!!");
		try {
			url = new URL("http://127.0.0.1:8080/test/AndroidWeb.jsp?testParam1=110");
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setDoOutput(true);
			
			outputStream = httpurlconnection.getOutputStream();
			outputStream.write("testParam2=110".getBytes());
			outputStream.flush();
			inputStream = httpurlconnection.getInputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while((i=inputStream.read(b)) != -1){
				System.out.println(new String(b));
				Log.v("AndroidWeb", new String(b));
				b=new byte[1024];
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.v("AndroidWeb", "hello");
		return true;
	}

}

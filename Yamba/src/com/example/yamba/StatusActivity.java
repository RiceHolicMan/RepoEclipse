package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity {
	
	private static final String TAG="StatusActivity";
	EditText editText;
	Button updateButton;
	
	TextView textCount;
	
	//got mad !!!
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		textCount = (TextView) findViewById(R.id.textView2);
		editText = (EditText) findViewById(R.id.editText1);
		updateButton = (Button) findViewById(R.id.button1);
		
		
		updateButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String status = editText.getText().toString();
				new PostToTwitter().execute(status);
				//twitter.setStatus(editText.getText().toString());
				Log.d(TAG, "onClicked");
			}
		}
		);
		
		editText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int count = 140 - s.length();
				textCount.setText(String.valueOf(count));
				textCount.setTextColor(Color.GREEN);
				if(count < 10)
					textCount.setTextColor(Color.YELLOW);
				if(count < 0)
					textCount.setTextColor(Color.RED);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		}
		);
		
		//twitter = new Twitter("student","password");
		//twitter.setAPIRootUrl("http://yamba.marakana.com/api");
	}
	
	
	
	class PostToTwitter extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Twitter twitter = ((YambaApplication) getApplication()).getTwitter();
			winterwell.jtwitter.Status status = twitter.updateStatus(params[0]);
			return status.text;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_settings:
			startActivity(new Intent(this, PrefsActivity.class));
			break;
		case R.id.itemServiceStart:
			startService(new Intent(this, UpdaterService.class));
			break;
		case R.id.itemServiceStop:
			stopService(new Intent(this, UpdaterService.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}

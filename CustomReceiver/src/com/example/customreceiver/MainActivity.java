package com.example.customreceiver;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

		
	public void startAlarm(int timer)
	{
		Intent broadcast = new Intent();
		broadcast.putExtra("data1","data1" );
		broadcast.putExtra("data2", "data2");
		broadcast.setAction("testAction");
		Log.e("test","Broadcast fired");
		long triggerAtMillis = System.currentTimeMillis()+timer;
		PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this, 0, broadcast,PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);				
		alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
	}
	
	
	public void fireBroadCast(View view)
	{
		startAlarm(2000);
	}
	@Override
	protected void onPause() {
	   super.onPause();
	   try {
	        unregisterReceiver(mCronReceiver); 
	    } catch (Exception exc) {

	    }
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		IntentFilter filter = new IntentFilter();
        filter.addAction("testAction");
		registerReceiver(mCronReceiver, filter);
	}
	
	 // Creating an instance of our BR for activity to use
  	protected final CronReceiver mCronReceiver = new CronReceiver(); 
  	
	// BR that is encapsulated in Activity and therefore has access to it's methods, since it has access to Activity instance
 	protected class CronReceiver extends BroadcastReceiver {
 	    @Override
 	    public void onReceive(Context context, Intent intent) {
 	       	 Toast.makeText(getApplicationContext(), "Receiver fired", Toast.LENGTH_LONG).show();	 	    	
 	    }
 	}

}

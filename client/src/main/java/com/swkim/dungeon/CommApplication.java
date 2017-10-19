package com.swkim.dungeon;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;

import com.swkim.dungeon.receiver.SWReceiver;

public class CommApplication extends Application {
	
	SWReceiver ScreenOffReceiver = new SWReceiver();
	String TAG = "CommApplication";
	
	@Override
	public void onCreate() {
		Log.d(TAG,"onCreate");
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		
		registerReceiver(ScreenOffReceiver, filter);
		super.onCreate();
	}	

	public  void stopAlarm() {
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent("DummyService");
		PendingIntent ServicePending = PendingIntent.getService(
				getApplicationContext(), 123456, intent, 0);
		am.cancel(ServicePending);
	}

	public void startAlarm(int time) {
		try {

			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			Intent intent = new Intent("DummyService");
			PendingIntent ServicePending = PendingIntent.getService(
					getApplicationContext(), 123456, intent, 0);
			long firstTime = SystemClock.elapsedRealtime();
			am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
					firstTime,time * 1000, ServicePending);

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}
}

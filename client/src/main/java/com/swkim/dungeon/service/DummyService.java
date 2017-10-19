package com.swkim.dungeon.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by swkim on 14. 3. 6.
 */
public class DummyService extends IntentService {
	String TAG = "DummyService";

	public DummyService() {
		super("DummyService");
	}

	@Override
	public void onCreate() {

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			if (isServiceRunning() ==false){
				Log.d(TAG,"ProcessService 실행 커멘드");
				Intent intent2 = new Intent("ProcessService");
				startService(intent2);
			}
			else{
				Log.d(TAG,"실행중이라 실행안함");
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	

	private boolean isServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			/*Log.d(TAG,service.service
					.getClassName());*/
			if ("com.swkim.dungeon.service.ProcessService".equals(service.service
					.getClassName())) {
				return true;
			}
		}
		return false;
	}	

}
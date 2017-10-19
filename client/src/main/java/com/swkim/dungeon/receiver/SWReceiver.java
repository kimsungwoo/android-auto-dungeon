package com.swkim.dungeon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.swkim.dungeon.CommApplication;

public class SWReceiver extends BroadcastReceiver{

	String TAG = "SWReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
			Log.d(TAG,Intent.ACTION_SCREEN_OFF);
			((CommApplication)context.getApplicationContext()).stopAlarm();
		}
	}
}

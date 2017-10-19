package com.swkim.dungeon.service;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.swkim.dungeon.CommApplication;
import com.swkim.dungeon.MainActivity;
import com.swkim.dungeon.Model;
import com.swkim.dungeon.R;

/**
 * Created by swkim on 14. 3. 6.
 */
public class MainService extends Service {

	SettingsContentObserver mSettingsContentObserver;
	public static boolean bCheck;

	String TAG = "MainService";
	ArrayList<Model> arrModel = new ArrayList<Model>();
	int mTime = 30;

	@Override
	public void onCreate() {

		mSettingsContentObserver = new SettingsContentObserver(
				getApplicationContext(), new Handler());
		getApplicationContext().getContentResolver().registerContentObserver(
				android.provider.Settings.System.CONTENT_URI, true,
				mSettingsContentObserver);

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		// TODO Queue에 들어있는 모든서비스 종료시에 호출
		getApplicationContext().getContentResolver().unregisterContentObserver(
				mSettingsContentObserver);

		super.onDestroy();

	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
				
		try {
			Intent localIntent = new Intent(this, MainActivity.class);
			localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			localIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0,
					localIntent, 0);
	
			Notification localNotification;
	
			Bitmap localBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher);
	
			NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(
					this);
			localBuilder.setContentTitle("던왕서비스실행중").setContentText("방가")
					.setSmallIcon(R.drawable.ic_launcher).setLargeIcon(localBitmap)
					.setWhen(System.currentTimeMillis())
					.setPriority(NotificationCompat.PRIORITY_MIN);
			localBuilder.setContentIntent(localPendingIntent);
			localNotification = localBuilder.build();
	
			localNotification.flags = (Notification.FLAG_FOREGROUND_SERVICE | localNotification.flags);
			localNotification.flags = (Notification.FLAG_NO_CLEAR | localNotification.flags);
			localNotification.flags = (Notification.FLAG_ONGOING_EVENT | localNotification.flags);
			startForeground(999, localNotification);
			
			this.mTime = intent.getIntExtra("time",30);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return START_STICKY;

	}

	public class SettingsContentObserver extends ContentObserver {
		int previousVolume;
		Context context;

		public SettingsContentObserver(Context c, Handler handler) {
			super(handler);
			context = c;

			AudioManager audio = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		}

		@Override
		public boolean deliverSelfNotifications() {
			return super.deliverSelfNotifications();
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			AudioManager audio = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			int currentVolume = audio
					.getStreamVolume(AudioManager.STREAM_MUSIC);

			int delta = previousVolume - currentVolume;

			if (delta > 0) {
				Toast.makeText(context, "종료", Toast.LENGTH_LONG).show();
				((CommApplication)getApplicationContext()).stopAlarm();				
								
				previousVolume = currentVolume;
			} else if (delta < 0) {
				Toast.makeText(context, "서비스시작", Toast.LENGTH_LONG).show();

				((CommApplication)getApplicationContext()).startAlarm(mTime );
				previousVolume = currentVolume;
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


}
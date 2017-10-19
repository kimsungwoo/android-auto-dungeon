package com.swkim.dungeon.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.swkim.dungeon.Model;
import com.swkim.dungeon.Util;

import java.util.ArrayList;

/**
 * Created by swkim on 14. 3. 6.
 */
public class ProcessService extends IntentService {
	String TAG = "ProcessService";
	ArrayList<Model> arrModel;
	

	public ProcessService() {
		super("ProcessService");
	}

	@Override
	public void onCreate() {
		arrModel = new ArrayList<Model>();
		arrModel.add(new Model("탐험완료004", 900, 50, 400, 100, "완료"));	//탐험 완료
		arrModel.add(new Model("탐험완료005", 1070, 850, 200, 100, "선텍"));
		arrModel.add(new Model("다시하기006", 700, 938, 250, 80, "도전"));
		arrModel.add(new Model("보스등장", 1450, 920, 150, 100, "입장"));	//보스등장
		arrModel.add(new Model("반지부족", 790, 540, 100, 55, "반지"));		
		arrModel.add(new Model("탐험하기002", 1335, 938, 330, 100, "헉입쟝"));//입장
		arrModel.add(new Model("탐험하기003", 350, 120, 180, 80, "목록"));//게임시작
		
		Log.d(TAG, "onCreate");
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
			Log.d(TAG, "onHandleIntent");
			MainService.bCheck = true;

			String iret = Util.excuteScreenShot_Check(getApplicationContext(),
					arrModel);

			if (iret != null) {
				runProcess(iret);
				
				if ((iret.equals("탐험완료004")) || iret.equals("보스등장")){
				
					iret = Util.excuteScreenShot_Check(getApplicationContext(),
							arrModel);
					
					runProcess(iret);
					
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	private void runProcess(String iret) {
		try {

			String path = "monkey  -f  /sdcard/hero_auto/"
					+ iret + ".txt 1";

			Log.d(TAG, path);
			Util.excuteShell(path);

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

}

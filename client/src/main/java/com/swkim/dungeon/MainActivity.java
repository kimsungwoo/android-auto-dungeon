package com.swkim.dungeon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.swkim.autodungeon.R;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {

	
	String TAG = "MainActivity";

	TextView txtvResult;
	ImageView imgvMain;
	Button btnCrop001;
	EditText edtTime;
	Button btnStart;
	Button btnEnd;
	

	public static ArrayList<Model> arrModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent("MainService"));
		txtvResult = (TextView) findViewById(R.id.txtv_crop);
		imgvMain = (ImageView) findViewById(R.id.imgv_main);
		btnCrop001 = (Button) findViewById(R.id.btn_imageCrop001);
		
		edtTime = (EditText)findViewById(R.id.edt_time);
		
		btnStart = (Button)findViewById(R.id.btn_start);
		btnEnd = (Button)findViewById(R.id.btn_end);
		
		btnCrop001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				arrModel = new ArrayList<Model>();
				arrModel.add(new Model("탐험완료004", 900, 50, 400, 100, "완료"));	//탐험 완료
				arrModel.add(new Model("탐험완료005", 1070, 850, 200, 100, "선텍"));
				arrModel.add(new Model("다시하기006", 700, 938, 250, 80, "도전"));
				arrModel.add(new Model("보스등장", 1450, 920, 150, 100, "입쟝"));	//보스등장
				arrModel.add(new Model("반지부족", 790, 540, 100, 55, "반지"));		
				arrModel.add(new Model("탐험하기002", 1335, 938, 330, 100, "헉입쟝"));//입장
				arrModel.add(new Model("탐험하기003", 350, 120, 180, 80, "목록"));//게임시작
				
				Model clsmodel = new Model("다시하기006", 700, 938, 250, 80, "도전");
				Util.decodeTess(Environment.getExternalStorageDirectory()
						+ File.separator + "005.png", clsmodel, false,
						txtvResult, imgvMain);				

			}
		});


		try {
			Process sh = Runtime.getRuntime().exec("su", null, null);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("MainService");
				intent.putExtra("time",Integer.valueOf(edtTime.getText().toString()));				
				startService(intent);
				
			}
		});
		
		btnEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				stopService(new Intent("MainService"));
				
			}
		});

	}
}

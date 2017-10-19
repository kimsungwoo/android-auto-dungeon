package com.swkim.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.swkim.dungeon.service.MainService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by swkim on 14. 3. 6.
 */
public class Util {

	private String sourceLanguageCodeOcr = "kor"; // ISO 639-3 language code
	private int pageSegmentationMode = TessBaseAPI.PageSegMode.PSM_AUTO_OSD;
	private int ocrEngineMode = TessBaseAPI.OEM_TESSERACT_ONLY;
	private static final String TESSBASE_PATH = "/mnt/sdcard/tesseract/";
	private static final String DEFAULT_LANGUAGE = "kor";

	final static String TAG = "Util";

	public static void excuteScreenShot(Model clsmodel) {

		try {
			Process sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p " + "/sdcard/img.png")
					.getBytes());
			os.flush();

			os.close();
			sh.waitFor();

			decodeTess(Environment.getExternalStorageDirectory()
					+ File.separator + "img.png", clsmodel, true);

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

	}

	public static String excuteScreenShot_Check(Context context,
			ArrayList<Model> arrModel) {

		try {
			Process sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p " + "/sdcard/img.png")
					.getBytes());
			os.flush();

			os.close();
			sh.waitFor();

			for (int i = 0; i < arrModel.size(); i++) {
				if (MainService.bCheck == false) {
					return null;
				}
				Model model = arrModel.get(i);
				boolean bCheck = decodeTess(
						Environment.getExternalStorageDirectory()
								+ File.separator + "img.png", model, true);

				/**
				 * 성공
				 */
				if (bCheck == true) {
					return arrModel.get(i).getTag();
				}

			}

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return null;

	}

	/**
	 * 
	 * @param filepath
	 * @param clsModel
	 * 
	 */
	public static boolean decodeTess(String filepath, Model clsModel,
			boolean bRotate) {
		try {
			Bitmap screen = BitmapFactory.decodeFile(filepath);

			Bitmap screenRocate;

			if (bRotate == true) {

				Matrix matrix = new Matrix();
				matrix.postRotate(270);
				screenRocate = Bitmap.createBitmap(screen, 0, 0,
						screen.getWidth(), screen.getHeight(), matrix, true);

			} else {
				screenRocate = screen;
			}

			Bitmap resizedBitmap = Bitmap.createBitmap(screenRocate,
					clsModel.getX(), clsModel.getY(), clsModel.getWidth(),
					clsModel.getHeigth());

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();

			resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
			/*
			 * File f = new File(Environment.getExternalStorageDirectory() +
			 * File.separator + "img_crop.jpg"); f.createNewFile();
			 * FileOutputStream fo = new FileOutputStream(f);
			 * fo.write(bytes.toByteArray()); fo.close();
			 */

			final TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
			baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
			baseApi.setImage(resizedBitmap);

			// Ensure that the result is correct.
			final String outputText = baseApi.getUTF8Text();
			if (outputText != null) {
				Log.d(TAG, "getUTF8Text=" + outputText);
				if (outputText.contains(clsModel.getParseText()))
					return true;

			} else
				Log.d(TAG, "getUTF8Text is null");

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;
	}

	/**
	 * 
	 * @param filepath
	 * @param clsModel
	 * 
	 */
	public static void decodeTess(String filepath, Model clsModel,
			boolean bRotate, TextView txtv, ImageView imgv) {
		try {
			Bitmap screen = BitmapFactory.decodeFile(filepath);

			Bitmap screenRocate;

			if (bRotate == true) {

				Matrix matrix = new Matrix();
				matrix.postRotate(270);
				screenRocate = Bitmap.createBitmap(screen, 0, 0,
						screen.getWidth(), screen.getHeight(), matrix, true);

			} else {
				screenRocate = screen;
			}

			Bitmap resizedBitmap = Bitmap.createBitmap(screenRocate,
					clsModel.getX(), clsModel.getY(), clsModel.getWidth(),
					clsModel.getHeigth());

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();

			resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

			imgv.setImageBitmap(resizedBitmap);
			/*
			 * File f = new File(Environment.getExternalStorageDirectory() +
			 * File.separator + "img_crop.jpg"); f.createNewFile();
			 * FileOutputStream fo = new FileOutputStream(f);
			 * fo.write(bytes.toByteArray()); fo.close();
			 */

			final TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
			baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
			baseApi.setImage(resizedBitmap);

			// Ensure that the result is correct.
			final String outputText = baseApi.getUTF8Text();
			if (outputText != null) {
				Log.d(TAG, "getUTF8Text=" + outputText);

				txtv.setText(outputText);
				if (outputText.startsWith(clsModel.getParseText()))
					Log.d(TAG, clsModel.tag + "성공");
			} else
				Log.d(TAG, "getUTF8Text is null");

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	public static void excuteShell(String path) {
		try {
			Process sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write((path).getBytes());
			os.flush();

			os.close();
			sh.waitFor();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

}

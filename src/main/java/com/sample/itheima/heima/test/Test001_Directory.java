package com.sample.itheima.heima.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.os.StatFs;
import android.test.AndroidTestCase;
import android.text.format.Formatter;
import android.util.Log;

/**
 * Test Directory.
 * 
 * @author Aaric
 *
 */
public class Test001_Directory extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	public static final String TAG = Test001_Directory.class.getSimpleName();
	
	/**
	 * Test HttpClient.
	 * 
	 * @throws Exception
	 */
	public void testHttpClient() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Content-Type", "text/html;charset=UTF-8");
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		Log.d(TAG, result);
		
	}
	
	/**
	 * Show share preference.
	 */
	public void showSharePreference() {
		SharedPreferences sp = this.getContext().getSharedPreferences("cfg", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("username", "root");
		editor.putString("password", "123456");
		editor.commit();
		
	}
	
	/**
	 * Test save and read data.
	 */
	public void testSaveAndReadData() {
		// Write File.
		try {
			FileOutputStream fos = this.getContext().openFileOutput("private.txt", Context.MODE_PRIVATE);
			fos.write("string".getBytes());
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Read File.
		try {
			FileInputStream fis = this.getContext().openFileInput("private.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String result = br.readLine();
			Log.d(TAG, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Show storage size.
	 */
	@SuppressWarnings("deprecation")
	public void showStorageSize() {
		// 内部存储容量
		StatFs internalStatFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
		int internalBlockSize = internalStatFs.getBlockSize();
		int internalBlockCount = internalStatFs.getBlockCount();
		int internalAvailableBlocks = internalStatFs.getAvailableBlocks();
		Log.v(TAG, "内部存储容量："
				+ Formatter.formatFileSize(this.getContext(), internalBlockSize * internalAvailableBlocks)
				+ "/" + Formatter.formatFileSize(this.getContext(), internalBlockSize * internalBlockCount));
		
		// SD卡容量
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			StatFs externalStatFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
			int externalBlockSize = externalStatFs.getBlockSize();
			int externalBlockCount = externalStatFs.getBlockCount();
			int externalAvailableBlocks = externalStatFs.getAvailableBlocks();
			Log.v(TAG, "SD卡容量："
					+ Formatter.formatFileSize(this.getContext(), externalBlockSize * externalAvailableBlocks)
					+ "/" + Formatter.formatFileSize(this.getContext(), externalBlockSize * externalBlockCount));
			
		}
		
	}
	
	/**
	 * Show directory.
	 */
	public void showDirectory() {
		// 获得Data缓存目录
		File intelnalDateDirectory = this.getContext().getCacheDir();
		Log.v(TAG, "Data缓存目录：" + intelnalDateDirectory);
		
		// 获得内部存储目录
		File intelnalStorageDirectory = this.getContext().getFilesDir();
		Log.v(TAG, "内部存储目录：" + intelnalStorageDirectory);
		
		// 获得SD卡目录
		File sdcardDirectory = Environment.getExternalStorageDirectory();
		Log.v(TAG, "SD卡目录：" + sdcardDirectory);
		
		// 获得下载目录
		File downloadDirectory = Environment.getDownloadCacheDirectory();
		Log.v(TAG, "下载目录：" + downloadDirectory);
		
		
	}

}

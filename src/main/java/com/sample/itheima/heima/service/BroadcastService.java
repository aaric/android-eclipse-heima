package com.sample.itheima.heima.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Broadcast Service.
 * 
 * @author Aaric
 *
 */
public class BroadcastService extends Service {
	
	/**
	 * TAG
	 */
	private static final String TAG = BroadcastService.class.getSimpleName();
	
	private MyReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "BroadcastService: onCreate");
		super.onCreate();
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.sample.itheima.heima.myreceiver");
		registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "BroadcastService: onDestroy");
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	public int sum(int x, int y) {
		Log.v(TAG, "BroadcastService: sum");
		return x + y;
	}
	
	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.v(TAG, "BroadcastService: MyReceiver: onReceive");
			int x = intent.getIntExtra("x", 0);
			int y = intent.getIntExtra("y", 0);
			int result = sum(x, y);
			Log.v(TAG, "(" + x + " + " + y + ") = " + result);
			
		}
		
	}

}

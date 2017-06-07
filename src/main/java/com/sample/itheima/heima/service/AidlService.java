package com.sample.itheima.heima.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * AIDL Service.
 * 
 * @author Aaric
 *
 */
public class AidlService extends Service {
	
	/**
	 * TAG
	 */
	private static final String TAG = AidlService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "AidlService: onBind");
		return new MyBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v(TAG, "AidlService: onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "AidlService: onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "AidlService: onDestroy");
		super.onDestroy();
	}
	
	public int doSum(int x, int y) {
		Log.v(TAG, "AidlService: doSum");
		return x + y;
	}
	
	private class MyBinder extends IAidlService.Stub {

		@Override
		public int sum(int x, int y) throws RemoteException {
			return doSum(x, y);
		}
		
	}

}

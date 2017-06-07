package com.sample.itheima.heima.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Remote Binding Service.
 * 
 * @author Aaric
 *
 */
public class BindingService extends Service {
	
	/**
	 * TAG
	 */
	private static final String TAG = BindingService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this.getApplicationContext(), "Service: onBind", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "Service: onBind");
		return new MyBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// Toast.makeText(this.getApplicationContext(), "Service: onUnbind", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "Service: onUnbind");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		// Toast.makeText(this.getApplicationContext(), "Service: onCreate", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "Service: onCreate");
	}

	@Override
	public void onDestroy() {
		// Toast.makeText(this.getApplicationContext(), "Serivce: onDestroy", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "Serivce: onDestroy");
		super.onDestroy();
	}
	
	public void doMethodInService(String name) {
		Toast.makeText(this.getApplicationContext(), "Serivce: doMethod -- " + name, Toast.LENGTH_SHORT).show();
		Log.v(TAG, "Serivce: doMethod -- " + name);
		
	}
	
	private class MyBinder extends Binder implements IBindingService {

		@Override
		public void doMethod(String name) {
			doMethodInService(name);
		}
		
	}

}

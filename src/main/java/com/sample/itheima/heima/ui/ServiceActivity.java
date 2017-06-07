package com.sample.itheima.heima.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sample.itheima.heima.R;
import com.sample.itheima.heima.service.BindingService;
import com.sample.itheima.heima.service.BroadcastService;
import com.sample.itheima.heima.service.IAidlService;
import com.sample.itheima.heima.service.IBindingService;
import com.sample.itheima.heima.widget.AppMsg;

/**
 * Binding Serice Activity.
 * 
 * @author Aaric
 *
 */
public class ServiceActivity extends Activity {
	
	/**
	 * TAG
	 */
	private static final String TAG = ServiceActivity.class.getSimpleName();
	
	private MyServiceConnection conn;
	private IBindingService bindingService;
	
	private IAidlService aidlService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);
		
		// Start service.
		startService(new Intent(this, BroadcastService.class));
		
	}
	
	public void start(View view) {
		AppMsg.makeText(this, "start", AppMsg.STYLE_ALERT).show();
		Intent service = new Intent(this, BindingService.class);
		startService(service);
		
	}
	
	public void stop(View view) {
		AppMsg.makeText(this, "stop", AppMsg.STYLE_CONFIRM).show();
		Intent service = new Intent(this, BindingService.class);
		stopService(service);
		
	}
	
	public void bind(View view) {
		AppMsg.makeText(this, "bind", AppMsg.STYLE_INFO).show();
		Intent service = new Intent(this, BindingService.class);
		conn = new MyServiceConnection();
		bindService(service, conn, BIND_AUTO_CREATE);
		// Note: Can't be executed by the service has been created.
		// bindingService.doMethodInService("first");
		
	}
	
	public void execBindMethod(View view) {
		AppMsg.makeText(this, "execBindMethod", new AppMsg.Style(AppMsg.LENGTH_LONG, R.color.holo_blue_bright)).show();
		bindingService.doMethod("hello");
		
	}
	
	public void unbind(View view) {
		AppMsg.makeText(this, "unbind", new AppMsg.Style(AppMsg.LENGTH_LONG, R.color.holo_blue_bright)).show();
		unbindService(conn);
		
	}
	
	public void remoteBind(View view) {
		AppMsg.makeText(this, "remoteBind", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.holo_green_light)).show();
		Intent intent = new Intent();
		intent.setAction("com.sample.itheima.heima.aidl");
		bindService(intent, new AidlServiceConnection(), BIND_AUTO_CREATE);
		
	}
	
	public void execBroadcastServiceMethod(View view) {
		AppMsg.makeText(this, "execBroadcastServiceMethod", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.holo_orange_dark)).show();
		Intent intent = new Intent();
		intent.putExtra("x", 1);
		intent.putExtra("y", 1);
		intent.setAction("com.sample.itheima.heima.myreceiver");
		sendBroadcast(intent);
		
	}
	
	public void execRemoteBindMethod(View view) {
		AppMsg.makeText(this, "execRemoteBindMethod", new AppMsg.Style(AppMsg.LENGTH_STICKY, R.color.holo_purple)).show();
		try {
			int result = aidlService.sum(1, 1);
			Toast.makeText(this, "Result: " + result, Toast.LENGTH_SHORT).show();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * My Service Connection.
	 * 
	 * @author Aaric
	 *
	 */
	private class MyServiceConnection implements ServiceConnection {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(TAG, "MyServiceConnection: onServiceConnected");
			bindingService = (IBindingService) service;
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "MyServiceConnection: onServiceDisconnected");
			bindingService = null;
			
		}
		
	}
	
	/**
	 * AIDL Service Connection.
	 * 
	 * @author Aaric
	 *
	 */
	private class AidlServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			aidlService = IAidlService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			aidlService = null;
		}
		
	}

}

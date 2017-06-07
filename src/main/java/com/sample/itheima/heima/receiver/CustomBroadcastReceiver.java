package com.sample.itheima.heima.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Custom Broadcast Receiver.
 * 
 * @author Aaric
 *
 */
public class CustomBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Simple tips.
		Toast.makeText(context, "I have receive the custom broadcast.", Toast.LENGTH_SHORT).show();

	}

}

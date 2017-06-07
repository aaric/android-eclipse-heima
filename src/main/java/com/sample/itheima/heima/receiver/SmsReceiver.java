package com.sample.itheima.heima.receiver;

import java.util.Iterator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Sms Receiver.
 * 
 * @author Aaric
 *
 */
public class SmsReceiver extends BroadcastReceiver {
	/**
	 * TAG
	 */
	private static final String TAG = SmsReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "A message has been received...");
		Bundle data = intent.getExtras();
		Iterator<String> itertor = data.keySet().iterator();
		while (itertor.hasNext()) {
			String string = (String) itertor.next();
			Log.v(TAG, "key-->" + string);
		}
		
		// Intercept message;
		Object[] pdus = (Object[]) data.get("pdus");
		for (Object pdu : pdus) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
			String address = smsMessage.getOriginatingAddress();
			String body = smsMessage.getDisplayMessageBody();
			Log.i(TAG, "SMS-->[address:" + address + "  body:" + body + "]");
			
			// Send message.
			if (address.contains("5556")) {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(address, null, "Go dead!!!", null, null);
				
			}
			
		}
		
		// Abort Broadcast.
		this.abortBroadcast();

	}

}

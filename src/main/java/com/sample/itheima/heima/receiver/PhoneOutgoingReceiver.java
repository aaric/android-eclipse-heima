package com.sample.itheima.heima.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Phone Outgoing Receiver.
 * 
 * @author Aaric
 *
 */
public class PhoneOutgoingReceiver extends BroadcastReceiver {
	/**
	 * TAG
	 */
	private static final String TAG = PhoneOutgoingReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "A phone is outgoing...");
		
		// Get phone number of outgoing.
		String outgoingNumber = this.getResultData();
		Log.i(TAG, "Outgoing number: " + outgoingNumber);
		
		// Replace this number.
		outgoingNumber = "17951" + outgoingNumber;
		this.setResultData(outgoingNumber);

	}

}

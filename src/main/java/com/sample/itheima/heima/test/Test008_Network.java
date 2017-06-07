package com.sample.itheima.heima.test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.sample.itheima.heima.utils.NetworkUtils;

/**
 * Test008 Network.
 * 
 * @author Aaric
 *
 */
public class Test008_Network extends AndroidTestCase {

	/**
	 * TAG
	 */
	private static final String TAG = Test008_Network.class.getSimpleName();

	/**
	 * Test the method of isAvail.
	 */
	public void testIsAvail() {
		Log.v(TAG, "NET-->" + NetworkUtils.isNetworkAvail(this.getContext()));
	}

	/**
	 * Test the method of isWIFIAvail.
	 */
	public void testIsWIFIAvail() {
		Log.v(TAG, "WIFI-->" + NetworkUtils.isWIFINetworkAvail(this.getContext()));
	}

	/**
	 * Test the method of isMobileAvail.
	 */
	public void testIsMobileAvail() {
		Log.v(TAG, "3G-->" + NetworkUtils.isMobileNetworkAvail(this.getContext()));
	}

}

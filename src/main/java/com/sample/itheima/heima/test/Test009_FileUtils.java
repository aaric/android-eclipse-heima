package com.sample.itheima.heima.test;

import android.util.Log;

import com.sample.itheima.heima.utils.FileUtils;

/**
 * Test009 FileUtils.
 * 
 * @author Aaric
 *
 */
public class Test009_FileUtils {
	
	/**
	 * TAG
	 */
	private static final String TAG = Test009_FileUtils.class.getSimpleName();
	
	/**
	 * Test the method of isSDCradAvail.
	 */
	public void testIsSDCradAvail() {
		Log.v(TAG, "isSDCradAvail-->" + FileUtils.isSDCradAvail(1500));
		// Log.v(TAG, "isSDCradAvail-->" + FileUtils.isSDCradAvail(1500L));
	}
	
	/**
	 * Test the method of isInternalAvail.
	 */
	public void testIsInternalAvail() {
		Log.v(TAG, "isInternalAvail-->" + FileUtils.isInternalAvail(1500));
		// Log.v(TAG, "isInternalAvail-->" + FileUtils.isInternalAvail(1500L));
	}

}

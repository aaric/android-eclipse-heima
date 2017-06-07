package com.sample.itheima.heima.test;

import java.io.File;

import android.media.ExifInterface;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Test007 Graphic.
 * 
 * @author Aaric
 *
 */
public class Test007_Graphic extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	private static final String TAG = Test007_Graphic.class.getSimpleName();
	
	/**
	 * Test EXIF.
	 * @throws Exception 
	 */
	public void testExif() throws Exception {
		String filename = new File(Environment.getExternalStorageDirectory(), "wgk.jpg").getAbsolutePath();
		ExifInterface exif = new ExifInterface(filename);
		Log.v(TAG, "FilePath: " + filename);
		Log.v(TAG, "Model: " + exif.getAttribute(ExifInterface.TAG_MODEL));
		Log.v(TAG, "Make: " + exif.getAttribute(ExifInterface.TAG_MAKE));
		Log.v(TAG, "DateTime: " + exif.getAttribute(ExifInterface.TAG_DATETIME));
		Log.v(TAG, "ImageWidth: " + exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
		Log.v(TAG, "ImageLength: " + exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
		Log.v(TAG, "Orientation: " + exif.getAttribute(ExifInterface.TAG_ORIENTATION));
		
	}

}

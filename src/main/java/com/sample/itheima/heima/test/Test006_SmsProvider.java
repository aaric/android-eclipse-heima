package com.sample.itheima.heima.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sample.itheima.heima.utils.SmsUtils;

/**
 * Test006 SmsProvider.
 * 
 * @author Aaric
 *
 */
public class Test006_SmsProvider extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	private static final String TAG = Test006_SmsProvider.class.getSimpleName();
	
	private static final String CONTENT_BASE_URI_SMS = "content://sms";
	
	/**
	 * Test query all.
	 */
	@SuppressLint("SimpleDateFormat")
	public void testQueryAll() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(CONTENT_BASE_URI_SMS), null, null, null, null);
		
		// Column Names Output.
		Log.d(TAG, "Count-->" + cursor.getCount());
		Log.d(TAG, "Column-->" + cursor.getColumnCount());
//		for (String string : cursor.getColumnNames()) {
//			Log.d(TAG, string);
//		}
		
		// SMS Content Output.
		while(cursor.moveToNext()) {
			int _id = cursor.getInt(cursor.getColumnIndex("_id"));
			String address = cursor.getString(cursor.getColumnIndex("address"));
			String body = cursor.getString(cursor.getColumnIndex("body"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			int read = cursor.getInt(cursor.getColumnIndex("read"));
			long date = cursor.getLong(cursor.getColumnIndex("date"));
			long date_sent = cursor.getLong(cursor.getColumnIndex("date_sent"));
			
			Log.v(TAG, "_id = " + _id
					+ ", address = " + address
					+ ", body = " + body
					+ ", type = " + type
					+ ", read = " + read
					+ ", date = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date(date))
					+ ", date_sent = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date(date_sent)));
			
		}
		cursor.close();
		
	}
	
	/**
	 * Test query all by SmsUtils. 
	 */
	public void testQueryBySmsUtils() {
		List<SmsUtils.SmsObject> smsObjects =  SmsUtils.queryAll(this.getContext());
		for (SmsUtils.SmsObject smsObject : smsObjects) {
			System.out.println("--" + smsObject);
		}
		
	}
	
	/**
	 * Test insert.
	 */
	public void testInsert() {
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("address", "10001");
		values.put("body", "this is a message++");
		values.put("type", "1");
		values.put("read", "0");
		long token = Binder.clearCallingIdentity();
		try {
			Uri uri = resolver.insert(Uri.parse(CONTENT_BASE_URI_SMS + "/sent"), values);
			Log.v(TAG, uri.toString());
		} finally {
			Binder.restoreCallingIdentity(token);
		}
		
	}
	
	/**
	 * Test insert by SmsUtils
	 */
	public void testInsertBySmsUtils() {
		SmsUtils.insert(this.getContext(), "10086", "hehe");
		
	}

}

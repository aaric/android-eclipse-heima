package com.sample.itheima.heima.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Test005 Content Provider.
 * 
 * @author Aaric
 *
 */
public class Test005_ContentProvider extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	private static final String TAG = Test005_ContentProvider.class.getSimpleName();
	
	private static final String CONTENT_BASE_URI = "content://com.sample.itheima.heima.simple/";
	
	/**
	 * Test query.
	 */
	public void testQuery() {
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(CONTENT_BASE_URI + "query"), null, null, null, null);
		while (cursor.moveToNext()) {
			long millis = cursor.getLong(cursor.getColumnIndex("millis"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			Log.v(TAG, millis + "<-->" + date);
		}
		cursor.close();
	}
	
	/**
	 * Test insert.
	 */
	public void testInsert() {
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("_id", "0");
		values.put("string", "insert string");
		resolver.insert(Uri.parse(CONTENT_BASE_URI + "insert"), values);
		
	}
	
	/**
	 * Test delete.
	 */
	public void testDelete() {
		ContentResolver resolver = this.getContext().getContentResolver();
		resolver.delete(Uri.parse(CONTENT_BASE_URI + "delete"), "_id = ?", new String[]{"0"});
		
	}
	
	/**
	 * Test update.
	 */
	public void testUpdate() {
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("string", "update string " + System.currentTimeMillis());
		resolver.update(Uri.parse(CONTENT_BASE_URI + "update"), values, "_id = ?", new String[]{"1"});
		
	}
	
	/**
	 * Test query one.
	 */
	public void testQueryOne() {
		Uri uri = Uri.parse(CONTENT_BASE_URI + "query/" + 12);
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);
		Log.v(TAG, "type-->" + resolver.getType(uri));
		long millis = cursor.getLong(cursor.getColumnIndex("millis"));
		String date = cursor.getString(cursor.getColumnIndex("date"));
		Log.v(TAG, millis + "<-->" + date);
		cursor.close();
		
	}

}

package com.sample.itheima.heima.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.sample.itheima.heima.helper.DatabaseHelper;

/**
 * Simple Data Provider.
 * 
 * @author Aaric
 *
 */
public class SimpleDataProvider extends ContentProvider {
	
	/**
	 * TAG
	 */
	private static final String TAG = SimpleDataProvider.class.getSimpleName();
	
	/**
	 * Database helper.
	 */
	private DatabaseHelper helper = null;
	
	@Override
	public boolean onCreate() {
		helper = DatabaseHelper.getInstance(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		SQLiteDatabase db = helper.getReadableDatabase();;
		System.out.println(uriMacher.match(uri));
		switch (uriMacher.match(uri)) {
			case QUERY:
				Log.d(TAG, "========== query ==========");
				cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			case QUERY_BYID:
				Log.d(TAG, "========== query one ==========");
				long queryId = ContentUris.parseId(uri);
				Log.d(TAG, "queryId-->" + queryId);
				cursor = db.query(TABLE_NAME, projection, "_id = ?", new String[]{"" + queryId}, null, null, sortOrder);
				cursor.moveToLast();
				break;
			default:
				throw new IllegalArgumentException("Illegal uri --> " + uri);
		}
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMacher.match(uri)) {
			case QUERY:
				Log.d(TAG, "========== getType: all ==========");
				return TYPE_FRO_ALL;
			case QUERY_BYID:
				Log.d(TAG, "========== getType: one ==========");
				return TYPE_FOR_ONE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		switch (uriMacher.match(uri)) {
			case INSERT:
				Log.d(TAG, "========== insert ==========");
				SQLiteDatabase db = helper.getWritableDatabase();
				db.insert(TABLE_NAME, null, values);
				break;
			default:
				throw new IllegalArgumentException("Illegal uri --> " + uri);
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int effectColumns = 0;
		switch (uriMacher.match(uri)) {
			case DELETE:
				Log.d(TAG, "========== delete ==========");
				SQLiteDatabase db = helper.getWritableDatabase();
				effectColumns = db.delete(TABLE_NAME, selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Illegal uri --> " + uri);
		}
		return effectColumns;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int effectColumns = 0;
		switch (uriMacher.match(uri)) {
			case UPDATE:
				Log.d(TAG, "========== update ==========");
				SQLiteDatabase db = helper.getWritableDatabase();
				effectColumns = db.update(TABLE_NAME, values, selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Illegal uri --> " + uri);
		}
		return effectColumns;
	}
	
	/**
	 * Static.
	 */
	private static final int QUERY = 0x00;
	private static final int INSERT = 0x01;
	private static final int DELETE = 0x02;
	private static final int UPDATE = 0x03;
	private static final int QUERY_BYID = 0x04;
	
	private static final String TABLE_NAME = "simple_data";
	
	private static final String CONTENT_AUTHORITY = "com.sample.itheima.heima.simple";
	
	private static final String TYPE_FRO_ALL = "vnd.android.cursor.dir/" + TABLE_NAME;
	private static final String TYPE_FOR_ONE = "vnd.android.cursor.item/" + TABLE_NAME;
	
	private static final UriMatcher uriMacher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		uriMacher.addURI(CONTENT_AUTHORITY, "query", QUERY);
		uriMacher.addURI(CONTENT_AUTHORITY, "insert", INSERT);
		uriMacher.addURI(CONTENT_AUTHORITY, "delete", DELETE);
		uriMacher.addURI(CONTENT_AUTHORITY, "update", UPDATE);
		uriMacher.addURI(CONTENT_AUTHORITY, "query/#", QUERY_BYID);
	}

}

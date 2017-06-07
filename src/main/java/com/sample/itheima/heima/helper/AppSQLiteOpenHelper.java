package com.sample.itheima.heima.helper;

import com.sample.itheima.heima.entity.bpo.Article;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 构建数据库
 * 
 * @author Aaric
 *
 */
public class AppSQLiteOpenHelper extends SQLiteOpenHelper {
	
	/**
	 * 定义数据库的名称和版本号
	 */
	public static final int APP_DB_VER = 1;
	public static final String APP_DB_NAME = "app.db";

	/**
	 * 构造传入Context对象
	 * 
	 * @param context
	 */
	public AppSQLiteOpenHelper(Context context) {
		super(context, APP_DB_NAME, null, APP_DB_VER);
	}

	/**
	 * 初始化创建数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * 初始化创建文章表
		 */
		db.execSQL(Article.DATABASE_DDL_TABLE_CREATE);

	}

	/**
	 * 当数据库版本发生变化的时候会执行数据库DDL语句更新
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

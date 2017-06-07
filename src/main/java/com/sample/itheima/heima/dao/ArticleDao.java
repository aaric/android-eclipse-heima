package com.sample.itheima.heima.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sample.itheima.heima.entity.bpo.Article;
import com.sample.itheima.heima.helper.AppSQLiteOpenHelper;

/**
 * 构造AppSQLiteOpenHelper操作article表
 * 
 * @author Aaric
 *
 */
public class ArticleDao {
	
	/**
	 * TAG
	 */
	public static final String TAG = ArticleDao.class.getSimpleName();
	
	/**
	 * 定义AppSQLiteOpenHelper对象
	 */
	protected AppSQLiteOpenHelper helper;

	/**
	 * 构造传入Context对象
	 * 
	 * @param context
	 */
	public ArticleDao(Context context) {
		// 初始化AppSQLiteOpenHelper对象
		helper = new AppSQLiteOpenHelper(context);
	}
	
	/**
	 * 保存文章
	 * 
	 * @param title
	 * @param content
	 */
	public long save(String title, String content) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL("INSERT INTO " + Article.TABLE_NAME + " (title, content) VALUES (?, ?)",
//				new Object[]{title, content});
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("content", content);
		long returnValue = db.insert(Article.TABLE_NAME, null, values);
		db.close();
		return returnValue;
	}
	
	/**
	 * 查询是否包含该标题的文章
	 * 
	 * @param byTitle
	 * @return
	 */
	public boolean contains(String byTitle) {
		boolean returnValue = false;
		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.rawQuery("SELECT * FROM " + Article.TABLE_NAME + "  WHERE title = ?", new String[]{byTitle});
		Cursor cursor = db.query(Article.TABLE_NAME, null, "title = ?", new String[]{byTitle}, null, null, null);
		returnValue = cursor.moveToNext();
		cursor.close();
		db.close();
		return returnValue;
	}
	
	/**
	 * 根据标题修改文章的内容
	 * 
	 * @param newContent
	 * @param byTitle
	 */
	public int update(String newContent, String byTitle) {
		SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL("UPDATE " + Article.TABLE_NAME + " SET content = ? WHERE title = ?",
//				new Object[]{newContent, byTitle});
		ContentValues values = new ContentValues();
		values.put("content", newContent);
		int returnValue = db.update(Article.TABLE_NAME, values, "title = ?", new String[]{byTitle});
		db.close();
		return returnValue;
	}
	
	/**
	 * 根据标题删除文章数据
	 * 
	 * @param byTitle
	 */
	public int delete(String byTitle) {
		SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL("DELETE FROM " + Article.TABLE_NAME + " WHERE title = ?",
//				new Object[]{byTitle});
		int returnValue = db.delete(Article.TABLE_NAME, "title = ?", new String[]{byTitle});
		db.close();
		return returnValue;
	}
	
	/**
	 * 查询所有数据集合
	 */
	public List<Article> list() {
		List<Article> listArticles = new ArrayList<Article>();
		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.rawQuery("SELECT * FROM " + Article.TABLE_NAME, null);
		Cursor cursor = db.query(Article.TABLE_NAME, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			listArticles.add(new Article(id, title, content));
		}
		cursor.close();
		db.close();
		return listArticles;
	}
	
	/**
	 * 事务删除
	 * 
	 * @param byTitle
	 * @return
	 */
	public int deleteWithTransaction(String byTitle) {
		int returnValue = 0;
		SQLiteDatabase db = helper.getReadableDatabase();
		db.beginTransaction();
		try {
			returnValue = db.delete(Article.TABLE_NAME, "title = ?", new String[]{byTitle});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
		return returnValue;
	}

}

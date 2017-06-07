package com.sample.itheima.heima.test;

import java.util.List;

import com.sample.itheima.heima.dao.ArticleDao;
import com.sample.itheima.heima.entity.bpo.Article;
import com.sample.itheima.heima.helper.AppSQLiteOpenHelper;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Test003 SQLite.
 * 
 * @author Aaric
 *
 */
public class Test003_SQLite extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	private static final String TAG = Test003_SQLite.class.getSimpleName();

	/**
	 * Test create database.
	 */
	public void testCreateDatabase() {
		assertNotNull(new AppSQLiteOpenHelper(this.getContext()));
		
	}
	
	/**
	 * Test save method.
	 */
	public void testSave() {
		ArticleDao articleDao = new ArticleDao(this.getContext());
		articleDao.save("title", "content");
		
	}
	
	/**
	 * Test contains method.
	 */
	public void testContains() {
		ArticleDao articleDao = new ArticleDao(this.getContext());
		assertEquals(true, articleDao.contains("title"));
		
	}
	
	/**
	 * Test update method.
	 */
	public void testUpdate() {
		ArticleDao articleDao = new ArticleDao(this.getContext());
		articleDao.update("change content", "title");
		
	}
	
	/**
	 * Test delete method.
	 */
	public void testDelete() {
		ArticleDao articleDao = new ArticleDao(this.getContext());
		articleDao.delete("title");
		
	}
	
	/**
	 * Test list mothod.
	 */
	public void testList() {
		ArticleDao articleDao = new ArticleDao(this.getContext());
		List<Article> listArticles = articleDao.list();
		for (Article article : listArticles) {
			Log.v(TAG, article.toString());
		}
		
	}
	
	/**
	 * Test deleteWithTransaction method.
	 */
	public void testTransaction() {
		ArticleDao articleDao = new ArticleDao(this.getContext());
		articleDao.delete("title");
		
	}

}

package com.sample.itheima.heima.entity.bpo;

/**
 * Article Entity.
 * 
 * @author Aaric
 *
 */
public class Article {
	
	/**
	 * Define table name.
	 */
	public static final String TABLE_NAME = "article";
	public static final String DATABASE_DDL_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title  TEXT(20), content TEXT(20))";

	private int id;
	private String title;
	private String content;
	
	public Article() {
		super();
	}

	public Article(int id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content="
				+ content + "]";
	}
	
}

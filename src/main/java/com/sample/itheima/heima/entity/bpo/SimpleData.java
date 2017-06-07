package com.sample.itheima.heima.entity.bpo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Simple Data.
 * 
 * @author Aaric
 * 
 */
@DatabaseTable(tableName = "simple_data")
public class SimpleData {

	@DatabaseField(columnName = "_id", generatedId = true)
	private int id;
	@DatabaseField
	private long millis;
	@DatabaseField
	private boolean flag;
	@DatabaseField(index = true)
	private String string;
	@DatabaseField
	private Date date;

	public SimpleData() {
		super();
	}

	public SimpleData(long millis) {
		super();
		this.millis = System.currentTimeMillis();
		this.flag = (0 == this.millis % 2);
		this.string = this.millis % 1000 + "ms";
		this.date = new Date(this.millis);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMillis() {
		return millis;
	}

	public void setMillis(long millis) {
		this.millis = millis;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	@SuppressLint("SimpleDateFormat")
	public String toString() {
		return "SimpleData [id=" + id + ", millis=" + millis + ", flag=" + flag
				+ ", string=" + string + ", date="
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date)
				+ "]";
	}

}

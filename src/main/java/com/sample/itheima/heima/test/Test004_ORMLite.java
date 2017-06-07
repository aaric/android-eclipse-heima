package com.sample.itheima.heima.test;

import java.sql.SQLException;
import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.sample.itheima.heima.entity.bpo.SimpleData;
import com.sample.itheima.heima.helper.DatabaseHelper;

/**
 * Test004 ORMLite.
 * 
 * @author Aaric
 *
 */
public class Test004_ORMLite extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	public static final String TAG = Test004_ORMLite.class.getSimpleName();
	
	/**
	 * DAO
	 */
	private Dao<SimpleData, Integer> simpleDataDao;
	
	@Override
	protected void setUp() throws Exception {
		DatabaseHelper helper = DatabaseHelper.getInstance(this.getContext());
		simpleDataDao = helper.getDao(SimpleData.class);
		super.setUp();
	}

	/**
	 * Test insert.
	 * @throws SQLException 
	 */
	public void testInsert() throws SQLException {
		for (int i = 0; i < 15; i++) {
			simpleDataDao.create(new SimpleData(System.currentTimeMillis()));
		}
		
	}
	
	/**
	 * Test delete.
	 * @throws SQLException
	 */
	public void testDelele() throws SQLException {
		simpleDataDao.deleteById(5);
		
	}
	
	/**
	 * Test update.
	 * @throws SQLException
	 */
	public void testUpdate() throws SQLException {
		SimpleData updateSimpleData = new SimpleData(1408607586472L);
		updateSimpleData.setId(2);
		updateSimpleData.setString("This column has been updated.");
		simpleDataDao.update(updateSimpleData);
		
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void testQuery() throws SQLException {
		List<SimpleData> listSimpleDatas = simpleDataDao.queryForAll();
		for (SimpleData simpleData : listSimpleDatas) {
			Log.i(TAG, simpleData.toString());
		}
		
	}

}

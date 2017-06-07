package com.sample.itheima.heima;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sample.itheima.heima.service.PhoneTapeService;
import com.sample.itheima.heima.ui.SensorActivity;
import com.sample.itheima.heima.ui.CustomBroadcastActivity;
import com.sample.itheima.heima.ui.DrawerLayoutActivity;
import com.sample.itheima.heima.ui.GraphicActivity;
import com.sample.itheima.heima.ui.MediaPlayerActivity;
import com.sample.itheima.heima.ui.ServiceActivity;
import com.sample.itheima.heima.ui.ShapeActivity;
import com.sample.itheima.heima.widget.AppMsg;

/**
 * Main Activity.
 * 
 * @author Aaric
 *
 */
public class MainActivity extends Activity {
	
	/**
	 * TAG
	 */
	public static final String TAG = MainActivity.class.getSimpleName();
	
	/**
	 * Component
	 */
	private ListView mListView;
	
	private String[] demos;
	
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Start service.
		startService(new Intent(this, PhoneTapeService.class));
		
		// Loading...
		Log.v(TAG, "Loading...");
		
		// ListView.
		demos = this.getResources().getStringArray(R.array.str_ary_demos);
		mListView = (ListView) this.findViewById(R.id.lv_main_demos);
		mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demos));
		mListView.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Operation.
				doOperation(demos[position]);
				
			}
			
		});
		
	}
	
	/**
	 * Operation.
	 * 
	 * @param operation
	 */
	public void doOperation(String operation) {
		if ("SimpleAdapter".equals(operation)) {
			List<Map<String, String>> data = new ArrayList<Map<String,String>>();
			for (int i = 0; i < 15; i++) {
				Map<String, String> object = new HashMap<String, String>();
				object.put("name", "name" + i);
				data.add(object);
			}
			mListView.setAdapter(new SimpleAdapter(this, data, R.layout.list_item_lv, new String[]{"name"}, new int[]{R.id.tv_main_lv}));
			
		} else if ("SmartImageView".equals(operation)) {
			Intent intent = new Intent();
			intent.setClassName(this, "com.sample.itheima.heima.ui.SmartImageViewActivity");
			startActivity(intent);
			
		} else if ("SwipeRefreshLayout".equals(operation)) {
			Intent intent = new Intent();
			// packageName: application package directory path name.
			intent.setClassName("com.sample.itheima.heima", "com.sample.itheima.heima.ui.SwipeRefreshLayoutActivity");
			intent.putExtra("hello", "Hello, Android!");
			startActivity(intent);
			
		} else if ("DrawerLayout".equals(operation)) {
			Intent intent = new Intent();
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.setDataAndType(Uri.parse("heima://hello"), "text/plain");
			startActivity(new Intent(this, DrawerLayoutActivity.class));
			
		} else if("CustomBroadcast".equals(operation)) {
			startActivity(new Intent(this, CustomBroadcastActivity.class));
			
		} else if("AppMsg".equals(operation)) {
			// AppMsg.makeText(this, "Hello", AppMsg.STYLE_ALERT).show();
			// AppMsg.makeText(this, "Hello", AppMsg.STYLE_CONFIRM).show();
			// AppMsg.makeText(this, "Hello", AppMsg.STYLE_INFO).show();
			AppMsg.makeText(this, "Hello, AppMsg!", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.holo_purple)).show();
			
		} else if("Service".equals(operation)) {
			startActivity(new Intent(this, ServiceActivity.class));
			
		} else if("Graphic".equals(operation)) {
			startActivity(new Intent(this, GraphicActivity.class));
			
		} else if("MediaPlayer".equals(operation)) {
			startActivity(new Intent(this, MediaPlayerActivity.class));
			
		} else if("Sensor".equals(operation)) {
			startActivity(new Intent(this, SensorActivity.class));
			
		} else if("Shape".equals(operation)) {
			startActivity(new Intent(this, ShapeActivity.class));
			
		} else {
			Toast.makeText(this, operation, Toast.LENGTH_SHORT).show();
			
		}
		
	}

	/**
	 * This function for creating menus.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(com.sample.itheima.heima.R.menu.main, menu);
		return true;
	}

	/**
	 * This function for dealing button click event.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Deal the menu click.
		switch (item.getItemId()) {
		case R.id.action_settings:
			// Toast.
			Toast.makeText(this, R.string.action_settings, Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_exit:
			// Exit the application.
			// this.finish();
			// Kill application pid.
			Process.killProcess(Process.myPid());
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}

package com.sample.itheima.heima.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sample.itheima.heima.R;

/**
 * Swipe Refresh Layout Activity.
 * 
 * @author Aaric
 *
 */
public class SwipeRefreshLayoutActivity extends Activity {
	
	/**
	 * TAG
	 */
	private static final String TAG = SwipeRefreshLayoutActivity.class.getSimpleName();

	/**
	 * Component.
	 */
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	
	private List<String> items;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_refresh_layout);
		
		// Intent.
		String result = this.getIntent().getStringExtra("hello");
		Log.v(TAG, "Get prev activity data --> " + result);
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		
		// Initialize component.
		mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.srl_swipe_refresh_layout);
		mSwipeRefreshLayout.setColorScheme(R.color.holo_red_light, R.color.holo_green_light,
				R.color.holo_blue_bright, R.color.holo_orange_light);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						mSwipeRefreshLayout.setRefreshing(false);
						List<String> newItems = new ArrayList<String>();
						newItems.add("string2");
						newItems.addAll(items);
						items.clear();
						items.addAll(newItems);
						adapter.notifyDataSetChanged();
						
					}
					
				}, 500);
				
			}
			
		});
		
		items = new ArrayList<String>();
		items.add("string1");
		items.add("string1");
		items.add("string1");
		items.add("string1");
		items.add("string1");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		mListView = (ListView) this.findViewById(R.id.lv_swipe_refresh_layout);
		mListView.setAdapter(adapter);
		
	}

}

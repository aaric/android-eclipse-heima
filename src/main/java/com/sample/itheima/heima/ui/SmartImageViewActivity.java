package com.sample.itheima.heima.ui;

import com.sample.itheima.heima.R;
import com.sample.itheima.heima.view.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Smart Image View Activity.
 * 
 * @author Aaric
 *
 */
public class SmartImageViewActivity extends Activity {
	
	/**
	 * TAG
	 */
	private static final String TAG = SmartImageViewActivity.class.getSimpleName();

	/**
	 * Component.
	 */
	private SmartImageView mSmartImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smart_image_view);
		
		Log.v(TAG, "smart image view...");
		mSmartImageView = (SmartImageView) this.findViewById(R.id.siv_network_image);
		mSmartImageView.setImageUrl("http://www.baidu.com/img/bdlogo.png", R.drawable.photo, R.drawable.download);
		
	}

}

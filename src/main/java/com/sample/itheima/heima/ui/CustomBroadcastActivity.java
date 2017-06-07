package com.sample.itheima.heima.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sample.itheima.heima.R;

/**
 * Custom Broadcast Activity.
 * 
 * @author Aaric
 *
 */
public class CustomBroadcastActivity extends Activity {
	
	private Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_broadcast);
		
		mButton = (Button) this.findViewById(R.id.btn_custom_broadcast_click);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "Method: sendBroadcast", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction("com.sample.itheima.heima.alarm");
				// sendBroadcast(intent);
				sendOrderedBroadcast(intent, null);
				
			}
		});
		
	}
	
}

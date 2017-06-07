package com.sample.itheima.heima.ui;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.sample.itheima.heima.R;

/**
 * Sensor Activity.
 * 
 * @author Aaric
 *
 */
public class SensorActivity extends Activity implements SensorEventListener {
	
	/**
	 * TAG
	 */
	private static final String TAG = SensorActivity.class.getSimpleName();
	
	private ImageView mImageView;
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private float startAngle = 0;

	@Override
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);
		
		mImageView = (ImageView) this.findViewById(R.id.iv_compass_indicator);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_compass);
		mImageView.setAnimation(animation);
		
		Log.v(TAG, "Create sensor manager...");
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float[] values = event.values;
		float angle = values[0];
		// Log.v(TAG, "The angle compared the north : " + angle);
		RotateAnimation animation = new RotateAnimation(startAngle, angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		mImageView.startAnimation(animation);
		startAngle = -angle;
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Nothing.
		
	}
	

}

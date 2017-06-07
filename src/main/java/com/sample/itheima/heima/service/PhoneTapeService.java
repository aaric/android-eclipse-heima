package com.sample.itheima.heima.service;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Phone Incoming Service.
 * 
 * @author Aaric
 *
 */
public class PhoneTapeService extends Service {
	
	/**
	 * TAG
	 */
	private static final String TAG = PhoneTapeService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "Phone recorder service start...");
		
		// Get phone status.
		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		tm.listen(new TapePhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
		
	}
	
	/**
	 * Tape Phone State Listener.
	 * 
	 * @author Aaric
	 *
	 */
	public class TapePhoneStateListener extends PhoneStateListener {
		
		/**
		 * Define parameters.
		 */
		private MediaRecorder recorder;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:
					if (null != recorder) {
						recorder.stop();
						recorder.reset();
						recorder.release();
						recorder = null;
					}
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					Log.v(TAG, "Incoming number: " + incomingNumber);
					recorder = new MediaRecorder();
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					String audioStoragePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis() + ".3gp").getAbsolutePath();
					Log.v(TAG, "Audio storage path: " + audioStoragePath);
					recorder.setOutputFile(audioStoragePath);
					recorder.prepare();
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					if (null != recorder) {
						recorder.start();
					}
					break;
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			super.onCallStateChanged(state, incomingNumber);
		}
		
	}

}

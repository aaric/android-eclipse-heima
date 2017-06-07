package com.sample.itheima.heima.ui;

import java.io.File;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.sample.itheima.heima.R;

/**
 * Media Player Activity.
 * 
 * @author Aaric
 *
 */
public class MediaPlayerActivity extends Activity implements OnClickListener {
	
	/**
	 * TAG
	 */
	private static final String TAG = MediaPlayerActivity.class.getSimpleName();
	
	private Button mButtonShoot;
	
	private Button mButtonPlay;
	private Button mButtonPause;
	private Button mButtonReplay;
	private Button mButtonStop;
	
	private SeekBar mSeekBar;
	private SurfaceView mSurfaceView;
	
	private int shootId;
	private SoundPool soundPool;
	private boolean isPlaying;
	private int currentPosition = 0;
	private MediaPlayer mediaPlayer;

	@Override
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_player);
		
		// Initialize sound pool.
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		shootId = soundPool.load(this, R.raw.shoot, 1);
		
		// Component.
		mButtonShoot = (Button) this.findViewById(R.id.btn_media_player_shoot);
		mButtonPlay = (Button) this.findViewById(R.id.btn_media_player_play);
		mButtonPause = (Button) this.findViewById(R.id.btn_media_player_pause);
		mButtonReplay = (Button) this.findViewById(R.id.btn_media_player_replay);
		mButtonStop = (Button) this.findViewById(R.id.btn_media_player_stop);
		mSeekBar = (SeekBar) this.findViewById(R.id.sb_media_player_control);
		mSurfaceView = (SurfaceView) this.findViewById(R.id.sv_media_player_video);
		
		// Initialize component.
		mButtonShoot.setOnClickListener(this);
		mButtonPlay.setOnClickListener(this);
		mButtonPause.setOnClickListener(this);
		mButtonReplay.setOnClickListener(this);
		mButtonStop.setOnClickListener(this);
		
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Nothing.
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Nothing.
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Log.v(TAG, "SeekBar: onProgressChanged -- " + progress);
				if (null != mediaPlayer && mediaPlayer.isPlaying()) {
					mediaPlayer.seekTo(progress);
				}
				
			}
		});
		
		// Before API 11
		// 设置SurfaceView不维护自己的缓冲区，而是等待屏幕渲染引擎将内容推送给视图
		mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				Log.v(TAG, "SurfaceHolder.Callback: surfaceCreated");
				if (0 < currentPosition) {
					play(currentPosition);
				}
				
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				Log.v(TAG, "SurfaceHolder.Callback: surfaceChanged");
				
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.v(TAG, "SurfaceHolder.Callback: surfaceDestroyed");
				if (null != mediaPlayer && mediaPlayer.isPlaying()) {
					currentPosition = mediaPlayer.getCurrentPosition();
					stop();
				}
			}
			
		});
		
	}

	@Override
	protected void onDestroy() {
		soundPool.release();
		soundPool = null;
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_media_player_shoot:
				Log.v(TAG, "shoot");
				soundPool.play(shootId, 1.0f, 1.0f, 0, 0, 1.0f);
				
				break;
			case R.id.btn_media_player_play:
				play(0);
				break;
			case R.id.btn_media_player_pause:
				pause();
				break;
			case R.id.btn_media_player_replay:
				replay();
				break;
			case R.id.btn_media_player_stop:
				stop();
				break;
		}
		
	}

	/**
	 * Media stop.
	 */
	private void stop() {
		Log.v(TAG, "stop");
		if (null != mediaPlayer && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			isPlaying = false;
			mButtonPlay.setEnabled(true);
		}
		
	}

	/**
	 * Media replay.
	 */
	private void replay() {
		Log.v(TAG, "replay");
		if (null != mediaPlayer && mediaPlayer.isPlaying()) {
//			mediaPlayer.seekTo(0);
			stop();
			play(0);
		}
		
	}

	/**
	 * Media pause.
	 */
	private void pause() {
		Log.v(TAG, "pause");
		if (null != mediaPlayer) {
			if(this.getResources().getString(R.string.btn_media_player_goon).equals(mButtonPause.getText())) {
				mButtonPause.setText(R.string.btn_media_player_pause);
				mediaPlayer.start();
				return;
			}
			if(this.getResources().getString(R.string.btn_media_player_pause).equals(mButtonPause.getText())) {
				mButtonPause.setText(R.string.btn_media_player_goon);
				mediaPlayer.pause();
			}
		}
	}

	/**
	 * Media play.
	 * 
	 * @param position The position which the media player want to play.
	 */
	private void play(final int position) {
		Log.v(TAG, "play");
		// MP3
		// File mediaFile = new File(Environment.getExternalStorageDirectory(), "crossfire.mp3");
		// MP4
		File mediaFile = new File(Environment.getExternalStorageDirectory(), "llrr.MP4");
		if (mediaFile.exists()) {
			try {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				
				// Video
				mediaPlayer.setDisplay(mSurfaceView.getHolder());
				
				mediaPlayer.setDataSource(mediaFile.getAbsolutePath());
				// mediaPlayer.prepare();
				mediaPlayer.prepareAsync();
				mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer mp) {
						Log.v(TAG, "The media has been prepared.");
						mediaPlayer.start();
						mediaPlayer.seekTo(position);
						mSeekBar.setMax(mp.getDuration());
						
						// Dynamic position.
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								isPlaying = true;
								while(isPlaying) {
									if (null != mediaPlayer) {
										// Log.v(TAG, "The current position of media  : " + mediaPlayer.getCurrentPosition());
										mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
										
									}
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									
								}
							}
						}).start();
						
					}
				});
				mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						mButtonPlay.setEnabled(true);
						mediaPlayer.release();
						mediaPlayer = null;
						isPlaying = false;
						mButtonPlay.setEnabled(true);
					}
				});
				mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
					
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra) {
						mButtonPlay.setEnabled(false);
						return false;
					}
				});
				mButtonPlay.setEnabled(false);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		
	}

}

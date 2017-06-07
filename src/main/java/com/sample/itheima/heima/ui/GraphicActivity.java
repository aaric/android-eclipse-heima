package com.sample.itheima.heima.ui;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sample.itheima.heima.R;

/**
 * Graphic Activity.
 * 
 * @author Aaric
 *
 */
public class GraphicActivity extends Activity {
	
	/**
	 * TAG
	 */
	private static final String TAG = GraphicActivity.class.getSimpleName();
	
	private static int mDefaultScreenWidth;
	private static int mDefaultScreenHeight;
	
	private EditText mEditText;
	private ImageView mImageView;
	private SeekBar mSeekBar;
	
	private Bitmap mBitmap = null;
	private Canvas mCanvas = null;
	private Paint mPaint = null;

	@Override
	@SuppressWarnings("deprecation")
	protected void onStart() {
		super.onStart();
		// Initialize screen width and height.
		WindowManager wm = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		mDefaultScreenWidth = wm.getDefaultDisplay().getWidth();
		mDefaultScreenHeight = wm.getDefaultDisplay().getHeight();
		Log.i(TAG, "DefaultScreen: {width: " + mDefaultScreenWidth + ", height: " + mDefaultScreenHeight + "}");
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphic);
		
		// Component.
		mEditText = (EditText) this.findViewById(R.id.et_graphic_select);
		mImageView = (ImageView) this.findViewById(R.id.iv_graphic_photo);
		mSeekBar = (SeekBar) this.findViewById(R.id.sb_graphic_control);
		
		mSeekBar.setMax(15);
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Nothing.
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Nothing.
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Set saturation of the picture.
				if (null != mCanvas && !TextUtils.isEmpty(mEditText.getText())) {
					int progress = seekBar.getProgress();
					ColorMatrix matrix = new ColorMatrix();
					matrix.setSaturation((progress+5)/10);
					mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
					mCanvas.drawBitmap(mBitmap, new Matrix(), mPaint);
					mImageView.setImageBitmap(mBitmap);
					
				}
				
			}
			
		});
		
		// Graphic.
		mImageView.setOnTouchListener(new ImageView.OnTouchListener(){
			
			float startX, startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (null == mBitmap && !TextUtils.isEmpty(mEditText.getText())) {
							Options opts = new Options();
							String pathName = mEditText.getText().toString();
							opts.inJustDecodeBounds = true;
							BitmapFactory.decodeFile(pathName, opts);
							Log.v(TAG, "BitmapFactory.Options: {outWidth: " + opts.outWidth + ", outHeight: " + opts.outHeight + ", outMimeType: " + opts.outMimeType + "}");
							
							int scale = 1;
							int scaleX = opts.outWidth / mDefaultScreenWidth;
							int scaleY = opts.outHeight / mDefaultScreenHeight;
							if (scaleX > scaleY && scaleX > 1) {
								scale = scaleX;
							}
							if (scaleX < scaleY && scaleY > 1) {
								scale = scaleY;
							}
							Log.v(TAG, "scale: "+ scale);
							opts.inJustDecodeBounds = false;
							opts.inSampleSize = scale;
							// The code below has a bug on drawing line.
//							mBitmap = BitmapFactory.decodeFile(pathName, opts)
//									.copy(Bitmap.Config.ARGB_8888, true);
							Bitmap imageBitmap = BitmapFactory.decodeFile(pathName, opts);
							mBitmap = Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(), Bitmap.Config.ARGB_8888);
							
							mCanvas = new Canvas(mBitmap);
							mCanvas.drawColor(Color.TRANSPARENT);
							
							mPaint = new Paint();
							mPaint.setStrokeWidth(5);
							mPaint.setColor(Color.GREEN);
							mPaint.setAntiAlias(true);	// Anti alias.
							
							mCanvas.drawBitmap(imageBitmap, new Matrix(), mPaint);
							
//							startX = event.getRawX();
//							startY = event.getRawY();
							startX = event.getX();
							startY = event.getY();
							
						}
						
						break;
					case MotionEvent.ACTION_MOVE:
						if (null != mCanvas) {
//							float newX = event.getRawX();
//							float newY = event.getRawY();
							float newX = event.getX();
							float newY = event.getY();
							
							mCanvas.drawLine(startX, startY, newX, newY, mPaint);
							
							startX = newX;
							startY = newY;
							mImageView.setImageBitmap(mBitmap);
							
						}
						
						break;
					case MotionEvent.ACTION_UP:
						
						break;
				}
				return true;
			}
			
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != data) {
			// The path of picture.
			Uri uri = data.getData();
			// The thumbnail of picture.
			// Bitmap bitmap = data.getParcelableExtra("data");
			
			// Get absolute image path from uri.
//			@Deprecated
//			String[] projection = new String[]{MediaStore.Images.Media.DATA};
//			Cursor cursor = this.managedQuery(uri, projection, null, null, null);
//			cursor.moveToFirst();
//			String filename = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//			cursor.close();
			// New implements in support v4.
			CursorLoader loader = new CursorLoader(this, uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
			Cursor cursor = loader.loadInBackground();
			cursor.moveToFirst();
			String filename = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			cursor.close();
			
			// Set component text and image.
			mEditText.setText(filename);
			mImageView.setImageURI(uri);
			
			// ReDraw.
			mBitmap = null;
			mCanvas = null;
			mPaint = null;
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Select the path of the picture location.
	 * 
	 * @param view
	 */
	public void select(View view) {
		Log.v(TAG, "GraphicActivity: select");
		// Select picture.
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 0);
		
	}
	
	/**
	 * Save the new picture.
	 * 
	 * @param view
	 */
	public void save(View view) {
		Log.v(TAG, "GraphicActivity: save");
		if (null != mBitmap && !TextUtils.isEmpty(mEditText.getText())) {
			try {
				String filename = mEditText.getText().toString();
				filename = filename.substring(0, filename.lastIndexOf("."))
						+ "_" + System.currentTimeMillis()
						+ filename.substring(filename.lastIndexOf("."));
//				File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//				FileOutputStream stream = new FileOutputStream(file);
				Log.v(TAG, "Save Path: " + filename);
				FileOutputStream stream = new FileOutputStream(filename);
				mBitmap.compress(CompressFormat.JPEG, 100, stream);
				stream.close();
				Toast.makeText(this, "The picture has been saved!", Toast.LENGTH_SHORT).show();
				
				// The broadcast to update gallery.
				Intent broadcast = new Intent();
				broadcast.setAction(Intent.ACTION_MEDIA_MOUNTED);
				broadcast.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
				sendBroadcast(broadcast);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			Toast.makeText(this, "There is any picture selected!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Zoom the picture.
	 * 
	 * @param view
	 */
	public void zoom(View view) {
		Log.v(TAG, "GraphicActivity: zoom");
		if (null != mCanvas && !TextUtils.isEmpty(mEditText.getText())) {
			Matrix matrix = new Matrix();
			matrix.setScale(0.5f, 0.5f);
			mCanvas.drawBitmap(mBitmap, matrix, mPaint);
			mImageView.setImageBitmap(mBitmap);
			
		} else {
			Toast.makeText(this, "There is any picture selected!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Rotate the picture.
	 * @param view
	 */
	public void rotate(View view) {
		Log.v(TAG, "GraphicActivity: rotate");
		if (null != mCanvas && !TextUtils.isEmpty(mEditText.getText())) {
			Matrix matrix = new Matrix();
			matrix.setRotate(90f, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
			mCanvas.drawBitmap(mBitmap, matrix, mPaint);
			mImageView.setImageBitmap(mBitmap);
			
		} else {
			Toast.makeText(this, "There is any picture selected!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Translate the picture.
	 */
	public void trans(View view) {
		if (null != mCanvas && !TextUtils.isEmpty(mEditText.getText())) {
			Matrix matrix = new Matrix();
			// matrix.setTranslate(10f, 10f);
			matrix.setScale(-1, 1);
			matrix.postTranslate(mBitmap.getWidth(), 0);
			mCanvas.drawBitmap(mBitmap, matrix, mPaint);
			mImageView.setImageBitmap(mBitmap);
			
		} else {
			Toast.makeText(this, "There is any picture selected!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}

package com.sample.itheima.heima;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Guidance Activity.
 * 
 * @author Aaric
 * 
 */
public class GuidanceActivity extends Activity {

	/**
	 * TAG
	 */
	private static final String TAG = GuidanceActivity.class.getSimpleName();

	/**
	 * Parameter.
	 */
	private int currentIndex;
	private List<View> listViews;

	/**
	 * Component.
	 */
	private ViewPager mViewPager;
	private LinearLayout mLinearLayout;
	private ImageView[] mImageViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guidance);
		
		// Loading...
		Log.i(TAG, "Loading...");

		// Initialize parameters.
		listViews = new ArrayList<View>();
		listViews.add(View.inflate(this, R.layout.activity_guidance_layout1, null));
		listViews.add(View.inflate(this, R.layout.activity_guidance_layout2, null));

		// Last view.
		View mViewWithButton = View.inflate(this, R.layout.activity_guidance_layout3, null);
		Button mButton = (Button) mViewWithButton.findViewById(R.id.btn_guidance_click);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Configuration.
				SharedPreferences sp = v.getContext().getSharedPreferences("config", MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putBoolean(WelcomeActivity.APPLICATION_IS_FIRST_LAUNCHER, false);
				editor.commit();
				
				// To Load Main Activity.
				startActivity(new Intent(v.getContext(), MainActivity.class));
				
				// Finish.
				finish();

			}

		});
		listViews.add(mViewWithButton);

		// Initialize components.
		mViewPager = (ViewPager) this.findViewById(R.id.vp_guidance);
		mViewPager.setAdapter(new ViewsPagerAdapter(listViews));
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// History.
				mImageViews[currentIndex].setEnabled(true);
				// Current.
				currentIndex = position;
				mImageViews[currentIndex].setEnabled(false);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// Nothing.
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// Nothing.
				
			}
		});
		
		mLinearLayout = (LinearLayout) this.findViewById(R.id.ll_guidance);
		Log.i(TAG, "--->" + mLinearLayout.getChildCount());
		mImageViews = new ImageView[mLinearLayout.getChildCount()];
		for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
			mImageViews[i] = (ImageView) mLinearLayout.getChildAt(i);
			mImageViews[i].setTag(i);
			mImageViews[i].setEnabled(true);
			mImageViews[i].setOnClickListener(new ImageView.OnClickListener(){

				@Override
				public void onClick(View v) {
					// History.
					mImageViews[currentIndex].setEnabled(true);
					// Current.
					currentIndex = (Integer) v.getTag();
					mImageViews[currentIndex].setEnabled(false);
					mViewPager.setCurrentItem(currentIndex);
				}
				
			});
		}
		currentIndex = 0;
		mImageViews[currentIndex].setEnabled(false);

	}

	/**
	 * Views Pager Adapter.
	 * 
	 * @author Aaric
	 * 
	 */
	public class ViewsPagerAdapter extends PagerAdapter {

		public List<View> mListViews;

		public ViewsPagerAdapter(List<View> mListViews) {
			super();
			this.mListViews = mListViews;
		}

		@Override
		public int getCount() {
			return this.mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(this.mListViews.get(position), 0);
			return this.mListViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(this.mListViews.get(position));
		}

	}

}

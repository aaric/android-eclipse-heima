package com.sample.itheima.heima.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sample.itheima.heima.R;

/**
 * Drawer Layout Activity.
 * 
 * @author Aaric
 * 
 * @see http://developer.android.com/training/implementing-navigation/nav-drawer.html
 * @see http://developer.android.com/reference/android/support/v4/widget/DrawerLayout.html
 * @see http://developer.android.com/reference/android/support/v4/app/Fragment.html
 *
 */
public class DrawerLayoutActivity extends FragmentActivity {
	
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	
	private ImageView mImageViewHome;
	private TextView mTextViewTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_layout);
		
		// Initialize.
		mPlanetTitles = this.getResources().getStringArray(R.array.str_ary_tabs);
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.dl_drawer_layout);
		mDrawerList = (ListView) this.findViewById(R.id.lv_drawer_layout_left);
		
		mImageViewHome = (ImageView) this.findViewById(R.id.iv_drawer_layout_home);
		mTextViewTitle = (TextView) this.findViewById(R.id.tv_drawer_layout_title);
		
		// Set the adapter for the list view.
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mPlanetTitles));
		
		// Set the list's click listener.
		mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
			}
			
		});
		
		// Default.
		Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, 0);
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		fragmentManager.beginTransaction()
			.replace(R.id.fl_drawer_layout_content, fragment)
			.commit();
		mTextViewTitle.setText(R.string.tv_guidance_tab1);
		
		// Open.
		mImageViewHome.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View v) {
				if (!mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.openDrawer(mDrawerList);
				} else {
					mDrawerLayout.closeDrawer(mDrawerList);
				}
				
			}
			
		});
		
	}
	
	/**
	 * Swaps fragments in the main content view.
	 */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on position.
		Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		fragment.setArguments(args);
		
		// Insert the fragment by replacing any existing fragment.
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		fragmentManager.beginTransaction()
			.replace(R.id.fl_drawer_layout_content, fragment)
			.commit();
		
		// Highlight the selected item, update the title, and close the drawer.
		mDrawerList.setItemChecked(position, true);
		this.setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawers();
		
	}
	
	/**
	 * Fragment that appears in the "content_frame", shows a planet.
	 * 
	 * @author Aaric
	 *
	 */
	public static class PlanetFragment extends Fragment {
		
		public static final String ARG_PLANET_NUMBER = "planet_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			int index = 0;
			View rootView = null;
			TextView mTexView = (TextView) this.getActivity().findViewById(R.id.tv_drawer_layout_title);
			try {
				index = this.getArguments().getInt(ARG_PLANET_NUMBER);
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch (index) {
				case 1:
					mTexView.setText(R.string.tv_guidance_tab2);
					rootView = inflater.inflate(R.layout.activity_guidance_layout2, container, false);
					break;
				case 2:
					mTexView.setText(R.string.tv_guidance_tab3);
					rootView = inflater.inflate(R.layout.activity_guidance_layout3, container, false);
					break;
				default:
					mTexView.setText(R.string.tv_guidance_tab1);
					rootView = inflater.inflate(R.layout.activity_guidance_layout1, container, false);
			}
			return rootView;
		}
		
	}

}

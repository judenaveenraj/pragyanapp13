package com.pragyancsg.pragyanapp13;


import java.io.IOException;

import xmlHandlers.PragyanEventData;
import xmlHandlers.PragyanXmlParser;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PragyanSubActivity extends FragmentActivity {

	
	private PragyanDataParser dataProvider;
	
	CustomPagerAdapter myPagerAdapter;
	
	String rootName = "root"; 
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager myViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pragyan_main);

		
		dataProvider = HelperUtils.getDataProvider();
		
		Bundle bundle = getIntent().getExtras();
		rootName = bundle.getString("root");
		setMenuTitle(rootName);
		
		myPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
		myViewPager = (ViewPager) findViewById(R.id.pager);
		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				Log.d("PAGER","select" + String.valueOf(arg0));
				setMenuTitle(rootName);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		myViewPager.setAdapter(myPagerAdapter);

	}

	public void setMenuTitle(String title){
		TextView menuTitle = (TextView) findViewById(R.id.menuTitle);
		menuTitle.setText(title);
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class CustomPagerAdapter extends FragmentPagerAdapter {

		public CustomPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			//Get the FRAGMENT.
			Log.d("POSITION",String.valueOf(position));
			Fragment fragment = new StaggeredFragment(rootName, dataProvider);
			//Bundle args = new Bundle();
			//args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			//fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			//METHOD THAT WILL GET FRAGMENT COUNT
			return 1;
		}
		
		

	}

}

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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;


public class PragyanSubActivity extends FragmentActivity implements ViewFactory{

	private TextSwitcher menuSwitcher;
	private PragyanDataParser dataProvider;
	
	CustomPagerAdapter myPagerAdapter;
	
	String rootName = "root"; 
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager myViewPager;
	private ImageSwitcher bgSwitcher;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pragyan_main);

		
		dataProvider = HelperUtils.getDataProvider();
		
		Bundle bundle = getIntent().getExtras();
		rootName = bundle.getString("root");
		
		menuSwitcher = (TextSwitcher) findViewById(R.id.menuTitle);
		menuSwitcher.setFactory(this);
		Animation in =  AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		menuSwitcher.setInAnimation(in);
		Animation out =  AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
		menuSwitcher.setOutAnimation(out);
		setMenuTitle(rootName);
		
		bgSwitcher = (ImageSwitcher) findViewById(R.id.bg_image_switcher);
		bgSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				ImageView v1 = new ImageView(getApplicationContext());
				v1.setScaleType(ImageView.ScaleType.FIT_XY);
				v1.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				return v1;
			}
		});
		bgSwitcher.setInAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), android.R.anim.fade_in));
		bgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), android.R.anim.fade_out));
		
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
		HelperUtils.setNewBg(bgSwitcher, getActivity());

	}

	public Activity getActivity(){
		return this;
	}
	public void setMenuTitle(String title){
		menuSwitcher.setText(title);
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

	@Override
	public View makeView() {
		TextView tv = new TextView(this);
		final float scale = getResources().getDisplayMetrics().density;
		tv.setPadding((int)(15*scale+0.5f), (int)(10*scale+0.5f), 0, (int)(10*scale+0.5f));
		//tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tv.setTextColor(getResources().getColor(R.color.Ivory));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
		return tv;
	}

}

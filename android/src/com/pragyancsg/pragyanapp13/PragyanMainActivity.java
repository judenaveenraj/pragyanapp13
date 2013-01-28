package com.pragyancsg.pragyanapp13;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;

import xmlHandlers.PragyanEventData;
import xmlHandlers.PragyanXmlParser;
import android.R.menu;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
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
import android.view.animation.Interpolator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class PragyanMainActivity extends FragmentActivity implements
		ViewFactory {

	private TextSwitcher menuSwitcher;
	private ImageSwitcher bgSwitcher;
	private PragyanDataParser dataProvider;
	CustomPagerAdapter myPagerAdapter;

	private int currentPage = 0;
	String rootName = "root";

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager myViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pragyan_main);

		HelperUtils.cacheBgs(this);
		dataProvider = new PragyanDataParser(this);
		HelperUtils.setDataProvider(dataProvider);

		menuSwitcher = (TextSwitcher) findViewById(R.id.menuTitle);
		bgSwitcher = (ImageSwitcher) findViewById(R.id.bg_image_switcher);
		menuSwitcher.setFactory(this);
		setMenuTitle(dataProvider.getItemUnderWithIndex(rootName, 0)
				.getEventName(), "next");

		bgSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				Log.d("SWITCHER","Making view");
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
				Log.d("PAGER", "select" + String.valueOf(arg0));
				HelperUtils.setNewBg(bgSwitcher, getActivity());
				if (currentPage < arg0) {
					setMenuTitle(
							dataProvider.getItemUnderWithIndex(rootName, arg0)
									.getEventName(), "next");
					currentPage = arg0;
					
					return;
				}
				if (currentPage > arg0) {
					setMenuTitle(
							dataProvider.getItemUnderWithIndex(rootName, arg0)
									.getEventName(), "prev");
					currentPage = arg0;
					
					return;
				}
				
				

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
	public void setMenuTitle(String title, String direction) {
		if (direction.equalsIgnoreCase("next")) {
			Log.d("SWITCH", "next");
			Animation in = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.slide_in_right);
			menuSwitcher.setInAnimation(in);
			Animation out = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.slide_out_left);
			menuSwitcher.setOutAnimation(out);
		} else if (direction.equalsIgnoreCase("prev")) {
			Log.d("SWITCH", "prev");
			Animation in = AnimationUtils.loadAnimation(
					getApplicationContext(), android.R.anim.slide_in_left);
			menuSwitcher.setInAnimation(in);
			Animation out = AnimationUtils.loadAnimation(
					getApplicationContext(), android.R.anim.slide_out_right);
			menuSwitcher.setOutAnimation(out);
		}
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
			// Get the FRAGMENT.
			Log.d("POSITION", String.valueOf(position));
			Fragment fragment = new StaggeredFragment(dataProvider
					.getItemUnderWithIndex(rootName, position).getEventName(),
					dataProvider);
			// Bundle args = new Bundle();
			// args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position +
			// 1);
			// fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			// METHOD THAT WILL GET FRAGMENT COUNT
			return dataProvider.getNumberOfItemsUnder(rootName);
		}

	}

	@Override
	public View makeView() {
		TextView tv = new TextView(this);
		final float scale = getResources().getDisplayMetrics().density;
		tv.setPadding((int) (15 * scale + 0.5f), (int) (10 * scale + 0.5f), 0,
				(int) (10 * scale + 0.5f));
		// tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT));
		tv.setTextColor(getResources().getColor(R.color.Ivory));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
		return tv;
	}

}

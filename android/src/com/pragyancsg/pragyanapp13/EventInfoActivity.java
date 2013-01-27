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
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class EventInfoActivity extends FragmentActivity{

	private PragyanDataParser dataProvider;	
	CustomPagerAdapter mPagerAdapter;
	private ViewPager mViewPager;
	private PagerTabStrip mTitleIndicator;

	String titles[] = {"Description","Rules","Competitions"};
	String desc[] = {"DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
			"RulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRulesRules"
			,"CompetitionsCompetitionsCompetitionsCompetitionsCompetitionsCompetitionsCompetitionsCompetitionsCompetitions"};
	private String rootName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_info_layout);

		
		dataProvider = HelperUtils.getDataProvider();
		Bundle bundle = getIntent().getExtras();
		rootName = bundle.getString("root");
		
		TextView title = (TextView) findViewById(R.id.eventTitle);
		title.setText(rootName);
		mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mTitleIndicator = (PagerTabStrip) findViewById(R.id.pageIndicator);
		

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
			Fragment fragment = new InfoFragment("Description", dataProvider,position);
			//Bundle args = new Bundle();
			//args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			//fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			//METHOD THAT WILL GET FRAGMENT COUNT
			return 3;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}
		

	}
	public class InfoFragment extends Fragment{

		private int position;
		public InfoFragment(String string, PragyanDataParser dataProvider, int position) {
			this.position = position;
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView tv = new TextView(getApplicationContext());
			tv.setText(desc[position]);
			return tv;
			
		}
		
		
	}


}

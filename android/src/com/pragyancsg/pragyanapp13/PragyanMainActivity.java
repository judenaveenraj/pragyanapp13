package com.pragyancsg.pragyanapp13;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PragyanMainActivity extends FragmentActivity{

	CustomPagerAdapter myPagerAdapter;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager myViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pragyan_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		myPagerAdapter = new CustomPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		myViewPager = (ViewPager) findViewById(R.id.pager);
		myViewPager.setAdapter(myPagerAdapter);

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
			Fragment fragment = new StaggeredFragment();
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

	}

}

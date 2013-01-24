package com.pragyancsg.pragyanapp13;

import xmlHandlers.PragyanEventData;

import com.origamilabs.library.views.StaggeredGridView;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class StaggeredFragment extends Fragment {


	private String names[];
	private String urls[];
	
	
	
	public StaggeredFragment(String rootName,
			PragyanDataParser dataProvider) {
		int len = dataProvider.getNumberOfItemsUnder(rootName);
		names = new String[len];
		urls = new String[len];
		for(int i=0; i<len; i++){
			PragyanEventData item = dataProvider.getItemUnderWithIndex(rootName, i);
			names[i] = item.getEventName();
			urls[i] = item.getEventImage();
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		StaggeredGridView gridView = new StaggeredGridView(getActivity());
		
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		
		gridView.setItemMargin(margin); // set the GridView margin
		
		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
		
		StaggeredAdapter adapter = new StaggeredAdapter(getActivity(), R.id.imageView1, urls);
		
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		return gridView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}

}

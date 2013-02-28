package com.pragyancsg.pragyanapp13;

import java.util.ArrayList;

import xmlHandlers.PragyanEventData;

import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;



public class StaggeredNowFragment extends Fragment {


	private String names[];
	private String urls[];
	
	private ArrayList<PragyanEventData> nowEvents = new ArrayList<PragyanEventData>();
	
	public StaggeredNowFragment(PragyanDataParser dataProvider) {
		
		
		nowEvents = dataProvider.getNowEvents();
		int len = nowEvents.size();
		
		names = new String[len];
		urls = new String[len];
		
		Log.d("FRAGMENT",String.valueOf(len));
		for(int i=0; i<len; i++){
			PragyanEventData item = nowEvents.get(i);
			//Log.d("FIXED");
			names[i] = item.getEventName();
			urls[i] = item.getEventImage();
			//Log.d("FIXED",String.valueOf(i)+":::"+names[i]+":::"+urls[i]);
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		StaggeredGridView gridView = new StaggeredGridView(getActivity());
		gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(StaggeredGridView parent, View view, int position,
					long id) {
				ImageView imgvw = (ImageView) view.findViewById(R.id.imageView1);
				String tag = (String) imgvw.getTag();
				Log.d("CLICK",tag);
				
				Intent moveToEvent = new Intent(getActivity(), EventInfoActivity.class);
				moveToEvent.putExtra("root", tag);
				getActivity().startActivity(moveToEvent);
				//Toast.makeText(getActivity(), "No children for "+tag, Toast.LENGTH_SHORT).show();
				
				
			}
		});
		
		
		
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		
		gridView.setItemMargin(margin); // set the GridView margin
		
		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
		
		StaggeredAdapter adapter = new StaggeredAdapter(getActivity(), R.id.imageView1, urls, names);
		
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		return gridView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}

}

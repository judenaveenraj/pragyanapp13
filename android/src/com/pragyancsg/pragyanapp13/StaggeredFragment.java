package com.pragyancsg.pragyanapp13;

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
		gridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(StaggeredGridView parent, View view, int position,
					long id) {
				ImageView imgvw = (ImageView) view.findViewById(R.id.imageView1);
				String tag = (String) imgvw.getTag();
				Log.d("CLICK",tag);
				
				int numChildren = HelperUtils.getDataProvider().getNumberOfItemsUnder(tag);
				if(numChildren>0){
					Intent moveToSub = new Intent(getActivity(), PragyanSubActivity.class);
					moveToSub.putExtra("root", tag);
					getActivity().startActivity(moveToSub);
				}
				else{
					Intent moveToEvent = new Intent(getActivity(), EventInfoActivity.class);
					moveToEvent.putExtra("root", tag);
					getActivity().startActivity(moveToEvent);
					Toast.makeText(getActivity(), "No children for "+tag, Toast.LENGTH_SHORT).show();
				}
				
				
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

package com.pragyancsg.pragyanapp13;


import java.util.ArrayList;

import Helpers.ImageLoader;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class StaggeredAdapter extends ArrayAdapter<String> {

	private ImageLoader mLoader;
	private String[] tags;
	
	public StaggeredAdapter(Context context, int textViewResourceId,
			String[] objects, String[] objectTags) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
		tags = objectTags;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(getItem(position), holder.imageView, tags[position]);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
	}
}

package com.pragyancsg.pragyanapp13;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;



public class StaggeredDownloadFragment extends Fragment {


	private String names[];
	private String urls[];
	
	
	
	public StaggeredDownloadFragment(PragyanDataParser dataProvider) {
		int len = 3;
		names = new String[]{"Map","Schedule","Food Menu"};
		urls = new String[]{"http://delta.nitt.edu/~robo/appresources/map_icon.png", "http://delta.nitt.edu/~robo/appresources/icon_schedule.png", "http://delta.nitt.edu/~robo/appresources/food_icon.png"};
				
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
				
				if(tag.equalsIgnoreCase("map")){
					openImage("map1.jpg");
				}
				else if(tag.equalsIgnoreCase("schedule")){
					openPdf("schedule1.pdf");
				}
				else if(tag.equalsIgnoreCase("food menu")){
					openPdf("menu1.pdf");
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

	private void openPdf(String fileName){
		File fileBrochure = new File("/sdcard/eventXml/"+fileName);
	    if (!fileBrochure.exists())
	    {
	         CopyAssetsbrochure(fileName);
	    } 

	    /** PDF reader code */
	    File file = new File("/sdcard/eventXml/"+fileName);        

	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setDataAndType(Uri.fromFile(file),"application/pdf");
	    getActivity().finish();
	    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    try 
	    {
	        getActivity().startActivity(intent);
	    } 
	    catch (ActivityNotFoundException e) 
	    {
	         e.printStackTrace();
	    }
	
	}
	
	private void openImage(String fileName){
		File fileBrochure = new File("/sdcard/eventXml/"+fileName);
	    if (!fileBrochure.exists())
	    {
	         CopyAssetsbrochure(fileName);
	    } 

	    /** PDF reader code */
	    File file = new File("/sdcard/eventXml/"+fileName);        

	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setDataAndType(Uri.fromFile(file),"image/*");
	    getActivity().finish();

	    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    try 
	    {
	        getActivity().startActivity(intent);
	    } 
	    catch (ActivityNotFoundException e) 
	    {
	         e.printStackTrace();
	    }
	
	}
	
	private void CopyAssetsbrochure(String fileName) {
        AssetManager assetManager = getActivity().getAssets();
        String[] files = null;
        try 
        {
            files = assetManager.list("");
        } 
        catch (IOException e)
        {
            Log.e("tag", e.getMessage());
        }
        for(int i=0; i<files.length; i++)
        {
            String fStr = files[i];
            if(fStr.equalsIgnoreCase(fileName))
            {
                InputStream in = null;
                OutputStream out = null;
                try 
                {
                  in = assetManager.open(files[i]);
                  out = new FileOutputStream("/sdcard/eventXml/" + files[i]);
                  copyFile(in, out);
                  in.close();
                  in = null;
                  out.flush();
                  out.close();
                  out = null;
                  break;
                } 
                catch(Exception e)
                {
                    Log.e("tag", e.getMessage());
                } 
            }
        }
    }

 private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
}

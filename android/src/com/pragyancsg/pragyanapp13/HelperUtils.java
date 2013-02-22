package com.pragyancsg.pragyanapp13;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageSwitcher;

public class HelperUtils {

	private static PragyanDataParser dataProvider;
	private static int currBgId;
	private static int[] bgimgs = {R.drawable.blue,
								R.drawable.green,
								R.drawable.lime,
								R.drawable.magenta,
								R.drawable.orange,
								R.drawable.pink,
								R.drawable.purple,
								R.drawable.red,
								R.drawable.teal,
							}; 
	
	private static ArrayList<Drawable> bgs = new ArrayList<Drawable>();
								
	
	public static int getCurrentBg(){
		if(currBgId!=0)
			return currBgId;
		return bgimgs[0];
	}
	public static void setNewBg(ImageSwitcher is, Activity act){
		long time = System.currentTimeMillis();
		
		
		int picId = 0;
		while(currBgId == (picId = bgimgs[(new Random()).nextInt(9)]))  { }
		Log.d("TIMER",String.valueOf(System.currentTimeMillis()-time));
		
		class AsyncChangeBg extends AsyncTask<Integer, Void, Drawable>{
			
			private WeakReference<ImageSwitcher> bg;
			private Activity a;


			public AsyncChangeBg(ImageSwitcher bgsw, Activity act) {
				bg = new WeakReference<ImageSwitcher>(bgsw);
				a = act;
				}
			
			
			@Override
			protected Drawable doInBackground(Integer... params) {
				int index = params[0];
				return a.getResources().getDrawable(index);
				
			}
			
			@Override
			protected void onPostExecute(Drawable result) {
				if(bg != null && result!=null){
					ImageSwitcher imgsw = bg.get();
					if(imgsw!=null){
						long time2 = System.currentTimeMillis();
						imgsw.setImageDrawable(result);
						Log.d("TIMER2",String.valueOf(System.currentTimeMillis()-time2));
					}
					else
						Log.d("SWITCHER","no imageswitcher found");
				}
				else
					Log.d("SWITCHER","no result or reference");
			}
			
			
		}
		new AsyncChangeBg(is, act).execute(picId);
		
		currBgId=picId;
		Log.d("TIMERnever",String.valueOf(System.currentTimeMillis()-time));
		
	}

	
	
	public static void cacheBgs(Activity act){
		for (int i=0; i<9; i++){
			bgs.add(act.getResources().getDrawable(bgimgs[i]));
		}
	}
	
	public HelperUtils() {
		// TODO Auto-generated constructor stub
	}

	public static PragyanDataParser getDataProvider() {
		return dataProvider;
	}

	public static void setDataProvider(PragyanDataParser dataProvider) {
		HelperUtils.dataProvider = dataProvider;
	}
	
	
}

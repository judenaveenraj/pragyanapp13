package com.pragyancsg.pragyanapp13;

import java.util.Random;

import android.widget.ImageSwitcher;

public class HelperUtils {

	private static PragyanDataParser dataProvider;
	private static int currBgId;
	
	public static void setNewBg(ImageSwitcher is){
		int picId=0;
		Random randomGenerator = new Random();
		int picGen = randomGenerator.nextInt(8);
		if (picGen == 0)
			picId = R.drawable.blue;
		else if (picGen == 1)
			picId = R.drawable.green;
		else if (picGen == 2)
			picId = R.drawable.lime;
		else if (picGen == 3)
			picId = R.drawable.magenta;
		else if (picGen == 4)
			picId = R.drawable.orange;
		else if (picGen == 5)
			picId = R.drawable.pink;
		else if (picGen == 6)
			picId = R.drawable.purple;
		else if (picGen == 7)
			picId = R.drawable.red;
		else if (picGen == 8)
			picId = R.drawable.teal;
		
		if(currBgId!= picId){
			is.setImageResource(picId);
			currBgId=picId;
		}
		else{
			setNewBg(is);
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

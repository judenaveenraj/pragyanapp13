package com.pragyancsg.pragyanapp13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Stack;

import xmlHandlers.PragyanEventData;
import xmlHandlers.PragyanXmlParser;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;

public class PragyanDataParser {

	private Activity activity;
	private ArrayList<PragyanEventData> eventTree = new ArrayList<PragyanEventData>();
	
	
	public PragyanDataParser(Activity activity) {
		
		this.activity = activity;
		try {
			PragyanXmlParser parser = new PragyanXmlParser(activity.getAssets().open("pragyanv4.xml"));
			parser.parseDocument();
			while(!parser.isDone());
			eventTree = parser.getParsedEventTree();
			parser.printParsedData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public PragyanEventData getItemWithName(String name){
		Stack<PragyanEventData> stack = new Stack<PragyanEventData>();
		for(int i=0;i<eventTree.size();i++)
			stack.push(eventTree.get(i));
		while(!stack.empty()){
			PragyanEventData node = stack.pop();
			if(name.equalsIgnoreCase(node.getEventName())){
				return node;
			}
			for(int i=0;i<node.getEventChildren().size();i++)
				stack.push(node.getEventChildren().get(i));
		}
		return null;
	}
	
	
	public PragyanEventData getItemUnderWithIndex(String rootName, int index){
		if(rootName.equalsIgnoreCase("root")){
			return eventTree.get(index);
		}
		try{
			return getItemWithName(rootName).getEventChildren().get(index);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int getNumberOfItemsUnder(String name){
		ArrayList<String> titles = new ArrayList<String>();
		if(name.equalsIgnoreCase("root")){
			return eventTree.size();
		}
		else{
			return getItemWithName(name).getEventChildren().size();
		}
		
	}
		
	
	
}

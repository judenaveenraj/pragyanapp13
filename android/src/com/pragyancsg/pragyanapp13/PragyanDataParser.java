package com.pragyancsg.pragyanapp13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import org.apache.http.HttpConnection;
import org.xml.sax.SAXException;

import com.pragyancsg.pragyanapp13.PragyanMainActivity.UpdateXmlAsyncTask;

import xmlHandlers.PragyanEventData;
import xmlHandlers.PragyanXmlParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

public class PragyanDataParser {

	private Context activity;
	private ArrayList<PragyanEventData> eventTree = new ArrayList<PragyanEventData>();
	
	public static boolean updateDone = false;
	
	public PragyanDataParser(Context context) {
		
		this.activity = context.getApplicationContext();
		try {
			File cacheDir;
			if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"eventXml");
	        else
	            cacheDir=context.getApplicationContext().getCacheDir();
	        if(!cacheDir.exists())
	            cacheDir.mkdirs();
			
	        File f = new File(cacheDir, "events.xml");
	        PragyanXmlParser parser;
	        InputStream is;
	        if ( f.exists()){
	        	Log.d("MAIN","Using events");
	        	is = new FileInputStream(f);
	        	parser = new PragyanXmlParser(is);
	        }
	        else{
	        	Log.d("MAIN","Using asset");
	        	is=activity.getAssets().open("pragyanv4.xml");
	        	parser = new PragyanXmlParser(is);
	        }
			try {
				
				parser.parseDocument();
			
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				is.close();
				is = context.getAssets().open("pragyanv4.xml");
				Log.d("MAIN","falling back to old xml");
	        	parser = new PragyanXmlParser(is);
	        	try {
					parser.parseDocument();
				} catch (SAXException e1) {
					is.close();
					e.printStackTrace();
				}
			}
			
				
			
			while(!parser.isDone());
			eventTree = parser.getParsedEventTree();
			parser.printParsedData();
	       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//new remoteLoadXmlAsync().execute("http://192.168.1.150/pragyanv4.xml");//("http://delta.nitt.edu/~robo/pragyanv4.xml");
		
		//while(!updateDone){
			
		//}
		
		
	}
	

	public void updateFromRemoteXml(String url, UpdateXmlAsyncTask updateXmlAsyncTask) {
		
		File cacheDir;
		try{
			if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"eventXml");
	        else
	            cacheDir=activity.getApplicationContext().getCacheDir();
	        if(!cacheDir.exists())
	            cacheDir.mkdirs();
			
	        File f = new File(cacheDir, "events.xml");
	        
	        if ( !f.exists()){
	        	Log.d("XML","Starting download in null");
	        	updateXmlAsyncTask.displayMessage("Downloading from remote server...");
				URL xmlUrl = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) xmlUrl.openConnection();
				conn.setConnectTimeout(30000);
	            conn.setReadTimeout(30000);
	            InputStream is = conn.getInputStream();
	            OutputStream os = new FileOutputStream(f);
	            Utils.CopyStream(is, os);
	            os.close();
	         //   new Toast(activity).makeText(activity, "MADE NEW FILE", Toast.LENGTH_SHORT);
	            Log.d("XML","MADE NEW FILE");
	        }
	        
	        else{
	        	Log.d("XML","Starting download in not null");
	        	updateXmlAsyncTask.displayMessage("Downloading from remote server...");
	        	File temp = new File(cacheDir, "temp.xml");
	        	URL xmlUrl = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) xmlUrl.openConnection();
				conn.setConnectTimeout(30000);	
	            conn.setReadTimeout(30000);
	            InputStream is = conn.getInputStream();
	            OutputStream os = new FileOutputStream(temp);
	            Utils.CopyStream(is, os);
	            os.close();
	            
	            Log.d("XML","Comparing files");
	            /*
	            updateXmlAsyncTask.displayMessage("Parsing Updates...");
	            //Compare the to files
	            FileReader fr1 = new FileReader(new File(cacheDir, "events.xml"));
	            Log.d("XML","BETWEEn");
	            FileReader fr2 = new FileReader(new File(cacheDir, "temp.xml"));
	            Log.d("XML","Going to compare");
	            int s1=0 ,s2=0 ,c=0;
	            boolean reqUpdate = false;
	            
	            while(true){
	            	s1=fr1.read();
	            	s2=fr2.read();
	            	//Log.d("CMP","s1"+String.valueOf(s1)+"s2"+String.valueOf(s2));
	
	            	if (s1 != s2)
	            		{
	            			reqUpdate = true;
	            			Log.d("XML","files are not equal, must use new file");
	            			break;
	            			
	            		}
	            	if(s1==-1 && s2==-1)
	            		break;
	            	
	            	//Log.d("CMP","sad"+String.valueOf(c));
	            	c++;
	
	            	}
	            fr1.close();
	            fr2.close();*/
	            updateXmlAsyncTask.displayMessage("Update Done! Cleaning...");
	            File tempFile = new File(cacheDir, "temp.xml");
	            FileInputStream fin = new FileInputStream(tempFile);
	            FileOutputStream fout =  new FileOutputStream(new File(cacheDir, "events.xml"));
	            Utils.CopyStream(fin, fout);
	            fout.close();
	            tempFile.delete();
	            
	        	/*if(!reqUpdate){
	        		Log.d("XML","files are equal, using new file");
	        	//	new Toast(activity).makeText(activity, "EQUAL, using new file", Toast.LENGTH_SHORT);
	        	}*/
	        	
	        	
	        }
	        
	              
		} catch (MalformedURLException e) {
			e.printStackTrace();
			updateXmlAsyncTask.notifyFailure("Connection Failed");
			return;
		} catch (IOException e) {
			updateXmlAsyncTask.notifyFailure("Connection Timed Out");
			return;
		} 
			
	}

	/*public class remoteLoadXmlAsync extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			try {
				File cacheDir;
				if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"eventXml");
		        else
		            cacheDir=activity.getApplicationContext().getCacheDir();
		        if(!cacheDir.exists())
		            cacheDir.mkdirs();
				
		        File f = new File(cacheDir, "events.xml");
		        
		        if ( !f.exists()){
		        	Log.d("XML","Starting download in null");
					URL xmlUrl = new URL(params[0]);
					HttpURLConnection conn = (HttpURLConnection) xmlUrl.openConnection();
					conn.setConnectTimeout(30000);
		            conn.setReadTimeout(30000);
		            InputStream is = conn.getInputStream();
		            OutputStream os = new FileOutputStream(f);
		            Utils.CopyStream(is, os);
		            os.close();
		         //   new Toast(activity).makeText(activity, "MADE NEW FILE", Toast.LENGTH_SHORT);
		            Log.d("XML","MADE NEW FILE");
		        }
		        
		        else{
		        	Log.d("XML","Starting download in not null");
		        	File temp = new File(cacheDir, "temp.xml");
		        	URL xmlUrl = new URL(params[0]);
					HttpURLConnection conn = (HttpURLConnection) xmlUrl.openConnection();
					conn.setConnectTimeout(30000);
		            conn.setReadTimeout(30000);
		            InputStream is = conn.getInputStream();
		            OutputStream os = new FileOutputStream(temp);
		            Utils.CopyStream(is, os);
		            os.close();
		            
		            Log.d("XML","Comparing files");
		            //Compare the to files
		            FileReader fr1 = new FileReader(new File(cacheDir, "events.xml"));
		            Log.d("XML","BETWEEn");
		            FileReader fr2 = new FileReader(new File(cacheDir, "temp.xml"));
		            Log.d("XML","Going to compare");
		            int s1=0 ,s2=0 ,c=0;
		            boolean reqUpdate = false;
		            
		            while(true){
		            	s1=fr1.read();
		            	s2=fr2.read();
		            	//Log.d("CMP","s1"+String.valueOf(s1)+"s2"+String.valueOf(s2));

		            	if (s1 != s2)
		            		{
		            			reqUpdate = true;
		            			Log.d("XML","files are not equal, must use new file");
		            			break;
		            			
		            		}
		            	if(s1==-1 && s2==-1)
		            		break;
		            	
		            	//Log.d("CMP","sad"+String.valueOf(c));
		            	c++;

		            	}
		            fr1.close();
		            fr2.close();
		            File tempFile = new File(cacheDir, "temp.xml");
		            FileInputStream fin = new FileInputStream(tempFile);
		            FileOutputStream fout =  new FileOutputStream(new File(cacheDir, "events.xml"));
		            Utils.CopyStream(fin, fout);
		            fout.close();
		            tempFile.delete();
		            
		            
		            
		        	if(!reqUpdate){
		        		Log.d("XML","files are equal, using new file");
		        	//	new Toast(activity).makeText(activity, "EQUAL, using new file", Toast.LENGTH_SHORT);
		        	}
		        	
		        	
		        }
		        
		        
		        updateDone=true;
	            
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				return null;
			}
			
			
			return null;
		}
		
		
		
	}
	*/
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


	public ArrayList<PragyanEventData> getNowEvents() {
		// TODO Auto-generated method stub
		ArrayList<PragyanEventData> nowEvents = new ArrayList<PragyanEventData>();
		
		Stack<PragyanEventData> stack = new Stack<PragyanEventData>();
		
		for(int i=0;i<eventTree.size();i++)
			stack.push(eventTree.get(i));
		
		while(!stack.empty()){
			PragyanEventData node = stack.pop();
			if(eventHappeningNow(node)){
				nowEvents.add(node);
			}
			for(int i=0;i<node.getEventChildren().size();i++)
				stack.push(node.getEventChildren().get(i));
		}
		return nowEvents;
	}


	private boolean eventHappeningNow(PragyanEventData node) {
		// TODO Auto-generated method stub
		Date now = new Date();
		ArrayList<Date> starts = node.getStartTimes();
		ArrayList<Date> ends = node.getEndTimes();
		
		for(int i=0; i<starts.size();i++){
			//Log.d("FRAGMENTS",starts.get)
			if ( now.after(starts.get(i)) && now.before(ends.get(i)) )
				return true;
		}
		return false;
	}


		
	
	
}

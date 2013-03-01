package xmlHandlers;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.net.ParseException;
import android.util.Log;

public class PragyanXmlParser extends DefaultHandler {

	private final String rootUrl = "http://www.pragyan.org";
	//Make sure to get the xml file into this
	private ArrayList<PragyanEventData> events = new ArrayList<PragyanEventData>();
	private Stack<PragyanEventData> stackEvents = new Stack<PragyanEventData>();
	
	private PragyanEventData tempEvent;
	private String tempString;
	private InputStream fileToParse;
	
	private CharArrayWriter charWriter;
	
	private boolean done = false;
	private boolean startPage = false;
	private boolean startContent = false;
	private SimpleDateFormat format;
	private boolean writingDate= false;
	private CharArrayWriter dateWriter;
	
	
	
	/***
	 * 
	 * 
	 * Legend for the <br> annd <b> tags
	 * 
	 * --> $$##$$  :  <br />
	 * --> $$!!$$  :  <b>
	 * --> $$%%$$  :  </b>
	 * 
	 * 
	 * 
	 * @param file
	 */
	
	
	public PragyanXmlParser(InputStream file) {
		fileToParse = file;
		charWriter = new CharArrayWriter();
		dateWriter = new CharArrayWriter();
		format = new SimpleDateFormat("dd-MM-yyyy HH:mm");  
		
	}	
	
	public boolean isDone(){
		return this.done;
	}
	
	public ArrayList<PragyanEventData> getParsedEventTree(){
		return this.events;
	}
	
	public void printParsedData(){
		for (PragyanEventData event : events){
			event.print();
		}
	}
	public void parseDocument() throws SAXException{
		Log.d("MAIN", "parser starting");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
	            SAXParser parser = factory.newSAXParser();
	            parser.parse(fileToParse, this);
	            
	        } catch (ParserConfigurationException e) {
	            System.out.println("ParserConfig error");
	        } 
	         catch (IOException e) {
	            System.out.println("IO error");
	        }
		
	}
	
	@Override
	public void startDocument() throws SAXException {
		done = false;
	}
	
	@Override
	public void endDocument() throws SAXException {
		done = true;
		}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if(!startPage){
			tempString = "";
		}
		////Log.d("PARSE", "Starting element "+qName);
		if(qName.equalsIgnoreCase("item")){
			tempEvent = new PragyanEventData();
			if(stackEvents.empty())
				events.add(tempEvent);
			else{
				stackEvents.peek().appendEvent(tempEvent);
			}
		}
		
			
		if(qName.equalsIgnoreCase("children")){
			////Log.d("PARSE","Pushing to stack:"+tempEvent.toString());
			stackEvents.push(tempEvent);
		}
		
		if(qName.equalsIgnoreCase("main")){
			startPage = true;
		}
		if(qName.equalsIgnoreCase("page")){
			startPage = true;
		}
		if(qName.equalsIgnoreCase("starttime")){
			writingDate = true;
		}
		if(qName.equalsIgnoreCase("endtime")){
			writingDate = true;
		}
		
		if(qName.equalsIgnoreCase("content")){
			//Log.d("ACTION","hit content"+String.valueOf(startPage));
			startContent = true;
			tempString="";
			
		}
		
		
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		////Log.d("PARSE", "Ending element "+qName);
		//Log.d("XML", tempString);
		if(qName.equalsIgnoreCase("item")){
			//tempEvent = new PragyanEventData();
		}
		if(qName.equalsIgnoreCase("item")){
			tempEvent = new PragyanEventData();
		}
		if(qName.equalsIgnoreCase("itemname")){
			tempEvent.setEventName(tempString);
		}
		if(qName.equalsIgnoreCase("one")){
			tempEvent.setEventCaption(tempString);
		}
		
		if(qName.equalsIgnoreCase("imgurl")){
			tempEvent.setEventImage(tempString);
		}
		if(qName.equalsIgnoreCase("starttime")){
			try {  
				//Log.d("TIMESTART", tempString);
			    Date time = format.parse(dateWriter.toString());  
			    
			    tempEvent.addStartTime(time);
				
				writingDate = false;
				dateWriter.reset();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				tempString="";

				e.printStackTrace();
			}
			finally{}
		}
		
		if(qName.equalsIgnoreCase("endtime")){
			try {  
				//Log.d("TIMEEND", tempString);
			    Date time = format.parse(dateWriter.toString());  
				tempEvent.addEndTime(time);
				tempString="";
				writingDate = false;
				dateWriter.reset();

			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				//Log.d("ERROR", tempEvent.getEventName());
				//Log.d("ERROR", tempString);
				tempString="";

				e.printStackTrace();
			}
			finally{}
		}
		
		if(qName.equalsIgnoreCase("children")){
			tempEvent =stackEvents.pop();
		}
		if(qName.equalsIgnoreCase("main")){
			tempEvent.addPageTitle("About");
			tempString = tempString.replace("$$##$$", "\n");
			tempString = tempString.replace("$$!!$$", " ");
			tempString = tempString.replace("$$%%$$", " ");
			//Log.d("MAIN",tempString);
			tempEvent.addPageContent(tempString);
			startPage=false;
		}
		if(qName.equalsIgnoreCase("pagename")){
			if(startPage){
				////Log.d("FIXIT",tempString.trim());
				tempEvent.addPageTitle(tempString.trim());
				tempString="";
			}
		}
		if(qName.equalsIgnoreCase("pgimg")){
			if(startPage){
				tempEvent.setEventSecondaryImage(rootUrl+tempString);
				tempString="";
			}
		}
		if(qName.equalsIgnoreCase("content")){
			//Log.d("ACTION","hit contentend"+String.valueOf(startPage)+":"+tempString);
			if(startPage){
				tempEvent.addPageContent(charWriter.toString());
				////Log.d("CONT",charWriter.toString());
				tempString="";
				charWriter.reset();
				startContent = false;
			}
		}
		if(qName.equalsIgnoreCase("page")){
			////Log.d("FOXIT","pageend:"+String.valueOf(tempEvent.pageTitles.size()));
			startPage=false;
		}
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(startContent)
			charWriter.write(ch, start, length);
		else if (writingDate)
			dateWriter.write(ch, start, length);
		else
			tempString = new String(ch, start, length);
	}
}

package xmlHandlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class PragyanXmlParser extends DefaultHandler {

	
	//Make sure to get the xml file into this
	private ArrayList<PragyanEventData> events = new ArrayList<PragyanEventData>();
	private Stack<PragyanEventData> stackEvents = new Stack<PragyanEventData>();
	
	private PragyanEventData tempEvent;
	private String tempString;
	private InputStream fileToParse;
	
	private boolean done = false;
	
	public PragyanXmlParser(InputStream file) {
		fileToParse = file;
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
	public void parseDocument(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
	            SAXParser parser = factory.newSAXParser();
	            parser.parse(fileToParse, this);
	        } catch (ParserConfigurationException e) {
	            System.out.println("ParserConfig error");
	        } catch (SAXException e) {
	            System.out.println("SAXException : xml not well formed");
	        } catch (IOException e) {
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
		
		tempString = "";

		//Log.d("PARSE", "Starting element "+qName);
		if(qName.equalsIgnoreCase("item")){
			tempEvent = new PragyanEventData();
			if(stackEvents.empty())
				events.add(tempEvent);
			else{
				//Log.d("PARSE",stackEvents.peek().toString());
				//Log.d("PARSE",tempEvent.toString());
				
				stackEvents.peek().appendEvent(tempEvent);
			}
		}
		
			
		if(qName.equalsIgnoreCase("children")){
			//Log.d("PARSE","Pushing to stack:"+tempEvent.toString());
			stackEvents.push(tempEvent);
		}
		
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//Log.d("PARSE", "Ending element "+qName);
		if(qName.equalsIgnoreCase("item")){
			//tempEvent = new PragyanEventData();
		}
		if(qName.equalsIgnoreCase("item")){
			tempEvent = new PragyanEventData();
		}
		if(qName.equalsIgnoreCase("name")){
			tempEvent.setEventName(tempString);
		}
		if(qName.equalsIgnoreCase("imgurl")){
			tempEvent.setEventImage(tempString);
		}
		if(qName.equalsIgnoreCase("children")){
			tempEvent =stackEvents.pop();
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempString = new String(ch, start, length);
	}
}

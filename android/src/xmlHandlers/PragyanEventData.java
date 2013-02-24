package xmlHandlers;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

public class PragyanEventData {
	private String eventName;
	private String eventType;
	private String eventCaption;
	public ArrayList<String> pageTitles;
	public ArrayList<String> pageContents;
	private Date eventDate;
	private ArrayList<PragyanEventData> eventChildren ;
	private String eventImage;
	private String eventSecondaryImage;
	
	public PragyanEventData() {
		eventChildren = new ArrayList<PragyanEventData>();
		pageTitles = new ArrayList<String>();
		pageContents = new ArrayList<String>();
	}	
	public PragyanEventData(String name, Date datetime, String imgurl) {
		eventChildren = new ArrayList<PragyanEventData>();

		eventName = name;
		eventDate = datetime;
		eventImage = imgurl;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	
	
	public String getEventImage() {
		return eventImage;
	}
	public void setEventImage(String eventImage) {
		this.eventImage = eventImage;
	}
	
	
	
	
	public ArrayList<PragyanEventData> getEventChildren() {
		return this.eventChildren;
	}
	public void appendEvent(PragyanEventData event){
		//Log.d("PARSE",event.toString());
		
		this.eventChildren.add(event);
	}
	
	public void print(){
		//Log.d("CONTENT",getEventName()+":::::"+ getEventImage());
		for (PragyanEventData event : getEventChildren()){
			event.print();
		}
	}
	public String getEventCaption() {
		return eventCaption;
	}
	public void setEventCaption(String eventCaption) {
		this.eventCaption = eventCaption;
	}
	
	public int getPagesCount(){
		return pageTitles.size();
	}
	public void addPageTitle(String title){
		pageTitles.add(title);
	}
	
	public String getPageTitle(int index){
		return pageTitles.get(index);
	}
	
	public void addPageContent(String content){
		pageContents.add(content);
	}
	
	public String getPageContent(int index){
		return pageContents.get(index);
	}
	public String getEventSecondaryImage() {
		return eventSecondaryImage;
	}
	public void setEventSecondaryImage(String eventSecondaryImage) {
		this.eventSecondaryImage = eventSecondaryImage;
	}
	
}


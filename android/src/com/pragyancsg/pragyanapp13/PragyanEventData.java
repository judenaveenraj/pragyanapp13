package com.pragyancsg.pragyanapp13;

import java.util.ArrayList;
import java.util.Date;

public class PragyanEventData {
	private String eventName;
	private String eventType;
	private Date eventDate;
	private ArrayList<PragyanEventData> eventChildren;
	private String eventImage;
	
	
	
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
		return eventChildren;
	}
	public void appendEvent(PragyanEventData event){
		getEventChildren().add(event);
	}
	
	
}


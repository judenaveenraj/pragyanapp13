package com.pragyancsg.pragyanapp13;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class PragyanDataParser {

	private ArrayList<PragyanEventData> pragyanEvents;
	private String fetchUrl;
	
	public PragyanDataParser(String eventsUrl) {
		fetchUrl = eventsUrl;
		
		addEvents();
		addHighlights();
		
		
	}

	private void addHighlights() {
		ArrayList<String> highlights = new ArrayList<String>();
		highlights.add("Guest Lectures");
		highlights.add("Workshops");
		highlights.add("Pengufest");
		highlights.add("Exhibitions & Sangam");
		highlights.add("Outreach");
		highlights.add("PSR");
		highlights.add("Crossfire");
		highlights.add("Infotainment");
		
		for(int h=0;h<8;h++){
			PragyanEventData highlight = new PragyanEventData(highlights.get(h), new Date(), "");
			pragyanEvents.add(highlight);
			for(int i=0; i < new Random().nextInt(3); i++){
				PragyanEventData innerhighlight = new PragyanEventData("Highlight singular having number "+String.valueOf(i), new Date(), "");
				highlight.appendEvent(innerhighlight);
			}
		}
	}

	private void addEvents() {
		ArrayList<String> eventmenus = new ArrayList<String>();
		eventmenus.add("Core Engineering");
		eventmenus.add("Innovation");
		eventmenus.add("Robovigyan");
		eventmenus.add("Brainworks");
		eventmenus.add("Codeit");
		eventmenus.add("Pragyans Got Talent");
		eventmenus.add("Manigma");
		eventmenus.add("Chillpill");
		
		for(int e=0;e<8;e++){
			PragyanEventData event = new PragyanEventData(eventmenus.get(e),new Date(),"");
			pragyanEvents.add(event);
			for(int i=0; i < new Random().nextInt(5)+1; i++){
				PragyanEventData innerevent = new PragyanEventData("Singular event having number "+String.valueOf(i),null,"");
				event.appendEvent(innerevent);
			}
			
		}
	}
	
}

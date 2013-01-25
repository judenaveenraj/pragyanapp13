package com.pragyancsg.pragyanapp13;

public class HelperUtils {

	private static PragyanDataParser dataProvider;
	
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

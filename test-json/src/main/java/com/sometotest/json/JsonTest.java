package com.sometotest.json;

import com.google.gson.Gson;

public class JsonTest{

	public void getJsonString() {

		Gson gson = new Gson();

		String plainString = "{\"key\": \"value\"}";

		System.out.println("plainString: " + plainString);

		Object jsonObject = gson.toJson( plainString );
		System.out.println("gson.toJson returned class: " + jsonObject.getClass());

		String jsonString = gson.toJson( plainString );
		System.out.println("jsonString: " + jsonString);

		String backString = gson.fromJson( jsonString, String.class );
		System.out.println("back to plainString: " + backString);


	}

	public void doJson() {
		getJsonString();
	}

}

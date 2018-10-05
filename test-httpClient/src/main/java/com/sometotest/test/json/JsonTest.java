package com.sometotest.test.json;

import com.google.gson.Gson;

public class JsonTest{

	public String getJsonString() {
		Gson gson = new Gson();

		String plainString = "{\"key\": \"value\"}";

		String jsonString = gson.toJson( plainString );
		return jsonString;
	}

}

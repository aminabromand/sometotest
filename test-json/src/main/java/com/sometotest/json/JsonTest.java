package com.sometotest.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonTest{

	public void getJsonString() {

		Gson gson = new Gson();

		String plainString = "{\"key\": \"value\"}";

		System.out.println("plainString: " + plainString);

		Object jsonObjectTest = gson.toJson( plainString );
		System.out.println("gson.toJson returned class: " + jsonObjectTest.getClass());

		String jsonString = gson.toJson( plainString );
		System.out.println("jsonString: " + jsonString);

		String backString = gson.fromJson( jsonString, String.class );
		System.out.println("back to plainString: " + backString);




		String plainJsonString = "{\"key\": \"value\"}";
		JsonObject jsonObject = (new JsonParser()).parse(plainJsonString).getAsJsonObject();

		System.out.println(jsonObject.toString());

		String plainJsonString2 = "{\"key2\": value2}";
		JsonObject jsonObject2 = (new JsonParser()).parse(plainJsonString2).getAsJsonObject();
		String plainJsonString3 = "{\"key3\": \"value3\"}";
		JsonObject jsonObject3 = (new JsonParser()).parse(plainJsonString3).getAsJsonObject();

		JsonArray jsonArray = new JsonArray();
		jsonArray.add( jsonObject2 );
		jsonArray.add( jsonObject3 );


		jsonObject.add( "key4", jsonArray );

		System.out.println(jsonObject.toString());


		JsonElement jsonElement1 = jsonObject.getAsJsonArray( "key4" ).get( 0 );
		System.out.println(jsonElement1.toString());

		JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
		System.out.println(jsonObject1.get( "key2" ).getAsString());

		//String outputString = jsonObject.getAsJsonArray( "key4" ).get( 0 )
		//System.out.println(outputString);

	}

	public void doJson() {
		getJsonString();
	}

}

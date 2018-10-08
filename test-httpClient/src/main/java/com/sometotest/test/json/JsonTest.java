package com.sometotest.test.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class JsonTest{

	public String getJsonString() {

		String inputFilePath = JsonTest.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						+ File.separator + ".." + File.separator + "resources" + File.separator ;
		File inputFile = new File( inputFilePath + "test.png" );

		String fileString = "";
		int length = 0;
		try( InputStream is = new FileInputStream( inputFile ) ){
			byte[] inBuffer = new byte[1023];
			byte[] tempBuffer;
			byte[] outBuffer = new byte[0];
			StringBuilder builder = new StringBuilder();

			while((length = is.read( inBuffer )) > 0){
				tempBuffer = Arrays.copyOfRange(inBuffer, 0, length);
				outBuffer = Base64.getEncoder().encode( tempBuffer );
				builder.append( new String( outBuffer,"UTF-8" ) );
			}

			fileString = builder.toString();
		} catch(Exception e){
			e.printStackTrace();
		}

		String fileAttachementString1 = "{\"file1\":\""+fileString+"\"}";
//		System.out.println(fileAttachementString1);
		JsonObject jsonObject1 = (new JsonParser()).parse(fileAttachementString1).getAsJsonObject();

		String fileAttachementString2 = "{\"file2\":\""+fileString+"\"}";
//		System.out.println(fileAttachementString2);
		JsonObject jsonObject2 = (new JsonParser()).parse(fileAttachementString2).getAsJsonObject();

		JsonArray jsonArray = new JsonArray();
		jsonArray.add( jsonObject1 );
		jsonArray.add( jsonObject2 );

		JsonObject jsonObject = new JsonObject();
		jsonObject.add( "files", jsonArray );

		return jsonObject.toString();
	}

}

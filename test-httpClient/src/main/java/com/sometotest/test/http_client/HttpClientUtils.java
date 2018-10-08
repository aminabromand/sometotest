package com.sometotest.test.http_client;

import com.sometotest.test.json.JsonTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtils{


	static String postJson(String postUrl, Map<String, String> cookies, Map<String, String> headers, Map<String, String> params) throws IOException{

		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpPost httppost = new HttpPost(postUrl);

		JsonTest jsonTest = new JsonTest();

		String jsonString = jsonTest.getJsonString();
		System.out.println("jsonString: " + jsonString);

		StringEntity postingString = new StringEntity(jsonString);//gson.tojson() converts your pojo to json
		httppost.setEntity(postingString);
		httppost.setHeader("Content-type", "application/json");



		String results = "";

		CloseableHttpResponse response = null;
		response = httpclient.execute(httppost);

		System.out.println("response status code: " + response.getStatusLine().getStatusCode());
		// assertEquals(200, response.getStatusLine().getStatusCode());

		HttpEntity entity = response.getEntity();
		results = writeContentToString(entity);

		return results;
	}

	static String post(String postUrl, Map<String, String> cookies, Map<String, String> headers, Map<String, String> params,
					Map<String, File> files) throws ClientProtocolException, IOException{
		CloseableHttpResponse response = null;
		String results = "nothing";

		CookieStore httpCookieStore = new BasicCookieStore();

		if ( cookies != null ) {
			for (String key : cookies.keySet()) {
				String value = cookies.get( key );
				BasicClientCookie cookie = new BasicClientCookie( key, value );
				cookie.setDomain( "localhost" );
				cookie.setPath( "/vmwebtest/vmweb" );
		//		cookie.setDomain(".mycompany.com");
		//		cookie.setPath("/");
				httpCookieStore.addCookie( cookie );
			}
		}

		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore( httpCookieStore ).build();


		try {

			HttpPost httppost = new HttpPost(postUrl);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (params != null) {
				for (String key : params.keySet()) {
//					StringBody value = new StringBody(params.get(key), ContentType.TEXT_PLAIN);
					StringBody value = new StringBody(params.get(key), ContentType.MULTIPART_FORM_DATA);
					builder.addPart(key, value);
				}
			}

			if (files != null && files.size() > 0) {
				for (String key : files.keySet()) {
					File value = files.get(key);
					FileBody body = new FileBody( value, ContentType.IMAGE_PNG );
					builder.addPart(key, body);
				}
			}

			if (headers != null) {
				for (String key : headers.keySet()) {
					String value = headers.get(key);
					httppost.addHeader(key, value);
				}
			}

			System.out.println( "HttpPost.getURI: " + httppost.getURI() );
			for ( Header header : httppost.getAllHeaders()) {
				System.out.println( "HttpPost.getALLHeaders[x]: " + header.toString() );
			}

			HttpEntity reqEntity = builder.build();
			//reqEntity.writeTo( System.out );
			httppost.setEntity(reqEntity);

			response = httpclient.execute(httppost);

			System.out.println("response status code: " + response.getStatusLine().getStatusCode());
			// assertEquals(200, response.getStatusLine().getStatusCode());

			HttpEntity entity = response.getEntity();
			results = writeContentToString(entity);

		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		finally {

			try {
				if (response != null) {
					response.close();
				}
			} catch (Throwable t) {
				// No-op
			}

			httpclient.close();
		}

		return results;
	}

	public static String get(String getStr) throws IOException,
					ClientProtocolException {
		CloseableHttpResponse response = null;
		InputStream is = null;
		String results = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpGet = new HttpGet(getStr);
			response = httpclient.execute(httpGet);

//			assertEquals(200, response.getStatusLine().getStatusCode());

			HttpEntity entity = response.getEntity();
			results = writeContentToString(entity);


		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		finally {


			try {
				if (response != null) {
					response.close();
				}
			} catch (Throwable t) {
				// No-op
			}

			httpclient.close();
		}

		return results;
	}

	private static String writeContentToString( HttpEntity entity ) throws IOException {
		if (entity != null) {
			InputStream is = null;
			try {
				is = entity.getContent();
				StringWriter writer = new StringWriter();
				IOUtils.copy( is, writer, "UTF-8" );
				return writer.toString();
			} catch( IOException ex ) {
				try {
					if (is != null) {
						is.close();
					}
				} catch (Throwable t) {
					throw ex;
				}
				throw ex;
			}
		} else {
			return "";
		}
	}

	public static Map<String, String> getStandardHeaders() {
		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put( HttpHeaders.ACCEPT_ENCODING, "gzip, deflate" );
		myMap.put( HttpHeaders.ACCEPT_LANGUAGE, "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7" );
		myMap.put( HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36" );
		myMap.put( HttpHeaders.ACCEPT, "*/*" );
		myMap.put( HttpHeaders.REFERER, "http://185.66.66.158/vmwebtest/vmsf/1531725119/client/A78837FE722B13434138152B7DCC947C.cache.html" );
		myMap.put( HttpHeaders.CONNECTION, "keep-alive" );
		return myMap;
	}

}
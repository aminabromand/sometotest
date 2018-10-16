package com.sometotest.test.http_client;

import org.apache.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HttpClientUtilsTest{

	Properties myProperties;

	public HttpClientUtilsTest(){
		if ( myProperties == null ) {
			loadProperties( File.separator + "httpClient.properties" );
		}
	}

	public void doPostSsl() {
		try{
			String host = "localhost";
			int port = 8443;
			String url = "/";


			String result = HttpClientUtils.postSsl( host, port, url );
			System.out.println( "It worked!" );
			System.out.println( "result: " );
			System.out.println( result );
		} catch ( IOException ex ) {
			System.out.println( "It wouldn't work!" );
			ex.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch(KeyStoreException e){
			e.printStackTrace();
		} catch(KeyManagementException e){
			e.printStackTrace();
		}

	}

	public void doPostSecure() {
		try{
			String host = "localhost";
			int port = 8081;
			String url = "/";


			String result = HttpClientUtils.postSecure( host, port, url );
			System.out.println( "It worked!" );
			System.out.println( "result: " );
			System.out.println( result );
		} catch ( IOException ex ) {
			System.out.println( "It wouldn't work!" );
		}

	}

	public void doPostJson() {
		try{
			String url = "http://localhost:8081";
			Map<String, String> cookies = new HashMap<String, String>();
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();

			String result = HttpClientUtils.postJson( url, cookies, headers, params );
			System.out.println( "It worked!" );
			System.out.println( "result: " );
			System.out.println( result );
		} catch ( IOException ex ) {
			System.out.println( "It wouldn't work!" );
		}
	}


	public void doPost() {

		String url = getProperty( "post.url" );

		Map<String, String> cookies = new HashMap<String, String>();
		cookies.put( "LASTUSERNAME", getProperty( "cookies.LASTUSERNAME" ) );
		cookies.put( "LASTUSERDOMAIN", getProperty( "cookies.LASTUSERDOMAIN" ) );
		cookies.put( "JSESSIONID", getProperty( "cookies.JSESSIONID" ) );
		cookies.put( "XSRF-TOKEN", getProperty( "cookies.XSRF-TOKEN" ) );
		cookies.put( "GLog", getProperty( "cookies.GLog" ) );

		Map<String, String> headers = HttpClientUtils.getStandardHeaders();
		headers.put( "Origin", getProperty( "headers.Origin" ) );

		Map<String, String> params = new HashMap<String, String>();
		params.put( "task", getProperty( "params.task" ) );
		params.put( "xst", getProperty( "params.xst" ) );
		params.put( "windowid", getProperty( "params.windowid" ) );
		params.put( "xcpwinid", getProperty( "params.xcpwinid" ) );
		params.put( "actionmoniker", getProperty( "params.actionmoniker" ) );
		params.put( "xsrftoken", getProperty( "params.xsrftoken" ) );

		Map<String, File> files = new HashMap<String, File>();
		System.out.println("files.location1: " + getProperty( "files.location1" ));
		System.out.println("files.name1: " + getProperty( "files.name1" ));
		files.put( "upload_file", getFile( getProperty( "files.location1" ), getProperty( "files.name1" ) ) );

		try{
			String result = HttpClientUtils.post( url, cookies, headers, params, files );
			System.out.println( "It worked!" );
			System.out.println( "result: " );
			System.out.println( result );
		} catch ( IOException ex ) {
			System.out.println( "It wouldn't work!" );
		}

	}


	public Properties loadProperties( String propertiesLocation ){
		myProperties = new Properties();
		InputStream properties_input_stream = this.getClass().getResourceAsStream( propertiesLocation );
		try{
			myProperties.load( properties_input_stream );
			return myProperties;
		} catch( IOException ioex ) {
			return null;
		}
	}

	protected String getProperty( String key ) {
		return myProperties.getProperty( key );
	}

	private File getFile( String resource_location, String resource_name  ){
		try{
			String path = File.separator + resource_location + File.separator + resource_name;
			URL propertiesFileUrl = this.getClass().getResource( path );
			String p = propertiesFileUrl.getPath();
			return new File( p );
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}

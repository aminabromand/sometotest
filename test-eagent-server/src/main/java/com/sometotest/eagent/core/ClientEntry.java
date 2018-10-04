package com.sometotest.eagent.core;

public class ClientEntry{

	private String ipaddress;
	private String port;

	public ClientEntry( String ipaddress, String port ) {
		this.ipaddress = ipaddress;
		this.port = port;
	}

	public String toString() {
		return "Client @" + ipaddress +":" + port;
	}

	public String getURL() {
		return "http://" + ipaddress +":" + port;
	}

}

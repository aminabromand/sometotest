package com.sometotest.eagent.core;

public class ServerEntry{

	private String ipaddress;
	private String port;
	private Boolean registered;

	public ServerEntry( String ipaddress, String port ) {
		this.ipaddress = ipaddress;
		this.port = port;
	}

	public String toString() {
		return "Server @" + ipaddress +":" + port;
	}

	public String getURL() {
		return "http://" + ipaddress +":" + port;
	}

	public Boolean isRegistered(){
		return registered;
	}

	public void setRegistered( Boolean registered ){
		this.registered = registered;
	}
}

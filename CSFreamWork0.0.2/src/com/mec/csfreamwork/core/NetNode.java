package com.mec.csfreamwork.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetNode {
	private String ip;
	private int port;

	public NetNode() throws UnknownHostException {
		this.ip = InetAddress.getLocalHost().getHostAddress();
	}
	
	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

package com.mec.csfreamwork.core;

import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.mec.csfreamwork.action.IActionProcess;

public class Client {
	private static final Gson gson = new Gson();
	private Socket socket;
	private String serverIp;
	private NetNode me;
	private ClientConversation clientConversation;
	private IClientAction clientAction;
	private IActionProcess actionProcess; 
	
	public Client() throws UnknownHostException {
		this(INetConfigue.NETIP, INetConfigue.PORT);
	}

	public Client(String ip, int port) throws UnknownHostException {
			me = new NetNode();
			me.setPort(port);
			serverIp = ip;
	}
	
	public void setActionProcess(IActionProcess actionProcess) {
		this.actionProcess = actionProcess;
	}
	
	IActionProcess getActionProcess() {
		return actionProcess;
	}
	
	NetNode getMe() {
		return me;
	}

	IClientAction getClientAction() {
		return clientAction;
	}

	public void setClientAction(IClientAction clientAction) {
		this.clientAction = clientAction;
	}

	public static Gson getGson() {
		return gson;
	}
	
	public String getClientId() {
		return clientConversation.getId();
	}
	
	public boolean connectToServer() throws ClientActionNotSetException {
		if (clientAction == null) {
			throw new ClientActionNotSetException();
		}
		try {
			socket = new Socket(serverIp, me.getPort());
			clientConversation = new ClientConversation(this, socket);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public void offLine() {
		if (clientAction.confirmOffline()) {
			clientConversation.offLine();
		}
	}
	
	public void toOne(String targetId, String message) {
		clientConversation.toOne(targetId, message);
	}
	
	public void toOthers(String targetId, String message) {
		clientConversation.toOthers(targetId, message);
	}
	
	public void request(String action, String parameter) {
		clientConversation.request(action, parameter);
	}
}

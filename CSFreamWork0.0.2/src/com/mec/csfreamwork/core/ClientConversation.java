package com.mec.csfreamwork.core;

import java.net.Socket;

public class ClientConversation extends Communication {
	private Client client;
	private String id;
	private PeerMessageProcess peerMessageProcess;
	
	public ClientConversation(Client client, Socket socket) {
		super(socket);
		this.client = client;
		peerMessageProcess = new PeerMessageProcess();
		peerMessageProcess.setObject(this);
		new Thread(this).start();
	}

	String getId() {
		return id;
	}
	
	@Override
	protected void dealPeerDrop() {
		client.getClientAction().serverPeerDrop();
	}

	public void dealOutOfRoom(NetMessage message) {
		close();
		client.getClientAction().serverOutOfRoom();
	}
	
	public void dealForceDown(NetMessage message) {
		close();
		client.getClientAction().serverForceDrop();
	}

	public void dealWhoAreYou(NetMessage message) {
		String mess = client.getMe().getIp() + ":" + System.currentTimeMillis();
		mess += ("#" + mess.hashCode());
		sendMessage(new NetMessage().setCommand(ECommand.I_AM)
					.setParameter(mess));
	}
	
	public void dealOnlinePass(NetMessage message) {
		String parameter = message.getParameter();
		id = parameter;
		client.getClientAction().afterConnectToServer();
	}
	
	public void dealOffline(NetMessage message) {
		close();
		client.getClientAction().afterOffLine();
	}
	
	public void dealToOne(NetMessage message) {
		String parameter = message.getParameter();
		Interactive interactive = Client.getGson().fromJson(parameter, Interactive.class); 
		client.getClientAction().dealTalkToOne(interactive.getSourceId(), interactive.getMessage());
	}

	public void dealToOthers(NetMessage message) {
		String parameter = message.getParameter();
		Interactive interactive = Client.getGson().fromJson(parameter, Interactive.class); 
		client.getClientAction().dealTalkToOthers(interactive.getSourceId(), interactive.getMessage());
	}
	
	public void dealResponce(NetMessage message) {
		String action = message.getAction();
		String parameter = message.getParameter();
		try {
			client.getActionProcess().dealResponse(action, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void dealMessage(NetMessage message) {
		peerMessageProcess.fireMessageProcess(message);
	}

	void offLine() {
		client.getClientAction().offLine();
		sendMessage(new NetMessage().setCommand(ECommand.OFFLINE));
	}

	void toOne(String targetId, String message) {
		Interactive interactive = new Interactive(id,targetId,message);
		sendMessage(new NetMessage().setCommand(ECommand.TO_ONE)
					.setParameter(Client.getGson().toJson(interactive)));
	}

	void toOthers(String targetId, String message) {
		Interactive interactive = new Interactive(id,targetId,message);
		sendMessage(new NetMessage().setCommand(ECommand.TO_OTHERS)
				.setParameter(Client.getGson().toJson(interactive)));
	}

	public void request(String action, String parameter) {
		sendMessage(new NetMessage().setCommand(ECommand.REQUEST)
				.setAction(action).setParameter(parameter));
	}

}

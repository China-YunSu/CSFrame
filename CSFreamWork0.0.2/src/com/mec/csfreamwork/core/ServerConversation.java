package com.mec.csfreamwork.core;

import java.net.Socket;

public class ServerConversation extends Communication{
	private Server server;
	private String id;
	private PeerMessageProcess peerMessageProcess;
	
	ServerConversation(Server server,Socket socket) {
		super(socket);
		this.server = server;
		peerMessageProcess = new PeerMessageProcess();
		peerMessageProcess.setObject(this);
		new Thread(this).start();
	}
	
	public String getId() {
		return id;
	}

	@Override
	protected void dealPeerDrop() {
		server.getTempClientPool().removeClient(this);
		server.getClientPool().removeClient(this);
		server.publishMessage("客戶端[" + id + "]异常掉线");
	}

	public void dealIAm(NetMessage message) {
		String parameter = message.getParameter();
		enSureOnline(parameter);
	}
	
	public void dealOffline(NetMessage message) {
		server.getClientPool().removeClient(this);
		sendMessage(new NetMessage().setCommand(ECommand.OFFLINE));
		server.publishMessage("客户端["+ id + "]下线!");
		close();
	}
	
	public void dealToOne(NetMessage message) {
		String parameter = message.getParameter();
		Interactive interactive = Server.getGson().fromJson(parameter, Interactive.class);
		server.toOne(interactive);
	}
	
	public void dealToOthers(NetMessage message) {
		String parameter = message.getParameter();
		Interactive interactive = Server.getGson().fromJson(parameter, Interactive.class);
		server.toOthers(interactive);
	}

	public void dealRequest(NetMessage message) {
		String parameter = message.getParameter();
		try {
			Object res;
			res = server.getActionProcess().dealRequest(message.getAction(), parameter);
			sendMessage(new NetMessage().setCommand(ECommand.RESPONCE)
					.setAction(message.getAction())
					.setParameter(Server.getGson().toJson(res)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void dealMessage(NetMessage message) {
		peerMessageProcess.fireMessageProcess(message);
	}
	
	private void enSureOnline(String parameter) {
		server.getTempClientPool().removeClient(this);
		if (!cheakClient(parameter)) {
			close();
			return;
		}
		// 生成id号
		//加入clientpool
		String ip = parameter.substring(0, parameter.indexOf(':'));
		String id = ip + System.currentTimeMillis();
		this.id = id;
		server.getClientPool().addClient(this);
		sendMessage(new NetMessage().setCommand(ECommand.ONLINE_PASS)
					.setParameter(id));
		server.publishMessage("客户端["+ id + "]上线!");
	}
	
	private boolean cheakClient(String parameter) {
		int index = parameter.indexOf('#');
		String hashCode = parameter.substring(index + 1);
		String para = parameter.substring(0, index);
		if (Integer.valueOf(hashCode) == para.hashCode()) {
			return true;
		}
		return false;
	}

	void outOfRoom() {
		sendMessage(new NetMessage().setCommand(ECommand.OUT_OF_ROOM));
		close();
	}   


	void forceDown() {
		sendMessage(new NetMessage().setCommand(ECommand.FORCE_DOWN));
		close();
	}

	void whoAreYou() {
		sendMessage(new NetMessage().setCommand(ECommand.WHO_ARE_YOU));
	}

	public void toOne(Interactive interactive) {
		sendMessage(new NetMessage().setCommand(ECommand.TO_ONE)
					.setParameter(Server.getGson().toJson(interactive)));
	}

	public void toOthers(Interactive interactive) {
		sendMessage(new NetMessage().setCommand(ECommand.TO_OTHERS)
				.setParameter(Server.getGson().toJson(interactive)));
	}

}

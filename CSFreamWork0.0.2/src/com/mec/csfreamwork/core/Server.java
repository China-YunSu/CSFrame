package com.mec.csfreamwork.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mec.csfreamwork.action.IActionProcess;

public class Server implements Runnable, ISpeaker{
	private static final Gson gson = new Gson();
	private ServerSocket server;
	private NetNode me;
	private volatile boolean goon;
	private int maxConnectCount;
	private static TempClientPool tempClientPool;
	private static ClientPool clientPool;
	private static List<IListener> listenerList;
	private IActionProcess actionProcess;

	static {
		tempClientPool = new TempClientPool();
		clientPool = new ClientPool();
		listenerList = new ArrayList<IListener>();
	}
	
	public Server() {
		try {
			me = new NetNode();
			me.setPort(INetConfigue.PORT);
			maxConnectCount = INetConfigue.maxConnectCount;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}	
	
	public static Gson getGson() {
		return gson;
	}


	public void setActionProcess(IActionProcess actionProcess) {
		this.actionProcess = actionProcess;
	}
	
	IActionProcess getActionProcess() {
		return actionProcess;
	}
	
	public void startUp() {
		try {
			if (goon == true) {
				publishMessage("服务器已启动！");
			}
			publishMessage("开始启动服务器.......");
			server = new ServerSocket(me.getPort());
			publishMessage("服务器启动成功......");
			goon = true;
			new Thread(this).start();
		} catch (IOException e) {
			close();
		}
		
	}

	TempClientPool getTempClientPool() {
		return tempClientPool;
	}
	
	ClientPool getClientPool() {
		return clientPool;
	}
	
	@Override
	public void run() {
		publishMessage("请求连接......");
		while (goon) {
			try {
				Socket socket = server.accept();
				ServerConversation client = new ServerConversation(this, socket);
				if (tempClientPool.getCount() +  clientPool.getCount() >= maxConnectCount) {
					client.outOfRoom();
					return;
				}
				tempClientPool.addClient(client);
				client.whoAreYou();
			} catch (IOException e) {
				close();
			}
		}
		close();
	}
	
	private void close() {
		goon = false;
		if (server != null && !server.isClosed()) {
			try {
				server.close();
			} catch (IOException e) {
			} finally {
				server = null;
			}
		}
	}
	
	public void shutDown() {
		if (tempClientPool.getCount() > 0 ||
				clientPool.getCount() > 0) {
			publishMessage("尚有客户端在线，不能宕机");
			return;
		}
		
		close();
		publishMessage("服务器已宕机");
	}
	
	public void forceDown() {
		while (!tempClientPool.isEmpty()) {
			ServerConversation client = tempClientPool.popClient();
			client.forceDown();
		}
		
		List<ServerConversation> clients = clientPool.getAllClients();
		for (ServerConversation client : clients) {
			client.forceDown();
			clientPool.removeClient(client);
		}
		
		shutDown();
	}

	@Override
	public void publishMessage(String message) {
		for (IListener listener : listenerList) {
			listener.processMessage(message);
		}
	}

	@Override
	public void removeListener(IListener listener) {
		listenerList.remove(listener);
	}

	@Override
	public void addListener(IListener listener) {
		listenerList.add(listener);
	}

	public void toOne(Interactive interactive) {
		String targetId = interactive.getTargetId();
		ServerConversation client = clientPool.getClient(targetId);
		client.toOne(interactive);
	}

	public void toOthers(Interactive interactive) {
		String sourceId = interactive.getSourceId();
		List<ServerConversation> clients = clientPool.getClientsExcept(sourceId);
		for (ServerConversation client : clients) {
			client.toOthers(interactive);
		}
	}

	public boolean isStartUp() {
		return goon == true;
	}

	

}

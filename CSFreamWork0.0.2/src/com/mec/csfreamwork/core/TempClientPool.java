package com.mec.csfreamwork.core;

import java.util.LinkedList;

public class TempClientPool {
	private static LinkedList<ServerConversation> clients;

	static {
		clients = new LinkedList<ServerConversation>();
		
	}
	
	public TempClientPool() {
	}

	public void addClient(ServerConversation client) {
		if (!isHad(client)) {
			clients.add(client);
		}
	}

	public void removeClient(ServerConversation client) {
		if (isHad(client)) {
			clients.remove(client);
		}
	}
	
	public boolean isHad(ServerConversation client) {
		return clients.contains(client);
	}
	
	public int getCount() {
		return clients.size();
	}
	
	public boolean isEmpty() {
		return clients.isEmpty();
	}
	
	public ServerConversation popClient() {
		return clients.removeLast();
	}
	
	public ServerConversation removeFirstClient() {
		return clients.removeFirst();
	}
}


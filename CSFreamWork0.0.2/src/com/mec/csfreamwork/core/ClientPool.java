package com.mec.csfreamwork.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientPool {
	private static Map<String, ServerConversation> clientPool;
	
	static {
		clientPool = new HashMap<String, ServerConversation>();
	}
	
	public ClientPool() {
	}
	
	public void addClient(ServerConversation client) {
		if (!clientPool.containsKey(client.getId())) {
			clientPool.put(client.getId(), client);
		}
	}
	
	public void removeClient(ServerConversation client) {
		clientPool.remove(client.getId());
	}

	public void removeClient(String id) {
		if (clientPool.containsKey(id)) {
			clientPool.remove(id);
		}
	}
	
	public List<ServerConversation> getClientsExcept(String id) {
		List<ServerConversation> clients = new ArrayList<ServerConversation>();
		Set<String> keys = clientPool.keySet();
		for (String key : keys) {
			if (!key.equals(id)) {
				clients.add(clientPool.get(key));
			}
		}
		
		return clients;
	}
	
	public ServerConversation getClient(String id) {
		if (clientPool.containsKey(id)) {
			return clientPool.get(id);
		}
		return null;
	}
	
	public List<ServerConversation> getAllClients() {
		return getClientsExcept(null);
	}
	
	public int getCount() {
		return clientPool.size();
	}
	
}

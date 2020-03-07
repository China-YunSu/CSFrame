package com.mec.csfreamwork.core;

public class ClientActionAdpter implements IClientAction{

	public ClientActionAdpter() {
	}

	@Override
	public void afterOffLine() {
	}

	
	@Override
	public void afterConnectToServer() {
	}


	@Override
	public void serverOutOfRoom() {
	}

	
	@Override
	public boolean confirmOffline() {
		return false;
	}

	@Override
	public void serverPeerDrop() {
		
	}

	@Override
	public void serverForceDrop() {
		
	}

	@Override
	public void dealTalkToOne(String sourceId, String message) {
	}

	@Override
	public void offLine() {
	}

	@Override
	public void dealTalkToOthers(String sourceId, String message) {
	}

}

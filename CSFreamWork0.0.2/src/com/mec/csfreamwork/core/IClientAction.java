package com.mec.csfreamwork.core;

public interface IClientAction {
	void offLine();
	void afterOffLine();
	void dealTalkToOne(String sourceId, String message);
	void dealTalkToOthers(String sourceId, String message);
	void afterConnectToServer();
	void serverOutOfRoom();
	void serverPeerDrop();
	boolean confirmOffline();
	void serverForceDrop();
}

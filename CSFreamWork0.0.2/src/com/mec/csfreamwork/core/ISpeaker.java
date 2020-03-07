package com.mec.csfreamwork.core;

public interface ISpeaker {
	void removeListener(IListener listener);
	void addListener(IListener listener);
	void publishMessage(String message);
}

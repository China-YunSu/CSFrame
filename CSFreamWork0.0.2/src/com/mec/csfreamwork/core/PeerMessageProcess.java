package com.mec.csfreamwork.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PeerMessageProcess {
	private Object object;
	private Class<?> klass;
	
	PeerMessageProcess() {
	}
	
	void setObject(Object object) {
		this.object = object;
		this.klass = object.getClass();
	}
	
	String toLowerCastException(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	void fireMessageProcess(NetMessage message) {
		String command = message.getCommand().toString();
		String[] parts = command.split("_");
		
		StringBuffer actionName = new StringBuffer("deal");
		for (String part : parts) {
			actionName.append(toLowerCastException(part));
		}
		
		try {
			Method method = klass.getMethod(actionName.toString(), NetMessage.class);
			method.invoke(object, message);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}

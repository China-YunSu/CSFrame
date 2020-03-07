package com.mec.csfreamwork.action;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MakeArgument {
	private static final Gson gson = new GsonBuilder().create();
	private Map<String, String> argumentPool;
	private static final Type type = new TypeToken<Map<String,String>>(){}.getType();

	public MakeArgument() {
		argumentPool = new LinkedHashMap<String, String>();
	}
	
	public MakeArgument(String paramter) {
		argumentPool = gson.fromJson(paramter, type);
	}
	
	public void addArgument(String ArgumentName, Object value) {
		String argument = gson.toJson(value);
		argumentPool.put(ArgumentName, argument);
	}
	
	public Object getArgument(String ArgumentName, Class<?> type) {
		String argument = argumentPool.get(ArgumentName);
		return gson.fromJson(argument, type);
	}

	public Object getArgument(String ArgumentName, Type type) {
		String argument = argumentPool.get(ArgumentName);
		return gson.fromJson(argument, type);
	}

	@Override
	public String toString() {
		return gson.toJson(argumentPool);
	}
}

package com.mec.csfreamwork.action;

public class MakeType {
	
	public MakeType() {
	}
	
	public static Class<?> toType(String type) {
		if (type.equalsIgnoreCase("String")) {
			return String.class;
		}
		if (type.equalsIgnoreCase("int")) {
			return int.class;
		}
		if (type.equalsIgnoreCase("long")) {
			return long.class;
		}
		if (type.equalsIgnoreCase("short")) {
			return short.class;
		}
		if (type.equalsIgnoreCase("double")) {
			return double.class;
		}
		if (type.equalsIgnoreCase("float")) {
			return float.class;
		}
		if (type.equalsIgnoreCase("char")) {
			return char.class;
		}
		if (type.equalsIgnoreCase("byte")) {
			return byte.class;
		}
		
		try {
			return Class.forName(type);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

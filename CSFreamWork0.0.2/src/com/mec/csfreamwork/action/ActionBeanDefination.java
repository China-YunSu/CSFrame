package com.mec.csfreamwork.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ActionBeanDefination {
	private Class<?> klass;
	private Object object;
	private Method method;
	private List<String> parameterList;
	private int index = 0;
	
	ActionBeanDefination() {
		parameterList = new ArrayList<String>();
	}

	boolean hasNext() {
		boolean hasNext = index < parameterList.size();
		if (hasNext == false) {
			index = 0;
		}
		
		return hasNext;
	}
	
	 String next() {
		return parameterList.get(index++);
	}
	
	Class<?> getKlass() {
		return klass;
	}

	void setKlass(Class<?> klass) {
		this.klass = klass;
	}

	Object getObject() {
		return object;
	}

	void setObject(Object object) {
		if (this.object == null) {
			this.object = object;
		}
	}

	Method getMethod() {
		return method;
	}

	void setMethod(Method method) {
		this.method = method;
	}
	
	void addParameter(String parameter) {
		if (!parameterList.contains(parameter)) {
			parameterList.add(parameter);
		}
	}
	
}

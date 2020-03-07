package com.mec.csfreamwork.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.mec.csfreamwork.action.ActionBeanDefination;
import com.mec.csfreamwork.action.MakeType;
import com.mec.util.XMLParse;

public class ActionBeanFactory {
	private static Map<String, ActionBeanDefination> actionPool;
	
	static {
		actionPool = new HashMap<String, ActionBeanDefination>();
	}
	
	public ActionBeanFactory() {
	}
	
	public static void setObject(String action, Object object) {
		if (actionPool.containsKey(action)) {
			actionPool.get(action).setObject(object);
		}
	}
	
	static ActionBeanDefination getAction(String action) {
		if (actionPool.containsKey(action)) {
			return actionPool.get(action);
		}
		return null;
	}
	
	public static void scannActionMapping(String XMLPath) {
		new XMLParse() {
			
			@Override
			public void dealElement(Element element, int index) {
				ActionBeanDefination actionBean = new ActionBeanDefination();
				String className = element.getAttribute("class");
				String action = element.getAttribute("action");
				String methodName = element.getAttribute("method");
				try {
					Class<?> klass = Class.forName(className);
					actionBean.setKlass(klass);
					Method method = getMethod(actionBean, element, methodName, klass);
					actionBean.setMethod(method);
				} catch (Exception e) {
					e.printStackTrace();
				}
				actionPool.put(action, actionBean);
			}
		}.parseTagByDocument(XMLParse.getDocument(XMLPath), "action");
	}
	
	public static void scannActionClass(Class<?> klass) {
		Method[] methods = klass.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Action.class)) {
				ActionBeanDefination actionBean = new ActionBeanDefination();
				actionBean.setKlass(klass);
				actionBean.setMethod(method);
				Action anno = method.getAnnotation(Action.class);
				String action = anno.action();
				String[] parameterNames = anno.parameterName();
				for (String parameterName : parameterNames) {
					actionBean.addParameter(parameterName);
				}
				actionPool.put(action, actionBean);
			}
		}
	}

	public static Method getMethod(ActionBeanDefination actionBean, Element element,
							String methodName, Class<?> klass) throws Exception {
		List<String> typeNames = new ArrayList<String>();
		new XMLParse() {
			
			@Override
			public void dealElement(Element element, int index) {
				String typeName =  element.getAttribute("type");
				typeNames.add(typeName);
				String parameter = element.getAttribute("name");
				actionBean.addParameter(parameter);
			}
		}.parseTagByElement(element, "parameter");
		
		Class<?>[] types = new Class[typeNames.size()];
		for (int i = 0; i < typeNames.size(); ++i) {
			types[i] = MakeType.toType(typeNames.get(i));
		}
		Method method = klass.getMethod(methodName,types);
		
		return method;
	}

	
}

package com.mec.csfreamwork.action;

import java.lang.reflect.Method;

import com.mec.csfreamwork.core.Server;

public class ActionProcess implements IActionProcess{

	public ActionProcess() {
	}

	@Override
	public Object dealRequest(String action, String parameter) throws Exception {
		ActionBeanDefination actionBean = ActionBeanFactory.getAction(action);
		if (actionBean == null) {
			throw new NoSuchActionException("未找到方法["+ action + "]");
		}
		Method method = actionBean.getMethod();
		Class<?>[] types = method.getParameterTypes();
		Object object = actionBean.getObject();
		if (object == null) {
			object = actionBean.getKlass().newInstance();
			actionBean.setObject(object);
		}
		MakeArgument arguments = new MakeArgument(parameter);
		Object[] values = new Object[method.getParameterCount()];
		for (int i = 0; actionBean.hasNext(); ++i) {
			String parameterName =  actionBean.next();
			values[i] = arguments.getArgument(parameterName, types[i]);
		}
		
		return method.invoke(object, values);
	}

	@Override
	public void dealResponse(String action, String parameter) throws Exception {
		ActionBeanDefination actionBean = ActionBeanFactory.getAction(action);
		if (actionBean == null) {
			throw new NoSuchActionException("未找到方法["+ action + "]");
		}
		Method method = actionBean.getMethod();
		Object object = actionBean.getObject();
		Class<?>[] types = method.getParameterTypes();
		int parameterCount = method.getParameterCount();
		Object[] values = new Object[parameterCount];
		for (int i = 0 ; i < parameterCount; ++i) {
			values[i] = Server.getGson().fromJson(parameter, types[i]);
		}
		method.invoke(object, values);
	}


}

package com.mec.csfreamwork.action;

public interface IActionProcess {
	void dealResponse(String action, String parameter) throws Exception;
	Object dealRequest(String action, String parameter) throws Exception;
}

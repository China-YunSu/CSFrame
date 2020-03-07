package com.mec.csfreamwork.core;

public class NetMessage {
	private ECommand command;
	private String parameter;
	private String action;
	
	public NetMessage(String paramter) {
		int index = paramter.indexOf(':');
		this.command = ECommand.valueOf(paramter.substring(0, index));
		String message  = paramter.substring(index + 1);
		index =  message.indexOf(":");
		this.action = message.substring(0, index);
		this.parameter = message.substring(index + 1);
	}

	public NetMessage() {
	}

	public ECommand getCommand() {
		return command;
	}

	public NetMessage setCommand(ECommand command) {
		this.command = command;
		return this;
	}

	public String getParameter() {
		return parameter;
	}

	public NetMessage setParameter(String parameter) {
		this.parameter = parameter;
		return this;
	}

  	public String getAction() {
		return action;
	}

	public NetMessage setAction(String action) {
		this.action = action;
		return this;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer()
				.append(command).append(":")
				.append(action == null ? "" : action).append(":").
				 append(parameter);
		return str.toString();
	}

}

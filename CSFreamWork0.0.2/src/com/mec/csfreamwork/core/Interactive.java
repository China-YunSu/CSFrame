package com.mec.csfreamwork.core;

public class Interactive {
	private String sourceId;
	private String targetId;
	private String message;

	
	/**
	 * @param sourceId
	 * @param targetId
	 * @param message
	 */
	public Interactive(String sourceId, String targetId, String message) {
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.message = message;
	}

	public String getSourceId() {
		return sourceId;
	}

	public Interactive setSourceId(String sourceId) {
		this.sourceId = sourceId;
		return this;
	}

	public String getTargetId() {
		return targetId;
	}

	public Interactive setTargetId(String targetId) {
		this.targetId = targetId;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Interactive setMessage(String message) {
		this.message = message;
		return this;
	}

}

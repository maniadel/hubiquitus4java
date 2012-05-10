package org.hubiquitus.hapi.hStructures;

public enum ResultStatus {
	
	NO_ERROR (0),
	TECH_ERROR (1),
	CHAN_INACTIVE (2),
	NOT_CONNECTED (3),
	CHAN_INVALID (4),
	NOT_AUTHORIZED (5),
	MISSING_ATTR (6),
	INVALID_ATTR (7),
	ADMIN_REQUIRED (8),
	NOT_AVAILABLE (9),
	EXEC_TIMEOUT (10);
	
	private int value;
	
	private ResultStatus(int value) {
		this.value = value;
	}
	
	/**
	 * Method to get the value of ErrorCode
	 * @return ErrorCode's value
	 */
	public int value() {
		return value;
	}
	
	/**
	 * Get constant for value
	 * @param value
	 * @return
	 */
	public static ResultStatus constant(int value) {
		ResultStatus [] _values = ResultStatus.values();
		return _values[value];
	}
}
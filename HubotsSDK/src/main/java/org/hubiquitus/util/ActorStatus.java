package org.hubiquitus.util;

public enum ActorStatus {
	
	CREATED (0), 			// just created, not connected througth to the hclient API
	STARTED (1), 			// connected to the hAPI but not ready
	INITIALIZED (2),		// connected and initialized
	READY (3); 				// connected and ready. Adapters are all initialized and started
	
	private int value;
	
	private ActorStatus(int value) {
		this.value = value;
	}
	
	/**
	 * @return int equivalent.
	 */
	public int value() {
		return value;
	}
	
	/**
	 * Get constant for value
	 * @param value
	 * @return
	 */
	public static ActorStatus constant(int value) {
		ActorStatus [] _values = ActorStatus.values();
		return _values[value];
	}
}

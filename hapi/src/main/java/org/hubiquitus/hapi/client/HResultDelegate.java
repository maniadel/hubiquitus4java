package org.hubiquitus.hapi.client;

import org.hubiquitus.hapi.hStructures.HResult;

public interface HResultDelegate {
	
	/**
	 * Called on command result
	 */
	public void onResult(HResult result);
	
}

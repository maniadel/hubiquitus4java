package org.hubiquitus.hapi.client;

import org.hubiquitus.hapi.hStructures.HMessage;

public interface HResultDelegate {
	
	/**
	 * Called on hMeesage result 
	 */
	public void onResult(HMessage message);
	
}

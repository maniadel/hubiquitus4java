package org.hubiquitus.hfacebook.publics;

import org.json.JSONObject;

public interface HFacebookListners {

	/**
	 * called when a like status is received throw the Facebook API
	 * @param likeStatus
	 */
	void onStatusLike(JSONObject item);

	/**
	 * called when others  status are received throw the Facebook API 'Page not found'
	 * @param likeStatus
	 */	
	void onOthersLikeStatus(JSONObject item);

	
	
	
}

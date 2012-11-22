/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */

package org.hubiquitus.hubotsdk.adapters.HtwitterAdapter;
import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @version 0.5
 * Describes a twitter payload
 */
public class HTweet extends JSONObject{

	final Logger log = LoggerFactory.getLogger(HTweet.class);

	public HTweet() {
		super();
	};

	public HTweet(JSONObject jsonObj) throws JSONException{
		super(jsonObj, JSONObject.getNames(jsonObj));
	}


	/* Getters & Setters */
	
	
	/**
	 * @return The text of the tweet 
	 */
	public String getText() {
		String text;
		try {
			text = this.getString("text");
		} catch (Exception e) {
			text = null;
		}
		return text;
	}

	/**
	 * Set the text of the tweet.
	 * @param text
	 * @throws MissingAttrException 
	 */
	public void setText(String text) throws MissingAttrException {
		try {
			if(text == null || text.length()<= 0) {
				throw new MissingAttrException("text");
			} else {
				this.put("text", text);
			}
		} catch (JSONException e) {
			log.error("Can't update the text attribut",e);
		}
	}
	
	
	
	
	/**
	 * @return The source of the tweet, i.e. an html link to the application used to generate the tweet
	 */

	public String getSource() {
		String source;
		try {
			source = this.getString("source");
		} catch (Exception e) {
			source = null;			
		}
		return source;
	}

	/**
	 * Set the source of the tweet.
	 * @param source
	 * @throws MissingAttrException 
	 */
	public void setSource(String source) throws MissingAttrException {
		try {
			if(source == null || source.length()<= 0 ) {
				throw new MissingAttrException("source");
			} else {
				this.put("source", source);
			}
		} catch (JSONException e) {
			log.error("Can't update the source attribut",e);
		}


	}
	
	/**
	 * @return The author of the tweet. a copy.
	 */
	
	public JSONObject getAuthor() {
		JSONObject jsonObj;
		HTweetAuthor tweetAuthor = null;
		try {
			jsonObj  = this.getJSONObject("author");
			if(jsonObj != null){
				tweetAuthor = new HTweetAuthor(jsonObj);
			}
		} catch (JSONException e) {
			log.debug(e.toString());
		}
		return tweetAuthor;
	}

		
	/**
	 * Set the author of the tweet.
	 * @param tweetAuthor
	 * @throws MissingAttrException 
	 */
	public void setAuthor(HTweetAuthor tweetAuthor) throws MissingAttrException {
		try {
			if(tweetAuthor == null || tweetAuthor.length()<=0) {
				throw new MissingAttrException("author");
			} else {
				this.put("author", tweetAuthor);
			}
		} catch (JSONException e) {
			log.error("Can't update the author attribute",e);
		}
	}
	

	
	
	
	/**
	 * @return The id of the tweet in twitter’s domain
	 */
	public long getId() {
		long id;
		try {
			id = this.getLong("Id");
		} catch (Exception e) {
			id = 0;			
		}
		return id;
	}
	
	/**
	 * Set the id of the tweet if twitter's domain.
	 * @param id
	 */
	public void setId(long id) {
		try {
				this.put("Id", id);
		} catch (JSONException e) {
			log.error("Can't update the Id Tweet  attribut",e);
		}
	}
	
}


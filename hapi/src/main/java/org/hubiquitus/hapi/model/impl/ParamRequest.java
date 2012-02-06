/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hubiquitus.hapi.model.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Parameter for data base request
 * @author o.chauvie
 *
 */
public class ParamRequest {

	/**
	 * List of {@link KeyRequest}
	 */
	private List<KeyRequest> params;
	/**
	 * List of {@link SortRequest}
	 */
	private List<SortRequest> sorts;
	/**
	 * List of {@link GroupRequest}
	 */
	private List<GroupRequest> groups;
	
	private Limit limit;
	
	/**
	 * Do we have to get the count of the result ?
	 * The count is not the size of the result list.
	 * This is the the number of objects matching the query and it does not take limit/skip into consideration.
	 */
	private boolean count;
	
	/**
	 * Constructor
	 */
	public ParamRequest() {
		super();
		this.params = new ArrayList<KeyRequest>();
		this.sorts = new ArrayList<SortRequest>();
		this.groups = new ArrayList<GroupRequest>();
		this.limit = new Limit(); 
	}
	
	/**
	 * get the ParamRequest in xml format
	 * @return the xml
	 */
	public String toXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<param>");
		for(int i=0; i<params.size(); i++){
			if(params.get(i).toXML() != null){
				xml.append(params.get(i).toXML());
			}
		}	
		for(int i=0; i<sorts.size(); i++){
			if(sorts.get(i).toXML() != null){
				xml.append(sorts.get(i).toXML());
			}
		}
		xml.append(limit.toXML());
		xml.append("<count>" + count + "</count>");
		xml.append("</param>");
		
		return xml.toString();
	}
	
	/**
	 * @return the limit
	 */
	public Limit getLimit() {
		return limit;
	}


	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	/**
	 * Add a {@link KeyRequest}
	 * @param keyRequest the {@link KeyRequest} to add
	 */
	public void addKeyRequest(KeyRequest keyRequest){
		params.add(keyRequest);
	}
	
	/**
	 * getter params
	 * @return the params
	 */
	public List<KeyRequest> getParams() {
		return params;
	}

	/**
	 * setter params
	 * @param params the params to set
	 */
	public void setParams(List<KeyRequest> params) {
		this.params = params;
	}
	
	/**
	 * Add a {@link SortRequest}
	 * @param sortRequest the {@link SortRequest} to add
	 */
	public void addSortRequest(SortRequest sortRequest){
		sorts.add(sortRequest);
	}
	
	/**
	 * getter sorts
	 * @return the sorts
	 */
	public List<SortRequest> getSorts() {
		return sorts;
	}

	/**
	 * setter params
	 * @param params the params to set
	 */
	public void setSorts(List<SortRequest> sorts) {
		this.sorts = sorts;
	}
	
	
	/**
	 * @return the count
	 */
	public boolean isCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(boolean count) {
		this.count = count;
	}

	@Override
	public String toString(){
		String objet = "\tKeys :\n";
		for(int i=0; i<params.size(); i++){
			objet = objet + "\t\tKey : \n";
			objet = objet + params.get(i).toString();
			objet = objet + "\n";
		}
		for(int i=0; i<sorts.size(); i++){
			objet = objet + "\t\tKey : \n";
			objet = objet + sorts.get(i).toString();
			objet = objet + "\n";
		}
		objet = objet + "\t\tKey : \n";
		objet = objet + limit.toString();
		objet = objet + "\n";
		return objet;
	}
	
	/**
	 * Create a copy of this object
	 * @return the copy
	 */
	public ParamRequest createCopy(){
		ParamRequest paramRequestCopy = new ParamRequest();
		for(int i=0; i<this.getParams().size(); i++){
			KeyRequest keyRequest = this.getParams().get(i).createCopy();
			paramRequestCopy.addKeyRequest(keyRequest);
		}
		return paramRequestCopy;
	}

	
}

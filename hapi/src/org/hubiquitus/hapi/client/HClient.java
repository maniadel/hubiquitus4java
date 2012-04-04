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

package org.hubiquitus.hapi.client;


/**
 * @author j.desousag
 * @version 0.3
 * Class HClient
 */
public class HClient {

	private HOption options;
	
	private HCallback callback;
		
	public HClient(String publisher,String password ,HCallback callback ,HOption options) {
		
		
		if(options == null) {
			this.options = new HOption();
		} else {
			this.options = options;
		}
		this.options.setPublisher(publisher);
		this.options.setPassword(password);
		this.callback = callback;
		
	}
	
	
}

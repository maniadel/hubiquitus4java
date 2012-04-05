package org.hubiquitus.hapi.test;
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


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hubiquitus.hapi.util.HUtil;
import org.hubiquitus.hapi.client.HOption;
import org.junit.Test;

/**
 * @author j.desousag
 * @version 0.3
 * Unit test for HUtil
 */

public class TestUnit {

	
	/**
	 * Unit test for getDomain
	 */
	@Test
	public void getDomain_ValidDomain_OK() {
		List<String> var_test = Arrays.asList("XMPP://domain:port/namespace");
		HOption option = new HOption();
		option.setPublisher("logintest");
		option.setEndpoints(var_test);
		String res_test = HUtil.getDomain(option);
		System.out.println(res_test);
		assertNotNull(res_test);
	}
	
	@Test
	public void getDomain_InvalidDomain_OK() {
		List<String> var_test = Arrays.asList("domain:port");
		HOption option = new HOption();
		option.setPublisher("logintest");
		option.setEndpoints(var_test);
		String res_test = HUtil.getDomain(option);
		System.out.println(res_test);
		assertNotNull(res_test);
	}

	/**
	 * Unitary test for getPort
	 */
	
	@Test
	public void getPort_ValidPort_OK() {
		List<String> var_test = Arrays.asList("XMPP://domain:2023/namespace");
		HOption option = new HOption();
		option.setEndpoints(var_test);
		String res_test = HUtil.getPort(option);
		System.out.println(res_test);
		assertNotNull(res_test);
	}
	
	@Test
	public void getPort_INValidPort_OK() {
		List<String> var_test = Arrays.asList("XMPP://domain");
		HOption option = new HOption();
		option.setEndpoints(var_test);
		String res_test = HUtil.getPort(option);
		System.out.println(res_test);
		assertNotNull(res_test);
	}
}

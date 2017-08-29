package com.ktiteng.web.rest.service;

import org.junit.Test;

import com.ktiteng.web.rest.RestMockFramework;

public class ResourceRestServiceTest extends RestMockFramework {

	@Test
	public void getParents() throws Exception {
		invokeGet("/resource/parents");
	}
}

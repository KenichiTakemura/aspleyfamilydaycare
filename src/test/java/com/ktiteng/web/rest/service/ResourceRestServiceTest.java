package com.ktiteng.web.rest.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.manager.EntityManager;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.web.rest.RestMockFramework;

public class ResourceRestServiceTest extends RestMockFramework {
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;

	@Test
	public void getParents() throws Exception {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst1", "clast1", "Q00085", p1);
		MockHttpResponse response = invokeGet("/resource/parents");
		log.info("{}", response.getContentAsString());
		List<Parent> parents = gson.fromJson(response.getContentAsString(), EntityManager.getParentType());
		assertEquals("test1@gmail.com", parents.get(0).getEmailAddress());
	}
}

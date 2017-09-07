package com.ktiteng.web.rest.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.type.JavaType;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.manager.EntityManager;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.web.rest.RestMockFramework;

public class ResourceRestServiceTest extends RestMockFramework {
	@Inject
	@Log
	private Logger log;

	@Test
	public void getParents() throws Exception {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst1", "clast1", "Q00085", p1);
		MockHttpResponse response = invokeGet("/resource/parents");
		log.info("{}", response.getContentAsString());

		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Parent.class);

		List<Parent> parents = mapper.readValue(response.getContentAsString(), type);
		assertEquals("test1@gmail.com", parents.get(0).getEmailAddress());

		response = invokeGet(addQueryParam("/resource/parents", "p", p1.getId()));
		log.info("{}", response.getContentAsString());
		Parent parent = mapper.readValue(response.getContentAsString(), Parent.class);
		assertEquals("test1@gmail.com", parent.getEmailAddress());
	}
}

package com.ktiteng.web.rest.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JavaType;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.web.rest.RestMockFramework;

public class ResourceRestServiceTest extends RestMockFramework {
	@Inject
	@Log
	private Logger log;

	private MockHttpResponse response;

	@Test
	public void getParents() throws Exception {

		invokePost("/resource/parent", mapper.writeValueAsString(parent1));

		response = invokeGet(addQueryParam("/resource/parents", "p", parent1.getId()));
		log.info("{}", response.getContentAsString());

		Parent parent = mapper.readValue(response.getContentAsString(), Parent.class);
		assertEquals(parent1.getEmailAddress(), parent.getEmailAddress());

		response = invokeGet("/resource/parents");
		log.info("{}", response.getContentAsString());

		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Parent.class);

		List<Parent> parents = mapper.readValue(response.getContentAsString(), type);

		assertEquals(parent1, parents.stream().filter(p -> p.equals(parent1)).findFirst().get());
	}

	@Test
	public void getChildren() throws Exception {

		invokePost("/resource/parent", mapper.writeValueAsString(parent2));

		String cson = mapper.writeValueAsString(child2);
		log.info("adding child {}", cson);
		invokePost("/resource/child", cson);

		response = invokeGet(addQueryParam("/resource/children", "c", child2.getId()));
		log.info("{}", response.getContentAsString());

		Child child = mapper.readValue(response.getContentAsString(), Child.class);
		assertEquals(child2.getChildNumber(), child.getChildNumber());

		response = invokeGet("/resource/children");
		log.info("{}", response.getContentAsString());

		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Child.class);

		List<Child> children = mapper.readValue(response.getContentAsString(), type);
		assertEquals(child2.getChildNumber(), children.get(0).getChildNumber());
	}
}

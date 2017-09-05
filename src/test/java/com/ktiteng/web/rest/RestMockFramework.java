package com.ktiteng.web.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.web.rest.service.ResourceRestService;

public class RestMockFramework extends ArquillianUnitTest {

	Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	MockHttpResponse response = new MockHttpResponse();
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;
	
	protected Gson gson = new Gson();

	public void afterBefore() {
		ResteasyProviderFactory resteasyProviderFactory = dispatcher.getProviderFactory();
		resteasyProviderFactory.setInjectorFactory(new CdiInjectorFactory());
		Assert.assertNotNull(cc);
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(ResourceRestService.class));
		log.info("afterBefore done.");
	}

	protected MockHttpResponse invokeGet(String path) throws Exception {
		MockHttpRequest request = MockHttpRequest.get(path);
		dispatcher.invoke(request, response);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		return response;
	}

}

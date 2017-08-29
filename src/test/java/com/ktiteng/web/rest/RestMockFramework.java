package com.ktiteng.web.rest;

import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.bean.service.ChildControllerBean;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.web.rest.service.ResourceRestService;

public class RestMockFramework extends ArquillianUnitTest {

	Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	MockHttpResponse response = new MockHttpResponse();

	@Before
	public void afterBefore()  {
		dispatcher.getRegistry().pushContext(Logger.class, LoggerFactory.getLogger(this.getClass().getName()));
		ResteasyProviderFactory.pushContext(ChildController.class, new ChildControllerBean());
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(PaymentRestService.class));
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(ResourceRestService.class));

	}
	
	protected void invokeGet(String path) throws Exception {
		MockHttpRequest request = MockHttpRequest.get(path);
		dispatcher.invoke(request, response);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}
}

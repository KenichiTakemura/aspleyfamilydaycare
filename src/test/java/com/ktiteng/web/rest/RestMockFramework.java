package com.ktiteng.web.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.ktiteng.web.rest.service.PaymentRestService;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.web.rest.service.ResourceRestService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.junit.Assert.fail;

public class RestMockFramework extends ArquillianUnitTest {

	Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	@Inject
	@Log
	private Logger log;
	@Inject
	protected ChildController cc;
	@Inject
	protected PaymentController pc;
	protected ObjectMapper mapper = new ObjectMapper();

	public void afterBefore() {
		ResteasyProviderFactory resteasyProviderFactory = dispatcher.getProviderFactory();
		resteasyProviderFactory.setInjectorFactory(new CdiInjectorFactory());
		Assert.assertNotNull(cc);
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(ResourceRestService.class));
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(PaymentRestService.class));
		log.info("afterBefore done.");
	}

	protected MockHttpResponse invokeGet(String path) throws Exception {
		log.info("path {}", path);
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.get(path);
		log.info("invoke {}", request.getUri().getRequestUri());
		dispatcher.invoke(request, response);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		return response;
	}

	protected String addQueryParam(String path, String key, String value) {
		try {
			return String.format("%s?%s=%s", path, key,  URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
		return path;
	}

}

package com.ktiteng.web.rest;

import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Assert;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.web.rest.jackson.JacksonConfig;
import com.ktiteng.web.rest.service.PaymentRestService;
import com.ktiteng.web.rest.service.ReceiptRestService;
import com.ktiteng.web.rest.service.ResourceRestService;

public class RestMockFramework extends ArquillianUnitTest {

	Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	@Inject
	@Log
	private Logger log;
	protected ObjectMapper mapper;

	public void afterBefore() {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		ResteasyProviderFactory resteasyProviderFactory = dispatcher.getProviderFactory();
		resteasyProviderFactory.setInjectorFactory(new CdiInjectorFactory());
		ResteasyProviderFactory.getInstance().registerProvider(JacksonConfig.class);
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(ResourceRestService.class));
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(PaymentRestService.class));
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(ReceiptRestService.class));
		log.info("afterBefore done.");
	}

	protected MockHttpResponse invokePost(String path, String json) throws Exception {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.post(path);
		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON_TYPE);
		request.content(json.getBytes());
		log.info("invoke {}", request.getUri().getRequestUri());
		dispatcher.invoke(request, response);
		Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		return response;
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
			return String.format("%s?%s=%s", path, key, URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
		return path;
	}

	protected String toJson(Object entity) throws JsonProcessingException {
		return mapper.writeValueAsString(entity);
	}
}

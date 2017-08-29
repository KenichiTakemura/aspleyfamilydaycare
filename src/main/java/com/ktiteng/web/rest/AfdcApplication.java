package com.ktiteng.web.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.ktiteng.web.rest.service.ResourceRestService;

@ApplicationPath("/rest")
public class AfdcApplication extends Application {
	private Set<Class<?>> requests = new HashSet<Class<?>>();
	private Set<Object> singletons = new HashSet<Object>();

	public AfdcApplication() {
		requests.add(PaymentRestService.class);
		requests.add(ResourceRestService.class);
	}

	public Set<Class<?>> getClasses() {
		return requests;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
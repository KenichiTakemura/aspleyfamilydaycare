package com.ktiteng.web.rest;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.web.rest.service.ResourceRestService;

@ApplicationPath("/rest")
public class AfdcApplication extends Application {
	@Inject
	@Log
	private Logger log;
	private Set<Class<?>> requests = new HashSet<Class<?>>();
//	private Set<Object> singletons = new HashSet<Object>();

	public AfdcApplication() {
		log.info("Adding classes");
		requests.add(PaymentRestService.class);
		requests.add(ResourceRestService.class);
	}

	public Set<Class<?>> getClasses() {
		log.info("getClasses returns {}", requests);
		return requests;
	}

//	@Override
//	public Set<Object> getSingletons() {
//		log.info("getSingletons returns {}", singletons);
//		return singletons;
//	}
}
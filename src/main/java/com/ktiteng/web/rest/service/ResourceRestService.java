package com.ktiteng.web.rest.service;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.service.Parent;

@Path("/resource")
@Produces("application/json")
public class ResourceRestService {
	@Inject
	@Log
	private Logger log;
	@Inject
	private ChildController cc;

	@GET
	@Path("/parents")
	public Collection<Parent> getParents() throws IOException {
		log.info("started.");
		return cc.getAllParents();
	}

}

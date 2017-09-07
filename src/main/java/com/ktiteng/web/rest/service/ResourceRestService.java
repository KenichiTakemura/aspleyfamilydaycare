package com.ktiteng.web.rest.service;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ktiteng.util.Utils;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.service.Parent;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceRestService {
	@Inject
	@Log
	private Logger log;
	@Inject
	private ChildController cc;

	@GET
	@Path("/parents")
	public Response getParents(@QueryParam("p") final String parentId) throws IOException {
		log.info("started. parentId={}", parentId);
		if (Utils.isNullOrBlank(parentId)) {
			return Response.ok().entity(cc.getAllParents()).build();
		} else {
			return Response.ok().entity(cc.findParent(parentId)).build();
		}
	}

}

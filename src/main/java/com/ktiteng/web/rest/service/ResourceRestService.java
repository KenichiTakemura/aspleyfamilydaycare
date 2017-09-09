package com.ktiteng.web.rest.service;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.util.Utils;

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
		log.info("getParents started. parentId={}", parentId);
		if (Utils.isNullOrBlank(parentId)) {
			return Response.ok().entity(cc.getAllParents()).build();
		} else {
			return Response.ok().entity(cc.findParent(parentId)).build();
		}
	}

	@GET
	@Path("/children")
	public Response getChildren(@QueryParam("c") final String childId) throws IOException {
		log.info("getChildren started. childId={}", childId);
		if (Utils.isNullOrBlank(childId)) {
			return Response.ok().entity(cc.getAllChildren()).build();
		} else {
			return Response.ok().entity(cc.findChild(childId)).build();
		}
	}

	@POST
	@Path("/parent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addParent(final Parent parent) throws IOException {
		log.info("addParent started. parent={}", parent);
		if (parent != null) {
			cc.addParent(parent);
			return Response.ok().build();
		} else {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/child")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addChild(final Child child, String parentId) throws IOException {
		log.info("addChild started. child={}", child);
		if (child != null) {
			cc.addChild(child);
			return Response.ok().build();
		} else {
			return Response.serverError().build();
		}
	}
}

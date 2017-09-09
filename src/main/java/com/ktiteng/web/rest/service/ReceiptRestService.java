package com.ktiteng.web.rest.service;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
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
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;

@Path("/receipt")
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptRestService {
	@Inject
	@Log
	private Logger log;
	@Inject
	PaymentController pc;
	@Inject
	ReceiptController rc;

	@POST
	@Path("/issue/{type}")
	public Response issueReceipt(@PathParam("type") final String type,
			@QueryParam("c") final String childId,
			@QueryParam("ps") final String paymentScheduleId) {
		log.info("issueReceipt for {} {} {}", type, childId, paymentScheduleId);
		try {
			if (type.equals("weeks")) {
				rc.issueReceiptWeeks(childId, paymentScheduleId);
			} else {
				throw new Exception("Unknown request type for issueReceipt");
			}
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("/issue/{type}")
	@Produces("application/pdf")
	public Response getReceipt(@PathParam("type") final String type,
			@QueryParam("c") final String childId,
			@QueryParam("ps") final String paymentScheduleId) {
//		//Prepare a file object with file to return
//        File file = new File(&quot;c:/demoPDFFile.pdf&quot;);
//         
//        ResponseBuilder response = Response.ok((Object) file);
//        response.header(&quot;Content-Disposition&quot;, &quot;attachment; filename=&quot;howtodoinjava.pdf&quot;&quot;);
//        return response.build();
		return Response.ok().build();
		
	}

}
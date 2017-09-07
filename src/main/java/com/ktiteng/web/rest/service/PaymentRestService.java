package com.ktiteng.web.rest.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.entity.service.Payment;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentRestService {
	@Inject
	@Log
	private Logger log;
	@Inject
	PaymentController pc;

	@GET
	@Path("/paymentschedule")
	public Response getPaymentSchedule(@QueryParam("c") final String childId) {
		log.info("getPaymentSchedule {}", childId);
		try {
			Payment payment = pc.findPayment(childId);
			return Response.ok().entity(payment.getPaymentSchedule()).build();
		} catch (Exception e) {
			log.error("Cannot getPaymentSchedule.", e);
			return Response.serverError().build();
		}
	}

}

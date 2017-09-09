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
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;

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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPaymentSchedule(final PaymentSchedule paymentSchedule) throws IOException {
		log.info("addPaymentSchedule started. paymentSchedule={}", paymentSchedule);
		if (paymentSchedule != null) {
			pc.addPaymentSchedule(paymentSchedule);
			return Response.ok().entity(cc.getAllParents()).build();
		} else {
			return Response.serverError().build();
		}
	}

	
	@POST
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

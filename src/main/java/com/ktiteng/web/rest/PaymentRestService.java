package com.ktiteng.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.service.PaymentSchedule;

@Path("/parent")
@Produces("application/json")
public class PaymentRestService {
	@Inject
	@Log
	private Logger log;

	@GET
	@Path("/all")
	public List<PaymentSchedule> getParents() {
		return null;
	}

}

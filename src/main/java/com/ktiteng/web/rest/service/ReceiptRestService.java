package com.ktiteng.web.rest.service;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import com.ktiteng.afdc.InvoiceType;
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
    @Path("/issue")
    public Response issueReceipt(@QueryParam("t") final String type, @QueryParam("c") final String childId,
                                 @QueryParam("ps") final String payableId) {
        log.info("issueReceipt for {} {} {}", type, childId, payableId);
        try {
        	if (InvoiceType.WEEKS ==InvoiceType.get(type)) {
        		rc.issueReceipt(childId, pc.findPaymentSchedule(childId, payableId));
        	} else if (InvoiceType.DEPOSIT ==InvoiceType.get(type)) {
        		rc.issueReceipt(childId, pc.findDeposit(childId));
        	} else if  (InvoiceType.DEPOSIT ==InvoiceType.get(type)) {
        		rc.issueReceipt(childId, pc.findEnrollmentFee(childId));
        	} else {
        		log.error("Unknown InvoiceType {}" + type);
        		return Response.serverError().build();
        	}
        } catch (Exception e) {
            log.error("Something went wrong", e);
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/issue")
    public Response deleteReceipt(@QueryParam("t") final String type, @QueryParam("c") final String childId,
                                  @QueryParam("r") final String receiptId) {
        log.info("deleteReceipt for {} {} {}", type, childId, receiptId);
        try {
            rc.deleteReceipt(childId, receiptId);
        } catch (Exception e) {
            log.error("Something went wrong", e);
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/issue")
    @Produces("application/pdf")
    public Response getReceipt(@QueryParam("t") final String type, @QueryParam("c") final String childId,
                               @QueryParam("ps") final String paymentScheduleId) {
        log.info("getReceipt for {} {} {}", type, childId, paymentScheduleId);
        // Prepare a file object with file to return
//        File file = new File(&quot;c:/demoPDFFile.pdf&quot;);
        //
        // ResponseBuilder response = Response.ok((Object) file);
        // response.header(&quot;Content-Disposition&quot;, &quot;attachment;
        // filename=&quot;howtodoinjava.pdf&quot;&quot;);
        // return response.build();
        return Response.ok().build();

    }

}

package com.ktiteng.web.rest.service;

import static com.ktiteng.util.Utils.toDate;

import javax.inject.Inject;

import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.web.rest.RestMockFramework;

public class ReceiptRestServiceTest extends RestMockFramework {
	@Inject
	@Log
	private Logger log;
	private MockHttpResponse response;

	@Test
	public void issueReceipt() throws Exception {
		invokePost("/resource/parent", toJson(parent1));
		invokePost("/resource/child", toJson(child1));
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
				.setBillingStartDate(toDate("2017-07-03")).setAmountInvoiced(114.00d);
		invokePost(addQueryParam("/payment/paymentschedule", "c", child1.id()), toJson(ps));
		invokePost(addQueryParam("/receipt/issue", new String[]{"t", "weeks", "c", child1.id(),
				"ps", ps.id()}), null);
	}
}

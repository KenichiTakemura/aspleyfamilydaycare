package com.ktiteng.web.rest.service;

import com.fasterxml.jackson.databind.JavaType;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.web.rest.RestMockFramework;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

import static com.ktiteng.util.Utils.toDate;
import static org.junit.Assert.assertEquals;

public class PaymentRestServiceTest extends RestMockFramework {
	@Inject
	@Log
	private Logger log;

	@Test
	public void getPaymentSchedule() throws Exception {
		Parent p1 = cc.addParent(parent1);
		Child c = cc.addChild(child1);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
				.setBillingStartDate(toDate("2017-07-03")).setBillingEndDate(toDate("2017-07-16"))
				.setAmountInvoiced(114.00d).setAmountReceived(114.00d).setBalanceDue(0.0d);
		PaymentSchedule paymentSchedule = pc.addPaymentSchedule(c, ps);
		MockHttpResponse response = invokeGet(addQueryParam("/payment/paymentschedule", "c", c.getId()));
		log.info("{}", response.getContentAsString());
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, PaymentSchedule.class);
		List<PaymentSchedule> paymentSchedules = mapper.readValue(response.getContentAsString(), type);
		assertEquals(paymentSchedule.getAmountInvoiced(), paymentSchedules.get(0).getAmountInvoiced(), 0.0d);
	}
}

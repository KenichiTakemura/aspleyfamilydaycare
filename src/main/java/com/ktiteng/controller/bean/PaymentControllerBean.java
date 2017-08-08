package com.ktiteng.controller.bean;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.ktiteng.controller.PaymentController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.fop.PdfGenerator;

@Default
@ApplicationScoped
public class PaymentControllerBean extends BaseController implements PaymentController {

	@Inject
	private PdfGenerator pdfGen;
	
	@Override
	public Payment findPayment(Child child) throws IOException {
		Payment p = (Payment) em.find(Payment.class, child.getId());
		return p != null ? p : new Payment();
	}

	@Override
	public Payment updateInitialPayment(Child child, Payment payment, InitialPayment initialPayment)
			throws IOException {
		payment.setChildId(child.getId());
		payment.setInitialPayment(initialPayment);
		save(payment);
		return payment;
	}

	@Override
	public Payment updatePaymentSchedule(Child child, Payment payment, PaymentSchedule paymentSchedule)
			throws IOException {
		payment.setChildId(child.getId());
		payment.addPaymentSchedule(paymentSchedule);
		save(payment);
		return payment;
	}

	// Private Area
	private PaymentSchedule findPaymentSchedule(String childId, String paymentScheduleId) throws IOException {
		return (PaymentSchedule) em.find(PaymentSchedule.class, childId, paymentScheduleId);
	}

}

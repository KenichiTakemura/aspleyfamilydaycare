package com.ktiteng.controller.bean;

import java.io.IOException;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.ktiteng.controller.PaymentController;
import com.ktiteng.controller.TaxInvoiceSeederController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;
import com.ktiteng.entity.manager.PersistenceManager;
import com.ktiteng.util.Utils;

@Default
@ApplicationScoped
public class PaymentControllerBean extends BaseController implements PaymentController {

	@Inject
	TaxInvoiceSeederController tcs;

	@Inject
	PersistenceManager pm;

	@Override
	public Payment findPayment(Child child) throws IOException {
		Payment p = (Payment) em.find(Payment.class, child.getId());
		return p != null ? p : new Payment();
	}

	@Override
	public Payment updateInitialPayment(Child child, InitialPayment initialPayment) throws IOException {
		Payment payment = findPayment(child);
		payment.setChildId(child.getId());
		payment.setInitialPayment(initialPayment);
		save(payment);
		return payment;
	}

	@Override
	public PaymentSchedule updatePaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException {
		Payment payment = findPayment(child);
		payment.setChildId(child.getId());
		Receipt receipt = new Receipt().setTaxInvoiceId(tcs.generateNextId());
		receipt.setName(String.format("Receipt for %s %s(%s-%s)", child.getFirstName(), child.getLastName(),
				Utils.toS(paymentSchedule.getBillingStartDate()), Utils.toS(paymentSchedule.getBillingEndDate())));
		String location = String.format("%s/receipt/Receipt_%s_%s(%s-%s).pdf", pm.getPath().toString(),
				child.getFirstName(), child.getLastName().trim(),
				Utils.toS(paymentSchedule.getBillingStartDate()).replaceAll("/", ""),
				Utils.toS(paymentSchedule.getBillingEndDate()).replaceAll("/", ""));
		receipt.setLocation(location);
		paymentSchedule.setReceipt(receipt);
		payment.addPaymentSchedule(paymentSchedule);
		save(payment);
		return paymentSchedule;
	}

	@Override
	public PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException {
		Payment payment = findPayment(child);
		Optional<PaymentSchedule> ops = payment.getPaymentSchedule().stream()
				.filter(ps -> ps.getId().equals(paymentScheduleId)).findFirst();
		return ops.get();
	}

}

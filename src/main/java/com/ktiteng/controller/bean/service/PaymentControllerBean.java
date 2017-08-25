package com.ktiteng.controller.bean.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.TaxInvoiceSeederController;
import com.ktiteng.entity.manager.PersistenceManager;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.entity.service.Receipt;
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
		return p != null ? p : new Payment().setChildId(child.getId());
	}

	@Override
	public InitialPayment addInitialPayment(Child child, InitialPayment initialPayment) throws IOException {
		Payment payment = findPayment(child);
		if (payment.getInitialPayment() != null) {
			throw new IOException("Already exists. Use update.");
		}
		initialPayment.setGeneratedAt();
		payment.setInitialPayment(initialPayment);
		save(payment);
		return initialPayment;
	}

	@Override
	public PaymentSchedule addPaymentSchedule(Child child, PaymentSchedule ps) throws IOException {
		Payment payment = findPayment(child);
		PaymentSchedule paymentSchedule = this.findPaymentSchedule(child, ps);
		if (paymentSchedule != null) {
			log.warn("Already exists. Use update.");
			return paymentSchedule;
		}
		ps.setGeneratedAt();
		ps.setReceipt(genReceipt(child, ps));
		payment.addPaymentSchedule(ps);
		save(payment);
		return ps;
	}

	@Override
	public PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException {
		Payment payment = findPayment(child);
		Optional<PaymentSchedule> ops = payment.getPaymentSchedule().stream()
				.filter(ps -> ps.getId().equals(paymentScheduleId)).findFirst();
		return ops.isPresent() ? ops.get() : null;
	}

	@Override
	public PaymentSchedule findPaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException {
		Payment payment = findPayment(child);
		Optional<PaymentSchedule> ops = payment.getPaymentSchedule().stream().filter(ps -> ps.equals(paymentSchedule))
				.findFirst();
		return ops.isPresent() ? ops.get() : null;
	}

	@Override
	public Collection<Payment> getAllPayments() throws IOException {
		return (Collection<Payment>) (em.getAll(Payment.class).getEntities());
	}

	@Override
	public PaymentSchedule updatePaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException {
		Payment payment = findPayment(child);
		if (this.findPaymentSchedule(child, paymentSchedule.getId()) == null) {
			throw new IOException("Not exists. Use add.");
		}
		payment.updatePaymentSchedule(paymentSchedule);
		save(payment);
		return paymentSchedule;
	}

	private Receipt genReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException {
		Receipt receipt = new Receipt().setTaxInvoiceId(tcs.generateNextId());
		receipt.setName(String.format("Receipt for %s %s(%s-%s)", child.getFirstName(), child.getLastName(),
				Utils.toS(paymentSchedule.getBillingStartDate()), Utils.toS(paymentSchedule.getBillingEndDate())));
		String location = String.format("%s/receipt/Receipt_%s_%s(%s-%s).pdf", pm.getPath().toString(),
				child.getFirstName(), child.getLastName().trim(),
				Utils.toS(paymentSchedule.getBillingStartDate()).replaceAll("/", ""),
				Utils.toS(paymentSchedule.getBillingEndDate()).replaceAll("/", ""));
		receipt.setLocation(location);
		return receipt;
	}

	@Override
	public InitialPayment updateInitialPayment(Child child, InitialPayment initialPayment) throws IOException {
		Payment payment = findPayment(child);
		if (payment.getInitialPayment() == null) {
			throw new IOException("Not exists. Use add.");
		}
		payment.setInitialPayment(initialPayment);
		save(payment);
		return initialPayment;
	}
}

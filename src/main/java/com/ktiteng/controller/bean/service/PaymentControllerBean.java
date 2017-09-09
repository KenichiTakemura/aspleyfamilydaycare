package com.ktiteng.controller.bean.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.controller.service.ChildController;
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
	@Log
	private Logger log;
	@Inject
	TaxInvoiceSeederController tcs;

	@Inject
	ChildController cc;
	
	@Inject
	PersistenceManager pm;

	@Override
	public Payment findPayment(Child child) throws IOException {
		return this.findPayment(child.getId());
	}

	@Override
	public Payment findPayment(String childId) throws IOException {
		Payment p = (Payment) em.find(Payment.class, childId);
		return p != null ? p : new Payment().setChildId(childId);
	}

	@Override
	public Payment addInitialPayment(Child child, InitialPayment initialPayment) throws IOException {
		Payment payment = findPayment(child);
		if (payment.getInitialPayment() == null) {
			initialPayment.setGeneratedAt();
			initialPayment.setReceiptDeposit(genDepositReceipt(child, initialPayment));
			initialPayment.setReceiptEnrollmentFee(genEnrollmentFeeReceipt(child, initialPayment));
			payment.setInitialPayment(initialPayment);
			save(payment);
		} else {
			log.info("Already exists. Use update");
		}
		return payment;
	}

	@Override
	public PaymentSchedule addPaymentSchedule(String childId, PaymentSchedule paymentSchedule) throws IOException {
		Child child = cc.findChild(childId);
		if (child == null) {
			throw new IOException("Child not found by " + childId);
		}
		Payment payment = findPayment(childId);
		Optional<PaymentSchedule> ops = payment.getPaymentSchedule().stream().filter(ps -> ps.equals(paymentSchedule))
				.findFirst();
		if (ops.isPresent()) {
			log.warn("Already exists. Use update.");
			return paymentSchedule;
		}
		if (paymentSchedule.getGeneratedAt() == null) {
			paymentSchedule.setGeneratedAt();
		}
		paymentSchedule.setReceipt(genReceipt(child, paymentSchedule));
		payment.addPaymentSchedule(paymentSchedule);
		save(payment);
		return paymentSchedule;
	}

	@Override
	public PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException {
		Payment payment = findPayment(child);
		Optional<PaymentSchedule> ops = payment.getPaymentSchedule().stream()
				.filter(ps -> ps.getId().equals(paymentScheduleId)).findFirst();
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

	private Receipt genDepositReceipt(Child child, InitialPayment initialPayment) throws IOException {
		Receipt receipt = new Receipt().setTaxInvoiceId(tcs.generateNextId());
		receipt.setName(String.format("Deposit Receipt for %s %s", child.getFirstName(), child.getLastName()));
		String location = String.format("%s/receipt/Deposit_Receipt_%s_%s.pdf", pm.getPath().toString(),
				child.getFirstName(), child.getLastName().trim());
		receipt.setLocation(location);
		return receipt;
	}

	private Receipt genEnrollmentFeeReceipt(Child child, InitialPayment initialPayment) throws IOException {
		Receipt receipt = new Receipt().setTaxInvoiceId(tcs.generateNextId());
		receipt.setName(String.format("Enrollment Fee Receipt for %s %s", child.getFirstName(), child.getLastName()));
		String location = String.format("%s/receipt/EnrollmentFee_Receipt_%s_%s.pdf", pm.getPath().toString(),
				child.getFirstName(), child.getLastName().trim());
		receipt.setLocation(location);
		return receipt;
	}

	@Override
	public Payment updateInitialPayment(Child child, InitialPayment initialPayment) throws IOException {
		Payment payment = findPayment(child);
		if (payment.getInitialPayment() == null) {
			throw new IOException("Not exists. Use add.");
		}
		payment.setInitialPayment(initialPayment);
		save(payment);
		return payment;
	}
}

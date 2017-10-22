package com.ktiteng.controller.bean.service;

import java.io.IOException;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.Payable;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;

@Default
@ApplicationScoped
public class PaymentControllerBean extends BaseController implements PaymentController {

	@Inject
	ChildController cc;

	@Override
	public Payment findPayment(String childId) throws IOException {
		Payment p = (Payment) em.find(Payment.class, childId);
		return p != null ? p : new Payment().setChildId(childId);
	}

	@Override
	public Payable find(String childId, String payableId) throws IOException {
		Payment p = findPayment(childId);
		log.info("find by payableId {}", payableId);
		if (p.getDeposit() != null && p.getDeposit().id().equals(payableId)) {
			log.info("Return Deposit");
			return p.getDeposit();
		}
		if (p.getEnrollmentFee() != null && p.getEnrollmentFee().id().equals(payableId)) {
			log.info("Return getEnrollmentFee");
			return p.getEnrollmentFee();
		}
		PaymentSchedule ps = p.getPaymentSchedule(payableId);
		if (ps != null) {
			log.info("Return PaymentSchedule");
			return ps;
		}
		return null;
	}

	@Override
	public void updatePayment(String childId) throws IOException {
		save(findPayment(childId));
	}

	public Child findChild(String childId) throws IOException {
		Child child = cc.findChild(childId);
		if (child == null) {
			throw new IOException("Child not found by " + childId);
		}
		return child;
	}

	@Override
	public Deposit addDeposit(String childId, Deposit deposit) throws IOException {
		Payment payment = findPayment(childId);
		if (payment.getDeposit() == null) {
			deposit.setGeneratedAt();
			payment.setDeposit(deposit);
			save(payment);
		} else {
			log.info("Already exists. Use update");
		}
		return deposit;
	}

	@Override
	public Deposit updateDeposit(String childId, Deposit deposit) throws IOException {
		Payment payment = findPayment(childId);
		if (payment.getDeposit() != null) {
			log.warn("Not exists. Use add.");
			return deposit;
		}
		payment.setDeposit(deposit);
		save(payment);
		return deposit;
	}

	@Override
	public EnrollmentFee addEnrollmentFee(String childId, EnrollmentFee enrollmentFee) throws IOException {
		Payment payment = findPayment(childId);
		if (payment.getEnrollmentFee() == null) {
			enrollmentFee.setGeneratedAt();
			payment.setEnrollmentFee(enrollmentFee);
			save(payment);
		} else {
			log.info("Already exists. Use update");
		}
		return enrollmentFee;
	}

	@Override
	public EnrollmentFee updateEnrollmentFee(String childId, EnrollmentFee enrollmentFee) throws IOException {
		Payment payment = findPayment(childId);
		if (payment.getEnrollmentFee() != null) {
			log.warn("Not exists. Use add.");
			return enrollmentFee;
		}
		payment.setEnrollmentFee(enrollmentFee);
		save(payment);
		return enrollmentFee;
	}

	@Override
	public PaymentSchedule addPaymentSchedule(String childId, PaymentSchedule paymentSchedule) throws IOException {
		Payment payment = findPayment(childId);
		if (payment.getPaymentSchedule(paymentSchedule.id()) != null) {
			log.warn("Already exists. Use update.");
			return paymentSchedule;
		}
		paymentSchedule.setGeneratedAt();
		payment.addPaymentSchedule(paymentSchedule);
		save(payment);
		return paymentSchedule;
	}

	@Override
	public PaymentSchedule updatePaymentSchedule(String childId, PaymentSchedule paymentSchedule) throws IOException {
		Payment payment = findPayment(childId);
		if (payment.getPaymentSchedule(paymentSchedule.id()) == null) {
			log.warn("Not exists. Use add.");
			return paymentSchedule;
		}
		payment.updatePaymentSchedule(paymentSchedule);
		save(payment);
		return paymentSchedule;
	}

	@Override
	public PaymentSchedule findPaymentSchedule(String childId, String paymentScheduleId) throws IOException {
		return findPayment(childId).getPaymentSchedule(paymentScheduleId);
	}

	@Override
	public Collection<Payment> getAllPayments() throws IOException {
		return (Collection<Payment>) (em.getAll(Payment.class).getEntity());
	}

	@Override
	public Deposit findDeposit(String childId) throws IOException {
		return findPayment(childId).getDeposit();
	}

	@Override
	public EnrollmentFee findEnrollmentFee(String childId) throws IOException {
		return findPayment(childId).getEnrollmentFee();
	}

}

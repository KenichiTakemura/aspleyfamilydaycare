package com.ktiteng.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.util.Validator;

@Default
@ApplicationScoped
public class ChldControllerImpl extends BaseController implements ChildController {

	@Inject @Log
	private Logger log;

	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public Parent addParent(String firstName, String lastName, String phoneNumber, String emailAddress)
			throws IOException {
		Parent p = new Parent();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setPhoneNumber(phoneNumber);
		p.setEmailAddress(Validator.isValidEmailAddress(emailAddress));
		if (p.getEmailAddress() == null) {
			throw new IOException("Email is invalid");
		}
		return updateParent(p);
	}

	@Override
	public Parent updateParent(Parent parent) throws IOException {
		save(parent);
		return parent;
	}

	@Override
	public Child addChild(String firstName, String lastName, Parent parent) throws IOException {
		Child c = new Child();
		c.setFirstName(firstName);
		if (lastName == null) {
			c.setLastName(parent.getLastName());
		} else {
			c.setLastName(lastName);
		}
		c.setParentId(parent.getId());
		return updateChild(c);
	}

	@Override
	public Child updateChild(Child child) throws IOException {
		save(child);
		return child;
	}

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
	public Parent findParent(String id) throws IOException {
		return (Parent) em.find(Parent.class, id);
	}

	@Override
	public Child findChild(String id) throws IOException {
		return (Child) em.find(Parent.class, id);
	}

	@Override
	public Payment updatePaymentSchedule(Child child, Payment payment, PaymentSchedule paymentSchedule)
			throws IOException {
		payment.setChildId(child.getId());
		payment.addPaymentSchedule(paymentSchedule);
		save(payment);
		return payment;
	}

	@Override
	public void issueReceipt(String childId, String paymentScheduleId) throws IOException {
		Child child = findChild(childId);
		PaymentSchedule paymentSchedule = findPaymentSchedule(childId, paymentScheduleId);

	}

	// Private Area
	private PaymentSchedule findPaymentSchedule(String childId, String paymentScheduleId) throws IOException {
		return (PaymentSchedule) em.find(PaymentSchedule.class, childId, paymentScheduleId);
	}
}

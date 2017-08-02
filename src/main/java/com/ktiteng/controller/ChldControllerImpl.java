package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.manager.EntityManager;
import com.ktiteng.util.Validator;

public class ChldControllerImpl extends BaseController implements ChildController {

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
		Payment p = (Payment) EntityManager.getInstance().find(Payment.class, child.getId());
		return p != null ? p : new Payment();
	}

	@Override
	public Payment updateInitialPayment(Child child, Payment payment, InitialPayment initialPayment) throws IOException {
		payment.setChildId(child.getId());
		payment.setInitialPayment(initialPayment);
		save(payment);
		return payment;
	}

	@Override
	public Parent findParent(String id) throws IOException {
		return (Parent) EntityManager.getInstance().find(Parent.class, id);
	}

	@Override
	public Child findChild(String id) throws IOException {
		return (Child) EntityManager.getInstance().find(Parent.class, id);
	}

	@Override
	public Payment updatePaymentSchedule(Child child, Payment payment, PaymentSchedule paymentSchedule) throws IOException {
		payment.setChildId(child.getId());
		payment.addPaymentSchedule(paymentSchedule);
		save(payment);
		return payment;
	}

}

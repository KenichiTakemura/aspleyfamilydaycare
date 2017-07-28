package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;
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
		save(p);
		return p;
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
		save(c);
		return c;
	}

}

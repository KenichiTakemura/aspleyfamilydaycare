package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Parent;
import com.ktiteng.util.Validator;

public class ParentControllerImpl extends BaseController implements ParentController {

	private static final String NAME_JOIN = "-";
	
	private String getFileName(Parent p) {
		return String.join(NAME_JOIN, p.getFirstName(), p.getLastName());
	}
	
	@Override
	public void addParent(String firstName, String lastName, String phoneNumber, String emailAddress) throws IOException {
		Parent p = new Parent();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setPhoneNumber(phoneNumber);
		p.setEmailAddress(Validator.isValidEmailAddress(emailAddress));
		if (p.getEmailAddress() == null) {
			throw new IOException("Email is invalid");
		}
		persist(p, getFileName(p));
	}

}

package com.ktiteng.controller.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import com.ktiteng.controller.ChildController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;

@Default
@ApplicationScoped
public class ChldControllerBean extends BaseController implements ChildController {


	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public Parent addParent(String firstName, String lastName) throws IOException {
		return addParent(firstName, lastName, null, null);
	}

	@Override
	public Parent addParent(String firstName, String lastName, String phoneNumber, String emailAddress)
			throws IOException {
		Parent p = new Parent();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		if (phoneNumber != null) {
			p.setPhoneNumber(phoneNumber);
		}
		if (emailAddress != null) {
			p.setEmailAddress(emailAddress);
		}
		return updateParent(p);
	}

	@Override
	public Parent updateParent(Parent parent) throws IOException {
		validate(parent);
		save(parent);
		return parent;
	}

	@Override
	public Child addChild(String firstName, String lastName, String childNumber, Parent parent) throws IOException {
		Child c = new Child();
		c.setFirstName(firstName);
		if (lastName == null) {
			c.setLastName(parent.getLastName());
		} else {
			c.setLastName(lastName);
		}
		c.setChildNumber(childNumber);
		c.setParentId(parent.getId());
		return updateChild(c);
	}

	@Override
	public Child updateChild(Child child) throws IOException {
		save(child);
		return child;
	}

	@Override
	public Parent findParent(String id) throws IOException {
		return (Parent) em.find(Parent.class, id);
	}

	@Override
	public Child findChild(String id) throws IOException {
		return (Child) em.find(Parent.class, id);
	}


}

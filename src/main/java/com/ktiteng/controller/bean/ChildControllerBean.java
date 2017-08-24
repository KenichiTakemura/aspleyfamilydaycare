package com.ktiteng.controller.bean;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import com.ktiteng.controller.ChildController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;

@Default
@ApplicationScoped
public class ChildControllerBean extends BaseController implements ChildController {

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
		if (this.findParent(firstName, lastName) != null) {
			throw new IOException("Already exists. Use update.");
		}
		Parent p = new Parent();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		if (phoneNumber != null) {
			p.setPhoneNumber(phoneNumber);
		}
		if (emailAddress != null) {
			p.setEmailAddress(emailAddress);
		}
		p.setGeneratedAt();
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
		if (this.findChild(firstName, lastName) != null) {
			throw new IOException("Already exists. Use update.");
		}
		Child c = new Child();
		c.setFirstName(firstName);
		if (lastName == null) {
			c.setLastName(parent.getLastName());
		} else {
			c.setLastName(lastName);
		}
		c.setChildNumber(childNumber);
		c.setParentId(parent.getId());
		c.setGeneratedAt();
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
		return (Child) em.find(Child.class, id);
	}

	@Override
	public Collection<Parent> getAllParents() throws IOException {
		return (Collection<Parent>) (em.getAll(Parent.class).getEntities());
	}

	@Override
	public Collection<Child> getAllChildren() throws IOException {
		return (Collection<Child>) (em.getAll(Child.class).getEntities());
	}

	@Override
	public Parent findParent(String firstName, String lastName) throws IOException {
		return (Parent) em.find(Parent.class, firstName, lastName);
	}

	@Override
	public Child findChild(String firstName, String lastName) throws IOException {
		return (Child) em.find(Child.class, firstName, lastName);
	}

}

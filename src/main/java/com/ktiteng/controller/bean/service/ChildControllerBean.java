package com.ktiteng.controller.bean.service;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.util.Utils;
import com.ktiteng.util.Validator;

@Default
@ApplicationScoped
public class ChildControllerBean extends BaseController implements ChildController {
	@Inject
	@Log
	private Logger log;

	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public Parent addParent(Parent parent) throws IOException {
		if (this.findParent(parent.getFirstName(), parent.getLastName()) != null) {
			throw new IOException("Already exists. Use update.");
		}
		if (parent.getGeneratedAt() == null) {
			parent.setGeneratedAt();
		}
		return updateParent(parent);
	}

	@Override
	public Parent updateParent(Parent parent) throws IOException {
		validate(parent);
		save(parent);
		return parent;
	}

	@Override
	public Child addChild(Child child) throws IOException {
		if (this.findChild(child.getFirstName(), child.getLastName()) != null) {
			throw new IOException("Already exists. Use update.");
		}
		if (Utils.isNullOrBlank(child.getParentId()) || this.findParent(child.getParentId()) == null) {
			throw new IOException("No Parent for this child.");
		}
		if (child.getGeneratedAt() == null) {
			child.setGeneratedAt();
		}
		return updateChild(child);
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
		return (Collection<Parent>) (em.getAll(Parent.class).getEntity());
	}

	@Override
	public Collection<Child> getAllChildren() throws IOException {
		return (Collection<Child>) (em.getAll(Child.class).getEntity());
	}

	@Override
	public Parent findParent(String firstName, String lastName) throws IOException {
		return (Parent) em.find(Parent.class, firstName, lastName);
	}

	@Override
	public Child findChild(String firstName, String lastName) throws IOException {
		return (Child) em.find(Child.class, firstName, lastName);
	}

	protected void validate(BaseEntity entity) throws IOException {
		if (entity instanceof Parent) {
			Parent parent = (Parent) entity;

			if (parent.getEmailAddress() != null) {
				if (Validator.isValidEmailAddress(parent.getEmailAddress()) == null) {
					log.warn("Email is invalid {}", parent.getEmailAddress());
					throw new IllegalArgumentException("Email is invalid");
				}
				log.debug("Email is valid {}", parent.getEmailAddress());
			}
		}
	}

}

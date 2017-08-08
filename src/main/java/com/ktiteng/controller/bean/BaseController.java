package com.ktiteng.controller.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.kotcrab.annotation.CallSuper;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.manager.EntityManager;
import com.ktiteng.util.Validator;

public abstract class BaseController {

	@Inject
	@Log
	protected Logger log;

	@Inject
	protected EntityManager em;

	protected void save(BaseEntity entity) throws IOException {
		em.save(entity);
	}

	@PostConstruct
	@CallSuper
	public void init() {
		log.info("PostConstruct starting.");
	}

	@PreDestroy
	@CallSuper
	protected void destroy() {
		log.info("PreDestroy");
	}

	protected void validate(Parent parent) throws IllegalArgumentException {
		if (parent.getEmailAddress() != null) {
			if (Validator.isValidEmailAddress(parent.getEmailAddress()) == null) {
				log.warn("Email is invalid {}", parent.getEmailAddress());
				throw new IllegalArgumentException("Email is invalid");
			}
			log.debug("Email is valid {}", parent.getEmailAddress());
		}
	}
}

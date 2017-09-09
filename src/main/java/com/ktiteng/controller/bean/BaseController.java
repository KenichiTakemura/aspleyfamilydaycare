package com.ktiteng.controller.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.jboss.resteasy.util.IsAssignableFrom;
import org.slf4j.Logger;

import com.kotcrab.annotation.CallSuper;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.manager.EntityManager;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.util.Validator;

public abstract class BaseController {

	@Inject
	@Log
	private Logger log;

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

}

package com.ktiteng.controller.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.kotcrab.annotation.CallSuper;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.manager.EntityManager;

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

}

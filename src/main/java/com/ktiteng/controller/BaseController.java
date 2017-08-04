package com.ktiteng.controller;

import java.io.IOException;

import javax.inject.Inject;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.manager.EntityManager;

public abstract class BaseController {

	@Inject
	protected EntityManager em;
	
	protected void save(BaseEntity entity) throws IOException {
		em.save(entity);
	}

}

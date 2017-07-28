package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.manager.EntityManager;

public abstract class BaseController {

	protected void save(BaseEntity entity) throws IOException {
		EntityManager.getInstance().save(entity);
	}

}

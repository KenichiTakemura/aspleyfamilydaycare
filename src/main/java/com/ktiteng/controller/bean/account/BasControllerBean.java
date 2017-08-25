package com.ktiteng.controller.bean.account;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.account.BasController;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.entity.account.BasQuarterly;

public class BasControllerBean extends BaseController implements BasController {
	@Inject
	@Log
	private Logger log;

	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public BasQuarterly getBasYearly(LocalDate year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasQuarterly getBasQuarterly(LocalDate quarter) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.ktiteng.controller.bean.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.controller.service.TaxInvoiceSeederController;
import com.ktiteng.entity.service.TaxInvoiceSeeder;

@Default
@ApplicationScoped
public class TaxInvoiceSeederControllerBean extends BaseController implements TaxInvoiceSeederController {

	@Inject
	@Log
	private Logger log;
	private TaxInvoiceSeeder seeder;

	@PostConstruct
	public void init() {
		log.info("produced");
		seeder = (TaxInvoiceSeeder) em.get(TaxInvoiceSeeder.class);
	}

	@Override
	public String generateNextId() throws IOException {
		try {
			return seeder.nextVal();
		} finally {
			save(seeder);
		}
	}

}

package com.ktiteng.controller.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import com.ktiteng.controller.TaxInvoiceSeederController;
import com.ktiteng.entity.TaxInvoiceSeeder;

@Default
@ApplicationScoped
public class TaxInvoiceSeederControllerBean extends BaseController implements TaxInvoiceSeederController {
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

package com.ktiteng.controller.bean;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.entity.TaxInvoiceSeeder;

public class TaxInvoiceSeederControllerTest extends ArquillianUnitTest {

	@Inject
	TaxInvoiceSeeder tcs;
	
	@Test
	public void generate() {
		assertEquals("AFDC#10001", tcs.nextVal());
		assertEquals("AFDC#10002", tcs.nextVal());
	}

}

package com.ktiteng.afdc;

import java.io.IOException;

import org.junit.Test;

import com.ktiteng.entity.service.Child;

public class Leon extends InitTest {
	@Test
	public void leon() throws IOException {
		Child c = findChild("Leon", "Jeong");
		wf.sendReceipt(c.getId(), wf.confirmBiWeeklyPayment(c.getId(), "2017-09-11", "202.90", "2017-10-02"));
	}

}

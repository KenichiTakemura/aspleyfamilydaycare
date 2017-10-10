package com.ktiteng.afdc;

import java.io.IOException;

import org.junit.Test;

import com.ktiteng.entity.service.Child;

public class Ruby extends InitTest {
	@Test
	public void ruby() throws IOException {
		Child c = findChild("Ruby", "Hong");
		wf.sendReceipt(c.getId(), wf.confirmBiWeeklyPayment(c.getId(), "2017-09-11", "131.88", "2017-10-03"));
	}

}

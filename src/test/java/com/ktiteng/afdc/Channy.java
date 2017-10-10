package com.ktiteng.afdc;

import java.io.IOException;

import org.junit.Test;

import com.ktiteng.entity.service.Child;

public class Channy extends InitTest {

	@Test
	public void channy() throws IOException {
		Child c = findChild("Channy", "Youn");
		wf.sendReceipt(c.getId(), wf.confirmBiWeeklyPayment(c.getId(), "2017-09-11", "266.00", "2017-09-25"));
	}

}

package com.ktiteng.afdc;

import java.io.IOException;

import org.junit.Test;

import com.ktiteng.entity.service.Child;

public class Louis extends InitTest {
	@Test
	public void louis() throws IOException {
		Child c = findChild("Louis", "Oh");
		wf.sendReceipt(c.getId(), wf.confirmBiWeeklyPayment(c.getId(), "2017-09-11", "177.70", "2017-10-02"));
	}

}

package com.ktiteng.afdc;

import java.io.IOException;

import org.junit.Test;

import com.ktiteng.entity.service.Child;

public class Ian extends InitTest {
	@Test
	public void ian() throws IOException {
		Child c = findChild("Ian", "Kim");
		wf.sendReceipt(c.getId(), wf.confirmBiWeeklyPayment(c.getId(), "2017-09-11", "172.32", "2017-10-02"));
	}

}

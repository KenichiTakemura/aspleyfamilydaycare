package com.ktiteng.afdc;

import java.io.IOException;

import org.junit.Test;

import com.ktiteng.entity.service.Child;

public class Yuna extends InitTest {
	@Test
	public void ian() throws IOException {
		Child c = findChild("Yuna", "Cho");
		wf.sendReceipt(c.getId(), wf.confirmBiWeeklyPayment(c.getId(), "2017-09-11", "45.48", "2017-10-02"));
	}

}

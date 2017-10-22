package com.ktiteng.entity.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExpenseTest {

	@Test
	public void of() {
		Good g = Good.of("test", 2.99, 2);
		assertEquals(g.getPrice().doubleValue(), 5.98, 0.01);
		assertEquals(g.getGst().doubleValue(), 0.54, 0.01);
		assertEquals(g.getPriceExGst().doubleValue(), 5.44, 0.01);
		Good g1 = Good.of("test1", 0.99, 10, true);
		assertEquals(g1.getPrice().doubleValue(), 9.9, 0.01);
		assertEquals(g1.getGst().doubleValue(), 0.0, 0.01);
		assertEquals(g1.getPriceExGst().doubleValue(), 9.9, 0.01);
	}
}

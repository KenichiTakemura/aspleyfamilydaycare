package com.ktiteng.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.ktiteng.entity.BankDetail;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;

public class ChildControllerTest {

	private Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");
	ChildController cc;
	
	@Before
	public void before() throws IOException {
		FileUtils.deleteDirectory(path.toFile());
		path.toFile().mkdirs();
		cc = new ChldControllerImpl();
	}
	
	@Test
	public void add() throws IOException {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Parent p2 = cc.addParent("pfirst2", "plast2", "0433654801", "test2@gmail.com");
		assertTrue(Paths.get(path.toString(), "parent.json").toFile().exists());
		p1.setBankDetail(new BankDetail().setAccount("pfirst1"));
		p1 = cc.updateParent(p1);
		Child c1 = cc.addChild("cfirst1", "clast1", p1);
		cc.addChild("cfirst2", "clast2", p2);
		c1.setStartDate("2017-06-12");
		cc.updateChild(c1);
	}
	
	@Test
	public void payment()  throws IOException {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst1", "clast1", p1);
		Payment payment = cc.findPayment(c1);
		assertNotNull(payment);
		assertNull(payment.getId());
	}
}

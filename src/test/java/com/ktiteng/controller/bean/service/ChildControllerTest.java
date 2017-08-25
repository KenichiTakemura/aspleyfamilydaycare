package com.ktiteng.controller.bean.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.service.BankDetail;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;

public class ChildControllerTest extends ArquillianUnitTest {

	@Inject
	ChildController cc;

	@Test
	public void add() throws IOException {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		assertEquals("pfirst1", cc.findParent(p1.getId()).getFirstName());
		Parent p2 = cc.addParent("pfirst2", "plast2", "0433654801", "test2@gmail.com");
		assertEquals("pfirst2", cc.findParent(p2.getId()).getFirstName());
		assertEquals("pfirst2", cc.findParent(p2.getFirstName(), p2.getLastName()).getFirstName());
		assertTrue(Paths.get(getPathStr(), "parent.json").toFile().exists());
		p1.setBankDetail(new BankDetail().setAccount("pfirst1"));
		p1 = cc.updateParent(p1);
		Child c1 = cc.addChild("cfirst1", "clast1", "Q00085", p1);
		assertEquals("cfirst1", cc.findChild(c1.getId()).getFirstName());
		Child c2 = cc.addChild("cfirst2", "clast2", "Q00085", p2);
		assertEquals("cfirst2", cc.findChild(c2.getFirstName(), c2.getLastName()).getFirstName());
		c1.setStartDate("2017-06-12");
		cc.updateChild(c1);
		assertTrue(Paths.get(getPathStr(), "child-" + c1.getId() + ".json").toFile().exists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void badEmail() {
		try {
			cc.addParent("pfirst3", "plast3", "0433654800", "test1.gmail.com");
		} catch (IOException ioe) {
			fail();
		}
	}

	@Test(expected = IOException.class)
	public void addTwice() throws IOException {
		assertEquals("pfirst4", cc.addParent("pfirst4", "plast4", "0433654800", "test1@gmail.com").getFirstName());
		cc.addParent("pfirst4", "plast4", "0433654800", "test1@gmail.com");
		fail();
	}
}

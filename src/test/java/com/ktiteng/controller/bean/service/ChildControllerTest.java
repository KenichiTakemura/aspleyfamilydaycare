package com.ktiteng.controller.bean.service;

import static org.junit.Assert.assertEquals;
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
		Parent p1 = cc.addParent(parent1);
		assertEquals("pfirst1", cc.findParent(p1.id()).getFirstName());
		Parent p2 = cc.addParent(parent2);
		assertEquals("pfirst2", cc.findParent(p2.id()).getFirstName());
		assertEquals("pfirst2", cc.findParent(p2.getFirstName(), p2.getLastName()).getFirstName());
		assertTrue(Paths.get(getPathStr("service"), "parents.json").toFile().exists());
		p1.setBankDetail(new BankDetail().setAccount("pfirst1"));
		p1 = cc.updateParent(p1);
		Child c1 = cc.addChild(child1);
		assertEquals("cfirst1", cc.findChild(c1.id()).getFirstName());
		Child c2 = cc.addChild(child2);
		assertEquals("cfirst2", cc.findChild(c2.getFirstName(), c2.getLastName()).getFirstName());
		c1.setStartDate("2017-06-12");
		cc.updateChild(c1);
		assertTrue(Paths.get(getPathStr("service"), "child-" + c1.id() + ".json").toFile().exists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void badEmail() {
		try {
			parent3.setEmailAddress("bad.domain.com");
			cc.addParent(parent3);
		} catch (IOException ioe) {
			fail();
		}
	}

	@Test(expected = IOException.class)
	public void addTwice() throws IOException {
		assertEquals("pfirst4", cc.addParent(parent4).getFirstName());
		cc.addParent(parent4);
		fail();
	}
	
	@Test
	public void getParents() throws IOException {
		cc.getAllParents();
	}
}

package com.ktiteng.afdc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.ChildController;
import com.ktiteng.controller.PaymentController;
import com.ktiteng.controller.ReceiptController;
import com.ktiteng.entity.Parent;

public class LoadTest extends ArquillianUnitTest {

	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;
	@Inject
	ReceiptController rc;

	protected Path getPath() {
		return Paths.get(System.getProperty("user.home"), ".afdc", "master");
	}

	protected boolean getDeletePath() {
		return false;
	}

	@Before
	public void before() {
		Paths.get(getPath().toString(), "receipt").toFile().mkdirs();
	}

	@Test
	public void loadParent() throws IOException {
		Collection<Parent>  parents = cc.getAllParents();
		parents.stream().forEach(p -> log.info("Parent " + p.getFirstName()));
		assertEquals(7, parents.size());

	}

}

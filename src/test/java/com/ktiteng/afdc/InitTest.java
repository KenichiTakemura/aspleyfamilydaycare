package com.ktiteng.afdc;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.service.Child;

public class InitTest extends ArquillianUnitTest {
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;
	@Inject
	private AfdcWorkflow wf;

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

	private Child findChild(String... attrs) throws IOException {
		Child c = cc.findChild(attrs[0], attrs[1]);
		if (c == null) {
			throw new IOException("Cannot find child");
		}
		log.info("{}", c);
		return c;
	}

	// @Test
	public void channy() throws IOException {
		Child c = findChild("Channy", "Youn");
	}

	//@Test
	public void allan() throws IOException {
		Child c = findChild("Allan", "Broadhurst");
	}

	//@Test
	public void yuna() throws IOException {
		Child c = findChild("Yuna", "Cho");
	}

	//@Test
	public void ruby() throws IOException {
		Child c = findChild("Ruby", "Hong");
	}

	//@Test
	public void leon() throws IOException {
		Child c = findChild("Leon", "Jeong");
	}

	//@Test
	public void jason() throws IOException {
		Child c = findChild("Jason", "Lee");
	}

	//@Test
	public void ian() throws IOException {
		Child c = findChild("Ian", "Kim");
	}

	@Test
	public void louis() throws IOException {
		Child c = findChild("Louis", "Oh");
	}

}

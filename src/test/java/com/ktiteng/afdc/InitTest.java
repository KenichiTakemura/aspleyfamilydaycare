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
	protected AfdcWorkflow wf;

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

	protected Child findChild(String... attrs) throws IOException {
		Child c = cc.findChild(attrs[0], attrs[1]);
		if (c == null) {
			throw new IOException("Cannot find child");
		}
		log.info("{}", c);
		return c;
	}

	@Test
	public void sendNewsletter() throws IOException {
		wf.sendNewsLetter("October", "October_2017_Editionx_QLD.pdf");
	}


}

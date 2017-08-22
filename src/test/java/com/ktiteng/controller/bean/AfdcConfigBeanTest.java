package com.ktiteng.controller.bean;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Config;
import com.ktiteng.controller.AfdcConfig;

public class AfdcConfigBeanTest extends ArquillianUnitTest {

	@Inject
	@Config(configName="test.properties")
	protected AfdcConfig ac;

	@Test
	public void load() {
		assertEquals("test1@gmail.com", ac.getKaAdminEmail());
		assertEquals("test2@gmail.com", ac.getAfdcAdminEmail());
		assertEquals("test3@domain.com", ac.getGamilUserName());
		assertEquals("password", new String(ac.getGmailPassword()));
		assertEquals("\nmessage1\n\nmessage2\n\nmessage3\nmessage4\nmessage5", new String(ac.getEmailContents()));
	}
}

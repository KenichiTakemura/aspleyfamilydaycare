package com.ktiteng.afdc;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.ChildController;

public class InitTest extends ArquillianUnitTest {

	@Inject
	ChildController cc;

	// Channy: kattio85@hotmail.com
	@Test
	public void addParent() throws IOException {
		cc.addParent("Jihyun", "Broadhurst");
	}

}

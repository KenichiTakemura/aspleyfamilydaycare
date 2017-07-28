package com.ktiteng.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class ParentControllerTest {

	private Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");
	
	@Test
	public void addParent() throws IOException {
		ParentController pc = new ParentControllerImpl();
		pc.addParent("firstName", "lastName", "0433654800", "test@gmail.com");
		assertTrue(Paths.get(path.toString(), "firstName-lastName.json").toFile().exists());
	}
}

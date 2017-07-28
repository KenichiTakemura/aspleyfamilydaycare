package com.ktiteng.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.ktiteng.entity.Parent;

public class ChildControllerTest {

	private Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");
	
	@Test
	public void addParents() throws IOException {
		ChildController cc = new ChldControllerImpl();
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Parent p2 = cc.addParent("pfirst1", "plast2", "0433654801", "test2@gmail.com");
		assertTrue(Paths.get(path.toString(), "parent.json").toFile().exists());
		cc.addChild("cfirst1", "clast1", p1);
		cc.addChild("cfirst2", "clast2", p2);
	}
}

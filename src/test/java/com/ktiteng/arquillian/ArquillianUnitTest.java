package com.ktiteng.arquillian;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.manager.PersistenceManager;

@RunWith(Arquillian.class)
public class ArquillianUnitTest {
	@Inject
	@Log
	protected Logger log;

	private Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackages(true, "com.ktiteng")
				.addPackages(true, "org.apache.commons.io").addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	PersistenceManager pm;

	@Before
	public void before() throws IOException {
		pm.setPath(path);
		FileUtils.deleteDirectory(path.toFile());
		path.toFile().mkdirs();
	}

	protected Path getPath() {
		return path;
	}

	protected String getPathStr() {
		return path.toString();
	}
}

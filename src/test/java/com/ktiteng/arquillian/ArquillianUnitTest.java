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
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.manager.PersistenceManager;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;

@RunWith(Arquillian.class)
public class ArquillianUnitTest {
	@Inject
	@Log
	protected Logger log;

	private static Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");

	protected Parent parent1 = new Parent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
	protected Parent parent2 = new Parent("pfirst2", "plast2", "0433654801", "test2@gmail.com");
	protected Parent parent3 = new Parent("pfirst3", "plast3", "0433654803", "test3@gmail.com");
	protected Parent parent4 = new Parent("pfirst4", "plast4", "0433654804", "test4@gmail.com");
	protected Child child1 = new Child("cfirst1", "clast1", "Q00081", parent1.id());
	protected Child child2 = new Child("cfirst2", "clast2", "Q00082", parent2.id());
	protected Child child3 = new Child("cfirst3", "clast3", "Q00083", parent3.id());
	protected Child child4 = new Child("cfirst4", "clast4", "Q00084", parent4.id());

	@BeforeClass
	public static void beforeClass() {
		System.setProperty(PersistenceManager.DATA_PATH_PROP, path.toString());
	}

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackages(true, "com.ktiteng")
				.addPackages(true, "org.apache.commons.io")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		System.out.println(jar.getContent());
		return jar;
	}

	@Inject
	PersistenceManager pm;

	@Before
	public void before() throws IOException {
		pm.setPath(getPath());
		if (getDeletePath()) {
			FileUtils.deleteDirectory(getPath().toFile());
			pm.getServicePath().toFile().mkdirs();
			pm.getAccountPath().toFile().mkdirs();
		}
		afterBefore();
	}
	
	protected void afterBefore() {
		
	}

	protected boolean getDeletePath() {
		return true;
	}

	protected Path getPath() {
		return path;
	}

	protected String getPathStr(String dir) {
		return path.resolve(dir).toString();
	}
	
	protected void assertDoubleEquals(double e, double a) {
		org.junit.Assert.assertEquals(e, a, 0.01);
	}
}

package com.ktiteng.entity.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktiteng.cdi.Log;

@Singleton
public class PersistenceManager {

	@Inject
	@Log
	private Logger log;

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final String ext = "json";
	private static final String join = ".";

	private Path path;

	@PostConstruct
	public void init() {
		path = Paths.get(System.getProperty("user.home"), ".afdc", "master");
		path.toFile().mkdirs();
		log.info("produced.");
	}

	public void setPath(Path path) {
		this.path = path;
		log.info("path changed to {}.", path);
	}

	private String getFilePath(String fileName) {
		return Paths.get(path.toString(), fileName).toString();
	}

	protected void persist(Object entity, String fileName) throws IOException {
		try (Writer writer = new FileWriter(getFilePath(String.join(join, fileName, ext)))) {
			gson.toJson(entity, writer);
		}
	}

}

package com.ktiteng.entity.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktiteng.entity.BaseEntity;

public class PersistenceManager {

	private static PersistenceManager instance;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final String ext = "json";
	private static final String join = ".";

	private static final Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");

	private PersistenceManager() {
	}

	public static PersistenceManager getInstance() {
		if (instance == null) {
			path.toFile().mkdirs();
			instance = new PersistenceManager();
		}
		return instance;
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

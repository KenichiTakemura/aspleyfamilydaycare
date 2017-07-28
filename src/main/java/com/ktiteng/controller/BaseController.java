package com.ktiteng.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktiteng.entity.BaseEntity;

public abstract class BaseController {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final String EXT = "json";
	private static final String JOIN = ".";

	private static final Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");

	static {
		path.toFile().mkdirs();
	}
	
	private String getFilePath(String fileName) {
		return Paths.get(path.toString(), fileName).toString();
	}

	protected void persist(BaseEntity entity, String fileName) throws IOException {
		try (Writer writer = new FileWriter(getFilePath(String.join(JOIN, fileName, EXT)))) {
			gson.toJson(entity, writer);
		}
	}

}

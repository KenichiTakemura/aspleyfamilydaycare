package com.ktiteng.entity.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.ktiteng.cdi.Log;

@Singleton
public class PersistenceManager {

	@Inject
	@Log
	private Logger log;

	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.create();
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

	public Path getPath() {
		return path;
	}

	private String getFilePath(String fileName) {
		return Paths.get(path.toString(), fileName).toString();
	}

	protected Object load(Type entityType, String filename) {
		Reader in = null;
		try {
			String jsonFile = filename.endsWith(ext) ? filename : String.join(join, filename, ext);
			in = new FileReader(new File(getPath().toString(), jsonFile));
			JsonReader reader = new JsonReader(in);
			Object data = gson.fromJson(reader, entityType);
			log.info("Loaded from {}", filename);
			return data;
		} catch (Exception e) {
			log.warn("Cannot load. {}", e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.warn("Cannot close the reader.", e);
				}
			}
		}
		return null;
	}

	protected void persist(Object entity, Type entityType, String filename) throws IOException {
		try (Writer writer = new FileWriter(getFilePath(String.join(join, filename, ext)))) {
			gson.toJson(entity, writer);
		}
	}

}

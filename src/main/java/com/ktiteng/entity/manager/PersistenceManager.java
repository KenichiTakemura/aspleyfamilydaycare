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
	public static final String DATA_PATH_PROP = "com.ktiteng.afdc.data.path";

	private Path path;

	@PostConstruct
	public void init() {
		path = System.getProperty(DATA_PATH_PROP) != null ?
			Paths.get(System.getProperty(DATA_PATH_PROP)) :
				Paths.get(System.getProperty("user.home"), ".afdc", "master");
		path.toFile().mkdirs();
		log.info("produced. with path {}", path);
	}

	public void setPath(Path path) {
		this.path = path;
		log.info("path changed to {}.", path);
	}

	public Path getServicePath() {
		return path.resolve("service");
	}

	public Path getAccountPath() {
		return path.resolve("bas");
	}
	
	Object load(Type entityType, Path filePath, String filename) {
		Reader in = null;
		try {
			String jsonFile = filename.endsWith(ext) ? filePath.toString() : String.join(join, filePath.toString(), filename, ext);
			in = new FileReader(new File(jsonFile));
			JsonReader reader = new JsonReader(in);
			Object data = gson.fromJson(reader, entityType);
			log.info("Loaded from {}", jsonFile);
			return data;
		} catch (Exception e) {
			log.warn("Cannot load from {} {}", filePath, e.getMessage());
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

	void persist(Object entity, Type entityType, Path path, String filename) throws IOException {
		if (!path.toFile().exists()) {
			path.toFile().mkdirs();
		}
		try (Writer writer = new FileWriter(path.resolve(String.join(join, filename, ext)).toFile())) {
			gson.toJson(entity, writer);
		}
	}

}

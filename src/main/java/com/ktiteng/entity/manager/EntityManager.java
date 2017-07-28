package com.ktiteng.entity.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;

public class EntityManager {

	private final static String parent = "parent";
	private final static String child = "child-";
	private static EntityManager instance;
	private PersistenceManager pm;
	List<Parent> parents;
	List<Child> children;

	private EntityManager() {
		pm = PersistenceManager.getInstance();
		parents = new ArrayList<>();
		children = new ArrayList<>();
	}

	public static EntityManager getInstance() {
		if (instance == null) {
			instance = new EntityManager();
		}
		return instance;
	}

	public void save(BaseEntity entity) throws IOException {
		if (entity instanceof Parent) {
			parents.add((Parent) entity);
			pm.persist(parents, parent);
		}
		if (entity instanceof Child) {
			children.add((Child) entity);
			pm.persist(entity, child + entity.getId());
		}
	}
	

}

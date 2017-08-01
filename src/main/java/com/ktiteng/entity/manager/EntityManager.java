package com.ktiteng.entity.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.TimeCard;

public class EntityManager {

	private final static String parent = "parent";
	private final static String child = "child-";
	private final static String payment = "payment-";
	private final static String timecard= "timecard-";

	private static EntityManager instance;
	private PersistenceManager pm;
	List<Parent> parents;
	List<Child> children;
	List<Payment> payments;
	List<TimeCard> timecards;

	private EntityManager() {
		pm = PersistenceManager.getInstance();
		parents = new ArrayList<>();
		children = new ArrayList<>();
		payments = new ArrayList<>();
		timecards = new ArrayList<>();
	}

	public static EntityManager getInstance() {
		if (instance == null) {
			instance = new EntityManager();
		}
		return instance;
	}

	public void save(BaseEntity entity) throws IOException {
		if (entity instanceof Parent) {
			parents.removeIf(p -> p.getId().equals(entity.getId()));
			parents.add((Parent) entity);
			pm.persist(parents, parent);
		} else if (entity instanceof Child) {
			children.removeIf(c -> c.getId().equals(entity.getId()));
			children.add((Child) entity);
			pm.persist(entity, child + entity.getId());
		} else if (entity instanceof Payment) {
			payments.removeIf(c -> c.getId().equals(entity.getId()));
			payments.add((Payment) entity);
			pm.persist(entity, payment + entity.getId());
		} else if (entity instanceof TimeCard) {
			timecards.removeIf(c -> c.getId().equals(entity.getId()));
			timecards.add((TimeCard) entity);
			pm.persist(entity, timecard + entity.getId());
		}
	}

	public BaseEntity find(Class<?> entityClass, String id) {
		if (entityClass.isAssignableFrom(Payment.class)) {
			Optional<Payment> payment = payments.stream().filter(p -> p.getChildId().equals(id)).findFirst();
			return payment.isPresent() ? payment.get() : null;
		}
		return null;
		
	}

}

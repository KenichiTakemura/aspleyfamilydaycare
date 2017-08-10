package com.ktiteng.entity.manager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.google.gson.reflect.TypeToken;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.TaxInvoiceSeeder;
import com.ktiteng.entity.TimeCard;

@Singleton
public class EntityManager {

	@Inject
	@Log
	private Logger log;

	@Inject
	PersistenceManager pm;

	private final static String parent = "parent";
	private final static String child = "child-";
	private final static String payment = "payment-";
	private final static String timecard = "timecard-";
	private final static String taxinvoiceseeder = "taxinvoiceseeder";

	List<Parent> parents;
	Type parentType = new TypeToken<ArrayList<Parent>>() {
	}.getType();
	List<Child> children;
	Type childType = new TypeToken<ArrayList<Child>>() {
	}.getType();
	List<Payment> payments;
	Type paymentType = new TypeToken<ArrayList<Payment>>() {
	}.getType();
	List<TimeCard> timecards;
	Type timecardType = new TypeToken<ArrayList<TimeCard>>() {
	}.getType();
	TaxInvoiceSeeder taxInvoiceSeeder;

	@PostConstruct
	public void init() {
		onLoad();
		parents = new ArrayList<>();
		children = new ArrayList<>();
		payments = new ArrayList<>();
		timecards = new ArrayList<>();
		if (parents == null) {
			parents = new ArrayList<>();
		}
		if (taxInvoiceSeeder == null) {
			taxInvoiceSeeder = new TaxInvoiceSeeder();
		}
		log.info("produced.");
	}

	void onLoad() {
		Type parentType = new TypeToken<ArrayList<Parent>>() {
		}.getType();
		parents = (List<Parent>) pm.load(parentType, parent);
		taxInvoiceSeeder = (TaxInvoiceSeeder) pm.load(TaxInvoiceSeeder.class, taxinvoiceseeder);
	}

	public void save(BaseEntity entity) throws IOException {
		log.info("save {}", entity);
		if (entity instanceof Parent) {
			parents.removeIf(p -> p.getId().equals(entity.getId()));
			parents.add((Parent) entity);
			pm.persist(parents, parentType, parent);
		} else if (entity instanceof Child) {
			children.removeIf(c -> c.getId().equals(entity.getId()));
			children.add((Child) entity);
			pm.persist(entity, childType, child + entity.getId());
		} else if (entity instanceof Payment) {
			payments.removeIf(c -> c.getId().equals(entity.getId()));
			payments.add((Payment) entity);
			pm.persist(entity, parentType, payment + entity.getId());
		} else if (entity instanceof TimeCard) {
			timecards.removeIf(c -> c.getId().equals(entity.getId()));
			timecards.add((TimeCard) entity);
			pm.persist(entity, timecardType, timecard + entity.getId());
		} else if (entity instanceof TaxInvoiceSeeder) {
			pm.persist(taxInvoiceSeeder, taxInvoiceSeeder.getClass(), taxinvoiceseeder);
		}
	}

	public BaseEntity get(Class<?> entityClass) {
		if (entityClass.isAssignableFrom(TaxInvoiceSeeder.class)) {
			return taxInvoiceSeeder;
		}
		return null;
	}

	public BaseEntity find(Class<?> entityClass, String... ids) {
		if (entityClass.isAssignableFrom(Parent.class)) {
			Optional<Parent> parent = parents.stream().filter(p -> p.getId().equals(ids[0])).findFirst();
			return parent.isPresent() ? parent.get() : null;
		} else if (entityClass.isAssignableFrom(Child.class)) {
			Optional<Child> child = children.stream().filter(p -> p.getId().equals(ids[0])).findFirst();
			return child.isPresent() ? child.get() : null;
		} else if (entityClass.isAssignableFrom(Payment.class)) {
			Optional<Payment> payment = payments.stream().filter(p -> p.getChildId().equals(ids[0])).findFirst();
			return payment.isPresent() ? payment.get() : null;
		}
		return null;
	}

}

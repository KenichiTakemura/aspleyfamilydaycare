package com.ktiteng.entity.manager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
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
import com.ktiteng.entity.EntityBag;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.TaxInvoiceSeeder;
import com.ktiteng.entity.service.TimeCard;

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
	List<Child> children;
	List<Payment> payments;
	List<TimeCard> timecards;
	TaxInvoiceSeeder taxInvoiceSeeder;

	@PostConstruct
	public void init() {
		onLoad();
		timecards = new ArrayList<>();
		if (parents == null) {
			log.info("Create a new parent list");
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
		children = new ArrayList<>();
		payments = new ArrayList<>();
		try {
			Files.newDirectoryStream(pm.getPath(),
					path -> path.toFile().isFile() && path.toFile().getName().startsWith(child)).forEach(file -> {
						children.add((Child) pm.load(Child.class, file.toFile().getName()));
					});
		} catch (IOException e) {
			log.warn("Cannot load children");
		}
		try {
			Files.newDirectoryStream(pm.getPath(),
					path -> path.toFile().isFile() && path.toFile().getName().startsWith(payment)).forEach(file -> {
						payments.add((Payment) pm.load(Payment.class, file.toFile().getName()));
					});
		} catch (IOException e) {
			log.warn("Cannot load payment");
		}
		taxInvoiceSeeder = (TaxInvoiceSeeder) pm.load(TaxInvoiceSeeder.class, taxinvoiceseeder);
	}

	public void save(BaseEntity entity) throws IOException {
		log.info("save {}", entity);
		if (entity instanceof Parent) {
			parents.removeIf(p -> p != null && p.getId().equals(entity.getId()));
			parents.add((Parent) entity);
			pm.persist(parents, getParentType(), parent);
		} else if (entity instanceof Child) {
			children.removeIf(c -> c != null && c.getId().equals(entity.getId()));
			children.add((Child) entity);
			pm.persist(entity, Child.class, child + entity.getId());
		} else if (entity instanceof Payment) {
			payments.removeIf(p -> p != null && p.getId().equals(entity.getId()));
			payments.add((Payment) entity);
			pm.persist(entity, Payment.class, payment + entity.getId());
		} else if (entity instanceof TimeCard) {
			timecards.removeIf(t -> t != null && t.getId().equals(entity.getId()));
			timecards.add((TimeCard) entity);
			pm.persist(entity, TimeCard.class, timecard + entity.getId());
		} else if (entity instanceof TaxInvoiceSeeder) {
			pm.persist(taxInvoiceSeeder, taxInvoiceSeeder.getClass(), taxinvoiceseeder);
		}
	}

	public EntityBag getAll(Class<?> entityClass) {
		EntityBag bag = new EntityBag();
		if (entityClass.isAssignableFrom(Parent.class)) {
			return bag.setEntity(parents);
		} else if (entityClass.isAssignableFrom(Child.class)) {
			return bag.setEntity(children);
		} else if (entityClass.isAssignableFrom(Payment.class)) {
			return bag.setEntity(payments);
		}
		return bag;
	}

	public BaseEntity get(Class<?> entityClass) {
		if (entityClass.isAssignableFrom(TaxInvoiceSeeder.class)) {
			return taxInvoiceSeeder;
		}
		return null;
	}

	public BaseEntity find(Class<?> entityClass, String... ids) {
		if (entityClass.isAssignableFrom(Parent.class)) {
			Optional<Parent> parent = (ids.length == 1)
					? parents.stream().filter(p -> p.getId().equals(ids[0])).findFirst()
					: parents.stream().filter(p -> p.getFirstName().equals(ids[0]) && p.getLastName().equals(ids[1]))
							.findFirst();
			return parent.isPresent() ? parent.get() : null;
		} else if (entityClass.isAssignableFrom(Child.class)) {
			Optional<Child> child = (ids.length == 1)
					? children.stream().filter(c -> c.getId().equals(ids[0])).findFirst()
					: children.stream().filter(c -> c.getFirstName().equals(ids[0]) && c.getLastName().equals(ids[1]))
							.findFirst();
			return child.isPresent() ? child.get() : null;
		} else if (entityClass.isAssignableFrom(Payment.class)) {
			Optional<Payment> payment = payments.stream().filter(p -> p.getChildId().equals(ids[0])).findFirst();
			return payment.isPresent() ? payment.get() : null;
		}
		return null;
	}

	public static Type getParentType() {
		return new TypeToken<ArrayList<Parent>>() {
		}.getType();
	}

}

package com.ktiteng.entity.manager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.ktiteng.cdi.Log;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.EntityBag;
import com.ktiteng.entity.account.BankTransaction;
import com.ktiteng.entity.account.Banks;
import com.ktiteng.entity.account.BasQuarter;
import com.ktiteng.entity.account.BasQuarterly;
import com.ktiteng.entity.account.BasQuarterlyDetails;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.Receipt;
import com.ktiteng.entity.service.TaxInvoiceSeeder;
import com.ktiteng.entity.service.TimeCard;

@Singleton
public class EntityManager {

	@Inject
	@Log
	private Logger log;

	@Inject
	PersistenceManager pm;

	private final static String _parents = "parents";
	private final static String _child = "child-";
	private final static String _payment = "payment-";
	private final static String _timecard = "timecard-";
	private final static String _receipts = "receipts";
	private final static String _taxinvoiceseeder = "taxinvoiceseeder";
	private final static String _banktransaction = "banktransaction-";
	private final static String _basquarter = "basquarter";
	private final static String _basquarterdetails = "basquarterdetails";
	List<Parent> parents;
	List<Child> children;
	List<Receipt> receipts;
	List<Payment> payments;
	List<TimeCard> timecards;
	TaxInvoiceSeeder taxInvoiceSeeder;
	List<BankTransaction> bankTransactions;
	List<BasQuarterly> basquarters;
	List<BasQuarterlyDetails> basQuarterlyDetails;
	@PostConstruct
	public void init() {
		onLoad();
		log.info("produced.");
	}

	void onLoad() {
		parents = (List<Parent>) pm.load(getParentType(), pm.getServicePath(), _parents);
		if (parents == null) {
			log.info("Create a new parent list");
			parents = Lists.newArrayList();
		}
		receipts = (List<Receipt>) pm.load(getReceiptType(), pm.getServicePath(), _receipts);
		if (receipts == null) {
			log.info("Create a new receipt list");
			receipts = Lists.newArrayList();
		}
		taxInvoiceSeeder = (TaxInvoiceSeeder) pm.load(TaxInvoiceSeeder.class, pm.getServicePath(), _taxinvoiceseeder);
		if (taxInvoiceSeeder == null) {
			log.info("Create a new TaxInvoiceSeeder");
			taxInvoiceSeeder = new TaxInvoiceSeeder();
		}
		timecards = Lists.newArrayList();
		children = Lists.newArrayList();
		payments = Lists.newArrayList();
		bankTransactions = Lists.newArrayList();
		basquarters = Lists.newArrayList();
		basQuarterlyDetails = Lists.newArrayList();
		try {
			Files.newDirectoryStream(pm.getServicePath(),
					path -> path.toFile().isFile() && path.toFile().getName().startsWith(_child)).forEach(file -> {
						children.add((Child) pm.load(Child.class, pm.getServicePath(), file.toFile().getName()));
					});
		} catch (IOException e) {
			log.warn("Cannot load Child");
		}
		try {
			Files.newDirectoryStream(pm.getServicePath(),
					path -> path.toFile().isFile() && path.toFile().getName().startsWith(_payment)).forEach(file -> {
						payments.add((Payment) pm.load(Payment.class, pm.getServicePath(), file.toFile().getName()));
					});
		} catch (IOException e) {
			log.warn("Cannot load Payment");
		}
		try {
			Files.newDirectoryStream(pm.getAccountPath(),
					path -> path.toFile().isFile() && path.toFile().getName().startsWith(_banktransaction))
					.forEach(file -> {
						bankTransactions.add((BankTransaction) pm.load(BankTransaction.class, pm.getAccountPath(),
								file.toFile().getName()));
					});
		} catch (IOException e) {
			log.warn("Cannot load BankTransaction");
		}
		try {
			Files.newDirectoryStream(pm.getAccountPath(),
					path -> path.toFile().isFile() && path.toFile().getName().startsWith(_banktransaction))
					.forEach(file -> {
						bankTransactions.add((BankTransaction) pm.load(BankTransaction.class, pm.getAccountPath(),
								file.toFile().getName()));
					});
		} catch (IOException e) {
			log.warn("Cannot load BankTransaction");
		}
	}

	public void save(BaseEntity entity) throws IOException {
		log.info("save {}", entity);
		if (entity instanceof Parent) {
			parents.removeIf(p -> p != null && p.id().equals(entity.id()));
			parents.add((Parent) entity);
			pm.persist(parents, getParentType(), pm.getServicePath(), _parents);
		} else if (entity instanceof Child) {
			children.removeIf(c -> c != null && c.id().equals(entity.id()));
			children.add((Child) entity);
			pm.persist(entity, Child.class, pm.getServicePath(), _child + entity.id());
		} else if (entity instanceof Payment) {
			payments.removeIf(p -> p != null && p.id().equals(entity.id()));
			payments.add((Payment) entity);
			pm.persist(entity, Payment.class, pm.getServicePath(), _payment + entity.id());
		} else if (entity instanceof TimeCard) {
			timecards.removeIf(t -> t != null && t.id().equals(entity.id()));
			timecards.add((TimeCard) entity);
			pm.persist(entity, TimeCard.class, pm.getServicePath(), _timecard + entity.id());
		} else if (entity instanceof Receipt) {
			receipts.removeIf(p -> p != null && p.id().equals(entity.id()));
			receipts.add((Receipt) entity);
			pm.persist(receipts, getReceiptType(), pm.getServicePath(), _receipts);
		} else if (entity instanceof TaxInvoiceSeeder) {
			pm.persist(taxInvoiceSeeder, taxInvoiceSeeder.getClass(), pm.getServicePath(), _taxinvoiceseeder);
		} else if (entity instanceof BankTransaction) {
			bankTransactions.removeIf(e -> e != null && e.id().equals(entity.id()));
			bankTransactions.add((BankTransaction) entity);
			Banks bank = ((BankTransaction) entity).getBanks();
			if (bank == null) {
				throw new IOException("Bank name is required");
			}
			pm.persist(entity, BankTransaction.class, pm.getAccountPath(),
					_banktransaction + ((BankTransaction) entity).getBanks());
		} else if (entity instanceof BasQuarterly) {
			basquarters.removeIf(e -> e != null && e.id().equals(entity.id()));
			basquarters.add((BasQuarterly) entity);
			pm.persist(entity, BasQuarterly.class,
					pm.getAccountPath().resolve(((BasQuarterly) entity).getQuarter().name()), _basquarter);
		} else if (entity instanceof BasQuarterlyDetails) {
			basQuarterlyDetails.removeIf(e -> e != null && e.id().equals(entity.id()));
			basQuarterlyDetails.add((BasQuarterlyDetails) entity);
			BasQuarterlyDetails e = (BasQuarterlyDetails) entity;
			BasQuarterly basQuarterly = (BasQuarterly) find(BasQuarterly.class, new String[] { e.getBasQuarterId() });
			pm.persist(entity, BasQuarterlyDetails.class, pm.getAccountPath().resolve(basQuarterly.getQuarter().name()),
					_basquarterdetails);
		} else {
			throw new IOException("Unknown entity " + entity);
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

	public BaseEntity find(Class<?> entityClass, Object... params) {
		if (entityClass.isAssignableFrom(Parent.class)) {
			return params.length == 1 ? parents.stream().filter(p -> p.id().equals(params[0])).findFirst().orElse(null)
					: parents.stream()
							.filter(p -> p.getFirstName().equals(params[0]) && p.getLastName().equals(params[1]))
							.findFirst().orElse(null);
		} else if (entityClass.isAssignableFrom(Child.class)) {
			return params.length == 1 ? children.stream().filter(c -> c.id().equals(params[0])).findFirst().orElse(null)
					: children.stream()
							.filter(c -> c.getFirstName().equals(params[0]) && c.getLastName().equals(params[1]))
							.findFirst().orElse(null);
		} else if (entityClass.isAssignableFrom(Payment.class)) {
			return payments.stream().filter(p -> p != null && p.getChildId().equals(params[0])).findFirst()
					.orElse(null);
		} else if (entityClass.isAssignableFrom(Receipt.class)) {
			Receipt receipt = receipts.stream().filter(r -> r != null && r.id().equals(params[0])).findFirst()
					.orElse(null);
			return receipt == null ? receipts.stream().filter(r -> r != null && r.getPayableId().equals(params[0]))
					.findFirst().orElse(null) : receipt;
		} else if (entityClass.isAssignableFrom(BankTransaction.class)) {
			return bankTransactions.stream().filter(b -> b.getBanks().toString().equals(params[0])).findFirst()
					.orElse(null);
		} else if (entityClass.isAssignableFrom(BasQuarterly.class)) {
			if (params[0] instanceof String) {
				return basquarters.stream().filter(b -> b.id().equals(params[0])).findFirst()
					.orElse(null);
			} else if (params[0] instanceof BasQuarter) {
				return basquarters.stream().filter(b -> b.getQuarter() == params[0]).findFirst()
						.orElse(null);
			}
		} else if (entityClass.isAssignableFrom(BasQuarterlyDetails.class)) {
			if (params[0] instanceof String) {
				return basQuarterlyDetails.stream().filter(b -> b.id().equals(params[0])).findFirst()
					.orElse(null);
			}
		}

		return null;
	}

	public static Type getParentType() {
		return new TypeToken<ArrayList<Parent>>() {
		}.getType();
	}

	public static Type getReceiptType() {
		return new TypeToken<ArrayList<Receipt>>() {
		}.getType();
	}
}

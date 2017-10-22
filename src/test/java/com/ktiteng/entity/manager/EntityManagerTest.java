package com.ktiteng.entity.manager;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.entity.account.BankTransaction;
import com.ktiteng.entity.account.Banks;
import com.ktiteng.entity.account.Transaction;

public class EntityManagerTest extends ArquillianUnitTest {

	@Inject
	EntityManager em;

	@Test
	public void saveAndFind() throws IOException {
		em.save(new BankTransaction(Banks.SUNCORP).addTransaction(new Transaction(null, "desc", 1.0, 1.0)));
		BankTransaction bankTransaction = (BankTransaction) em.find(BankTransaction.class, new String[]{Banks.SUNCORP.toString()});
		assertNotNull(bankTransaction.getGeneratedAt());
	}

}

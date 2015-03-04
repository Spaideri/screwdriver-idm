package org.screwdriver.idm.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.screwdriver.idm.config.DataSourceConfig;
import org.screwdriver.idm.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.flyway.core.Flyway;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataSourceConfig.class })
public class AccountRepositoryTest {

	private static final String USER_NAME = "testUsername";
	private static final String PASSWORD = "testPassword";
	private static final int NUMBER_OF_ACCOUNTS = 3;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private Flyway flyway;

	private Account account;

	@Before
	public void setup() {
		flyway.clean();
		flyway.migrate();
		account = accountRepository.findByUsername(USER_NAME);
	}

	@Test
	public void shouldFindAccountByUserName() {
		assertEquals(PASSWORD, account.getPassword());
	}

	@Test
	public void shouldHaveThreeAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		Iterable<Account> all = accountRepository.findAll();
		Iterator<Account> iterator = all.iterator();
		while (iterator.hasNext()) {
			Account account = iterator.next();
			accounts.add(account);
		}
		assertEquals(NUMBER_OF_ACCOUNTS, accounts.size());
	}
}

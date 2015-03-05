package org.screwdriver.idm.repository;

import com.googlecode.flyway.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.screwdriver.idm.config.DataSourceConfig;
import org.screwdriver.idm.entity.AccessGroup;
import org.screwdriver.idm.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataSourceConfig.class })
public class AccountRepositoryTest {

	private static final String USER_NAME = "testUsername";
	private static final String PASSWORD = "testPassword";
	private static final int NUMBER_OF_ACCOUNTS = 3;
    private static final String ACCESS_GROUP_NAME = "Super users";

    @Autowired
	private AccountRepository accountRepository;

	@Autowired
	private Flyway flyway;

	@Before
	public void setup() {
		flyway.clean();
		flyway.migrate();
	}

	@Test
	public void shouldFindAccountByUserName() {
        Account account = accountRepository.findByUsername(USER_NAME);
		assertEquals(PASSWORD, account.getPassword());
	}
    
    @Test
    @Transactional
    public void accountShouldHaveAccessGroup() {
        Account account = accountRepository.findByUsername(USER_NAME);
        List<AccessGroup> accessGroups = account.getAccessGroups();
        AccessGroup accessGroup = accessGroups.get(0);
        
        assertEquals(ACCESS_GROUP_NAME, accessGroup.getName());

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

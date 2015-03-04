package org.screwdriver.dto;

import static org.junit.Assert.assertEquals;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.screwdriver.entity.Account;

public class AccountDTOTest {

    private DozerBeanMapper mapper = new DozerBeanMapper();

    private Account accountEntity;

    private final static Long ID = 3445L;

    private final static String USERNAME = "username";

    private final static String PASSWORD = "password";

    @Before
    public void setup() {
        this.accountEntity = new Account.Builder().id(ID).username(USERNAME).password(PASSWORD).build();
    }


    @Test
    public void shouldMapEntityToDTO() {
        AccountDTO dto = mapper.map(accountEntity, AccountDTO.class);

        assertEquals(ID, dto.getId());
        assertEquals(USERNAME, dto.getUsername());
    }
}

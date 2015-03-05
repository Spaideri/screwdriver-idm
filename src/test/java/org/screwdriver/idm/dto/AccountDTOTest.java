package org.screwdriver.idm.dto;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.screwdriver.idm.entity.AccessGroup;
import org.screwdriver.idm.entity.Account;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AccountDTOTest {

    private DozerBeanMapper mapper = new DozerBeanMapper();

    private Account accountEntity;

    private AccessGroup accessGroupEntity;

    private final static Long ID = 3445L;

    private final static String USERNAME = "username";

    private final static String ACCESS_GROUP_NAME = "groupname";

    private final static String PASSWORD = "password";

    @Before
    public void setup() {
        this.accessGroupEntity = new AccessGroup.Builder().name(ACCESS_GROUP_NAME).build();
        this.accountEntity = new Account.Builder().id(ID).username(USERNAME).password(PASSWORD).accessGroups(Arrays.asList(accessGroupEntity)).build();
    }


    @Test
    public void shouldMapEntityToDTO() {
        AccountDTO dto = mapper.map(accountEntity, AccountDTO.class);

        assertEquals(ID, dto.getId());
        assertEquals(USERNAME, dto.getUsername());
        assertEquals(ACCESS_GROUP_NAME, dto.getAccessGroups().get(0).getName());
    }
}

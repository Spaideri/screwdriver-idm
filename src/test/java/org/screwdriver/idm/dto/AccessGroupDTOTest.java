package org.screwdriver.idm.dto;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.screwdriver.idm.entity.AccessGroup;

import static org.junit.Assert.*;

public class AccessGroupDTOTest {

    private DozerBeanMapper mapper = new DozerBeanMapper();

    private AccessGroup accessGroup;

    private final static Long ID = 3445L;

    private final static String NAME = "Group name";


    @Before
    public void setup() {
        accessGroup = new AccessGroup.Builder().id(ID).name(NAME).build();
    }


    @Test
    public void shouldMapEntityToDTO() {
        AccessGroupDTO dto = mapper.map(accessGroup, AccessGroupDTO.class);

        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }
}

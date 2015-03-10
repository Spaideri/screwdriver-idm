package org.screwdriver.idm.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TokenDTOTest {



    private TokenDTO tokenDTO;

    private ObjectMapper mapper = new ObjectMapper();

    private final static Long ID = 3445L;

    private final static String USERNAME = "username";

    private static final Long GROUP_ID = 123L;

    private static final String GROUP_NAME = "Group name";

    private final static String TIMESTAMP = "2015-03-04T21:53:31.123+0200";

    private final static String MAC = "MAC123XYZ";

    private final static String JSON_TOKEN = "{" +
        "\"account\":{" +
            "\"id\":" + ID + "," +
            "\"username\":\"" + USERNAME + "\"," +
            "\"accessGroups\":[{" +
                "\"id\":" + GROUP_ID + ","+
                "\"name\":\"" + GROUP_NAME + "\""+
            "}]"+
        "},"+
        "\"expireTime\":\"" + TIMESTAMP +"\"," +
        "\"mac\":\""+ MAC + "\"" +
    "}";

    @Before
    public void setup() {
        AccessGroupDTO accessGroupDTO = new AccessGroupDTO();
        accessGroupDTO.setId(GROUP_ID);
        accessGroupDTO.setName(GROUP_NAME);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(ID);
        accountDTO.setUsername(USERNAME);
        accountDTO.setAccessGroups(Arrays.asList(accessGroupDTO));
        this.tokenDTO = new TokenDTO();
        this.tokenDTO.setAccount(accountDTO);
        this.tokenDTO.setExpireTime(TIMESTAMP);
        this.tokenDTO.setMac(MAC);
    }

    @Test
    public void shouldConvertJSONToTokenDTO() throws Exception {
        TokenDTO convertedTokenDTO = mapper.readValue(JSON_TOKEN, TokenDTO.class);

        assertEquals(ID, convertedTokenDTO.getAccount().getId());
        assertEquals(USERNAME, convertedTokenDTO.getAccount().getUsername());
        assertEquals(TIMESTAMP, convertedTokenDTO.getExpireTime());
        assertEquals(MAC, convertedTokenDTO.getMac());
        assertEquals(GROUP_ID, convertedTokenDTO.getAccount().getAccessGroups().get(0).getId());
    }

    @Test
    public void shouldConvertTokenDTOToJSON() throws Exception {
        String tokenJSON = mapper.writeValueAsString(tokenDTO);
        assertEquals(JSON_TOKEN, tokenJSON);
    }
}

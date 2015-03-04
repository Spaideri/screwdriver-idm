package org.screwdriver.idm.dto;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class TokenDTOTest {

    private TokenDTO tokenDTO;

    private ObjectMapper mapper = new ObjectMapper();

    private final static Long ID = 3445L;

    private final static String USERNAME = "username";

    private final static String TIMESTAMP = "2015-03-04T21:53:31.123+0200";

    private final static String MAC = "MAC123XYZ";

    private final static String JSON_TOKEN = "{" +
            "\"account\":{" +
            "\"id\":" + ID + "," +
            "\"username\":\"" + USERNAME + "\"" +
            "}," +
            "\"expireTime\":\"" + TIMESTAMP +"\"," +
            "\"mac\":\""+ MAC + "\"" +
            "}";

    @Before
    public void setup() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(ID);
        accountDTO.setUsername(USERNAME);
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

    }

    @Test
    public void shouldConvertTokenDTOToJSON() throws Exception {
        String tokenJSON = mapper.writeValueAsString(tokenDTO);
        assertEquals(JSON_TOKEN, tokenJSON);
    }
}

package org.screwdriver.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mock;
import org.screwdriver.dto.AccountDTO;
import org.screwdriver.dto.TokenDTO;
import org.screwdriver.entity.Account;

import java.security.MessageDigest;
import java.util.Base64;


public class TokenServiceTest {

    private TokenService tokenService;

    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private TimeService timeService;

    private AccountDTO account;

    private final static Long ID = 321l;

    private final static String USERNAME = "user name";

    private final static String TOKEN_SECRET = "SECRET123XXX";

    private final static String TIMESTAMP = "2015-03-04T21:53:31.123+0200";


    @Before
    public void setup(){
        initMocks(this);
        this.tokenService = new TokenService(new ObjectMapper(), timeService, TOKEN_SECRET);
        this.account = new AccountDTO();
        this.account.setId(ID);
        this.account.setUsername(USERNAME);
        when(timeService.getNewTokenTimestampISO()).thenReturn(TIMESTAMP);
    }

    @Test
    public void shouldReturnValidToken() throws Exception {
        String base64token = tokenService.generateToken(account);
        String decodedToken = new String(Base64.getDecoder().decode(base64token));
        TokenDTO token = mapper.readValue(decodedToken, TokenDTO.class);

        assertEquals(ID, token.getAccount().getId());
        assertEquals(USERNAME, token.getAccount().getUsername());
        assertEquals(TIMESTAMP, token.getExpireTime());
        assertEquals(getMac(), token.getMac());
    }

    private String getMac() throws Exception{
        String accountAsString = mapper.writeValueAsString(account);
        String stringToHash = TOKEN_SECRET+accountAsString+TIMESTAMP;
        return DigestUtils.sha256Hex(stringToHash);
    }
}

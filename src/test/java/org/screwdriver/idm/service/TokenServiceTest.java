package org.screwdriver.idm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.screwdriver.idm.dto.AccountDTO;
import org.screwdriver.idm.dto.TokenDTO;
import org.screwdriver.idm.service.authentication.UnauthorizedException;

import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;


public class TokenServiceTest {

    private ITokenService tokenService;

    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private TimeService timeService;

    private AccountDTO account;

    private final static Long ID = 321l;

    private final static String USERNAME = "user name";

    private final static String TOKEN_SECRET = "SECRET123XXX";

    private final static String TIMESTAMP = "2015-03-05T12:00:00.000Z";

    private final static String TIMESTAMP_IN_FUTURE = "2015-03-05T13:00:00.000Z";

    private final static String TIMESTAMP_IN_PAST = "2015-03-05T11:00:00.000Z";


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
        assertEquals(getMac(TIMESTAMP), token.getMac());
    }

    @Test
    public void shouldNotThrowExceptionsOnValidToken() throws Exception {
        String tokenBase64 = generateTokenBase64String(TIMESTAMP_IN_FUTURE, getMac(TIMESTAMP_IN_FUTURE));
        assertTrue(tokenService.validate(tokenBase64));
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldThrowUnauthorizedOnInvalidMac() throws Exception {
        String tokenBase64 = generateTokenBase64String(TIMESTAMP_IN_FUTURE, getMac(TIMESTAMP));
        tokenService.validate(tokenBase64);
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldThrowUnauthorizedOnExpiredTimeStamp() throws Exception {
        String tokenBase64 = generateTokenBase64String(TIMESTAMP_IN_PAST, getMac(TIMESTAMP_IN_PAST));
        tokenService.validate(tokenBase64);
    }

    private String generateTokenBase64String(String timestamp, String mac) throws Exception {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccount(account);
        tokenDTO.setExpireTime(timestamp);
        tokenDTO.setMac(mac);
        String tokenJson = mapper.writeValueAsString(tokenDTO);
        return new String(Base64.getEncoder().encode(tokenJson.getBytes()));
    }

    private String getMac(String timeStamp) throws Exception{
        String accountAsString = mapper.writeValueAsString(account);
        String stringToHash = TOKEN_SECRET+accountAsString+timeStamp;
        return DigestUtils.sha256Hex(stringToHash);
    }
}

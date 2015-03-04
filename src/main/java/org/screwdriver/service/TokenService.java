package org.screwdriver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.screwdriver.dto.AccountDTO;
import org.screwdriver.dto.TokenDTO;

import java.util.Base64;

public class TokenService {

    private ObjectMapper mapper;

    private TimeService timeService;

    private String tokenSecret;

    public TokenService(ObjectMapper mapper, TimeService timeService, String tokenSecret) {
        this.mapper = mapper;
        this.timeService = timeService;
        this.tokenSecret = tokenSecret;
    }

    public String generateToken(AccountDTO account) throws Exception {
        TokenDTO token = new TokenDTO();
        token.setAccount(account);
        token.setExpireTime(timeService.getNewTokenTimestampISO());
        token.setMac(getMac(account));
        return new String(Base64.getEncoder().encode(mapper.writeValueAsBytes(token)));
    }

    private String getMac(AccountDTO account) throws Exception {
        return DigestUtils.sha256Hex(tokenSecret + mapper.writeValueAsString(account) + timeService.getNewTokenTimestampISO());
    }
}

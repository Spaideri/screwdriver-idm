package org.screwdriver.idm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.screwdriver.idm.dto.AccountDTO;
import org.screwdriver.idm.dto.TokenDTO;
import org.screwdriver.idm.service.authentication.UnauthorizedException;

import java.util.Base64;

public class TokenService implements ITokenService {

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
        token.setMac(getMac(account, timeService.getNewTokenTimestampISO()));
        return new String(Base64.getEncoder().encode(mapper.writeValueAsBytes(token)));
    }

    @Override
    public boolean validate(String token) throws Exception {
        TokenDTO tokenDTO = decodeBase64token(token);
        String mac = getMac(tokenDTO.getAccount(), tokenDTO.getExpireTime());
        if(!mac.equals(tokenDTO.getMac())) {
            throw new UnauthorizedException();
        }
        DateTime expireTime = DateTime.parse(tokenDTO.getExpireTime());
        if(expireTime.isBeforeNow()) {
            throw new UnauthorizedException();
        }
        return true;
    }

    private TokenDTO decodeBase64token(String base64Token) throws Exception {
        String tokenInJson = new String(Base64.getDecoder().decode(base64Token));
        return mapper.readValue(tokenInJson, TokenDTO.class);
    }

    private String getMac(AccountDTO account, String timeStamp) throws Exception {
        return DigestUtils.sha256Hex(tokenSecret + mapper.writeValueAsString(account) + timeStamp);
    }
}

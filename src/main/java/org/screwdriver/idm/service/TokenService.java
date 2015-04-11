package org.screwdriver.idm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.screwdriver.idm.dto.AccountDTO;
import org.screwdriver.idm.dto.TokenDTO;
import org.screwdriver.idm.service.authentication.UnauthorizedException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class TokenService implements ITokenService {

    private ObjectMapper mapper;

    private TimeService timeService;

    private String tokenSecret;

    public TokenService(ObjectMapper mapper, TimeService timeService, String tokenSecret) {
        this.mapper = mapper;
        this.timeService = timeService;
        this.tokenSecret = tokenSecret;
    }

    public static String decodeBase64(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    public static String encodeBase64(String s) {
        return Base64.encodeBase64String(StringUtils.getBytesUtf8(s));
    }

    public String generateToken(AccountDTO account) throws Exception {
        TokenDTO token = new TokenDTO();
        token.setAccount(account);
        token.setExpireTime(timeService.getNewTokenTimestampISO());
        token.setMac(getMac(account, timeService.getNewTokenTimestampISO()));
        return TokenService.encodeBase64(new String(mapper.writeValueAsBytes(token)));
    }

    @Override
    public boolean validate(String token) throws Exception {
        TokenDTO tokenDTO = decodeBase64token(token);
        String mac = getMac(tokenDTO.getAccount(), tokenDTO.getExpireTime());
        if(!mac.equals(tokenDTO.getMac())) {
            throw new UnauthorizedException();
        }
        DateTime expireTime = DateTime.parse(tokenDTO.getExpireTime());
        DateTime now = timeService.now();
        if(expireTime.isBefore(now)) {
            throw new UnauthorizedException();
        }
        return true;
    }

    private TokenDTO decodeBase64token(String base64Token) throws Exception {
        String tokenInJson = new String(TokenService.decodeBase64(base64Token));
        return mapper.readValue(tokenInJson, TokenDTO.class);
    }

    private String getMac(AccountDTO account, String timeStamp) throws Exception {
        return DigestUtils.sha256Hex(tokenSecret + mapper.writeValueAsString(account) + timeStamp);
    }

}

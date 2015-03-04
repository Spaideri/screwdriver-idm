package org.screwdriver.service;

import org.screwdriver.service.authentication.UnauthorizedException;

/**
 * Created by juho on 04/03/15.
 */
public interface IAuthenticationService {

    /**
     *
     * @param username
     * @param password
     * @return Authentication token as BASE64 encoded string
     * @throws org.screwdriver.service.authentication.UnauthorizedException
     */
    public String authenticate(String username, String password) throws Exception;

}

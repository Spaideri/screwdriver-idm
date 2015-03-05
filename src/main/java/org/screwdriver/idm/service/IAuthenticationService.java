package org.screwdriver.idm.service;

import org.screwdriver.idm.dto.LoginCredentialsDTO;

/**
 * Created by juho on 04/03/15.
 */
public interface IAuthenticationService {

    /**
     *
     * @param credentials DTO Containing login information
     * @return Authentication token as BASE64 encoded string
     * @throws org.screwdriver.idm.service.authentication.UnauthorizedException
     */
    public String authenticate(LoginCredentialsDTO credentials) throws Exception;

}

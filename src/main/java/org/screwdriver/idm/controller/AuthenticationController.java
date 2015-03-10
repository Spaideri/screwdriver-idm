package org.screwdriver.idm.controller;

import org.screwdriver.idm.dto.LoginCredentialsDTO;
import org.screwdriver.idm.service.IAuthenticationService;
import org.screwdriver.idm.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private ITokenService tokenService;

    @RequestMapping(
            value = "/authenticate",
            method = RequestMethod.POST,
            headers = "content-type="+MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(@RequestBody LoginCredentialsDTO credentialsDTO) throws Exception {
        return "{\"token\":\"" + authenticationService.authenticate(credentialsDTO) + "\"}";
    }

    @RequestMapping(
            value = "/validate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String validate(@RequestParam(value="token") String token) throws Exception {
        return "{\"valid\":" + tokenService.validate(token) + "}";
    }
}

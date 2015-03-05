package org.screwdriver.idm.controller;

import org.screwdriver.idm.dto.LoginCredentialsDTO;
import org.screwdriver.idm.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @RequestMapping(
            value = "/authenticate",
            method = RequestMethod.POST,
            headers = "content-type="+MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(@RequestBody LoginCredentialsDTO credentialsDTO) throws Exception {
        return "{\"token\":\"" + authenticationService.authenticate(credentialsDTO) + "\"}";
    }
}

package org.screwdriver.controller;

import org.screwdriver.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(
            @RequestParam(value="username", required=true) String username,
            @RequestParam(value="password", required=true) String password) throws Exception {

        return "{\"token\":\"" + authenticationService.authenticate(username, password) + "\"}";
    }
}

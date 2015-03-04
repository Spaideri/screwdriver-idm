package org.screwdriver.service.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Login failed")
public class UnauthorizedException extends RuntimeException {
}

package org.screwdriver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.screwdriver.service.TimeService;
import org.screwdriver.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.screwdriver")
public class ApplicationConfig {

    @Value("${token.lifetimeInSeconds}")
    private Integer tokenLifetimeInSeconds;

    @Value("${token.secret}")
    private String tokenSecret;

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() { return new ObjectMapper(); }

    @Bean
    public TimeService timeService() { return new TimeService(tokenLifetimeInSeconds); }

    @Bean
    public TokenService tokenService() { return new TokenService(jacksonObjectMapper(), timeService(), tokenSecret); }

}

package org.screwdriver.service;


import org.joda.time.DateTime;

public class TimeService {

    private Integer tokenLifetimeInSeconds;

    public TimeService(Integer tokenLifetimeInSeconds) {
        this.tokenLifetimeInSeconds = tokenLifetimeInSeconds;
    }

    public String getNewTokenTimestampISO() {
        DateTime timestamp = new DateTime().plusSeconds(tokenLifetimeInSeconds);
        return timestamp.toDateTimeISO().toString();
    }
}

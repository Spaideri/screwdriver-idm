package org.screwdriver.idm.service;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class TimeService {

    private Integer tokenLifetimeInSeconds;

    public TimeService(Integer tokenLifetimeInSeconds) {
        DateTimeZone.setDefault(DateTimeZone.UTC);
        this.tokenLifetimeInSeconds = tokenLifetimeInSeconds;
    }

    public String getNewTokenTimestampISO() {
        DateTime timestamp = new DateTime().plusSeconds(tokenLifetimeInSeconds);
        return timestamp.toDateTimeISO().toString();
    }
}

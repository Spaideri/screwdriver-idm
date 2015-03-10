package org.screwdriver.idm.dto;

import java.util.List;

public class AccountDTO {

    private Long id;

    private String username;

    private List<AccessGroupDTO> accessGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AccessGroupDTO> getAccessGroups() {
        return accessGroups;
    }

    public void setAccessGroups(List<AccessGroupDTO> accessGroups) {
        this.accessGroups = accessGroups;
    }
}

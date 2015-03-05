package org.screwdriver.idm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Account extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String username;

	@Column
	private String password;

    @ManyToMany
    private List<AccessGroup> accessGroups;

    public Account() {
    }

    private Account(Builder builder) {
        super.setId(builder.id);
        this.username = builder.username;
        this.password = builder.password;
        this.accessGroups = builder.accessGroups;
    }

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

    public List<AccessGroup> getAccessGroups() {
        return accessGroups;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private List<AccessGroup> accessGroups;

        public Account build(){
            return new Account(this);
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder accessGroups(List<AccessGroup> accessGroups) {
            this.accessGroups = accessGroups;
            return this;
        }
    }
}

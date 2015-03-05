package org.screwdriver.idm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class AccessGroup extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "accessGroups")
    private List<Account> accounts;

    private AccessGroup(Builder builder) {
        super.setId(builder.id);
        this.name = builder.name;
        this.accounts = builder.accounts;
    }

    public AccessGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<Account> accounts;

        public AccessGroup build() {
            return new AccessGroup(this);
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder accounts(List<Account> accounts) {
            this.accounts = accounts;
            return this;
        }
    }
}

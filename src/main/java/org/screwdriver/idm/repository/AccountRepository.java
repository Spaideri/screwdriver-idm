package org.screwdriver.idm.repository;

import org.springframework.data.repository.CrudRepository;

import org.screwdriver.idm.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findByUsername(String username);

}

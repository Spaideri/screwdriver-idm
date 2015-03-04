package org.screwdriver.repository;

import org.springframework.data.repository.CrudRepository;

import org.screwdriver.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Account findByUsername(String username);

}

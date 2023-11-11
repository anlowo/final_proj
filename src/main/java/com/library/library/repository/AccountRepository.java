package com.library.library.repository;

import com.library.library.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  Account getByEmail(String email);

}

package com.library.library.repository;

import com.library.library.model.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {

  AuthGroup getByName(String name);

}

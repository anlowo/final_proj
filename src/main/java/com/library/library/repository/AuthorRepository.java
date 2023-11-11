package com.library.library.repository;

import com.library.library.model.Author;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

  Author getByName(String name);
  List<Author> getAuthorsByNameIsContainingIgnoreCase(String name, Sort sort);
  boolean existsByName(String name);

}

package com.library.library.repository;

import com.library.library.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

  Book getByName(String name);
  List<Book> getBooksByNameIsContainingIgnoreCase(String name, Sort sort);
  boolean existsByName(String name);

}

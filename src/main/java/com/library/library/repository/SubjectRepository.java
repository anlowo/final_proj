package com.library.library.repository;

import com.library.library.model.Subject;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

  Subject getByName(String name);
  List<Subject> getSubjectsByNameIsContainingIgnoreCase(String name, Sort sort);
  boolean existsByName(String name);

}

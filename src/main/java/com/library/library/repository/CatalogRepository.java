package com.library.library.repository;

import com.library.library.model.Catalog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

  List<Catalog> getCatalogByNameContainingIgnoreCaseAndAccount_Id(String name, Sort sort, Integer accountId);

}

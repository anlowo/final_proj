package com.library.library.service;

import com.library.library.model.Catalog;
import com.library.library.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CatalogService {

  private final CatalogRepository catalogRepository;

  @Autowired
  public CatalogService(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  public Catalog saveAndFlush(Catalog catalog) {
    return catalogRepository.saveAndFlush(catalog);
  }

  public Catalog getById(Integer id) {
    return catalogRepository.getById(id);
  }

  public List<Catalog> getCatalogByNameContainingIgnoreCaseAndAccount_Id(String name, Sort sort, Integer accountId) {
    return catalogRepository.getCatalogByNameContainingIgnoreCaseAndAccount_Id(name, sort, accountId);
  }

  public void deleteById(Integer id) {
    catalogRepository.deleteById(id);
  }

}

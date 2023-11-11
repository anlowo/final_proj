package com.library.library.service;

import com.library.library.model.Catalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchService {

  private final AccountService accountService;
  private final AuthorService authorService;
  private final BookService bookService;
  private final CatalogService catalogService;
  private final SubjectService subjectService;

  @Autowired
  public SearchService(AccountService accountService,
                       AuthorService authorService,
                       BookService bookService,
                       CatalogService catalogService,
                       SubjectService subjectService) {
    this.accountService = accountService;
    this.authorService = authorService;
    this.bookService = bookService;
    this.catalogService = catalogService;
    this.subjectService = subjectService;
  }

  public List<?> getSearchResults(String terms, String filter, String order) {
    filter = filter.replace("list", "catalog"); // usage of "list" is for front end only
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort sort = Sort.by(sortDirection, "name");

    switch (filter) {
      case "authors":
        return authorService.getAuthorsByNameIsContainingIgnoreCase(terms, sort);
      case "subjects":
        return subjectService.getSubjectsByNameIsContainingIgnoreCase(terms, sort);
      case "catalogs":
        if (!accountService.isLoggedIn()) {
          return new ArrayList<Catalog>();
        } else {
          return catalogService.getCatalogByNameContainingIgnoreCaseAndAccount_Id(terms, sort, accountService.getLoggedInId());
        }
      default:
        return bookService.getBooksByNameIsContainingIgnoreCase(terms, sort);
    }
  }

  public Integer getLastPageNumber(int resultCount, int resultsPerPage) {
    int lastPage = resultCount / resultsPerPage;

    if (resultCount % resultsPerPage != 0) {
      lastPage++;
    }

    return lastPage;
  }

  public List<String> getPageNumbers(int resultCount, int currentPage, int resultsPerPage) {
    List<String> resultPages = new ArrayList<>();
    int lastPage = getLastPageNumber(resultCount, resultsPerPage);
    int pageCounter = 0;

    while (++pageCounter <= lastPage) {
      if  (
            pageCounter <= 3 || // First three pages
            pageCounter >= lastPage - 2 || // Last three pages
            // Up to five pages around and including the current page
            (pageCounter >= currentPage - 2 && pageCounter <= currentPage + 2)
          ) {
        resultPages.add(String.valueOf(pageCounter));
      }
      else if (
                pageCounter == currentPage - 3 ||
                pageCounter == currentPage + 3
              ) {
        resultPages.add("...");
      }
    }

    return resultPages;
  }

  public List<?> limitResultsByPage(List<?> results, int page, int resultsPerPage) {
    if (results.isEmpty()) {
      return results;
    }

    int fromIndex = page * resultsPerPage - resultsPerPage;

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    if (fromIndex > results.size()) {
      fromIndex = results.size();
    }

    int toIndex = fromIndex + resultsPerPage;

    if (toIndex > results.size()) {
      toIndex = results.size();
    }

    return results.subList(fromIndex, toIndex);
  }

}

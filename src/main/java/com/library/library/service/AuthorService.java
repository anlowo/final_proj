package com.library.library.service;

import com.library.library.model.Author;
import com.library.library.model.Book;
import com.library.library.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public void save(Author author) {
    authorRepository.save(author);
  }

  public void saveAll(List<Author> authors) {
    authorRepository.saveAll(authors);
  }

  public boolean existsByName(String name) {
    return authorRepository.existsByName(name);
  }

  public Author getById(Integer id) {
    return authorRepository.getById(id);
  }

  public Author getByName(String name) {
    return authorRepository.getByName(name);
  }

  public List<Author> getAuthorsByNameIsContainingIgnoreCase(String name, Sort sort) {
    return authorRepository.getAuthorsByNameIsContainingIgnoreCase(name, sort);
  }

  public Author getByRandom() {
    List<Author> authors = authorRepository.findAll();
    Random random = new Random();
    return authors.get(random.nextInt(authors.size()));
  }

  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  public void mapToBook(Author author, Book book) {
    List<Book> books = author.getBooks();
    boolean alreadyPresent = false;

    if (books == null) {
      books = new ArrayList<>();
    }

    for (Book b : books) {
      if (b.getName().equals(book.getName())) {
        alreadyPresent = true;
        break;
      }
    }

    if (!alreadyPresent) {
      books.add(book);
    }

    author.setBooks(books);
    authorRepository.save(author);
  }

}

package com.library.library.service;

import com.library.library.model.Book;
import com.library.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public void save(Book book) {
    bookRepository.save(book);
  }

  public void saveAll(List<Book> books) {
    bookRepository.saveAll(books);
  }

  public boolean existsByName(String name) {
    return bookRepository.existsByName(name);
  }

  public Book getById(Integer id) {
    return bookRepository.getById(id);
  }

  public Book getByName(String name) {
    return bookRepository.getByName(name);
  }

  public List<Book> getBooksByNameIsContainingIgnoreCase(String name, Sort sort) {
    return bookRepository.getBooksByNameIsContainingIgnoreCase(name, sort);
  }

  public Book getByRandom() {
    List<Book> books = bookRepository.findAll();
    Random random = new Random();
    return books.get(random.nextInt(books.size()));
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

}

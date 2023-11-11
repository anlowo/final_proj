package com.library.library.controller;

import com.library.library.model.Author;
import com.library.library.model.Book;
import com.library.library.model.Subject;
import com.library.library.service.*;
import com.library.library.view.TemplateView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@Controller
public class BookController extends TemplateView {

  private final AccountService accountService;
  private final BookService bookService;
  private final AuthorService authorService;
  private  final SubjectService subjectService;
  private final FileService fileService;

  @Autowired
  public BookController(AccountService accountService,
                        BookService bookService, AuthorService authorService, SubjectService subjectService, FileService fileService) {
    this.accountService = accountService;
    this.bookService = bookService;
    this.authorService = authorService;
    this.subjectService = subjectService;
    this.fileService = fileService;
  }

  @GetMapping("/books")
  public String getBooks() {
    return "redirect:/search";
  }

  @GetMapping("/books/{id}")
  public String getBook(Model model, @PathVariable Integer id) {
    Book book = bookService.getById(id);
    model.addAttribute("book", book);

    if (accountService.isLoggedIn()) {
      model.addAttribute("lists", accountService.getLoggedInAccount().getCatalogs());
    }

    return loadView(model, "books/book");
  }

  @GetMapping("/books/{id}/read")
  public String getBookReader(@RequestParam(required=false) String book) {
    return "books/bibi";
  }

  @PostMapping("/creatBookWithEpubFile")
  public String createBook(RedirectAttributes redirectAttributes,
                           @RequestParam MultipartFile fileCover,
                           @RequestParam MultipartFile fileReader,
                           @RequestParam String bookName,
                           @RequestParam String bookSubject,
                           @RequestParam String bookAuthor) throws IOException {
    Book book = new Book(bookName);
    if (!bookService.existsByName(bookName)){
      bookService.save(book);
    }else {
      redirectAttributes.addFlashAttribute("Something goes wrong");
    }

      Author author;
    if (authorService.existsByName(bookAuthor)) {
      author = authorService.getByName(bookAuthor);
    }
    else {
      author = new Author(bookName);
    }
    authorService.mapToBook(author, book);

    Subject subject;
    if (subjectService.existsByName(bookSubject)) {
      subject = subjectService.getByName(bookSubject);
    }
    else {
      subject = new Subject(bookSubject);
    }
    subjectService.mapToBook(subject, book);

    String imageName = "cover-" + book.getId() + ".png";
    if (!fileService.exists(imageName)) {

      String imageContentType = "image/png";
      byte [] imageContent = fileCover.getBytes();
      MultipartFile cover = new MockMultipartFile(imageName, imageName, imageContentType, imageContent);
      fileService.save(cover);
    }else {
      redirectAttributes.addFlashAttribute(imageName+" already exists");
    }

    String readerName = "reader-" + book.getId() + ".epub";
    if (!fileService.exists(readerName)) {
      String readerContentType = "application/epub+zip";
      byte [] readerContent = fileReader.getBytes();
      MultipartFile reader = new MockMultipartFile(readerName, readerName, readerContentType, readerContent);
      fileService.save(reader);
    }else {
      redirectAttributes.addFlashAttribute(readerName+" already exists");
    }

    return "redirect:/index";
  }
}



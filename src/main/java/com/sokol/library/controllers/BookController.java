package com.sokol.library.controllers;

import com.sokol.library.dao.BookDAO;
import com.sokol.library.dao.PersonDAO;
import com.sokol.library.models.Book;
import com.sokol.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "books/list";
    }

    @GetMapping("/new")
    public String newForm(Model model, @ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String processNewForm(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("people", personDAO.findAll());
            if (book.get().getPerson_id() != null) {
                Optional<Person> person = personDAO.get(book.get().getPerson_id());
                person.ifPresent(p -> model.addAttribute("person", p));
            }
            return "books/show";
        } else {
            return "redirect:/books";
        }
    }

    @PatchMapping("/assign")
    public String assign(@RequestParam("person_id") int personId, @RequestParam("book_id") int bookId) {
        System.out.println(personId);
        System.out.println(bookId);
        Optional<Person> person = personDAO.get(personId);
        if (person.isPresent()) {
            bookDAO.setPersonId(bookId, personId);
            return "redirect:/books";
        }
        return null;
    }

    @PatchMapping("/free")
    public String free(@RequestParam("book_id") int bookId) {
        bookDAO.setPersonId(bookId, null);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/edit";
        }
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(book);
        return "redirect:/books";
    }

}

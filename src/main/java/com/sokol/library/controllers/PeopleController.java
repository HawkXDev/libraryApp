package com.sokol.library.controllers;

import com.sokol.library.dao.BookDAO;
import com.sokol.library.dao.PersonDAO;
import com.sokol.library.models.Book;
import com.sokol.library.models.Person;
import com.sokol.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("people", personDAO.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String savePerson(@ModelAttribute("person") @Valid Person person, BindingResult result) {

        personValidator.validate(person, result);

        if (result.hasErrors()) {
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personDAO.get(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("books", bookDAO.getBooksForPerson(person.get().getId()));
            return "people/show";
        } else {
            return "redirect:/people";
        }
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personDAO.get(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            return "people/edit";
        } else {
            return "redirect:/people";
        }
    }

    @PatchMapping
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult result) {
        personValidator.validate(person, result);

        if (result.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}

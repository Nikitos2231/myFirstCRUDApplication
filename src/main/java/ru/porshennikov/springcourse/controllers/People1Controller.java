package ru.porshennikov.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.porshennikov.springcourse.dao.Person1DAO;
import ru.porshennikov.springcourse.models.Person1;
import ru.porshennikov.springcourse.util.Person1Validator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people1")
public class People1Controller {

    private Person1DAO person1DAO;
    private Person1Validator person1Validator;

    @Autowired
    public People1Controller(Person1DAO person1DAO, Person1Validator person1Validator) {
        this.person1DAO = person1DAO;
        this.person1Validator = person1Validator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people1", person1DAO.index());
        return "people1/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", person1DAO.show(id));
        model.addAttribute("books", person1DAO.getListBookByPersonId(id));
        return "people1/show";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person1 person1, BindingResult result) {
        person1Validator.validate(person1, result);
        if (result.hasErrors()) {
            return "people1/new";
        }
        person1DAO.create(person1);
        return "redirect:/people1";
    }

    @GetMapping("/new")
    public String showPerson(@ModelAttribute("person") Person1 person1) {
        return "people1/new";
    }

    @DeleteMapping("/{person1_id}")
    public String deletePerson(@PathVariable("person1_id") int person1_id) {
        person1DAO.delete(person1_id);
        return "redirect:/people1";
    }

    @GetMapping("/{person1_id}/edit")
    public String showPersonByID(@PathVariable("person1_id") int id, Model model) {
        model.addAttribute("person", person1DAO.show(id));
        return "people1/edit";
    }

    @PatchMapping("/{person1_id}/edit")
    public String editPerson(@PathVariable("person1_id") int id, @ModelAttribute("person") @Valid Person1 person1, BindingResult result) {
        person1Validator.validate(person1, result);
        if (result.hasErrors())
            return "people1/edit";
        person1DAO.edit(id, person1);
        return "redirect:/people1";
    }
}

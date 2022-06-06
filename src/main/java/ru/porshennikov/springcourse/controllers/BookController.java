package ru.porshennikov.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.porshennikov.springcourse.dao.BookDAO;
import ru.porshennikov.springcourse.dao.Person1DAO;
import ru.porshennikov.springcourse.models.Book;
import ru.porshennikov.springcourse.models.Person1;
import ru.porshennikov.springcourse.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final Person1DAO person1DAO;

    private BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO BookDAO, Person1DAO person1DAO, BookValidator bookValidator) {
        this.bookDAO = BookDAO;
        this.person1DAO = person1DAO;
        this.bookValidator = bookValidator;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int id, Model model, @ModelAttribute("person") Person1 person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person1> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", person1DAO.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        bookValidator.validate(book, result);
        if (result.hasErrors()) {
            return "books/new";
        }
        bookDAO.create(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String editPage(Model model, @PathVariable("book_id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}/edit")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult result, @PathVariable("book_id") int id) {
        bookValidator.validate(book, result);
        if (result.hasErrors()) {
            return "books/edit";
        }
        bookDAO.editBook(book, id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String release(@PathVariable("book_id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{book_id}/assign")
    public String assign(@PathVariable("book_id") int id, @ModelAttribute("person") Person1 selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

}

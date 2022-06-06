package ru.porshennikov.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.porshennikov.springcourse.models.Book;
import ru.porshennikov.springcourse.models.Person1;

import java.util.List;

@Component
public class Person1DAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public Person1DAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person1> index() {
        return jdbcTemplate.query("SELECT * FROM Person1", new BeanPropertyRowMapper<>(Person1.class));
    }

    public Person1 show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person1 WHERE person1_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person1.class)).stream().findAny().orElse(null);
    }

    public void create(Person1 person1) {
        jdbcTemplate.update("INSERT INTO Person1(full_name, year) VALUES(?, ?)",
                person1.getFull_name(), person1.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person1 WHERE person1_id=?", id);
    }

    public void edit(int id, Person1 person1) {
        jdbcTemplate.update("UPDATE Person1 SET full_name=?, year=? WHERE person1_id=?",
                person1.getFull_name(), person1.getYear(), id);
    }

    public List<Book> getListBookByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person1_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}

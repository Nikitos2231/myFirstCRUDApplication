package ru.porshennikov.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.porshennikov.springcourse.models.Book;
import ru.porshennikov.springcourse.models.Person1;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?,?,?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void editBook(Book book, int id) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public Optional<Person1> getBookOwner(int id) {
        // Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT Person1.* FROM Book JOIN Person1 ON Book.person1_id = Person1.person1_id" + " WHERE book.book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person1.class)).stream().findAny();
    }

    public void assign(int id, Person1 person1) {
        jdbcTemplate.update("UPDATE Book SET person1_id = ? Where book_id = ?", person1.getPerson1_id(), id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person1_id=NULL WHERE book_id=?", id);
    }

}

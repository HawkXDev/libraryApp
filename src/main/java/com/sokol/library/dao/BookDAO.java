package com.sokol.library.dao;

import com.sokol.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY id", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Optional<Book> findById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id =?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void setPersonId(int bookId, Integer personId) {
        jdbcTemplate.update("UPDATE book SET person_id =? WHERE id =?", personId, bookId);
    }

    public List<Book> getBooksForPerson(int personId) {
        List<Integer> bookIds = jdbcTemplate.query("SELECT id FROM book WHERE person_id =?",
                new Object[]{personId}, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt("id");
                    }
                });
        List<Book> books = new ArrayList<>();
        for (Integer bookId : bookIds) {
            books.add(findById(bookId).get());
        }
        return books;
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id =?", id);
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE book SET title =?, author =?, year =? WHERE id =?",
                book.getTitle(), book.getAuthor(), book.getYear(), book.getId());
    }
}

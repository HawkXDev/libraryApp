package com.sokol.library.dao;

import com.sokol.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> get(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name =?",
                new BeanPropertyRowMapper<>(Person.class), name).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, year_of_birth) VALUES (?,?)",
                person.getName(), person.getYearOfBirth());
    }

    public Optional<Person> get(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id =?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}

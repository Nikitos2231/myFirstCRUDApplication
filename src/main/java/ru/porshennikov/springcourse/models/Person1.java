package ru.porshennikov.springcourse.models;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.*;

public class Person1 {


    private int person1_id;
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 6, max = 70, message = "Name should be between 6 and 70 characters")
    private String full_name;
    @Min(value = 1900, message = "year should be more than 1900")
    private int year;

    public Person1(int person1_id, String full_name, int year) {
        this.person1_id = person1_id;
        this.full_name = full_name;
        this.year = year;
    }

    public Person1() {
    }

    public int getPerson1_id() {
        return person1_id;
    }

    public void setPerson1_id(int person1_id) {
        this.person1_id = person1_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

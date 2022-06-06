package ru.porshennikov.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.porshennikov.springcourse.dao.Person1DAO;
import ru.porshennikov.springcourse.models.Person1;

@Component
public class Person1Validator implements Validator {

    private final Person1DAO person1DAO;

    @Autowired
    public Person1Validator(Person1DAO person1DAO) {
        this.person1DAO = person1DAO;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person1.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person1 person1 = (Person1) o;

//        if (person1DAO.show(person1.get).isPresent()) {
//            errors.rejectValue("email", "", "This email is already taken");
//        }

    }
}
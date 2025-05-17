package com.miromorii.cursoerudio.services;

import com.miromorii.cursoerudio.models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id){
        logger.info("finding one person");
        return mockPerson(counter.incrementAndGet());
    }

    private Person mockPerson(Long id){
        Person person = new Person();

        person.setId(id);
        person.setFirstName("firstName " + id);
        person.setLastName("lastName " + id);
        person.setAddress("address " + id);
        person.setGender("male " + id);

        return person;
    }

    public List<Person> findAll(){
        logger.info("finding all persons");
        List<Person> persons = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            persons.add(mockPerson(counter.incrementAndGet()));
        }

        return persons;
    }

    public Person create(Person person){
        logger.info("creating person");

        return mockPerson(counter.incrementAndGet());
    }

    public Person update(Person person){
        logger.info("updating person");

        return person;
    }

    public void delete(Long id){
        logger.info("deleting person");
    }
}

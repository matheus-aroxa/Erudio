package com.miromorii.cursoerudio.services;

import com.miromorii.cursoerudio.exceptions.ResourceNotFoundException;
import com.miromorii.cursoerudio.models.Person;
import com.miromorii.cursoerudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    @Autowired
    private PersonRepository personRepository;

    public Person findById(Long id){
        logger.info("finding one person");
        return personRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Person> findAll(){
        logger.info("finding all persons");

        return personRepository.findAll();
    }

    public Person create(Person person){
        logger.info("creating person");

        return personRepository.save(person);
    }

    public Person update(Person person){
        logger.info("updating person");

        Person find = personRepository.findById(person.getId()).orElseThrow(ResourceNotFoundException::new);

        find.setFirstName(person.getFirstName());
        find.setLastName(person.getLastName());
        find.setAddress(person.getAddress());
        find.setGender(person.getGender());
        return personRepository.save(find);
    }

    public void delete(Long id){
        logger.info("deleting person");

        Person find = personRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        personRepository.deleteById(find.getId());
    }

}

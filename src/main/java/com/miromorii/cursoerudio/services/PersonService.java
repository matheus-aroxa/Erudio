package com.miromorii.cursoerudio.services;

import com.miromorii.cursoerudio.data.dto.v1.PersonDTO;
import com.miromorii.cursoerudio.data.dto.v2.PersonDTOV2;
import com.miromorii.cursoerudio.exceptions.ResourceNotFoundException;
import com.miromorii.cursoerudio.mapper.ObjectMapper;
import com.miromorii.cursoerudio.mapper.custom.PersonMapper;
import com.miromorii.cursoerudio.models.Person;
import com.miromorii.cursoerudio.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public PersonDTO findById(Long id){
        logger.info("finding one person");
        return ObjectMapper.parseObject(personRepository.findById(id).orElseThrow(ResourceNotFoundException::new), PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        logger.info("finding all persons");

        return ObjectMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("creating person");

        Person entity = personRepository.save(ObjectMapper.parseObject(person, Person.class));
        return ObjectMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("creating a person v2");

        return personMapper.convertEntityToDTO(personRepository.save(personMapper.convertDTOToEntity(person)));
    }

    public PersonDTO update(PersonDTO person){
        logger.info("updating person");

        Person find = personRepository.findById(person.getId()).orElseThrow(ResourceNotFoundException::new);

        find.setFirstName(person.getFirstName());
        find.setLastName(person.getLastName());
        find.setAddress(person.getAddress());
        find.setGender(person.getGender());
        return ObjectMapper.parseObject(personRepository.save(find), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("deleting person");

        Person find = personRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        personRepository.deleteById(find.getId());
    }

}

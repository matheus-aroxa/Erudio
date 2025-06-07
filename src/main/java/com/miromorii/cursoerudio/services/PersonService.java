package com.miromorii.cursoerudio.services;

import com.miromorii.cursoerudio.controllers.PersonController;
import com.miromorii.cursoerudio.data.dto.v1.PersonDTO;
import com.miromorii.cursoerudio.data.dto.v2.PersonDTOV2;
import com.miromorii.cursoerudio.exceptions.RequiredObjectIsNullException;
import com.miromorii.cursoerudio.exceptions.ResourceNotFoundException;
import com.miromorii.cursoerudio.mapper.ObjectMapper;
import com.miromorii.cursoerudio.mapper.custom.PersonMapper;
import com.miromorii.cursoerudio.models.Person;
import com.miromorii.cursoerudio.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PagedResourcesAssembler<PersonDTO> assembler;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public PersonDTO findById(Long id){
        logger.info("finding one person");
        PersonDTO dto = ObjectMapper.parseObject(personRepository.findById(id).orElseThrow(ResourceNotFoundException::new), PersonDTO.class);
        return addHateoasLinks(dto);
    }

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
        logger.info("finding all persons");

        Page<Person> page = personRepository.findAll(pageable);
        var persons = page.map(person -> {
            PersonDTO dto = ObjectMapper.parseObject(person, PersonDTO.class);
            return addHateoasLinks(dto);
        });

        Link findAllLinks = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(persons, findAllLinks);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {
        logger.info("finding people by name");

        Page<Person> page = personRepository.findPeopleByName(firstName, pageable);
        var persons = page.map(person -> {
            PersonDTO dto = ObjectMapper.parseObject(person, PersonDTO.class);
            return addHateoasLinks(dto);
        });

        Link findAllLinks = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(persons, findAllLinks);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("creating person");

        if(person == null) throw new RequiredObjectIsNullException();

        Person entity = personRepository.save(ObjectMapper.parseObject(person, Person.class));
        PersonDTO personDTO = ObjectMapper.parseObject(entity, PersonDTO.class);
        addHateoasLinks(personDTO);
        return personDTO;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("creating a person v2");

        return personMapper.convertEntityToDTO(personRepository.save(personMapper.convertDTOToEntity(person)));
    }

    public PersonDTO update(PersonDTO person){
        logger.info("updating person");
        if(person == null) throw new RequiredObjectIsNullException();

        Person find = personRepository.findById(person.getId()).orElseThrow(ResourceNotFoundException::new);

        find.setFirstName(person.getFirstName());
        find.setLastName(person.getLastName());
        find.setAddress(person.getAddress());
        find.setGender(person.getGender());
        PersonDTO personDTO = ObjectMapper.parseObject(personRepository.save(find), PersonDTO.class);
        addHateoasLinks(personDTO);
        return personDTO;
    }

    public void delete(Long id){
        logger.info("deleting person");

        Person find = personRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        personRepository.deleteById(find.getId());
    }

    @Transactional
    public PersonDTO disablePerson(Long id){
        logger.info("disabling person with id {}", id);

        Person person = personRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        person.setEnabled(false);

        return ObjectMapper.parseObject(personRepository.save(person), PersonDTO.class);
    }

    private static PersonDTO addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(0, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).disable(dto.getId())).withRel("disable").withType("PATCH"));
        return dto;
    }
}

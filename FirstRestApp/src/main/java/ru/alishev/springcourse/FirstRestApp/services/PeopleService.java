package ru.alishev.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Person;
import ru.alishev.springcourse.FirstRestApp.repositories.PeopleRepositories;
import ru.alishev.springcourse.FirstRestApp.util.PersonNotFoundException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//Не относится к 3 ----------------------------------------------------------------


@Service
public class PeopleService {

    private final PeopleRepositories peopleRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> findAll(){
        return peopleRepositories.findAll();
    }


    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepositories.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);//в этом случае выб-ся наше исключение
    }

    @Transactional
    public void save(Person person){
        enrichPerson(person);
        peopleRepositories.save(person);
    }


    public void enrichPerson(Person person) {//добавляем в person доп данные Которые назначаются не клиентом, а сервером
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");
    }

}

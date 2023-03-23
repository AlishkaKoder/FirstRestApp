package ru.alishev.springcourse.FirstRestApp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstRestApp.models.Person;

//Не относится к 3 проекту


@Repository
public interface PeopleRepositories extends JpaRepository<Person,Integer> {



}

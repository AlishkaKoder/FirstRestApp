package ru.alishev.springcourse.FirstRestApp.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.PersonDTO;
import ru.alishev.springcourse.FirstRestApp.models.Person;
import ru.alishev.springcourse.FirstRestApp.services.PeopleService;
import ru.alishev.springcourse.FirstRestApp.util.PersonErrorResponse;
import ru.alishev.springcourse.FirstRestApp.util.PersonNotCreatedException;
import ru.alishev.springcourse.FirstRestApp.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

//Не относится к 3 проекту

@RestController // Теперь можно не писать аннотацию @ResponseBody (@ResponseBody - Spring понимает что в этом методе мы больше не возвращаем представления, а возвращаем данные) над каждым методом
@RequestMapping("/people")
public class PeopleController {//чтобы возвращать список из объектов персон

    private final PeopleService peopleService;
    private final ModelMapper modelMapper; // в конфигурации добавили этот бин

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople(){// Jackson конвертирует эти объекты в Json и отправляет по сети
        return peopleService.findAll().stream().map(this::convertToPersonDTO)//Для каждого объекта из листа вызываем метод convertToPersonDTO()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id")int id){//Вернём DTO т.к. клиенту не нужны некоторые поля из person(id,createdAt и т.д.)
        return convertToPersonDTO(peopleService.findOne(id));// Jackson конвертирует этот объект в Json и отправляет по сети
    }

    @PostMapping
    //Возвращаем HTTP ответ клиенту после сохранения переданных данных
    //принимаем Json и приводим к объекту PersonDTO
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();//Получаем ошибки из bindingResult

            for(FieldError error : errors){
                errorMsg.append(error.getField())//на каком поле была совершена ошибка
                        .append(" - ").append(error.getDefaultMessage())//какая ошибка была на поле
                        .append(";");//чтобы ошибки не склеились в одну строку
            }

            throw new PersonNotCreatedException(errorMsg.toString());//В исключении будет храниться сообщ об ошибках
        }

        peopleService.save(convertToPerson(personDTO));//конвертируем DTO в модель и сохраняет в БД
        //HTTP ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler//Тут ловим нужную нам ошибку
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){//В параметре пишем какое исключение ловим и возвращаем JSON с объектом PersonNotFound
        //Это объект с ошибкой который Jackson в качестве JSON отправит по сети
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found",
                System.currentTimeMillis()
        );

        //Тело HTTP ответа - response в качестве JSON
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//будет ошибка HTTP 404 статус
    }

    @ExceptionHandler//Тут ловим нужную нам ошибку
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){//В параметре пишем какое исключение ловим и возвращаем JSON с объектом PersonNotCreated
        //Это объект с ошибкой который Jackson в качестве JSON отправит по сети
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(), //тут лежит то, что лежит в самом исключении - super(msg)
                System.currentTimeMillis()
        );

        //Тело HTTP ответа - response в качестве JSON
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//будет ошибка HTTP 404 статус
    }

    private Person convertToPerson(PersonDTO personDTO) {//добавляем в person доп данные которые назначаются клиентом

        Person person = modelMapper.map(personDTO,Person.class);//задаём исходный объект и тот класс в объект которого назначаем данные

        return person;
    }

    private PersonDTO convertToPersonDTO(Person person) {//убираем из person некоторые данные которые не нужны клиенту

        PersonDTO personDTO = modelMapper.map(person,PersonDTO.class);

        return personDTO;
    }

}

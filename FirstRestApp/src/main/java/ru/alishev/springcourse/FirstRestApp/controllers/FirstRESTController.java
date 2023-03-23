package ru.alishev.springcourse.FirstRestApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//Не относится к 3 проекту

@RestController  // Теперь можно не писать аннотацию @ResponseBody (@ResponseBody - Spring понимает что в этом методе мы больше не возвращаем представления, а возвращаем данные) над каждым методом
@RequestMapping("/api")
public class FirstRESTController {


    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello world!"; // Клиент получит объект завёрнутый в JSON с помощью Jackson (В данном случае строку)
    }


}

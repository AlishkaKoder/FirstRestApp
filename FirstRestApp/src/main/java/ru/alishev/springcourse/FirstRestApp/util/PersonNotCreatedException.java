package ru.alishev.springcourse.FirstRestApp.util;

//Не относится к 3 ----------------------------------------------------------------


public class PersonNotCreatedException extends  RuntimeException{
    public PersonNotCreatedException(String msg){
        super(msg);//сообщение будет в исключении
    }
}

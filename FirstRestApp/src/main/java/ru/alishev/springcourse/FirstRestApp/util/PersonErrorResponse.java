package ru.alishev.springcourse.FirstRestApp.util;

//Не относится к 3 ----------------------------------------------------------------


public class PersonErrorResponse {//его объекты отправляются при ошибке в JSON

    private String message;//Сообщение об ошибке

    private long timeStamp;//время в которое произошла ошибка

    public PersonErrorResponse(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

package ru.alishev.springcourse.FirstRestApp.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {
    // айди нет т.к. оно не приходит от клиента
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3,max = 30, message = "Name should be between 2-30 characters")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

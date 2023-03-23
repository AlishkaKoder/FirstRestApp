package ru.alishev.springcourse.FirstRestApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FirstRestAppApplication {//Здесь конфигурируется наше Spring Boot приложение

	public static void main(String[] args) {
		SpringApplication.run(FirstRestAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){//теперь этот объект будет в контексте spring
		return new ModelMapper();//Это класс из зависимости ModelMapper(pom.xml) делает копирование данных из одного объекта в другой

	}
}



// Я сильно затупил создав проект на уже имеющемся rest api проекте, взял его за основу как фундамент,
// видимо лень было писать заново, так делать нельзя, перепиши его,
// либо удали сущность person и всё что с ней связано
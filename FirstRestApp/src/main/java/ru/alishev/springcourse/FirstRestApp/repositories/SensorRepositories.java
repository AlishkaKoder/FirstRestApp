package ru.alishev.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;

@Repository
public interface SensorRepositories extends JpaRepository<Sensor,Integer> {

    Sensor findByName(String name);
}

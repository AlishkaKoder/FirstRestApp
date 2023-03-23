package ru.alishev.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepositories extends JpaRepository<Measurement,Integer> {


    Measurement save(Measurement measurement);

    List<Measurement> findAll();

    List<Measurement> findByRaining(boolean raining);
}

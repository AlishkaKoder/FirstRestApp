package ru.alishev.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.repositories.SensorRepositories;

@Service
@Transactional
public class SensorService {

    private final SensorRepositories sensorRepositories;
    @Autowired
    public SensorService(SensorRepositories sensorRepositories) {
        this.sensorRepositories = sensorRepositories;
    }

    public Sensor findOne(String name){
        Sensor sensor = sensorRepositories.findByName(name);
        return sensor;
    }

    public void save(Sensor sensor) {
        sensorRepositories.save(sensor);
    }
}

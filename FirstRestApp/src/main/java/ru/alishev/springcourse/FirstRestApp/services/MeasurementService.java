package ru.alishev.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.repositories.MeasurementRepositories;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MeasurementService {

    public final MeasurementRepositories measurementRepositories;
    public final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepositories measurementRepositories, SensorService sensorService) {
        this.measurementRepositories = measurementRepositories;
        this.sensorService = sensorService;
    }


    public void MeasurementAdd(Measurement measurement) {
        //Почему-то тот сенсор, который отправили при запросе почему-то надо в enrichMeasurement ещё раз добавить
        //Понял - в JSON приходит просто строка с названием сенсора, и после конвертации этот сенсор не будет находиться в контексте hibernate, надо менять на тот, который находится в hibernate контексте, то есть сенсор из БД

        //получается надо обогатить (enrich) перед добавлением в БД наш measurement
        enrichMeasurement(measurement);
       measurementRepositories.save(measurement);

    }

    private void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.findOne(measurement.getSensor().getName()));//Мы как бы меняем сенсор который сконвертировался из Json в тот который в контектсе Hibernate(в Б Д)
        measurement.setTimestamp(LocalDateTime.now());
    }

    public List<Measurement> showAll(){//Возвращает все измерения
        return measurementRepositories.findAll();
    }

    public List<Measurement> countOfRainyDays(){//Возвращает кол-во дождливых дней
        return measurementRepositories.findByRaining(true);
    }
}

package ru.alishev.springcourse.FirstRestApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.services.MeasurementService;
import ru.alishev.springcourse.FirstRestApp.services.SensorService;

@Component
public class MeasurementValidator implements Validator {

    private final MeasurementService measurementService;
    private final SensorService sensorService;

    public MeasurementValidator(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {// Здесь в таргет почему-то валидировался не measurement, а measurementDTO,
        // я это понял потому что после проверки через if, в errors.rejectValue() так как он не видел поле(field) "sensor", а требовал поле "sensorDTO"
        //Понял причину - потому что клиент заполняет поля для DTO, а не для модели, затупил ты :), Стой, а как так, мы же сканвертировали measurement,
        //значит должен быть sensor, а не sensorDTO, странно,
        //Может из-за того что клиент заполняет поля для DTO, а не для модели и вернуть надо именно то поле  с ошибкой которое заполнял клиент - точно!
        //Проблема не актуальна на 22.03.2023 я починил, внеся вправки
        Measurement measurement = (Measurement) target;//Сенсор с таким именем должен быть в БД

        if(measurement.getSensor()==null)return;

        if(sensorService.findOne(measurement.getSensor().getName())==null){
            errors.rejectValue("sensor","","There is no sensor with this name in the database");
        }

    }
}

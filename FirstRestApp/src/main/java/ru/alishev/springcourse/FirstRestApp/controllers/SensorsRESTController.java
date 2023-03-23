package ru.alishev.springcourse.FirstRestApp.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.SensorDTO;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.services.SensorService;
import ru.alishev.springcourse.FirstRestApp.util.project3.MeasurementErrorResponse;
import ru.alishev.springcourse.FirstRestApp.util.project3.MeasurementException;
import ru.alishev.springcourse.FirstRestApp.validator.SensorValidator;

import javax.validation.Valid;

import static ru.alishev.springcourse.FirstRestApp.util.project3.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorsRESTController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsRESTController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }


    @PostMapping("/registration")
    //Возвращаем HTTP ответ клиенту после сохранения переданных данных
    //принимаем Json и приводим к объекту SensorDTO
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);//конвертируем DTO в модель и сохраняет в БД
        sensorValidator.validate(sensor,bindingResult);
        if (bindingResult.hasErrors()) {//Ошибки могут появиться как через валидатор так и через @Valid

            returnErrorsToClient(bindingResult);
        }

        sensorService.save(sensor);

        //HTTP ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler //ловим искл-я со всех контроллеров, но в handleException уточняем какое именно ловим, таких хандлеров может быть много
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO,Sensor.class);

        return sensor;
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        SensorDTO sensorDTO = modelMapper.map(sensor,SensorDTO.class);

        return sensorDTO;
    }
}

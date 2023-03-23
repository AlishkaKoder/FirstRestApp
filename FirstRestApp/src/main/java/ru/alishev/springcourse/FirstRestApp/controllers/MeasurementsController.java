package ru.alishev.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.services.MeasurementService;
import ru.alishev.springcourse.FirstRestApp.util.project3.MeasurementErrorResponse;
import ru.alishev.springcourse.FirstRestApp.util.project3.MeasurementException;
import ru.alishev.springcourse.FirstRestApp.validator.MeasurementValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.alishev.springcourse.FirstRestApp.util.project3.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("measurements")
public class MeasurementsController {

    public final MeasurementValidator measurementValidator;
    public final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementValidator measurementValidator, MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add")
    //Возвращаем HTTP ответ клиенту после сохранения переданных данных
    //принимаем Json и приводим к объекту MeasurementDTO
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);//в measurementDTO не клался SensorDTO, надо было в json написать не просто sensor, а sensorDTO
        measurementValidator.validate(measurement,bindingResult);// Здесь выходила ошибка, 22.03.2023 - почему она выходила?

        if (bindingResult.hasErrors()) {
            //Здесь должна будет выбрасываться ошибка
            returnErrorsToClient(bindingResult);
        }

        measurementService.MeasurementAdd(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<MeasurementDTO>> showAll(){
        return new ResponseEntity(measurementService.showAll().stream().map(this::convertToMeasurementDTO), HttpStatus.OK);//ЗДЕСЬ НАДО КОНВЕРТИРОВАТЬ В MeasurementDTO, А НЕ ЧЕРЕЗ МОДЕЛЬ ПЕРЕДОВАТЬ
                                    //measurementService.showAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.List());
    }


    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> CountOfRainyDays(){ //Возвращает кол-во дождливых дней из БД
        Integer count = measurementService.countOfRainyDays().size();
        return new ResponseEntity(count, HttpStatus.OK);
    }
    //Ловит ошибку и показывает её клиенту
    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e){
        //ловит выброшенную ошибку MeasurementException в которой находятся ошибки(а) валидации
        //И заполняет поля класса для ошибки
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                e.getMessage(),System.currentTimeMillis()
        );
        //И возвращает поля класса для ошибки клиенту в виде JSON
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        Measurement measurement = modelMapper.map(measurementDTO,Measurement.class);

        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        MeasurementDTO measurementDTO = modelMapper.map(measurement,MeasurementDTO.class);

        return measurementDTO;
    }
}

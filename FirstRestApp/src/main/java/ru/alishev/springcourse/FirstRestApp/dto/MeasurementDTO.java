package ru.alishev.springcourse.FirstRestApp.dto;

import ru.alishev.springcourse.FirstRestApp.models.Sensor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class MeasurementDTO {

    @NotNull
    @Max(100)
    @Min(-100)
    private double value;//температура воздуха

    @NotNull
    private boolean raining;//есть ли дождь

    @NotNull
    private SensorDTO sensor;// здесь я поменял название sensorDTO на sensor, так как jackson не хотел конвертировать
    // это поле из measurement, потому что оно там называлось просто sensor, а поля при конвертации должны называться
    // одинаково, хоть и тип данных разный (SensorDTO и Sensor)


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

}

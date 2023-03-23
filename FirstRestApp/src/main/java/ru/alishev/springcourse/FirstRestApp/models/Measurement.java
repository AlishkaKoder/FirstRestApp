package ru.alishev.springcourse.FirstRestApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "measurement")
public class Measurement {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull
    @Max(100)
    @Min(-100)
    private double value;//температура воздуха // надо было исп-ть Double т.к. у примитива дефолт знач-е 0.0 - а оно валидно, а у обёртки - нулл

    @Column(name = "raining")
    @NotNull
    private boolean raining;//есть ли дождь

    @Column(name = "timestamp")
    @NotNull
    private LocalDateTime timestamp;//когда прислали данные

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor",referencedColumnName = "name")//имя уникальное поэтому мы на него ссылаемся
    private Sensor sensor;


    public Measurement(){}

    public Measurement(double value, boolean raining) {//не надо было сз-ть конст-ор, джексону нужны лишь геттеры
        this.value = value;
        this.raining = raining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }



    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}

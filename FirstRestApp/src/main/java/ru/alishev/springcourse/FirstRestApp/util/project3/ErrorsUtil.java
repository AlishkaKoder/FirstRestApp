package ru.alishev.springcourse.FirstRestApp.util.project3;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil { // Класс чтобы выбрасывал исключение и заполнял ошибки в читаемый вид
    public static void returnErrorsToClient(BindingResult bindingResult){//чтобы не писать этот метод возврата об
                                                                        // ошибке клиенту на каждом из конт-ов, вынес его в отдельный класс
        StringBuilder errorMsg = new StringBuilder();
        //проходимся по всем ошибкам все ошибки соединяем в одну строку и возвращаем её в виде Exception
        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError error:errors){
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode():error.getDefaultMessage()).append(";");
        }
        throw new MeasurementException(errorMsg.toString());// в контроллере есть хандлер который ловит его и возвращает клиенту, в красивой форме
    }
}

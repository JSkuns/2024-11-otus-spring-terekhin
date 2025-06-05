package ru.otus.hw.controllers.advices;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.dto.models.error.ErrorDto;

@Log4j2
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        var errMsg = "The entity was not found.";
        log.error(errMsg, ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorText(errMsg);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        var errMsg = "Something is wrong.";
        log.error(errMsg, ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorText(errMsg);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

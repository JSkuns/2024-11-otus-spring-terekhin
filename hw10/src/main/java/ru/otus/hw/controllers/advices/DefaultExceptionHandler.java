package ru.otus.hw.controllers.advices;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.hw.exceptions.NotFoundException;

@Log4j2
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNotFoundException(NullPointerException ex) {
        var errMsg = "The entity was not found";
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException ex) {
        var errMsg = ex.getMessage();
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(NumberFormatException ex) {
        var errMsg = "Incorrect input data format";
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        var errMsg = "An unhandled error occurred";
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errMsg = "Incorrect input data format";
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

}

package ru.otus.project.controllers.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(basePackages = "ru.otus.project.controllers.mvc")
public class GlobalMvcExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        var errMsg = ex.getMessage();
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ModelAndView handleException(HandlerMethodValidationException ex) {
        var errMsg = "Validation error";
        log.error(errMsg, ex);
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error_message", errMsg);
        return modelAndView;
    }

}
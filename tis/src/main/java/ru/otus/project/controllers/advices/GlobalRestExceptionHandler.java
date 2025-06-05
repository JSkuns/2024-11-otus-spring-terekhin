package ru.otus.project.controllers.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.project.dto.models.ErrorDto;
import ru.otus.project.exceptions.NotFoundException;

@Slf4j
@RestControllerAdvice(basePackages = "ru.otus.project.controllers.rest")
public class GlobalRestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException ex) {
        var errMsg = "The entity was not found.";
        log.error(errMsg, ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorText(errMsg);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorDto", errorDto);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        var errMsg = "Something is wrong.";
        log.error(errMsg, ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorText(errMsg);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorDto", errorDto);
        return mav;
    }

}
package ru.otus.project.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RatesController {

    @GetMapping(path = "/rates")
    public String index() {
        return "rates";
    }

}

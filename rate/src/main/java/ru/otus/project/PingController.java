package ru.otus.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PingController {

    @GetMapping(value = "/ping")
    public String ping() {
        return "OK";
    }

}

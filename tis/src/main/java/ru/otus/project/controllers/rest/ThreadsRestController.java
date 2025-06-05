package ru.otus.project.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.dto.models.ThreadDto;
import ru.otus.project.services.ThreadsService;

import java.util.List;

@RestController
@RequestMapping("/threads")
@RequiredArgsConstructor
public class ThreadsRestController {

    private final ThreadsService threadsService;

    @GetMapping(path = "/")
    @ResponseBody
    public List<ThreadDto> getAllThreads() {
        return threadsService.findAll();
    }

}

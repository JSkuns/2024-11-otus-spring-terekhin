package ru.otus.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ToolsController {

    @GetMapping(path = "/tools")
    public String index(Model model) {
        return "tools";
    }

}
package ru.otus.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentsController {

    @PostMapping(path = "/comments/delete")
    public String deleteComment(@RequestParam(value = "comment_id") String id) {
        return "redirect:/comments";
    }

}

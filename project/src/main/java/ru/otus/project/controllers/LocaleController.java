package ru.otus.project.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class LocaleController {

    @GetMapping("/locale")
    @CacheEvict(value = {"messages"}, allEntries = true)
    public String setLocale(@RequestParam("lang") String lang, HttpServletRequest request) {
        Locale locale = new Locale(lang);
        request.getSession().setAttribute("org.springframework.core.convert.LocalizedConverter.LOCALE", locale);
        return "redirect:/";
    }

}

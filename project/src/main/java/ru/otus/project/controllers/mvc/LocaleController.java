package ru.otus.project.controllers.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Controller
public class LocaleController {

    @PostMapping("/locale")
    public String changeLocale(@RequestParam(name = "lang", required = true) String lang,
                               HttpServletRequest request) {
        if (!lang.isEmpty()) {
            Locale locale = new Locale(lang);
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        }
        return "redirect:/";
    }

}

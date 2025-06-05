package ru.otus.project.controllers.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Controller
public class LocaleController {

    @PostMapping("/locale")
    public String changeLocale(
            @RequestParam(name = "lang", required = true)
            @NotNull(message = "Language must not be null.")
            @Size(max = 3, message = "Maximum length is 3 characters.")
            String lang,
            HttpServletRequest request) {

        Locale locale = new Locale(lang);
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        return "redirect:/";
    }

}

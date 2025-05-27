package ru.otus.project.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.project.actuator.CustomHealthIndicator;

@Controller
@RequiredArgsConstructor
public class HealthController {

    private final CustomHealthIndicator customHealthIndicator;

    @GetMapping("/health")
    public String showHealth(Model model) {
        var healthDetails = customHealthIndicator.health().getDetails();
        model.addAttribute("dbIndicator", healthDetails.get("db-indicator"));
        model.addAttribute("rateIndicator", healthDetails.get("rate-service-indicator"));
        return "health";
    }

}
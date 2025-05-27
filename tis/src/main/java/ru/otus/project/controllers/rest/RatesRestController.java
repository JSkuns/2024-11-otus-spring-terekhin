package ru.otus.project.controllers.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RatesRestController {

    private final RestTemplate restTemplate;

    @GetMapping("/call-random")
    public int callRandomService(
            @RequestParam
            @Valid
            @Min(value = 1, message = "First parameter should be greater than zero.")
            @Max(value = 99999, message = "First parameter maximum allowed value is 99999.")
            Integer firstParam,

            @RequestParam
            @Valid
            @Min(value = 1, message = "Second parameter should be greater than zero.")
            @Max(value = 99999, message = "Second parameter maximum allowed value is 99999.")
            Integer secondParam
    ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8081/random")
                .queryParam("firstParam", firstParam)
                .queryParam("secondParam", secondParam);

        try {
            return restTemplate.getForObject(builder.toUriString(), Integer.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve data from remote service", e);
        }
    }

}

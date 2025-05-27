package ru.otus.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomController {

    private final RandomNumberService randomNumberService;

    @Autowired
    public RandomController(RandomNumberService randomNumberService) {
        this.randomNumberService = randomNumberService;
    }

    @GetMapping("/random")
    public int getRandom(@RequestParam Integer firstParam, @RequestParam Integer secondParam) {
        return randomNumberService.generateRandom(firstParam, secondParam);
    }

}
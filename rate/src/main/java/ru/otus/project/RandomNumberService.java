package ru.otus.project;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberService {

    public int generateRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}

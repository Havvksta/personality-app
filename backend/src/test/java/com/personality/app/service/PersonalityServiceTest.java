package com.personality.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonalityServiceTest {

    private PersonalityService personalityService;

    @BeforeEach
    public void setUp() {
        personalityService = new PersonalityService();
    }

    @Test
    public void testCalculatePersonalityIntrovert() {
        List<String> answers = Arrays.asList("A", "C");

        String result = personalityService.calculatePersonality(answers);

        assertEquals("Your personality trait is: Introvert", result);
    }

    @Test
    public void testCalculatePersonalityExtrovert() {
        List<String> answers = Arrays.asList("B", "D");

        String result = personalityService.calculatePersonality(answers);

        assertEquals("Your personality trait is: Extrovert", result);
    }

    @Test
    public void testCalculatePersonalityAmbivert() {
        List<String> answers = Arrays.asList("A", "C", "D");

        String result = personalityService.calculatePersonality(answers);

        assertEquals("Your personality trait is: Ambivert", result);
    }
}

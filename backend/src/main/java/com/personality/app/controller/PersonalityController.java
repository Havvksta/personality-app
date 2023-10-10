package com.personality.app.controller;

import com.google.gson.Gson;
import com.personality.app.model.Question;
import com.personality.app.service.PersonalityService;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PersonalityController {
    private PersonalityService personalityService;

    public PersonalityController(PersonalityService personalityService) {
        this.personalityService = personalityService;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return personalityService.getQuestions();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/calculate")
    public String calculatePersonality(@RequestBody List<String> answers) {
        return personalityService.calculatePersonality(answers);
    }
}
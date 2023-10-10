package com.personality.app.service;

import com.google.gson.Gson;
import com.personality.app.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class PersonalityService {
    private List<Question> questions = new ArrayList<>();
    private Map<String, Integer> answerScores = new HashMap<>();

    public PersonalityService() {
        initializeQuestions();
        initializeAnswerScores();
    }

    private void initializeQuestions() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(ResourceUtils.getFile("classpath:questions.json"))) {
            Question[] questionArray = gson.fromJson(reader, Question[].class);

            questions = Arrays.asList(questionArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeAnswerScores() {
        answerScores.put("A", 1);
        answerScores.put("B", 2);
        answerScores.put("C", 3);
        answerScores.put("D", 4);
    }

    public String calculatePersonality(List<String> answers) {
        int introvertScore = 0;
        int extrovertScore = 0;

        for (String answer : answers) {
            int score = answerScores.get(answer);
            if (score % 2 == 0) {
                extrovertScore += score;
            } else {
                introvertScore += score;
            }
        }

        String personalityTrait;
        if (introvertScore > extrovertScore) {
            personalityTrait = "Introvert";
        } else if (extrovertScore > introvertScore) {
            personalityTrait = "Extrovert";
        } else {
            personalityTrait = "Ambivert"; // Handle ties or balance if needed
        }

        return "Your personality is: " + personalityTrait;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Map<String, Integer> getAnswerScores() {
        return answerScores;
    }

    public void setAnswerScores(Map<String, Integer> answerScores) {
        this.answerScores = answerScores;
    }
}

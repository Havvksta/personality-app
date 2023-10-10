package com.personality.app.model;

import java.util.Map;

public class Question {
    private String question;
    private Map<String, String> answers;

    public Question() {
    }

    public Question(String question, Map<String, String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }
}
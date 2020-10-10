package com.example.question.model;

import java.util.Set;

public class Question {
    private String question;
    private Set<String> answers;

    public static Question of(String question, Set<String> answers) {
        return new Question(question, answers);
    }

    private Question(String question, Set<String> answers) {
        this.question = question;
        this.answers = answers;
    }

}

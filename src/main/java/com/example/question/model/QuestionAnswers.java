package com.example.question.model;

import java.util.Set;

public class QuestionAnswers {
    private String question;
    private Set<String> answers;

    public static QuestionAnswers of(String question, Set<String> answers) {
        return new QuestionAnswers(question, answers);
    }

    private QuestionAnswers(String question, Set<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public Set<String> getAnswers() {
        return answers;
    }
}

package com.example.question.persistence;

import com.example.question.model.QuestionAnswers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleTemporaryQuestionAnswersManager implements QuestionAnswersPersistenceManager {

    private final Map<String, QuestionAnswers> questionAnswers = new HashMap<>();

    @Override
    public boolean isQuestionPersisted(String question) {
        return questionAnswers.containsKey(question);
    }

    @Override
    public Set<String> getAnswers(String question) {
        return questionAnswers.get(question).getAnswers();
    }

    @Override
    public void addQuestionAnswers(QuestionAnswers questionAnswers) {
        this.questionAnswers.put(questionAnswers.getQuestion(), questionAnswers);
    }
}

package com.example.question.persistence;

import com.example.question.model.QuestionAnswers;

import java.util.Set;

public interface QuestionAnswersPersistenceManager {

    boolean isQuestionPersisted(String question);

    Set<String> getAnswers(String question);

    void addQuestionAnswers(QuestionAnswers questionAnswers);

}

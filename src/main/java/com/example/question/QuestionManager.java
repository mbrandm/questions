package com.example.question;

import com.example.question.model.Question;

public interface QuestionManager {

    void addQuestion(Question question);

    boolean hasQuestion(String question);

//    List<String> getAnswersForQuestion(String question) {
//        return questions.get(question);
//    }
}

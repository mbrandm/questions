package com.example.question.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionHandler {
    private static final char QUESTION_MARKER = '?';
    private static final int QUESTION_MAX_SIZE = 255;
    private final List<Error> errors = new ArrayList<>();
    private String question;
    private boolean questionValid;
    private int i;

    public void handleInput(CharSequence questionInput) {
        boolean questionFound = false;
        StringBuilder questionBuilder = new StringBuilder();
        for (; i < questionInput.length() && !questionFound; i++) {
            char actual = questionInput.charAt(i);
            questionBuilder.append(actual);
            if (QUESTION_MARKER == actual) {
                questionFound = true;
            }
        }
        if (questionFound) {
            if (questionBuilder.length() > QUESTION_MAX_SIZE) {
                errors.add(Error.QUESTION_TOO_LARGE);
            } else {
                if (isQuestionEmptyOrBlank(questionBuilder)) {
                    errors.add(Error.QUESTION_EMPTY_OR_BLANK);
                } else {
                    this.question = questionBuilder.toString();
                    this.questionValid = true;
                }
            }
        } else {
            errors.add(Error.QUESTION_NOT_DEFINED);
        }
    }

    public Optional<String> getQuestion() {
        return Optional.ofNullable(question);
    }

    public boolean isValidQuestion() {
        return questionValid && errors.isEmpty();
    }

    public List<Error> getErrors() {
        return errors;
    }

    public int getLatestIndex() {
        return i;
    }

    private boolean isQuestionEmptyOrBlank(CharSequence questionPhrase) {
        String question = questionPhrase.subSequence(0, questionPhrase.length()-1).toString();
        return question.isBlank();
    }
}

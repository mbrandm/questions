package com.example.question.validation;

import java.util.ArrayList;
import java.util.List;

public class QuestionValidator {
    private static final char QUESTION_MARKER = '?';
    private static final int QUESTION_MAX_SIZE = 255;
    private final List<InputValidationError> errors = new ArrayList<>();
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
            handleFoundedQuestion(questionBuilder);
        } else {
            errors.add(InputValidationError.QUESTION_NOT_DEFINED);
        }
    }

    private void handleFoundedQuestion(StringBuilder questionBuilder) {
        if (questionBuilder.length() > QUESTION_MAX_SIZE) {
            errors.add(InputValidationError.QUESTION_TOO_LARGE);
            return;
        }
        if (isQuestionEmptyOrBlank(questionBuilder)) {
            errors.add(InputValidationError.QUESTION_EMPTY_OR_BLANK);
            return;
        }
        this.question = questionBuilder.toString();
        this.questionValid = true;
    }


    public String getQuestion() {
        return question;
    }

    public boolean isValidQuestion() {
        return questionValid && errors.isEmpty();
    }

    public List<InputValidationError> getErrors() {
        return errors;
    }

    public int getLatestIndex() {
        return i;
    }

    private boolean isQuestionEmptyOrBlank(CharSequence questionPhrase) {
        return questionPhrase.subSequence(0, questionPhrase.length() - 1).toString().isBlank();
    }
}
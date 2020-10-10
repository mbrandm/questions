package com.example.question.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.question.validation.InputHandler.ELEMENT_MAX_SIZE;

public class QuestionHandler {
    private static final char QUESTION_MARKER = '?';

    private final List<Error> errors = new ArrayList<>();

    private String question;
    private boolean questionValid;
    private int i = 0;

    public void handleInput(CharSequence inputLine) {
        boolean questionFound = false;
        StringBuilder question = new StringBuilder();
        for (; i < inputLine.length() && !questionFound; i++) {
            char actual = inputLine.charAt(i);
            question.append(actual);
            if (QUESTION_MARKER == actual) {
                questionFound = true;
            }
        }
        if (questionFound) {
            if (question.length() > ELEMENT_MAX_SIZE) {
                errors.add(Error.QUESTION_TOO_LARGE);
            } else {
                this.question = question.toString();
                this.questionValid = true;
            }
        } else {
            errors.add(Error.QUESTION_NOT_DEFINED);
        }
    }

    public Optional<String> getQuestion() {
        return Optional.ofNullable(question);
    }

    public boolean isInputValid() {
        return questionValid && errors.isEmpty();
    }

    public List<Error> getErrors() {
        return errors;
    }

    public int getLatestIndex() {
        return i;
    }
}

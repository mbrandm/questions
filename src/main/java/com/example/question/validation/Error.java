package com.example.question.validation;

public enum Error {

    EMPTY_INPUT("Error: input empty, please ask a question, or set a question/answer-combination"),
    TEXT_IN_BETWEEN_MARKER("Error: there is text in between the marker"),

    QUESTION_TOO_LARGE("Error: question is too large, only 255 chars allowed!"),
    QUESTION_EMPTY_OR_BLANK("Error: question must not be empty or blank"),
    QUESTION_NOT_DEFINED("Error: there is no question defined, use a '?' at the end of a question"),

    ANSWER_TOO_LARGE("Error: answer is too large, only 255 chars allowed"),
    ANSWER_EMPTY_OR_BLANK("Error: answer must not be empty or blank"),
    ANSWER_DUPLICATED("Error: there a duplicated answers"),
    ANSWER_END_TAG_MISSING("Error: the end-tag of an answer is missing"),

    TEXT_BETWEEN_QUESTION_AND_ANSWER("Error: There is text between the question and answer-mark"),
    TEXT_BETWEEN_ANSWERS("Error: There is text between the answers"),
    TEXT_AFTER_LATEST_ANSWER("Error: There is text after the latest answer")
    ;

    private String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

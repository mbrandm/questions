package com.example.question.validation;

public enum Error {

    EMPTY_INPUT("Error: input empty or blank, please ask a question, or set a question/answer-combination"),

    QUESTION_TOO_LARGE("Error: question is too large, only 255 chars allowed!"),
    QUESTION_EMPTY_OR_BLANK("Error: question must not be empty or blank"),
    QUESTION_NOT_DEFINED("Error: there is no question defined, use a '?' at the end of a question"),

    ANSWER_TOO_LARGE("Error: answer is too large, only 255 chars allowed"),
    ANSWER_EMPTY_OR_BLANK("Error: answer must not be empty or blank"),
    ANSWER_DUPLICATED("Error: there a duplicated answers"),
    ANSWER_END_TAG_MISSING("Error: the end-tag of an answer is missing"),

    TEXT_OUTSIDE_TAGS("Error: There is text where not text should be. Either outside the \"-Tags, or in between question(?) and answer-marker(\")")

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

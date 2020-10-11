package com.example.question.persistence;

public enum PersistenceMessage {

    QUESTION_SAVED_SUCCESSFULLY("Question was saved successfully"),
    QUESTION_STILL_AVAILABLE("Error: The question is still saved. Please define a new one");

    private String message;

    PersistenceMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

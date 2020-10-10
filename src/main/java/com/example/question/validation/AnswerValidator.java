package com.example.question.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnswerValidator {
    private static final char START_TAG = '\"';
    private static final char END_TAG = '\"';
    private static final int MAX_ANSWER_SIZE = 255;

    private final List<InputValidationError> errors = new ArrayList<>();
    private final Set<String> answers = new HashSet<>();


    //TODO: refactor
    public void handleInput(CharSequence inputLine) {

        for (int i = 0; i < inputLine.length(); ) {
            char actual = inputLine.charAt(i);

            if (actual != ' ' && actual != START_TAG) {
                errors.add(InputValidationError.TEXT_OUTSIDE_TAGS);
                break;
            } else {

                if (START_TAG == actual) {

                    boolean answerStarted = true;
                    boolean answerCompleted = false;
                    i++;
                    StringBuilder answerBuilder = new StringBuilder();
                    String answer;

                    for (; i < inputLine.length() && !answerCompleted; i++) {

                        actual = inputLine.charAt(i);

                        if (END_TAG == actual) {
                            answerCompleted = true;
                        } else {
                            if (answerStarted) {
                                answerBuilder.append(actual);
                            }
                        }
                    }

                    if (answerCompleted) {
                        answer = answerBuilder.toString();
                        if (answer.isBlank()) {
                            errors.add(InputValidationError.ANSWER_EMPTY_OR_BLANK);
                        } else {
                            if (answer.length() > MAX_ANSWER_SIZE) {
                                errors.add(InputValidationError.ANSWER_TOO_LARGE);
                            } else {
                                if (answers.contains(answer)) {
                                    errors.add(InputValidationError.ANSWER_DUPLICATED);
                                } else {
                                    answers.add(answer);
                                }
                            }
                        }
                    } else {
                        errors.add(InputValidationError.ANSWER_END_TAG_MISSING);
                    }
                } else {
                    i++;
                }
            }
        }
    }


    public boolean hasAnswers() {
        return !answers.isEmpty();
    }

    public boolean isValidAnswerInput() {
        return errors.isEmpty();
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public List<InputValidationError> getErrors() {
        return errors;
    }
}




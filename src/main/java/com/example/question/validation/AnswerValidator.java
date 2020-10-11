package com.example.question.validation;

import java.util.*;

public class AnswerValidator {
    private static final char START_TAG = '\"';
    private static final char END_TAG = '\"';
    private static final int MAX_ANSWER_SIZE = 255;

    private final List<InputValidationError> errors = new ArrayList<>();
    private final Set<String> answers = new LinkedHashSet<>();


    //TODO: refactor, reduce complexity
    public void handleInput(CharSequence inputLine) {

        for (int i = 0; i < inputLine.length(); ) {
            char actual = inputLine.charAt(i);

            if (actual != ' ' && actual != START_TAG) {
                errors.add(InputValidationError.TEXT_OUTSIDE_TAGS);
                break;
            }

            if (START_TAG == actual) {
                boolean answerStarted = true;
                boolean answerCompleted = false;
                StringBuilder answerBuilder = new StringBuilder();

                for (i++; i < inputLine.length() && !answerCompleted; i++) {
                    actual = inputLine.charAt(i);
                    if (END_TAG == actual) {
                        answerCompleted = true;
                    } else if (answerStarted) {
                        answerBuilder.append(actual);
                    }
                }
                if (answerCompleted) {
                    handleCompletedAnswer(answerBuilder);
                } else {
                    errors.add(InputValidationError.ANSWER_END_TAG_MISSING);
                }
            } else {
                i++;
            }

        }
    }

    private void handleCompletedAnswer(StringBuilder answerBuilder) {
        String answer;
        answer = answerBuilder.toString();
        if (answer.isBlank()) {
            errors.add(InputValidationError.ANSWER_EMPTY_OR_BLANK);
            return;
        }
        if (answer.length() > MAX_ANSWER_SIZE) {
            errors.add(InputValidationError.ANSWER_TOO_LARGE);
            return;
        }
        if (answers.contains(answer)) {
            errors.add(InputValidationError.ANSWER_DUPLICATED_EXACT_MATCH);
            return;
        }
        if (hasEqualCaseSensitiveAnswer(answer)) {
            errors.add(InputValidationError.ANSWER_DUPLICATED_INSENSITIVE_MATCH);
            return;
        }
        answers.add(answer);
    }


    private boolean hasEqualCaseSensitiveAnswer(String answerToPutOnCollection) {
        return answers.stream()
                .anyMatch(answerToPutOnCollection::equalsIgnoreCase);
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
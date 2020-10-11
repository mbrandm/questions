package com.example.question.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InputValidator {
    private List<InputValidationError> generalErrors = new ArrayList<>();
    private QuestionValidator questionValidator = new QuestionValidator();
    private AnswerValidator answerValidator = new AnswerValidator();


    public InputValidator(String inputLine) {
        if (inputLine == null || inputLine.isBlank()) {
            generalErrors.add(InputValidationError.EMPTY_INPUT);
        } else {
            handleInput(inputLine);
        }
    }

    private void handleInput(String inputLine) {
        questionValidator.handleInput(inputLine);
        if (inputLine.length() > questionValidator.getLatestIndex()) {
            answerValidator.handleInput(inputLine.substring(questionValidator.getLatestIndex()));
        }
    }

    public boolean isInputValid() {
        return isValidSingleQuestion() || isValidQuestionAnswerCombination();
    }

    public String getQuestion() {
        return questionValidator.getQuestion();
    }

    public Set<String> getAnswers() {
        return answerValidator.getAnswers();
    }


    public boolean isValidSingleQuestion() {
        return generalErrors.isEmpty()
                && questionValidator.isValidQuestion()
                && answerValidator.isValidAnswerInput()
                && !answerValidator.hasAnswers();
    }

    public boolean isValidQuestionAnswerCombination() {
        return generalErrors.isEmpty()
                && questionValidator.isValidQuestion()
                && answerValidator.isValidAnswerInput()
                && answerValidator.hasAnswers();
    }

    public List<InputValidationError> getErrors() {
        List<InputValidationError> mergedErrorList = new ArrayList<>(generalErrors);
        mergedErrorList.addAll(questionValidator.getErrors());
        mergedErrorList.addAll(answerValidator.getErrors());
        return mergedErrorList;
    }
}

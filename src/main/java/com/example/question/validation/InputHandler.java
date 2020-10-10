package com.example.question.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InputHandler {
    private List<Error> generalErrors = new ArrayList<>();
    private QuestionHandler questionHandler = new QuestionHandler();
    private AnswerHandler answerHandler = new AnswerHandler();


    public InputHandler(String inputLine) {
        if (inputLine == null || inputLine.isBlank()) {
            generalErrors.add(Error.EMPTY_INPUT);
        } else {
            handleInput(inputLine);
        }
    }

    private void handleInput(String inputLine) {
        questionHandler.handleInput(inputLine);
        if (inputLine.length() > questionHandler.getLatestIndex()) {
            answerHandler.handleInput(inputLine.substring(questionHandler.getLatestIndex()));
        }
    }

    public boolean isInputValid() {
        return isValidSingleQuestion() || isValidQuestionAnswerCombination();
    }

    public Optional<String> getQuestion() {
        return questionHandler.getQuestion();
    }

    public Set<String> getAnswers() {
        return answerHandler.getAnswers();
    }


    public boolean isValidSingleQuestion() {
        return generalErrors.isEmpty()
                && questionHandler.isValidQuestion()
                && answerHandler.isValidAnswerInput()
                && !answerHandler.hasAnswers();
    }

    public boolean isValidQuestionAnswerCombination() {
        return generalErrors.isEmpty()
                && questionHandler.isValidQuestion()
                && answerHandler.isValidAnswerInput()
                && answerHandler.hasAnswers();
    }

    public List<Error> getErrors() {
        List<Error> mergedErrorList = new ArrayList<>(generalErrors);
        mergedErrorList.addAll(questionHandler.getErrors());
        mergedErrorList.addAll(answerHandler.getErrors());
        return mergedErrorList;
    }
}

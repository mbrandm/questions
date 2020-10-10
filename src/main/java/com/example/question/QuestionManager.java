package com.example.question;

import com.example.question.model.QuestionAnswers;
import com.example.question.persistence.QuestionAnswersPersistenceManager;
import com.example.question.persistence.SimpleTemporaryQuestionAnswersManager;
import com.example.question.validation.InputValidationError;
import com.example.question.validation.InputValidator;

import java.util.List;
import java.util.Set;

public class QuestionManager {

    //change implementation when required
    private QuestionAnswersPersistenceManager persistenceManager = new SimpleTemporaryQuestionAnswersManager();


    public void process(String inputLine) {
        InputValidator inputValidator = new InputValidator(inputLine);

        if (inputValidator.isValidSingleQuestion()) {
            processSingleQuestion(inputValidator.getQuestion(), persistenceManager);

        } else if (inputValidator.isValidQuestionAnswerCombination()) {
            QuestionAnswers questionAnswers = QuestionAnswers.of(inputValidator.getQuestion(), inputValidator.getAnswers());
            processQuestionAnswerCombination(questionAnswers, persistenceManager);

        } else {
            processErrors(inputValidator.getErrors());
        }

    }

    private void processSingleQuestion(String question, QuestionAnswersPersistenceManager persistenceManager) {
        if (persistenceManager.containsAnswer(question)) {
            Set<String> answers = persistenceManager.getAnswers(question);
            OutputPerformer.performOutput(answers);
        } else {
            OutputPerformer.performDefaultAnswer();
        }
    }

    private void processQuestionAnswerCombination(QuestionAnswers questionAnswers, QuestionAnswersPersistenceManager persistenceManager) {
        if(!persistenceManager.containsAnswer(questionAnswers.getQuestion())) {
            persistenceManager.addQuestionAnswers(questionAnswers);
        }
        else {
            //TODO: all the handlings and checks
        }

    }

    private void processErrors(List<InputValidationError> errors) {
        errors.forEach(System.out::println);
    }
}

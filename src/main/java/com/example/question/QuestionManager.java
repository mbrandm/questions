package com.example.question;

import com.example.question.model.QuestionAnswers;
import com.example.question.persistence.PersistenceMessage;
import com.example.question.persistence.QuestionAnswersPersistenceManager;
import com.example.question.persistence.SimpleTemporaryQuestionAnswersManager;
import com.example.question.validation.InputValidationError;
import com.example.question.validation.InputValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionManager {

    //change implementation when required
    private QuestionAnswersPersistenceManager persistenceManager = new SimpleTemporaryQuestionAnswersManager();
    private OutputPerformer outputPerformer = new OutputPerformer();


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
        if (persistenceManager.isQuestionPersisted(question)) {
            Set<String> answers = persistenceManager.getAnswers(question);
            outputPerformer.performOutput(answers);
        } else {
            outputPerformer.performDefaultAnswer();
        }
    }

    private void processQuestionAnswerCombination(QuestionAnswers questionAnswers, QuestionAnswersPersistenceManager persistenceManager) {
        if (!persistenceManager.isQuestionPersisted(questionAnswers.getQuestion())) {
            persistenceManager.addQuestionAnswers(questionAnswers);
            outputPerformer.outputMessage(PersistenceMessage.QUESTION_SAVED_SUCCESSFULLY.toString());
        } else {
            outputPerformer.outputMessage(PersistenceMessage.QUESTION_STILL_AVAILABLE.toString());
        }
    }

    private void processErrors(List<InputValidationError> errors) {
        outputPerformer.outputMessages(errors.stream()
                .map(InputValidationError::toString)
                .collect(Collectors.toList()));
    }

}

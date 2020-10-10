package com.example.question.validation;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    protected static final int ELEMENT_MAX_SIZE = 255;
    private List<Error> errors = new ArrayList<>();
    private QuestionHandler questionHandler = new QuestionHandler();
    private AnswerHandler answerHandler = new AnswerHandler();
    private char[] inputLine;
    private boolean validInput;

    public InputHandler(String inputLine) {
        if (inputLine == null || inputLine.isBlank()) {
            errors.add(Error.EMPTY_INPUT);
        } else {
            handleInput(inputLine);
        }
    }

    public boolean isInputValid() {
        return questionHandler.isInputValid() && errors.isEmpty();
    }

    public List<Error> getErrors() {
        return errors;
    }

    //    Tests
//    What is the question? "answer1" "answer2"
//    What is the question?   (3 blanks)
//  ERRORS:
//    What is the question??? "answer1" "answer" --> mehrere Fragezeichen
//    What is the question? sdflkjsdflkjsf "answer1" "answer" --> Antwort ohne
//    What is the Question? "asdf" lkjaslfkjslkfd
//    What is the Question? asdf asdf ""  --> Fehlermeldung, weil Text zwischen den Zeilen?
//


    private void handleInput(String inputLine) {

        questionHandler.handleInput(inputLine);

        if (inputLine.length() > questionHandler.getLatestIndex()) {
            answerHandler.handleInput(inputLine.substring(questionHandler.getLatestIndex()));
        }
    }



    //empty String
    //Check syntax
    //<question>? “<answer1>” “<answer2>” “<answerX>”
    //question max 255 char
    //answer max 255 char
    //

    public boolean isValidQuestionOnly() {
        //TODO:
        return false;
    }

    public boolean isValidQuestionAnswerCombination() {
        //TODO:
        return false;
    }


}

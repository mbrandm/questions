package com.example.question.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerTest {

    @Test
    void handleInput_emptyInput_shouldShowEmptyInputError() {
        InputHandler inputHandler = new InputHandler("");
        assertAll(
                () -> assertFalse(inputHandler.isInputValid()),
                () -> assertThat(inputHandler.getErrors())
                        .containsExactly(Error.EMPTY_INPUT)
        );
    }

    @Test
    void handleInput_blankedInput_shouldShowEmptyInputError() {
        InputHandler inputHandler = new InputHandler("      ");
        assertAll(
                () -> assertFalse(inputHandler.isInputValid()),
                () -> assertThat(inputHandler.getErrors())
                        .containsExactly(Error.EMPTY_INPUT)
        );
    }


    @Test
    void handleInput_questionWithMultipleQuestionMarker_shouldShowTextOutsideTagError() {
        InputHandler inputHandler = new InputHandler("     What is the question  ????     ");
        assertAll(
                () -> assertFalse(inputHandler.isInputValid()),
                () -> assertThat(inputHandler.getErrors())
                        .containsExactly(Error.TEXT_OUTSIDE_TAGS)
        );
    }


    @Test
    void handleInput_combinedCaseWithTextBetween_shouldNotBeValid() {
        InputHandler inputHandler = new InputHandler("What is the question???? \"t1\" \"t2\"");
        assertAll(
                () -> assertFalse(inputHandler.isInputValid()),
                () -> assertFalse(inputHandler.isValidQuestionAnswerCombination()),
                () -> assertFalse(inputHandler.isValidSingleQuestion())
        );
    }

    @Test
    void handleInput_combinedCaseWithMoreTextBetween_shouldNotBeValid() {
        InputHandler inputHandler = new InputHandler("What is the question???? \"t1\"??? \"t2\"");
        assertAll(
                () -> assertFalse(inputHandler.isInputValid()),
                () -> assertFalse(inputHandler.isValidQuestionAnswerCombination()),
                () -> assertFalse(inputHandler.isValidSingleQuestion())
        );
    }

    @Test
    void handleInput_correctCombinedCase_shouldBeValid() {
        InputHandler inputHandler = new InputHandler("What is the question? \"t1\" \"t2\"");
        assertAll(
                () -> assertTrue(inputHandler.isInputValid()),
                () -> assertFalse(inputHandler.isValidSingleQuestion()),
                () -> assertTrue(inputHandler.isValidQuestionAnswerCombination()),
                () -> assertThat(inputHandler.getAnswers())
                        .contains("t1")
                        .contains("t2")
        );
    }

    @Test
    void handleInput_correctQuestionCase_shouldBeValid() {
        InputHandler inputHandler = new InputHandler("What is the question?");
        assertAll(
                () -> assertTrue(inputHandler.isInputValid()),
                () -> assertFalse(inputHandler.isValidQuestionAnswerCombination()),
                () -> assertTrue(inputHandler.isValidSingleQuestion()),
                () -> assertThat(inputHandler.getQuestion().get())
                        .isEqualTo("What is the question?")
        );
    }

}

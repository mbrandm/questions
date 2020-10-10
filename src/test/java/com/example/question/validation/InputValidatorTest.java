package com.example.question.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    @Test
    void handleInput_emptyInput_shouldShowEmptyInputError() {
        InputValidator inputValidator = new InputValidator("");
        assertAll(
                () -> assertFalse(inputValidator.isInputValid()),
                () -> assertThat(inputValidator.getErrors())
                        .containsExactly(InputValidationError.EMPTY_INPUT)
        );
    }

    @Test
    void handleInput_blankedInput_shouldShowEmptyInputError() {
        InputValidator inputValidator = new InputValidator("      ");
        assertAll(
                () -> assertFalse(inputValidator.isInputValid()),
                () -> assertThat(inputValidator.getErrors())
                        .containsExactly(InputValidationError.EMPTY_INPUT)
        );
    }


    @Test
    void handleInput_questionWithMultipleQuestionMarker_shouldShowTextOutsideTagError() {
        InputValidator inputValidator = new InputValidator("     What is the question  ????     ");
        assertAll(
                () -> assertFalse(inputValidator.isInputValid()),
                () -> assertThat(inputValidator.getErrors())
                        .containsExactly(InputValidationError.TEXT_OUTSIDE_TAGS)
        );
    }


    @Test
    void handleInput_combinedCaseWithTextBetween_shouldNotBeValid() {
        InputValidator inputValidator = new InputValidator("What is the question???? \"t1\" \"t2\"");
        assertAll(
                () -> assertFalse(inputValidator.isInputValid()),
                () -> assertFalse(inputValidator.isValidQuestionAnswerCombination()),
                () -> assertFalse(inputValidator.isValidSingleQuestion())
        );
    }

    @Test
    void handleInput_combinedCaseWithMoreTextBetween_shouldNotBeValid() {
        InputValidator inputValidator = new InputValidator("What is the question???? \"t1\"??? \"t2\"");
        assertAll(
                () -> assertFalse(inputValidator.isInputValid()),
                () -> assertFalse(inputValidator.isValidQuestionAnswerCombination()),
                () -> assertFalse(inputValidator.isValidSingleQuestion())
        );
    }

    @Test
    void handleInput_correctCombinedCase_shouldBeValid() {
        InputValidator inputValidator = new InputValidator("What is the question? \"t1\" \"t2\"");
        assertAll(
                () -> assertTrue(inputValidator.isInputValid()),
                () -> assertFalse(inputValidator.isValidSingleQuestion()),
                () -> assertTrue(inputValidator.isValidQuestionAnswerCombination()),
                () -> assertThat(inputValidator.getAnswers())
                        .contains("t1")
                        .contains("t2")
        );
    }

    @Test
    void handleInput_correctQuestionCase_shouldBeValid() {
        InputValidator inputValidator = new InputValidator("What is the question?");
        assertAll(
                () -> assertTrue(inputValidator.isInputValid()),
                () -> assertFalse(inputValidator.isValidQuestionAnswerCombination()),
                () -> assertTrue(inputValidator.isValidSingleQuestion()),
                () -> assertThat(inputValidator.getQuestion())
                        .isEqualTo("What is the question?")
        );
    }

}

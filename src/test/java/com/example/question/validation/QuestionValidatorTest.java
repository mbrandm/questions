package com.example.question.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionValidatorTest {

    private QuestionValidator questionValidator;

    @BeforeEach
    void init() {
        questionValidator = new QuestionValidator();
    }


    @Test
    void handleInput_correctQuestion_shouldBeValid() {
        questionValidator.handleInput("What is the question?");
        assertAll(
                () -> assertTrue(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getQuestion())
                        .isEqualTo("What is the question?"),
                () -> assertThat(questionValidator.getErrors())
                        .isEmpty()
        );
    }

    @Test
    void handleInput_questionWithLeadingAndTrailingBlanks_trailingBlanksShouldBeCutted() {
        questionValidator.handleInput("     What is the question  ?     ");
        assertAll(
                () -> assertTrue(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getQuestion())
                        .isEqualTo("     What is the question  ?"),
                () -> assertThat(questionValidator.getErrors())
                        .isEmpty()
        );
    }

    @Test
    void handleInput_questionWithMultipleQuestionMarker_questionShouldBeOk() {
        questionValidator.handleInput("     What is the question  ????     ");
        assertAll(
                () -> assertTrue(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getQuestion())
                        .isEqualTo("     What is the question  ?"),
                () -> assertThat(questionValidator.getErrors())
                        .isEmpty()
        );
    }

    @Test
    void handleInput_questionTooLarge_shouldShowAQuestionTooLargeError() {
        questionValidator.handleInput("What is the question  slkdjfsöldjfsldöfjslökfdalsfdjlaöskdlöskajdglöksajdflöjflkj23t0iugf3209ghp9oagh928hgawioghksjaghaskhgdsoidgh29hg023hg023gh203gh203g923hg092h3g02h3g0ahgoagha2g89o23ogha2o3gha23og8ha23ohgio23gho3ghi2lg3kkwlehgaw98geh982hg9283gh923gh239g ?");
        assertAll(
                () -> assertFalse(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getErrors())
                        .containsExactly(InputValidationError.QUESTION_TOO_LARGE)
        );
    }


    @Test
    void handleInput_emptyQuestion_shouldShowQuestionNotDefinedError() {
        questionValidator.handleInput("?");
        assertAll(
                () -> assertFalse(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getErrors())
                        .containsExactly(InputValidationError.QUESTION_EMPTY_OR_BLANK)
        );
    }

    @Test
    void handleInput_blankedQuestion_shouldShowQuestionNotDefinedError() {
        questionValidator.handleInput("   ?");
        assertAll(
                () -> assertFalse(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getErrors())
                        .containsExactly(InputValidationError.QUESTION_EMPTY_OR_BLANK)
        );
    }

    @Test
    void handleInput_TextWithNoQuestionMarker_shouldShowQuestionNoteDefinedError() {
        questionValidator.handleInput("What is the question");
        assertAll(
                () -> assertFalse(questionValidator.isValidQuestion()),
                () -> assertThat(questionValidator.getErrors())
                        .containsExactly(InputValidationError.QUESTION_NOT_DEFINED)
        );
    }



}

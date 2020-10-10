package com.example.question.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionHandlerTest {

    private QuestionHandler questionHandler;

    @BeforeEach
    void init() {
        questionHandler = new QuestionHandler();
    }


    @Test
    void handleInput_correctQuestion_shouldBeValid() {
        questionHandler.handleInput("What is the question?");
        assertAll(
                () -> assertTrue(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getQuestion().get())
                        .isEqualTo("What is the question?"),
                () -> assertThat(questionHandler.getErrors())
                        .isEmpty()
        );
    }

    @Test
    void handleInput_questionWithLeadingAndTrailingBlanks_trailingBlanksShouldBeCutted() {
        questionHandler.handleInput("     What is the question  ?     ");
        assertAll(
                () -> assertTrue(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getQuestion().get())
                        .isEqualTo("     What is the question  ?"),
                () -> assertThat(questionHandler.getErrors())
                        .isEmpty()
        );
    }

    @Test
    void handleInput_questionWithMultipleQuestionMarker_questionShouldBeOk() {
        questionHandler.handleInput("     What is the question  ????     ");
        assertAll(
                () -> assertTrue(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getQuestion().get())
                        .isEqualTo("     What is the question  ?"),
                () -> assertThat(questionHandler.getErrors())
                        .isEmpty()
        );
    }

    @Test
    void handleInput_questionTooLarge_shouldShowAQuestionTooLargeError() {
        questionHandler.handleInput("What is the question  slkdjfsöldjfsldöfjslökfdalsfdjlaöskdlöskajdglöksajdflöjflkj23t0iugf3209ghp9oagh928hgawioghksjaghaskhgdsoidgh29hg023hg023gh203gh203g923hg092h3g02h3g0ahgoagha2g89o23ogha2o3gha23og8ha23ohgio23gho3ghi2lg3kkwlehgaw98geh982hg9283gh923gh239g ?");
        assertAll(
                () -> assertFalse(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getErrors())
                        .containsExactly(Error.QUESTION_TOO_LARGE)
        );
    }


    @Test
    void handleInput_emptyQuestion_shouldShowQuestionNotDefinedError() {
        questionHandler.handleInput("?");
        assertAll(
                () -> assertFalse(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getErrors())
                        .containsExactly(Error.QUESTION_EMPTY_OR_BLANK)
        );
    }

    @Test
    void handleInput_blankedQuestion_shouldShowQuestionNotDefinedError() {
        questionHandler.handleInput("   ?");
        assertAll(
                () -> assertFalse(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getErrors())
                        .containsExactly(Error.QUESTION_EMPTY_OR_BLANK)
        );
    }

    @Test
    void handleInput_TextWithNoQuestionMarker_shouldShowQuestionNoteDefinedError() {
        questionHandler.handleInput("What is the question");
        assertAll(
                () -> assertFalse(questionHandler.isValidQuestion()),
                () -> assertThat(questionHandler.getErrors())
                        .containsExactly(Error.QUESTION_NOT_DEFINED)
        );
    }



}

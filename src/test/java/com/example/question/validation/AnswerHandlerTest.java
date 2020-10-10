package com.example.question.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AnswerHandlerTest {

    private AnswerHandler answerHandler;

    @BeforeEach
    void init() {
        answerHandler = new AnswerHandler();
    }


    @Test
    void handleInput_2CorrectAnswers_shouldBeValid() {
        answerHandler.handleInput("\"test1\" \"test2\"");
        assertAll(
                () -> assertTrue(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getAnswers())
                        .hasSize(2)
                        .contains("test1")
                        .contains("test2")
        );
    }

    @Test
    void handleInput_2CorrectAnswersWithLeadingAndTrailingBlanks_shouldBeValid() {
        answerHandler.handleInput("    \"test1\"       \"test2\"    ");
        assertAll(
                () -> assertTrue(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getAnswers())
                        .hasSize(2)
                        .contains("test1")
                        .contains("test2")
        );
    }


    @Test
    void handleInput_2CorrectAnswersWithBlanksInBetweenAnswer_shouldBeValidAndBlanksShouldBeConsidered() {
        answerHandler.handleInput("    \"te    s t1\"       \"t   est2    \"    ");
        assertAll(
                () -> assertTrue(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getAnswers())
                        .hasSize(2)
                        .contains("te    s t1")
                        .contains("t   est2    ")
        );
    }

    @Test
    void handleInput_duplicatedAnswers_shouldShowAnError() {
        answerHandler.handleInput("\"t1\"\"t2\" \"t1\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .hasSize(1)
                        .containsExactly(Error.ANSWER_DUPLICATED)
        );
    }


    @Test
    void handleInput_blankAnswer_shouldShowEmptyOrBlankError() {
        answerHandler.handleInput("\"t1\"\"  \" \"t2\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .hasSize(1)
                        .containsExactly(Error.ANSWER_EMPTY_OR_BLANK)
        );
    }

    @Test
    void handleInput_emptyAnswer_shouldShowEmptyOrBlankError() {
        answerHandler.handleInput("\"t1\"\"\" \"t2\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .hasSize(1)
                        .containsExactly(Error.ANSWER_EMPTY_OR_BLANK)
        );
    }


    @Test
    void handleInput_emptyAndDuplicatedAnswer_shouldShowEmptyOrBlankAndDuplicatedErrors() {
        answerHandler.handleInput("\"t1\"\"\" \"t1\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .hasSize(2)
                        .contains(Error.ANSWER_EMPTY_OR_BLANK)
                        .contains(Error.ANSWER_DUPLICATED)
        );
    }


    @Test
    void handleInput_blankedInputNoAnswerDefined_shouldBeNotConsideredAsAnswerAndBeValid() {
        answerHandler.handleInput("   ");
        assertAll(
                () -> assertTrue(answerHandler.isValidAnswerInput()),
                () -> assertFalse(answerHandler.hasAnswers()),
                () -> assertThat(answerHandler.getErrors())
                        .isEmpty(),
                () -> assertThat(answerHandler.getAnswers())
                        .hasSize(0)
        );
    }

    @Test
    void handleInput_emptyInputNoAnswerDefined_shouldBeNotConsideredAsAnswerAndBeValid() {
        answerHandler.handleInput("");
        assertAll(
                () -> assertTrue(answerHandler.isValidAnswerInput()),
                () -> assertFalse(answerHandler.hasAnswers()),
                () -> assertThat(answerHandler.getErrors())
                        .isEmpty(),
                () -> assertThat(answerHandler.getAnswers())
                        .hasSize(0)
        );
    }



    @Test
    void handleInput_textAsInputNoAnswer_shouldShowAnTextOutsideError() {
        answerHandler.handleInput(" te   s  ttest");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertFalse(answerHandler.hasAnswers()),
                () -> assertThat(answerHandler.getErrors())
                        .containsExactly(Error.TEXT_OUTSIDE_TAGS),
                () -> assertThat(answerHandler.getAnswers())
                        .hasSize(0)
        );
    }

    @Test
    void handleInput_textBeforeAnswer_shouldShowAnTextOutsideError() {
        answerHandler.handleInput(" te   s  ttest \"t1\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .containsExactly(Error.TEXT_OUTSIDE_TAGS)
        );
    }

    @Test
    void handleInput_textBetweenAnswers_shouldShowAnTextOutsideError() {
        answerHandler.handleInput("\"t1\"asdf\"t2\"  \"t3\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .containsExactly(Error.TEXT_OUTSIDE_TAGS)
        );
    }


    @Test
    void handleInput_endTagMissing_shouldShowAnEndTagMissingError() {
        answerHandler.handleInput("\"t1\" \"t2\" \"t3");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .containsExactly(Error.ANSWER_END_TAG_MISSING)
        );
    }


    @Test
    void handleInput_middleTagMissing_shouldShowTextOutsideTagsError() {
        answerHandler.handleInput("\"t1\" \"t2   \"t3\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .containsExactly(Error.TEXT_OUTSIDE_TAGS)
        );
    }


    @Test
    void handleInput_inputLongernThan255Chars_shouldShowTextOutsideTagsError() {
        answerHandler.handleInput("\"t1\"   \"t2\" \"asdflksjadfljsfdlö    kjsalgökjslgjsalögjslödgjsa    ldfjalsdfjlkjglkjwg02ug9283hgsdhgkjsagflksjdglkjsagldkjsdglöasjgdlöskajgdlösdjglkjsglsjdglsjdglösdjglskdjgslkdjglskdgjlskdjglksjgdlksjglksjdlgkjslgdkjsdolgijsgoijgwejgosjglsjgoisdjgoisjgosidgjsodigjsoidgjsoidgjsoidgjsoidgjsoigjsoidgjsdog\"");
        assertAll(
                () -> assertFalse(answerHandler.isValidAnswerInput()),
                () -> assertThat(answerHandler.getErrors())
                        .containsExactly(Error.ANSWER_TOO_LARGE)
        );
    }






}

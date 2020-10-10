package com.example.question.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AnswerValidatorTest {

    private AnswerValidator answerValidator;

    @BeforeEach
    void init() {
        answerValidator = new AnswerValidator();
    }


    @Test
    void handleInput_2CorrectAnswers_shouldBeValid() {
        answerValidator.handleInput("\"test1\" \"test2\"");
        assertAll(
                () -> assertTrue(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getAnswers())
                        .hasSize(2)
                        .contains("test1")
                        .contains("test2")
        );
    }

    @Test
    void handleInput_2CorrectAnswersWithLeadingAndTrailingBlanks_shouldBeValid() {
        answerValidator.handleInput("    \"test1\"       \"test2\"    ");
        assertAll(
                () -> assertTrue(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getAnswers())
                        .hasSize(2)
                        .contains("test1")
                        .contains("test2")
        );
    }


    @Test
    void handleInput_2CorrectAnswersWithBlanksInBetweenAnswer_shouldBeValidAndBlanksShouldBeConsidered() {
        answerValidator.handleInput("    \"te    s t1\"       \"t   est2    \"    ");
        assertAll(
                () -> assertTrue(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getAnswers())
                        .hasSize(2)
                        .contains("te    s t1")
                        .contains("t   est2    ")
        );
    }

    @Test
    void handleInput_duplicatedAnswers_shouldShowAnError() {
        answerValidator.handleInput("\"t1\"\"t2\" \"t1\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .hasSize(1)
                        .containsExactly(InputValidationError.ANSWER_DUPLICATED)
        );
    }


    @Test
    void handleInput_blankAnswer_shouldShowEmptyOrBlankError() {
        answerValidator.handleInput("\"t1\"\"  \" \"t2\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .hasSize(1)
                        .containsExactly(InputValidationError.ANSWER_EMPTY_OR_BLANK)
        );
    }

    @Test
    void handleInput_emptyAnswer_shouldShowEmptyOrBlankError() {
        answerValidator.handleInput("\"t1\"\"\" \"t2\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .hasSize(1)
                        .containsExactly(InputValidationError.ANSWER_EMPTY_OR_BLANK)
        );
    }


    @Test
    void handleInput_emptyAndDuplicatedAnswer_shouldShowEmptyOrBlankAndDuplicatedErrors() {
        answerValidator.handleInput("\"t1\"\"\" \"t1\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .hasSize(2)
                        .contains(InputValidationError.ANSWER_EMPTY_OR_BLANK)
                        .contains(InputValidationError.ANSWER_DUPLICATED)
        );
    }


    @Test
    void handleInput_blankedInputNoAnswerDefined_shouldBeNotConsideredAsAnswerAndBeValid() {
        answerValidator.handleInput("   ");
        assertAll(
                () -> assertTrue(answerValidator.isValidAnswerInput()),
                () -> assertFalse(answerValidator.hasAnswers()),
                () -> assertThat(answerValidator.getErrors())
                        .isEmpty(),
                () -> assertThat(answerValidator.getAnswers())
                        .hasSize(0)
        );
    }

    @Test
    void handleInput_emptyInputNoAnswerDefined_shouldBeNotConsideredAsAnswerAndBeValid() {
        answerValidator.handleInput("");
        assertAll(
                () -> assertTrue(answerValidator.isValidAnswerInput()),
                () -> assertFalse(answerValidator.hasAnswers()),
                () -> assertThat(answerValidator.getErrors())
                        .isEmpty(),
                () -> assertThat(answerValidator.getAnswers())
                        .hasSize(0)
        );
    }



    @Test
    void handleInput_textAsInputNoAnswer_shouldShowAnTextOutsideError() {
        answerValidator.handleInput(" te   s  ttest");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertFalse(answerValidator.hasAnswers()),
                () -> assertThat(answerValidator.getErrors())
                        .containsExactly(InputValidationError.TEXT_OUTSIDE_TAGS),
                () -> assertThat(answerValidator.getAnswers())
                        .hasSize(0)
        );
    }

    @Test
    void handleInput_textBeforeAnswer_shouldShowAnTextOutsideError() {
        answerValidator.handleInput(" te   s  ttest \"t1\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .containsExactly(InputValidationError.TEXT_OUTSIDE_TAGS)
        );
    }

    @Test
    void handleInput_textBetweenAnswers_shouldShowAnTextOutsideError() {
        answerValidator.handleInput("\"t1\"asdf\"t2\"  \"t3\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .containsExactly(InputValidationError.TEXT_OUTSIDE_TAGS)
        );
    }


    @Test
    void handleInput_endTagMissing_shouldShowAnEndTagMissingError() {
        answerValidator.handleInput("\"t1\" \"t2\" \"t3");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .containsExactly(InputValidationError.ANSWER_END_TAG_MISSING)
        );
    }


    @Test
    void handleInput_middleTagMissing_shouldShowTextOutsideTagsError() {
        answerValidator.handleInput("\"t1\" \"t2   \"t3\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .containsExactly(InputValidationError.TEXT_OUTSIDE_TAGS)
        );
    }


    @Test
    void handleInput_inputLongernThan255Chars_shouldShowTextOutsideTagsError() {
        answerValidator.handleInput("\"t1\"   \"t2\" \"asdflksjadfljsfdlö    kjsalgökjslgjsalögjslödgjsa    ldfjalsdfjlkjglkjwg02ug9283hgsdhgkjsagflksjdglkjsagldkjsdglöasjgdlöskajgdlösdjglkjsglsjdglsjdglösdjglskdjgslkdjglskdgjlskdjglksjgdlksjglksjdlgkjslgdkjsdolgijsgoijgwejgosjglsjgoisdjgoisjgosidgjsodigjsoidgjsoidgjsoidgjsoidgjsoigjsoidgjsdog\"");
        assertAll(
                () -> assertFalse(answerValidator.isValidAnswerInput()),
                () -> assertThat(answerValidator.getErrors())
                        .containsExactly(InputValidationError.ANSWER_TOO_LARGE)
        );
    }






}

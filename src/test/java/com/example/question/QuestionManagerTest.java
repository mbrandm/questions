package com.example.question;

import com.example.question.validation.InputValidationError;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.example.question.persistence.PersistenceMessage.QUESTION_SAVED_SUCCESSFULLY;
import static com.example.question.persistence.PersistenceMessage.QUESTION_STILL_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionManagerTest {
    private ByteArrayOutputStream outContent;
    private static final PrintStream originalOut = System.out;

    private QuestionManager questionManager;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }


    @BeforeEach
    void init() {
        questionManager = new QuestionManager();
    }

    @Test
    void process_addQuestionAnswerCombination_sucessfullyAnswerShouldBeShown() {
        questionManager.process("What is the question? \"Answer 1\" \"Answer 2\"");
        assertThat(outContent.toString())
                .isEqualTo(QUESTION_SAVED_SUCCESSFULLY + System.lineSeparator());
    }

    @Test
    void process_addQuestionAndAskForSavedQuestion_answerShouldBeShown() {
        questionManager.process("What is the question? \"Answer 1\" \"Answer 2\"");
        questionManager.process("What is the question?");
        assertThat(outContent.toString())
                .isEqualTo(QUESTION_SAVED_SUCCESSFULLY + System.lineSeparator()
                        + "\t• Answer 1" + System.lineSeparator()
                        + "\t• Answer 2" + System.lineSeparator());
    }

    @Test
    void process_askForNotSavedQuestion_answerShouldBe42() {
        questionManager.process("What is the question?");
        assertThat(outContent.toString())
                .isEqualTo(OutputPerformer.BULLET_POINT
                        + OutputPerformer.DEFAULT_ANSWER + System.lineSeparator());
    }


    @Test
    void process_tryToSaveAlreadySavedQuestion_shouldAnswerAnErrror() {
        questionManager.process("What is the question? \"Answer 1\" \"Answer 2\"");
        questionManager.process("What is the question? \"Answer 3\" \"Answer 4\"");
        assertThat(outContent.toString())
                .isEqualTo(QUESTION_SAVED_SUCCESSFULLY + System.lineSeparator()
                        + QUESTION_STILL_AVAILABLE + System.lineSeparator());
    }

    @Test
    void process_textBetweenInput_shouldAnswerAnError() {
        questionManager.process("What is the question? \"sdfasdf\" sdfsfd \"ans2\"");
        assertThat(outContent.toString())
                .isEqualTo(InputValidationError.TEXT_OUTSIDE_TAGS+System.lineSeparator());
    }

}

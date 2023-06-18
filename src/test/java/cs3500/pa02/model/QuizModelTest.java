package cs3500.pa02.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.quizquestions.Question;
import cs3500.pa02.quizquestions.QuestionBank;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a quiz session controller
 */
class QuizModelTest {
  Question questionOne;
  Question questionTwo;
  Question questionThree;
  ArrayList<Question> questions;
  ArrayList<Question> chosenQuestions;
  int numQsToAsk;
  QuestionBank qb;
  QuizModel quizModel;
  String studyGuideRoot = "src/test/resources/studyGuides/";


  /**
   * Initializes questions for a QuestionBank and a QuizModel for testing
   */
  @BeforeEach
  public void setup() {
    questionOne = new Question("Some question1?", "Some answer1");
    questionTwo = new Question("Some question2?", "Some answer2");
    questionThree = new Question("Some question3?", "Some answer3");
    questions = new ArrayList<>(
        Arrays.asList(questionOne, questionTwo, questionThree));
    qb = new QuestionBank(questions);
    numQsToAsk = 3; // TODO: figure out why 2 is not working

    // testing chosenQuestions is set up properly
    quizModel = new QuizModel(qb, numQsToAsk);
    chosenQuestions = new ArrayList<>(
        Arrays.asList(questionTwo, questionOne, questionThree));

    assertEquals(quizModel.getQuestionBank().questions, chosenQuestions);
  }


  /**
   * Tests that the question is changed each time changeQuestion() is called, unless
   * there are no more questions to go through
   */
  @Test
  void testChangeQuestion() {
    assertEquals(quizModel.getCurrQuestion(), questionTwo);
    quizModel.changeQuestion();
    assertEquals(quizModel.getCurrQuestion(), questionOne);
    quizModel.changeQuestion();
    assertEquals(quizModel.getCurrQuestion(), questionThree);
  }

  /**
   * Tests that a question is modified or moved on from when an option is provided
   */
  @Test
  void testChooseOption() {
    // Mark as Easy
    quizModel.chooseOption("1");
    assertEquals(quizModel.getCurrQuestion(), questionOne);

    // See answer
    quizModel.chooseOption("3");
    assertEquals(quizModel.getCurrQuestion(), questionThree);


    // Invalid option
    assertThrows(IllegalArgumentException.class, () ->
        quizModel.chooseOption("0"), "Invalid option selected. Options range from 1-4.");
  }

  /**
   * More tests for chooseOption method
   */
  @Test
  void testMoreChooseOption() {
    // Mark as Hard
    quizModel.chooseOption("2");
    assertEquals(quizModel.getCurrQuestion(), questionOne);

    // Move to next question
    quizModel.chooseOption("4");
    assertEquals(quizModel.getCurrQuestion(), questionThree);
  }

  /**
   * Tests that the quiz is ended successfully
   *
   * @throws IOException if the destination file cannot be found/accessed
   */
  @Test
  void testEndQuiz() throws IOException {
    // run mock quiz (all hard questions)
    quizModel.chooseOption("2");
    quizModel.chooseOption("2");
    quizModel.chooseOption("2");
    String destFile = studyGuideRoot + "anotherSample";
    quizModel.endQuiz(destFile);

    Scanner sc = new Scanner(Path.of(destFile + ".sr"));
    StringBuilder sb = new StringBuilder();
    while (sc.hasNextLine()) {
      sb.append(sc.nextLine() + "\n");
    }
    assertEquals(sb.toString(), """
        Hard  ||  Some question2?  ||  Some answer2
        Hard  ||  Some question1?  ||  Some answer1
        Hard  ||  Some question3?  ||  Some answer3
        """);
  }

  /**
   * Tests that the correct current question is returned
   */
  @Test
  void testGetCurrQuestion() {
    assertEquals(quizModel.getCurrQuestion(), questionTwo);
  }

  /**
   * Tests that the correct QuestionBank is returned
   */
  @Test
  void testGetQuestionBank() {
    assertEquals(quizModel.getQuestionBank(), qb);
  }
}
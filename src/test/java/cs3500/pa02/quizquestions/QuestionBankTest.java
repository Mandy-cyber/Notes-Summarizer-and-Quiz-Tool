package cs3500.pa02.quizquestions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for creating and modifying a QuestionBank
 */
class QuestionBankTest {
  Question questionOne;
  Question questionTwo;
  Question questionThree;
  Question questionFour;
  ArrayList<Question> questions;
  QuestionBank qb;


  /**
   * Initializes questions and a QuestionBank for testing
   */
  @BeforeEach
  public void setup() {
    questionOne = new Question("Some question1?", "Some answer1");
    questionTwo = new Question("Some question2?", "Some answer2");
    questionThree = new Question("Some question3?", "Some answer3");
    questionFour = new Question("Some question4?", "Some answer4");
    questions = new ArrayList<>(
        Arrays.asList(questionOne, questionTwo, questionThree, questionFour));
    qb = new QuestionBank(questions);
  }

  /**
   * Tests that the QuestionBank is reset to include a new set of questions
   */
  @Test
  void testResetBank() {
    questions = new ArrayList<>(
        Arrays.asList(questionThree, questionFour));
    qb.resetBank(questions);
    assertEquals(qb.questions.size(), 2);
    assertEquals(qb.getStats(), """
        # of Easy Questions: 0
        # of Hard Questions: 2
        # of Questions in Total: 2
        # of Questions switched from Easy to Hard: 0
        # of Questions switched from Hard to Easy: 0
        """);
  }


  /**
   * Tests that the statistics are updated when a question's difficulty is changed
   */
  @Test
  void testUpdateStats() {
    // Easy -> Hard
    questionOne.changeDifficulty(DiffLevel.Easy);
    Question newQuestion = new Question("Some question1?", "Some answer1");
    qb.updateStats(questionOne, newQuestion);
    assertEquals(qb.getStats(), """
        # of Easy Questions: 0
        # of Hard Questions: 4
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 1
        # of Questions switched from Hard to Easy: 0
        """);

    // Hard -> Easy
    Question newQuestion2 = new Question(
        "Some question2?", "Some answer2", DiffLevel.Easy);
    qb.updateStats(questionTwo, newQuestion2);
    assertEquals(qb.getStats(), """
        # of Easy Questions: 1
        # of Hard Questions: 3
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 1
        # of Questions switched from Hard to Easy: 1
        """);

    // Easy -> Hard
    Question newQuestion3 = new Question("Some question2?", "Some answer2");
    qb.updateStats(newQuestion2, newQuestion3);
    assertEquals(qb.getStats(), """
        # of Easy Questions: 0
        # of Hard Questions: 4
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 2
        # of Questions switched from Hard to Easy: 1
        """);
    assertEquals(qb.numEasy, 0);
    assertEquals(qb.numHard, 4);

    // Hard -> Hard
    Question newQuestion4 = new Question("Some question2?", "Some answer2");
    qb.updateStats(newQuestion3, newQuestion4);
    assertEquals(qb.getStats(), """
        # of Easy Questions: 0
        # of Hard Questions: 4
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 2
        # of Questions switched from Hard to Easy: 1
        """);
  }

  /**
   * Tests numHard branches of updateStats are being reached/used properly
   */
  @Test
  void testNumHard() {
    // make all questions easy
    questions = new ArrayList<>(
        Arrays.asList(questionOne, questionTwo, questionThree, questionFour));
    for (Question question : questions) {
      question.changeDifficulty(DiffLevel.Easy);
    }
    qb = new QuestionBank(questions);
    assertEquals(qb.getStats(), """
        # of Easy Questions: 4
        # of Hard Questions: 0
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 0
        # of Questions switched from Hard to Easy: 0
        """);

    // Easy -> Hard
    assertEquals(qb.numHard, 0);
    Question newQuestionOne = new Question("Some question1?", "Some answer1");
    qb.updateStats(questionOne, newQuestionOne);
    assertEquals(qb.numHard, 1);
  }

  /**
   * Tests numEasy branches of updateStats are being reached/used properly
   */
  @Test
  void testNumEasy() {
    assertEquals(qb.getStats(), """
        # of Easy Questions: 0
        # of Hard Questions: 4
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 0
        # of Questions switched from Hard to Easy: 0
        """);

    // Hard -> Easy
    assertEquals(qb.numEasy, 0);
    Question newQuestionOne = new Question(
        "Some question1?", "Some answer1", DiffLevel.Easy);
    qb.updateStats(questionOne, newQuestionOne);
    assertEquals(qb.numEasy, 1);
  }


  /**
   * Tests that the statistics are formatted correctly when requested
   */
  @Test
  void testGetStats() {
    assertEquals(qb.getStats(), """
        # of Easy Questions: 0
        # of Hard Questions: 4
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 0
        # of Questions switched from Hard to Easy: 0
        """);
  }

  /**
   * Tests that the QuestionBank's questions are returned when requested
   */
  @Test
  void testGetQuestions() {
    assertEquals(qb.questions, questions);
  }

  /**
   * Tests that a list of hard and easy questions are randomly selected
   */
  @Test
  void testChooseQuestions() {
    // Number of questions chosen is equal to the size of the questions list
    ArrayList<Question> chosenQuestions = qb.chooseQuestions(4);
    for (Question originalQuestion : qb.questions) {
      assertTrue(chosenQuestions.contains(originalQuestion));
    }

    // Number of questions chosen is more than the size of the questions list
    chosenQuestions = qb.chooseQuestions(6);
    assertEquals(chosenQuestions.size(), 4);

    // Number of questions chosen is less than the size of the questions list
    questionOne.changeDifficulty(DiffLevel.Easy);
    questionThree.changeDifficulty(DiffLevel.Easy);
    chosenQuestions = qb.chooseQuestions(2);
    assertEquals(chosenQuestions.size(), 2);
    for (Question chosenQuestion : chosenQuestions) {
      assertEquals(chosenQuestion.difficulty, DiffLevel.Hard);
    }

    chosenQuestions = qb.chooseQuestions(3);
    assertEquals(chosenQuestions.size(), 3);
    int numHard = 0;
    int numEasy = 0;
    for (Question chosenQuestion : chosenQuestions) {
      if (chosenQuestion.difficulty.equals(DiffLevel.Hard)) {
        numHard += 1;
      } else {
        numEasy += 1;
      }
    }

    assertEquals(numHard, 2);
    assertEquals(numEasy, 1);

    // Invalid option
    assertThrows(IllegalArgumentException.class, () ->
        qb.chooseQuestions(0), "Cannot choose zero"
        + " or negative number of questions.");

    assertThrows(IllegalArgumentException.class, () ->
        qb.chooseQuestions(-3), "Cannot choose zero"
        + " or negative number of questions.");
  }
}
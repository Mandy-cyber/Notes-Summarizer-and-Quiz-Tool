package cs3500.pa02.quizquestions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for creating and modifying a question
 */
class QuestionTest {
  Question question1;
  Question question2;

  /**
   * Initializes two questions for testing
   */
  @BeforeEach
  public void setup() {
    question1 = new Question("What color is an apple?", "Red");
    question2 = new Question("What's the world's population?", "Too big");
  }


  /**
   * Tests that the correct text is generated for a question and implicitly tests that
   * a question's difficulty is changed when requested.
   */
  @Test
  void testToString() {
    assertEquals(question1.toString(), "Hard  ||  What color is an apple?  ||  Red");
    question1.changeDifficulty(DiffLevel.Hard);
    assertEquals(question1.toString(), "Hard  ||  What color is an apple?  ||  Red");
    question1.changeDifficulty(DiffLevel.Easy);
    assertEquals(question1.toString(), "Easy  ||  What color is an apple?  ||  Red");
  }


  /**
   * Tests that a question (without its answer) is returned in String format
   */
  @Test
  void testQuestionString() {
    assertEquals(question1.questionString(), """
        Hard  ||  What color is an apple?
        Answer:  __________""");

    question2.changeDifficulty(DiffLevel.Easy);
    assertEquals(question2.questionString(), """
        Easy  ||  What's the world's population?
        Answer:  __________""");
  }


  /**
   * Tests that a question is correctly identified as the same as another question
   */
  @Test
  void sameQuestion() {
    Question question0 = new Question("Question?", "Different Answer");
    Question question1 = new Question("Question?", "Answer");
    assertFalse(question0.sameQuestion(question1));
    assertFalse(question1.sameQuestion(question0)); // make sure it works both ways
    Question question2 = new Question("Question?", "Answer");
    assertTrue(question1.sameQuestion(question2));
    assertTrue(question2.sameQuestion(question2));
    Question question3 = new Question("Not Question?", "Not Answer");
    assertFalse(question1.sameQuestion(question3));
    Question question4 = new Question("Not Question?", "Not Answer", DiffLevel.Easy);
    assertTrue(question3.sameQuestion(question4)); // because difficulty doesn't matter
    Question question5 = new Question("Different Question?", "Answer");
    assertFalse(question5.sameQuestion(question1));
  }
}
package cs3500.pa02.viewers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.quizquestions.DiffLevel;
import cs3500.pa02.quizquestions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents a tool for displaying information to the user via the console
 */
class QuizViewerTest {
  Question questionOne;
  Question questionTwo;
  QuizViewer view;

  /**
   * Initializes questions and a QuizViewer for testing
   */
  @BeforeEach
  public void setup() {
    questionOne = new Question("What is a question1?", "Answer1");
    questionTwo = new Question("What is a question2?", "Answer2");
    questionTwo.changeDifficulty(DiffLevel.Easy);
    view = new QuizViewer();
  }

  /**
   * Tests that the correct output is printed to the console when viewing a question
   */
  @Test
  void viewQuestion() {
    String qstnOneString = view.viewQuestion(questionOne);
    assertEquals(qstnOneString, """
        --------------------------------------------------
        Hard  ||  What is a question1?
        Answer:  __________
        
        1. Easy Question
        2. Hard Question
        3. See Answer
        4. Next Question ->
        """);

    String qstnTwoString = view.viewQuestion(questionTwo);
    assertEquals(qstnTwoString, """
        --------------------------------------------------
        Easy  ||  What is a question2?
        Answer:  __________
        
        1. Easy Question
        2. Hard Question
        3. See Answer
        4. Next Question ->
        """);
  }


  /**
   * Tests that the correct output is printed to the console when viewing a question's answer
   */
  @Test
  void testViewAnswer() {
    assertEquals(view.viewAnswer(questionOne), "Answer:  Answer1  ");
    assertEquals(view.viewAnswer(questionTwo), "Answer:  Answer2  ");
  }

  /**
   * Tests that the correct output is printed to the console when starting the quiz
   */
  @Test
  void viewStart() {
    String startScreenStr = """
        IT'S STUDY TIME PARTY PEOPLE!
        ------------------------------
        Write the name of the file that hosts your study questions, followed by
        the number of questions you would like to study.
        
        e.g. sample.sr  10
        
        ___________
        """;

    assertEquals(view.viewStart(), startScreenStr);
  }


  /**
   * Tests that the correct output is printed to the console when ending a quiz
   */
  @Test
  void viewEnd() {
    String exampleStats = """
        # of Easy Questions: 0
        # of Hard Questions: 4
        # of Questions in Total: 4
        # of Questions switched from Easy to Hard: 0
        # of Questions switched from Hard to Easy: 0
        """;

    String endingScreenStr = """
        YOU HAVE SUCCESSFULLY COMPLETED THE QUIZ
        -------------------------------------------
        Here are the stats behind this quiz:
        
        """ + exampleStats;

    assertEquals(view.viewEnd(exampleStats), endingScreenStr);
  }
}
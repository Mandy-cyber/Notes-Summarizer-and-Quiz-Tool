package cs3500.pa02.filehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa02.quizquestions.DiffLevel;
import cs3500.pa02.quizquestions.Question;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents a tool for testing a FileReader
 */
class FileReaderTest {
  String file1;
  FileReader reader1;
  String studyGuideRoot = "src/test/resources/studyGuides/";


  /**
   * Instantiates a FileReader for testing
   *
   * @throws IOException if either of the files cannot be found/accessed
   */
  @BeforeEach
  public void setup() throws IOException {
    file1 = studyGuideRoot + "sample.sr";
    reader1 = new FileReader(file1);
  }

  /**
   * Tests for ensuring a FileReader returns the correct list of questions from a file
   *
   * @throws IOException if the file cannot be found/accessed
   */
  @Test
  void readToQuestions() throws IOException {
    ArrayList<Question> expectedList = new ArrayList<>(Arrays.asList(
        new Question("This is some question 1?", "This is some answer 1"),
        new Question("This is some question 2?", "This is some answer 2", DiffLevel.Easy),
        new Question("This is some question 3?", "This is some answer 3"),
        new Question("This is some question 4?", "This is some answer 4")
    ));

    ArrayList<Question> readQuestions = reader1.readToQuestions();
    assertEquals(readQuestions.size(), expectedList.size());
    assertTrue(readQuestions.get(0).sameQuestion(expectedList.get(0)));
    assertTrue(readQuestions.get(1).sameQuestion(expectedList.get(1)));
    assertTrue(readQuestions.get(2).sameQuestion(expectedList.get(2)));
    assertTrue(readQuestions.get(3).sameQuestion(expectedList.get(3)));
  }
}
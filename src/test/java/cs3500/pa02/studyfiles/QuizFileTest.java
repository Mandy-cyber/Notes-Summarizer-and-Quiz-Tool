package cs3500.pa02.studyfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for creating a file of quiz questions
 */
class QuizFileTest {
  File f1;
  File f2;
  File f3;
  QuizFile studyFile;
  ArrayList<File> files;
  String questionsRoot = "src/test/resources/questionFiles/";
  String guideRoot = "src/test/resources/studyGuides/";

  /**
   * Initializes files and a QuizFile for testing
   */
  @BeforeEach
  public void setup() {
    f1 = new File(questionsRoot + "bananas.md");
    f2 = new File(questionsRoot + "apples.md");
    f3 = new File(questionsRoot + "cantaloupe.md");
    files = new ArrayList<>(Arrays.asList(f1, f2, f3));

    String fullDestFile = guideRoot + "quizQuestions";
    studyFile = new QuizFile(files, fullDestFile, ".sr");
  }

  /**
   * Tests that a file of quiz questions (aka a QuizFile) is created and outputted
   * to the destination file correctly
   *
   * @throws IOException if a path/file cannot be found or accessed.
   */
  @Test
  void makeFile() throws IOException {
    studyFile.makeFile();
    Scanner sc = new Scanner(Path.of(guideRoot + "quizQuestions.sr"));
    StringBuilder sb = new StringBuilder();

    while (sc.hasNextLine()) {
      String newString = sc.nextLine() + "\n";
      sb.append(newString);
    }

    assertEquals(sb.toString(), """
        Hard  ||  What is the most popular fruit in the world?  ||  Bananas
        Hard  ||  Name the vitamin that bananas are a good source of  ||  Vitamin C
        Hard  ||  Can bananas cure cancer?  ||  No
        Hard  ||  How should you choose a banana?  ||  by looking for the firm ones
        Hard  ||  What type of plants do apples grow on?  ||  Trees
        Hard  ||  Name one apple variety  ||  Red Delicious, Granny Smith, Honeycrisp, Gala, or Fuji
        Hard  ||  Can apples lower the risk of heart disease?  ||  Yes
        Hard  ||  How much fiber is in a medium-sized apple?  ||  4 grams
        Hard  ||  What can you cook with apples?  ||  nth
        Hard  ||  What is the other name for a cantaloupe?  ||  Muskmelon
        Hard  ||  What do cantaloupes help protect the body against?  ||  Free radicals
        Hard  ||  Can cantaloupes reduce inflammation in your body?  ||  Yes
        Hard  ||  What temperature should a cantaloupe be stored at?  ||  Room temperature
        Hard  ||  Are there a lot of ways to enjoy cantaloupe?  ||  Absolutely yes there are
        """);
  }
}
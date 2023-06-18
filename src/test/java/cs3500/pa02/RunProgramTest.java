package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.controllers.QuizController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for running the program
 */
class RunProgramTest {
  File f1;
  File f2;
  File f3;
  File f4;
  ArrayList<File> unsortedFiles;
  String notesRoot = "src/test/resources/notes-root/";
  String shortRoot = "src/test/resources/shortMarkdowns/";
  String guideRoot = "src/test/resources/studyGuides/";
  String questionRoot = "src/test/resources/questionFiles/";
  String notes = """
        # Apples
        - **Apples** are a popular fruit enjoyed all around the world.
                
        ## Varieties
                
        ## Health Benefits
        - They are a good source of fiber  and vitamin C
                
        ### Fiber
        - One medium-sized apple contains about 4 grams of fiber
                
        ### Vitamin C
                
        ## Cooking with Apples
                
        # Bananas: A Delicious and Nutritious Fruit
                
        ## Nutritional Value of Bananas
                
        ## Health Benefits of Bananas
        - reducing the risk of heart disease
        - lowering blood sugar levels
        - improving digestion
        - reduce inflammation
        - boost energy levels
                
        ## Delicious Ways to Enjoy Bananas
        - make smoothies
        - bake into breads and muffins
        - grill on the barbecue
                
        ## Choosing and Storing Bananas
        - Your body will thank you
                
        # All About Cantaloupe
                
        ## Nutritional Value of Cantaloupe
                
        ## Health Benefits of Cantaloupe
        - boosting immunity
        - promoting healthy skin
        - improving digestion
        - reducing inflammation
        - supporting heart health
                
        ## How to Select and Store Cantaloupe
                
        ## Delicious Ways to Enjoy Cantaloupe
                
        ## Conclusion
        
        """;

  String questions = """
      Hard  ||  What type of plants do apples grow on?  ||  Trees
      Hard  ||  Name one apple variety  ||  Red Delicious, Granny Smith, Honeycrisp, Gala, or Fuji
      Hard  ||  Can apples lower the risk of heart disease?  ||  Yes
      Hard  ||  How much fiber is in a medium-sized apple?  ||  4 grams
      Hard  ||  What can you cook with apples?  ||  nth
      Hard  ||  What is the most popular fruit in the world?  ||  Bananas
      Hard  ||  Name the vitamin that bananas are a good source of  ||  Vitamin C
      Hard  ||  Can bananas cure cancer?  ||  No
      Hard  ||  How should you choose a banana?  ||  by looking for the firm ones
      Hard  ||  What is the other name for a cantaloupe?  ||  Muskmelon
      Hard  ||  What do cantaloupes help protect the body against?  ||  Free radicals
      Hard  ||  Can cantaloupes reduce inflammation in your body?  ||  Yes
      Hard  ||  What temperature should a cantaloupe be stored at?  ||  Room temperature
      Hard  ||  Are there a lot of ways to enjoy cantaloupe?  ||  Absolutely yes there are
      """;

  /**
   * Initializes files for testing and manually sets their last-modified date/times
   *
   * @throws IOException if a file cannot be found or accessed
   */
  @BeforeEach
  public void setup() throws IOException {
    f1 = new File(notesRoot + "folder1/bananas.md");
    f2 = new File(notesRoot + "apples.md");
    f3 = new File(notesRoot + "folder4/durian.md");
    f4 = new File(shortRoot + "testingOne.md");

    // hardcoding last-modified date/times
    long timeEx2 = System.currentTimeMillis();
    f2.setLastModified(timeEx2);
    f3.setLastModified(timeEx2 + 500);
    f1.setLastModified(timeEx2 + 1000);

    unsortedFiles = new ArrayList<>(Arrays.asList(f1, f2, f3));
  }

  /**
   * Tests if the program generates files and/or runs a quiz
   */
  @Test
  void run() throws IOException {
    // RUN WITH INVALID # of ARGS
    //----------------------------
    assertThrows(IllegalArgumentException.class, () ->
        RunProgram.run(new String[] {"a"}), "You may only pass zero or three arguments");

    // RUN WITH NO ARGS
    //-------------------
    assertThrows(NoSuchElementException.class, () ->
            RunProgram.run(new String[]{}),
        "Could not get user input.");

    // RUN WITH THREE ARGS
    //---------------------
    String[] sampleArgs = {questionRoot, "filename", guideRoot + "testingGuide"};
    RunProgram.run(sampleArgs);

    // study guide created properly
    Scanner sc = new Scanner(Path.of(guideRoot + "testingGuide.md"));
    StringBuilder sb = new StringBuilder();
    while (sc.hasNextLine()) {
      sb.append(sc.nextLine() + "\n");
    }
    assertEquals(sb.toString(), notes);

    // question file created properly
    Scanner sc1 = new Scanner(Path.of(guideRoot + "testingGuide.sr"));
    StringBuilder sb1 = new StringBuilder();
    while (sc1.hasNextLine()) {
      sb1.append(sc1.nextLine() + "\n");
    }
    assertEquals(sb1.toString(), questions);
  }

  /**
   * Tests if the program's quiz is run
   */
  @Test
  void runQuiz() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(
        guideRoot + "quizQuestions.sr", "3"));

    assertThrows(NoSuchElementException.class, () ->
            RunProgram.runQuiz(args, new QuizController()),
        "Could not get user input.");
  }

  /**
   * Tests if files are generated properly
   *
   * @throws IOException if a file cannot be found or accessed
   */
  @Test
  void genFiles() throws IOException {
    RunProgram.genFiles(questionRoot, "filename",
        guideRoot + "testingGuide2");

    // study guide created properly
    Scanner sc = new Scanner(Path.of(guideRoot + "testingGuide2.md"));
    StringBuilder sb = new StringBuilder();
    while (sc.hasNextLine()) {
      sb.append(sc.nextLine() + "\n");
    }
    assertEquals(sb.toString(), notes);

    // quiz file created properly
    Scanner sc1 = new Scanner(Path.of(guideRoot + "testingGuide2.sr"));
    StringBuilder sb1 = new StringBuilder();
    while (sc1.hasNextLine()) {
      sb1.append(sc1.nextLine() + "\n");
    }
    assertEquals(sb1.toString(), questions);
  }
}
package cs3500.pa02.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.studyfiles.NotesFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a study guide controller
 */
class StudyGuideModelTest {
  File f1;
  File f2;
  File f3;
  NotesFile studyFile;
  ArrayList<File> unsortedFiles;
  String notesRoot = "src/test/resources/notes-root/";
  String guideRoot = "src/test/resources/studyGuides/";
  StudyGuideModel studyController;


  /**
   * Initializes files and a StudyGuideModel for testing
   */
  @BeforeEach
  public void setup() {
    f1 = new File(notesRoot + "folder1/bananas.md");
    f2 = new File(notesRoot + "apples.md");
    f3 = new File(notesRoot + "folder4/durian.md");

    // hardcoding last-modified date/times
    long timeEx2 = System.currentTimeMillis();
    f2.setLastModified(timeEx2);
    f3.setLastModified(timeEx2 + 500);
    f1.setLastModified(timeEx2 + 1000);

    unsortedFiles = new ArrayList<>(Arrays.asList(f1, f2, f3));
    String fullDestFile = guideRoot + "studyGuide6";
    studyFile = new NotesFile(unsortedFiles, fullDestFile, ".md");

    studyController = new StudyGuideModel(studyFile, "filename");
  }

  /**
   * Tests that a study guide is created successfully
   *
   * @throws IOException if a path/file cannot be found or accessed when attempting to make
   *         the guide.
   */
  @Test
  void makeStudyGuide() throws IOException {
    studyController.makeStudyGuide();
    Scanner sc = new Scanner(Path.of(guideRoot + "studyGuide6.md"));
    StringBuilder sb = new StringBuilder();

    while (sc.hasNextLine()) {
      String newString = sc.nextLine() + "\n";
      sb.append(newString);
    }
    assertEquals(sb.toString(), """
        # Apples
        - **Apples** are a popular fruit enjoyed all around the world.
                
        ## Varieties
                
        ## Health Benefits
        - They are a good source of fiber and vitamin C
                
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
                
        # Durian: The King of Fruits
        - a tropical fruit that is famous for its unique flavor and pungent smell
                
        ## Appearance
        - spiky exterior that resembles a porcupine
                
        ## Taste
        - Love it or hate it
                
        ## Nutrition
        - It is particularly high in vitamin C, vitamin B6, potassium, and copper
        - A great addition to a healthy diet
                
        ## Culinary Uses
        - often used in a variety of sweet and savory dishes
        - Have you ever tried durian pizza?
                
        ## Health Benefits
        - It's been shown to have potential anti-cancer properties!
                
        """);
  }
}
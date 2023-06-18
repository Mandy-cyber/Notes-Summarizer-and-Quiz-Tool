package cs3500.pa02.filehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for writing text to a file
 */
class FileWriterTest {
  File f1;
  File f2;
  FileWriter fw;
  FileWriter fw2;
  String guideRoot = "src/test/resources/studyGuides/";

  /**
   * Initializes a FileWriter and the files it will write to
   */
  @BeforeEach
  public void setup() {
    f1 = new File(guideRoot + "studyGuide2");
    f2 = new File("src/test/resources/studyGuide/studyGuide2");
    fw = new FileWriter(f1, ".md");
    fw2 = new FileWriter(f2, ".md");
  }

  /**
   * Tests for writing strings to a file
   *
   * @throws RuntimeException if the file cannot be found or accessed
   */
  @Test
  void writeToFile() throws IOException {
    fw.writeToFile("Hello World");
    Scanner sc = new Scanner(Path.of(guideRoot + "studyGuide2.md"));
    StringBuilder sb = new StringBuilder();

    while (sc.hasNextLine()) {
      sb.append(sc.nextLine());
    }
    assertEquals(sb.toString(), "Hello World");
    assertThrows(RuntimeException.class, () ->
        fw2.writeToFile("Hello World"));
  }

}
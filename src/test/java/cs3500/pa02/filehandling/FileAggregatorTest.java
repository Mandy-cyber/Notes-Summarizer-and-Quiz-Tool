package cs3500.pa02.filehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for collecting all markdown files from a directory
 */
class FileAggregatorTest {
  FileAggregator fa;
  String notesRoot = "src/test/resources/notes-root/";

  /**
   * Initializes a FileAggregator and walks the file system
   *
   * @throws IOException if path cannot be found when walking the file system
   */
  @BeforeEach
  public void setup() throws IOException {
    fa = new FileAggregator(notesRoot);
    Files.walkFileTree(fa.path, fa);
  }


  /**
   * Tests creating a list of all markdown files in a directory
   */
  @Test
  void getValidFiles() {
    // try contains
    ArrayList<File> allMds = new ArrayList<>(Arrays.asList(
        new File(notesRoot + "apples.md"),
        new File(notesRoot + "folder1/bananas.md"),
        new File(notesRoot + "folder1/folder2/Bananas.md"),
        new File(notesRoot + "folder1/folder2/folder5/cantaloupe.md"),
        new File(notesRoot + "folder4/durian.md"),
        new File(notesRoot + "folder4/eggplant.md")));

    for (File file : allMds) {
      assertTrue(fa.getValidFiles().contains(file));
    }
  }


  /**
   * Tests visiting a file in a directory and adding it to a list only if it is
   * a markdown file
   *
   * @throws IOException if path cannot be found when walking the file system
   */
  @Test
  void visitFile() throws IOException {
    FileAggregator fa2 = new FileAggregator(notesRoot);
    ArrayList<File> validFiles = fa2.getValidFiles();
    assertEquals(validFiles.size(), 0);
    Files.walkFileTree(fa2.path, fa2);
    assertEquals(validFiles.size(), 6);

    FileAggregator fa3 = new FileAggregator("src/test/resources/note-root/");
    assertEquals(fa3.visitFileFailed(
        fa3.path, new IOException("File could not be found")),
        FileVisitResult.CONTINUE);
  }
}
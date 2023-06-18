package cs3500.pa02.comparators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for comparing files by their last modified date-time
 */
class MoComparatorTest {
  File f1;
  File f2;
  MoComparator moc;
  String notesRoot = "src/test/resources/notes-root/";

  /**
   * Initializes files for testing, manually setting their last-modified date/times.
   *
   * @throws IOException if a file cannot be found or accessed
   */
  @BeforeEach
  public void setup() throws IOException {
    f1 = new File(notesRoot + "folder1/folder2/folder5/cantaloupe.md");
    f2 = new File(notesRoot + "apples.md");
    moc = new MoComparator();

    long timeEx = System.currentTimeMillis();
    f1.setLastModified(timeEx);
    f2.setLastModified(timeEx + 500);
  }

  /**
   * Tests comparing files by their last modified date-time
   */
  @Test
  void compare() {
    assertTrue(moc.compare(f1, f2) < 0);
    assertFalse(moc.compare(f2, f1) < 0);
    assertThrows(RuntimeException.class, () ->
        moc.compare(f1, new File("/notes-root")));
  }
}
package cs3500.pa02.comparators;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for comparing files by their names
 */
class FnComparatorTest {
  File f1;
  File f2;
  File f3;
  FnComparator fnc;
  String notesRoot = "src/test/resources/notes-root/";

  /**
   * Initializes files for testing
   */
  @BeforeEach
  public void setup() {
    f1 = new File(notesRoot + "folder1/bananas.md");
    f2 = new File(notesRoot + "folder1/folder2/Bananas.md");
    f3 = new File(notesRoot + "apples.md");
    fnc = new FnComparator();
  }

  /**
   * Tests comparing files by their names (alphabetically)
   */
  @Test
  void compare() {
    // remember - uppercase letters always come before lowercase
    assertNotEquals(fnc.compare(f1, f2), 0);
    assertTrue(fnc.compare(f1, f2) > 0);
    assertTrue(fnc.compare(f2, f1) < 0);
    assertTrue(fnc.compare(f3, f1) < 0);
    assertTrue(fnc.compare(f3, f2) > 0);
  }
}
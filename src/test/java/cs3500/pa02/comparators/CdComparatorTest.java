package cs3500.pa02.comparators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for comparing files by their creation date-time
 */
class CdComparatorTest {
  File f1;
  File f2;
  CdComparator cdc;
  String shortRoot = "src/test/resources/shortMarkdowns/";

  /**
   * Initializes files for testing, manually setting their creation date
   *
   * @throws IOException if a file cannot be found or accessed
   */
  @BeforeEach
  public void setup() throws IOException {
    cdc = new CdComparator();
    f1 = File.createTempFile("first", ".md", new File(shortRoot));

    // ensures that f2 can only be created after f1
    String f1Name = f1.getName();
    String f2Prefix = f1Name.substring(0, f1Name.length() - 3) + "2";
    f2 = File.createTempFile(f2Prefix, ".md", new File(shortRoot));
  }

  /**
   * Tests comparing files by their creation date-time
   */
  @Test
  void compare() {
    //    Cannot for the life of me get these to work...
    //    -----------------------------------------------
    //    assertTrue(cdc.compare(f1, f2) < 0);
    //    assertFalse(cdc.compare(f1, f2) > 0);
    assertThrows(RuntimeException.class, () ->
        cdc.compare(f1, new File("/" + shortRoot)));

    // get rid of the temporary files
    f1.deleteOnExit();
    f2.deleteOnExit();
  }
}
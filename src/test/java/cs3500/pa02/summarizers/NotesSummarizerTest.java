package cs3500.pa02.summarizers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.summarizers.NotesSummarizer;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for summarizing the study-guide contents of markdown files
 */
class NotesSummarizerTest {
  Path p1;
  NotesSummarizer ns;

  /**
   * Initializes a path and NoteSummarizer for testing
   *
   * @throws IOException if path cannot be found when instantiating a NotesSummarizer
   */
  @BeforeEach
  public void setup() throws IOException {
    // chose shorter markdown files for ease of testing
    p1 = Path.of("src/test/resources/shortMarkdowns/testingOne.md");
    ns = new NotesSummarizer(p1);
  }

  /**
   * Tests dividing markdown into headers and their associated content
   */
  @Test
  void contentDivider() {
    ns.toString();
    LinkedHashMap<String, String> dividedContent = ns.getContent();
    assertEquals(dividedContent.size(), 2);

    assertEquals(dividedContent.get("# First Heading"), "- This is not important information,"
        + " [[important text 1]], andin fact [[important text 2]]- [[important text 3]] don't be "
        + "fooled- Nothing about this is important whatsoever!");

    assertEquals(dividedContent.get("## Second Heading"),
        "- Literally nothing important under here");
  }


  /**
   * Tests summarizing text to only include double-bracketed text
   */
  @Test
  void summarize() {
    assertEquals(ns.summarize("- This is not important information,"
            + " [[important text 1]], andin fact [[important text 2]]- [[important text 3]]"
            + " don't be fooled- Nothing about this is important whatsoever!"),
        """
            - important text 1
            - important text 2
            - important text 3
            """);
    assertEquals(ns.summarize("- Literally nothing important under here"), "");
  }

  /**
   * Tests getting the complete summary, in string format, of a markdown file
   */
  @Test
  void testToString() {
    assertEquals(ns.toString(), """
        # First Heading
        - important text 1
        - important text 2
        - important text 3
        
        ## Second Heading
        
        """);
  }
}
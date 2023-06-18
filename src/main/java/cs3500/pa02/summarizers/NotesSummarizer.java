package cs3500.pa02.summarizers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Represents a tool for summarizing mark-down text, that are study notes, from a file
 */
public class NotesSummarizer extends Summarizer {
  private final LinkedHashMap<String, String> dividedContent;
  private boolean hasBeenDivided;
  private final Scanner sc;

  /**
   * Instantiates a scanner for this tool to read the given file
   *
   * @param p the path to the file being summarized
   * @throws IOException if file cannot be found/utilized
   */
  public NotesSummarizer(Path p) throws IOException {
    super(p);
    dividedContent = new LinkedHashMap<>();
    hasBeenDivided = false;
    sc = new Scanner(p);
  }

  /**
   * Divides the contents of a file into headers and their associated bodies/content
   */
  @Override
  public void contentDivider() {
    hasBeenDivided = true;
    ArrayList<String> headers = new ArrayList<>();
    StringBuilder sb = new StringBuilder();

    // differentiate between headers and regular text
    while (sc.hasNextLine()) {
      String txt = sc.nextLine();
      if (txt.length() > 0 && txt.substring(0, 1).equals(("#"))) {
        headers.add(txt);
        sb.append(System.lineSeparator()); // delimiter for later separation
      } else {
        sb.append(txt);
      }
    }

    // associate headers with relevant sections
    ArrayList<String> sections = new ArrayList<>(
        Arrays.asList(sb.toString().split(System.lineSeparator())));
    sections.remove(0); // first header always produces an empty-string to be removed

    // add header/content pairs to dictionary
    for (int i = 0; i < headers.size(); i++) {
      dividedContent.put(headers.get(i), sections.get(i));
    }
  }

  /**
   * Rewrites the given text to only include text found within double-brackets that are not
   * question and answers (aka question blocks)
   *
   * @param content the text to be summarized
   * @return the summarized text as bullet-points, or the empty string if nothing to summarize
   */
  @Override
  public String summarize(String content) {
    contentDivider();
    StringBuilder sb = new StringBuilder();
    Scanner sc2 = new Scanner(content);

    for (String s; (s = sc2.findWithinHorizon("(?<=\\[\\[).*?(?=\\]\\])", 0))
        != null;) {
      // ensure the double-bracketed text does not have ":::" in it
      if (!s.contains(":::")) {
        // clean the text and add bullet points
        s = s.replaceAll("\\[", "");
        s = s.replaceAll("\\]", "");
        String newString = "- " + s + "\n";
        sb.append(newString);
      }
    }
    return sb.toString();
  }

  /**
   * Creates a final summary of the file being summarized (i.e. format headings and bullet points)
   *
   * @return the final summary of the file being summarized
   */
  @Override
  public String toString() {
    if (!hasBeenDivided) {
      contentDivider();
    }
    StringBuilder sb = new StringBuilder();
    dividedContent.forEach((key, val) ->
        sb.append(key + "\n" + summarize(val) + "\n"));
    return sb.toString();
  }

  /**
   * Return the dividedContent if it has already been divided. If not, first divide the content,
   * then return it.
   *
   * @return the divided content
   */
  public LinkedHashMap<String, String> getContent() {
    if (!hasBeenDivided) {
      contentDivider();
    }
    return dividedContent;
  }

}

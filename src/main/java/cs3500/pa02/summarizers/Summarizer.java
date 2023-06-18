package cs3500.pa02.summarizers;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Represents a tool for summarizing mark-down text from a file
 */
public abstract class Summarizer {
  private final Path path;

  /**
   * Sets the path for the file to be summarized
   *
   * @param p the path to the file being summarized
   */
  public Summarizer(Path p) {
    path = p;
  }

  /**
   * Divides the contents of a file into headers and their associated bodies/content
   */
  public abstract void contentDivider();

  /**
   * Rewrites the given text to only include text found within double-brackets
   *
   * @param content the text to be summarized
   * @return the summarized text as bullet-points, or the empty string if nothing to summarize
   */
  public abstract String summarize(String content);


  /**
   * Creates a final summary of the file being summarized (i.e. format headings and bullet points)
   *
   * @return the final summary of the file being summarized
   */
  public abstract String toString();

}

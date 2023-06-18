package cs3500.pa02.summarizers;

import cs3500.pa02.quizquestions.Question;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a tool for summarizing mark-down text, that are quiz questions, from a file
 */
public class QuizSummarizer extends Summarizer {
  private String dividedContent;
  private ArrayList<Question> questions;
  private boolean hasBeenDivided;
  private final Scanner sc;


  /**
   * Instantiates a scanner for this tool to read the given file
   *
   * @param p  the path to the file being summarized
   * @throws IOException if file cannot be found/utilized
   */
  public QuizSummarizer(Path p) throws IOException {
    super(p);
    dividedContent = "";
    questions = new ArrayList<>();
    hasBeenDivided = false;
    sc = new Scanner(p);
  }

  /**
   * Divides the contents of the file to only include non-header text
   */
  @Override
  public void contentDivider() {
    hasBeenDivided = true;
    StringBuilder sb = new StringBuilder();

    // differentiate between headers and regular text
    while (sc.hasNextLine()) {
      String txt = sc.nextLine();
      if (!(txt.length() > 0 && txt.substring(0, 1).equals(("#")))) {
        sb.append(txt);
      }
    }

    dividedContent = sb.toString();
  }

  /**
   * Rewrites the given text to only include text found within double-brackets that are
   * question and answers (aka question blocks). Also converts the question blocks into Questions.
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
      // ensure the double-bracketed text does have ":::" in it
      if (s.contains(":::")) {
        sb.append(s + "\n");
        // dissect string into Q&A components
        String[] questionComponents = s.split(":::");
        String question = questionComponents[0];
        String answer = questionComponents[1];

        // turn into question and add to list
        Question q = new Question(question, answer);
        questions.add(q);
      }
    }
    return sb.toString();
  }

  /**
   * Creates a final summary of the file being summarized
   *
   * @return the final summary of the file being summarized
   */
  @Override
  public String toString() {
    // create the summary so the list of questions is generated
    summarize(getContent());

    // turn each Question into its string format
    StringBuilder sb = new StringBuilder();
    for (Question question : questions) {
      sb.append(question + "\n");
    }
    return sb.toString();
  }


  /**
   * Return the dividedContent if it has already been divided. If not, first divide the content,
   * then return it.
   *
   * @return the divided content (the non-header text)
   */
  public String getContent() {
    if (!hasBeenDivided) {
      contentDivider();
    }
    return dividedContent;
  }

  /**
   * Return the list of questions
   *
   * @return a list of Question
   */
  public ArrayList<Question> getQuestions() {
    summarize(getContent());
    return questions;
  }
}

package cs3500.pa02;

import java.io.IOException;

/**
 * Represents the main driver of the project
 */
public class Driver {

  // src/test/resources/questionFiles/ filename src/test/resources/studyGuides/studyGuide

  /**
   * Runs the program
   *
   * @param args - path to the directory of notes to summarize,
   *             - an ordering flag (filename, creation, or modified),
   *             - and an output path for where the study guide should be created
   * @throws IOException if the destination file cannot be found/accessed
   */
  public static void main(String[] args) throws IOException {
    RunProgram.run(args);
  }
}















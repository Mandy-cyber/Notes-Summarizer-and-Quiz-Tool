package cs3500.pa02.filehandling;

import cs3500.pa02.quizquestions.Question;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a tool for reading files
 */
public class FileReader {
  private final Path filename;

  /**
   * Initializes a FileReader to read from the given filename
   *
   * @param file the name of the file to be read from
   */
  public FileReader(String file) {
    filename = Path.of(file);
  }

  /**
   * Reads the file and converts each line into a question
   *
   * @return the list of questions from the file
   * @throws IOException if the file cannot be found or accessed
   */
  public ArrayList<Question> readToQuestions() throws IOException {
    Scanner sc = new Scanner(filename);
    ArrayList<Question> questions = new ArrayList<>();

    while (sc.hasNextLine()) {
      questions.add(new Question(sc.nextLine()));
    }

    return questions;
  }
}

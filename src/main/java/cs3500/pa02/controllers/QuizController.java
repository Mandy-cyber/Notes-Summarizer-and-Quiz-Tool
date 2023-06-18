package cs3500.pa02.controllers;

import cs3500.pa02.viewers.QuizViewer;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Represents a tool for gathering user inputs
 */
public class QuizController {

  /**
   * Gathers starting input from the user (.sr file location and number of
   * questions to ask)
   *
   * @return a list where the first element is the .sr file location, and the second
   *         is the number of questions to ask
   */
  public ArrayList<String> startingQuizInput() {
    new QuizViewer().viewStart();
    String userInput = getInput();

    // separate string
    ArrayList<String> info = new ArrayList<>();
    info.addAll(Arrays.asList(userInput.split(" ")));
    return info;
  }

  /**
   * Reads other user input from the terminal
   *
   * @return the user's input
   */
  public String getInput() {
    // convert console input to string
    Readable input = new InputStreamReader(System.in);
    Scanner scanner = new Scanner(input);
    return scanner.nextLine();
  }


}

package cs3500.pa02.viewers;

import cs3500.pa02.quizquestions.Question;

/**
 * Represents a tool for visualizing aspects of a spaced-repetition quiz study session
 */
public class QuizViewer {

  /**
   * Prints to the console the given question (without its answer) in its String format
   * alongside the options for what a user can choose regarding the question.
   *
   * @param question the question to be viewed
   * @return the question and the options available
   */
  public String viewQuestion(Question question) {
    String questionPlusOptions = "--------------------------------------------------\n"
        + question.questionString() + "\n" + viewOptions();
    System.out.println(questionPlusOptions);
    return questionPlusOptions;
  }

  /**
   * Prints to the console the options for what a user can choose regarding any question.
   *
   * @return the options available to the user
   */
  private String viewOptions() {
    String options = """
        
        1. Easy Question
        2. Hard Question
        3. See Answer
        4. Next Question ->
        """;
    System.out.println(options);
    return options;
  }


  /**
   * Prints to the console the answer to the given question
   *
   * @param question the question of whose answer is being viewed
   * @return the answer to the given question
   */
  public String viewAnswer(Question question) {
    String answer = "Answer:  " + question.answer + "  ";
    System.out.println(answer);
    return answer;
  }


  /**
   * Prints to the console the starting screen and prompts the user to enter how many
   * questions they would like to study and where the study questions should come from.
   *
   * @return the starting screen and its prompts
   */
  public String viewStart() {
    String startScreenString = """
        IT'S STUDY TIME PARTY PEOPLE!
        ------------------------------
        Write the name of the file that hosts your study questions, followed by
        the number of questions you would like to study.
        
        e.g. sample.sr  10
        
        ___________
        """;
    System.out.println(startScreenString);
    return startScreenString;
  }


  /**
   * Prints to the console the ending screen and the given statistics from their study
   * session.
   *
   * @param stats the statistics to be displayed
   * @return the ending screen and statistics
   */
  public String viewEnd(String stats) {
    String endingScreen = """
        YOU HAVE SUCCESSFULLY COMPLETED THE QUIZ
        -------------------------------------------
        Here are the stats behind this quiz:
        
        """ + stats;
    System.out.println(endingScreen);
    return endingScreen;
  }


}

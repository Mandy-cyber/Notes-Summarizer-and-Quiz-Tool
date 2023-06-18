package cs3500.pa02.quizquestions;

/**
 * Represents a question to be asked in a Quiz (i.e during spaced-repetition studying)
 */
public class Question {
  /**
   * the question to be asked
   */
  public final String question;
  /**
   * the answer to the question
   */
  public final String answer;
  /**
   * the level of difficulty of the question
   */
  public DiffLevel difficulty;

  /**
   * Instantiates this Question to have the given question and answer text, and
   * automatically set the question as hard.
   *
   * @param qstn the actual question to be asked
   * @param ans the answer to the question
   */
  public Question(String qstn, String ans) {
    question = qstn;
    answer = ans;
    difficulty = DiffLevel.Hard;
  }

  /**
   * Instantiates a Question using the given string
   *
   * @param str the string to be converted into a question
   */
  public Question(String str) {
    String[] components = str.split("  ");
    question = components[2];
    answer = components[4];
    difficulty = (components[0].equals("Hard")) ? DiffLevel.Hard : DiffLevel.Easy;
  }

  /**
   * Instantiates this Question to have the given question and answer text, and
   * a given difficulty level
   *
   * @param qstn the actual question to be asked
   * @param ans the answer to the question
   * @param diffLevel the difficulty the question should have
   */
  public Question(String qstn, String ans, DiffLevel diffLevel) {
    question = qstn;
    answer = ans;
    difficulty = diffLevel;
  }


  /**
   * Change the difficulty of this question to the given diffLevel if they are not
   * already the same difficulty level.
   *
   * @param newDiffLevel the new difficulty level for this question to be
   */
  public void changeDifficulty(DiffLevel newDiffLevel) {
    if (!newDiffLevel.equals(difficulty)) {
      difficulty = newDiffLevel;
    }
  }


  /**
   * Determines whether, or not, two questions are the same (i.e. have the same question and answer)
   *
   * @param otherQuestion the question being compared to this question
   * @return true if they are the same question
   */
  public boolean sameQuestion(Question otherQuestion) {
    return question.equals(otherQuestion.question)
        && answer.equals(otherQuestion.answer);
  }

  /**
   * Write out this question and answer in String format
   *
   * @return the question and answer in String format
   */
  public String toString() {
    String diffLevel = (difficulty.equals(DiffLevel.Hard)) ? "Hard" : "Easy";
    String strQuestion = diffLevel + "  ||  " + question + "  ||  " + answer;
    return strQuestion;
  }

  /**
   * Write out this question (without the answer) in String format
   *
   * @return the question in String format
   */
  public String questionString() {
    String questionString = difficulty + "  ||  " + question + "\n"
        + "Answer:  __________";
    return questionString;
  }


}

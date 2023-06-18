package cs3500.pa02.quizquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a bank (list) of questions, and the associated statistics
 */
public class QuestionBank {
  /**
   * All questions hosted in this bank
   */
  public ArrayList<Question> questions;
  private final int numQuestions;
  /**
   * How many hard questions there are
   */
  public int numHard;
  /**
   * How many easy questions there are
   */
  public int numEasy;
  /**
   * How many questions switched from easy to hard
   */
  public int numEasyToHard;
  /**
   * How many questions switched from hard to easy
   */
  public int numHardToEasy;
  private final Random random;


  /**
   * Instantiates a bank of questions and a random object, and then calculates their
   * initial statistics.
   *
   * @param qstns the questions contained in this bank
   */
  public QuestionBank(ArrayList<Question> qstns) {
    questions = qstns;
    numQuestions = qstns.size();
    random = new Random(5);
    calcNumEasyAndHard();
    initStats();
  }

  /**
   * Set the starting statistics based on this QuestionBank's question list
   */
  private void initStats() {
    numEasyToHard = 0;
    numHardToEasy = 0;
  }

  /**
   * Resets this QuestionBank's questions to be the given list of questions, and update
   * the statistics accordingly.
   *
   * @param qstns the list of questions to be used
   */
  public void resetBank(ArrayList<Question> qstns) {
    questions = qstns;
    calcNumEasyAndHard();
    initStats();
  }


  /**
   * Calculate or recalculate the number of easy vs hard questions
   */
  private void calcNumEasyAndHard() {
    numEasy = 0;
    numHard = 0;
    for (Question q : questions) {
      if (q.difficulty.equals(DiffLevel.Hard)) {
        numHard += 1;
      } else {
        numEasy += 1;
      }
    }
  }


  /**
   * Updates the statistics when the oldQuestion is replaced by the newQuestion
   *
   * @param oldQuestion the old question to be replaced
   * @param newQuestion the new question replacing the old one
   */
  public void updateStats(Question oldQuestion, Question newQuestion) {
    if (oldQuestion.difficulty.equals(DiffLevel.Hard)
        && newQuestion.difficulty.equals(DiffLevel.Easy)) {
      // check if question went from Hard -> Easy
      numHardToEasy += 1;
      numEasy += 1;

    } else if (oldQuestion.difficulty.equals(DiffLevel.Easy)
        && newQuestion.difficulty.equals(DiffLevel.Hard)) {
      // check if question went from Easy -> Hard
      numEasyToHard += 1;
      numHard += 1;
    }

    // replace the oldQuestion with the newQuestion
    int oldIndex = questions.indexOf(oldQuestion);
    questions.remove(oldQuestion);
    questions.add(oldIndex, newQuestion);
    calcNumEasyAndHard();
  }


  /**
   * Get the statistics of this QuestionBank
   *
   * @return the statistics of this QuestionBank
   */
  public String getStats() {
    StringBuilder sb = new StringBuilder();
    sb.append("# of Easy Questions: " + numEasy + "\n");
    sb.append("# of Hard Questions: " + numHard + "\n");
    sb.append("# of Questions in Total: " + (numEasy + numHard) + "\n");
    sb.append("# of Questions switched from Easy to Hard: " + numEasyToHard + "\n");
    sb.append("# of Questions switched from Hard to Easy: " + numHardToEasy + "\n");
    return sb.toString();
  }


  /**
   * Choose a list of questions from the QuestionBanks list of questions,
   * prioritizing those that are marked as Hard.
   *
   * @param numToChoose the number of questions to choose
   * @return the chosen questions
   * @throws IllegalArgumentException if a user attempts to choose zero or less than zero questions
   */
  public ArrayList<Question> chooseQuestions(int numToChoose) {
    if (numToChoose <= 0) {
      throw new IllegalArgumentException("Cannot choose zero or negative number of questions.");
    } else {
      // reduce the numToChoose if too large
      numToChoose = Math.min(numToChoose, numQuestions);

      // get the shuffled and separated lists
      ArrayList<ArrayList<Question>> lists = separateAndShuffleQs();
      ArrayList<Question> easyQuestions = lists.get(0);
      ArrayList<Question> hardQuestions = lists.get(1);

      calcNumEasyAndHard();
      // determine how many hard vs easy questions should be chosen
      int numHardToChoose = Math.min(numToChoose, numHard);
      int numEasyToChoose = (numHardToChoose >= numToChoose) ? 0 : (numToChoose - numHardToChoose);

      ArrayList<Question> chosenQuestions = new ArrayList<>();
      // choose hard questions
      for (int i = 0; i < numHardToChoose; i++) {
        Question hardQuestion = hardQuestions.get(i);
        chosenQuestions.add(hardQuestion);
      }
      // choose easy questions
      if (numEasyToChoose > 0) {
        for (int i = 0; i < numEasyToChoose; i++) {
          Question easyQuestion = easyQuestions.get(i);
          chosenQuestions.add(easyQuestion);
        }
      }
      return chosenQuestions;
    }
  }

  /**
   * Separates the questions into a list of easy questions and a list of hard questions--shuffling
   * both lists
   *
   * @return a list of lists where each index in the array is one of the lists
   */
  private ArrayList<ArrayList<Question>> separateAndShuffleQs() {
    // separate into easy and hard lists
    ArrayList<Question> easyQuestions = new ArrayList<>();
    ArrayList<Question> hardQuestions = new ArrayList<>();
    for (Question q : questions) {
      if (q.difficulty.equals(DiffLevel.Easy)) {
        easyQuestions.add(q);
      } else {
        hardQuestions.add(q);
      }
    }

    // shuffle both lists
    Collections.shuffle(easyQuestions, random);
    Collections.shuffle(hardQuestions, random);

    // combine lists
    ArrayList<ArrayList<Question>> bothLists = new ArrayList<>(
        Arrays.asList(easyQuestions, hardQuestions));
    return bothLists;
  }

}

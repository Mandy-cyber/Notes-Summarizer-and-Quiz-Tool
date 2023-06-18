package cs3500.pa02.model;

import cs3500.pa02.filehandling.FileWriter;
import cs3500.pa02.quizquestions.DiffLevel;
import cs3500.pa02.quizquestions.Question;
import cs3500.pa02.quizquestions.QuestionBank;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents the functionality of a spaced-repetition quiz
 */
public class QuizModel {
  private final QuestionBank questionBank;
  /**
   * All the original questions
   */
  public ArrayList<Question> allQuestions;
  /**
   * The questions that have been changed
   */
  public ArrayList<Question> updatedQuestions;
  private final int numQuestionsToAsk;
  private Question currQuestion;
  /**
   * Whether, or not, the quiz is over
   */
  public boolean endQuiz;


  /**
   * Instantiates a bank of questions and assigns one of those questions as the first
   * question in this model
   *
   * @param qstnBank the question bank to be controlled
   * @param numQstns the number of questions that are being asked in the quiz session
   */
  public QuizModel(QuestionBank qstnBank, int numQstns) {
    questionBank = qstnBank;
    numQuestionsToAsk = numQstns;
    updatedQuestions = new ArrayList<>();
    endQuiz = false;
    // deep copy of a QB's questions
    allQuestions = new ArrayList<>();
    allQuestions.addAll(questionBank.questions);

    initControl();
  }


  /**
   * Chooses questions and updates the QuestionBank and starting question accordingly
   */
  private void initControl() {
    // remove all newly chosen questions from main list of questions
    ArrayList<Question> chosenQuestions = questionBank.chooseQuestions(numQuestionsToAsk);

    for (Question chosenQuestion : chosenQuestions) {
      allQuestions.remove(chosenQuestion);
    }

    // reset the QB to only have the chosen questions
    questionBank.resetBank(chosenQuestions);
    currQuestion = questionBank.questions.get(0);
  }

  /**
   * Changes the current question to be the next question in the list, or ends the quiz
   * if there are no more questions to be shown
   */
  public void changeQuestion() {
    ArrayList<Question> chosenQuestions = questionBank.questions;
    int currQuestionIdx = chosenQuestions.indexOf(currQuestion);

    if (currQuestionIdx >= (chosenQuestions.size() - 1)) {
      // if this is the last question then end the quiz
      endQuiz = true;
    } else {
      // otherwise change the current question
      currQuestion = chosenQuestions.get(currQuestionIdx + 1);
    }
  }


  /**
   * Merge the original list of questions with the updated list and write them
   * to the given destination file
   *
   * @param destinationFile the path to the quiz file that should be written to (excluding .sr)
   */
  public void endQuiz(String destinationFile) {
    allQuestions.addAll(updatedQuestions);

    // convert all back into strings
    StringBuilder sb = new StringBuilder();
    for (Question q : allQuestions) {
      sb.append(q + "\n");
    }

    // write to destination file
    Path path = Path.of(destinationFile);
    FileWriter fileWriter = new FileWriter(path.toFile(), ".sr");
    fileWriter.writeToFile(sb.toString());
  }

  /**
   * Updates the difficulty of the current question, view the answer, or move
   * to the next question, depending on the given option
   *
   * @param option the option the user chose
   *               1 = Mark as Easy   2 = Mark as Hard
   *               3 = See answer     4 = Next question
   * @throws IllegalArgumentException if an invalid option is inputted
   */
  public void chooseOption(String option) {
    // the old question
    String currQstn = currQuestion.question;
    String currAnswer = currQuestion.answer;
    Question oldQuestion = currQuestion;

    int numOption = Integer.parseInt(option);
    if (numOption == 1) {
      // Mark as Easy and change the question
      currQuestion = new Question(currQstn, currAnswer, DiffLevel.Easy);

    } else if (numOption == 2) {
      // Mark as Hard and change the question
      currQuestion = new Question(currQstn, currAnswer, DiffLevel.Hard);

    } else if (numOption == 3) {
      // View the answer and change the question
      currQuestion = new Question(currQstn, currAnswer, currQuestion.difficulty);

    } else if (numOption == 4) {
      // Just change the question
      currQuestion = new Question(currQstn, currAnswer, currQuestion.difficulty);

    } else {
      // Invalid option
      throw new IllegalArgumentException("Invalid option selected. Options range from 1-4.");
    }

    // update stats + change question
    updatedQuestions.add(currQuestion);
    questionBank.updateStats(oldQuestion, currQuestion);
    changeQuestion();
  }

  /**
   * Gets the current question of this model
   *
   * @return the current question
   */
  public Question getCurrQuestion() {
    return currQuestion;
  }

  /**
   * Gets the question bank of this model
   *
   * @return the question bank
   */
  public QuestionBank getQuestionBank() {
    return questionBank;
  }

}

package cs3500.pa02;

import cs3500.pa02.controllers.QuizController;
import cs3500.pa02.filehandling.FileAggregator;
import cs3500.pa02.filehandling.FileReader;
import cs3500.pa02.model.QuizModel;
import cs3500.pa02.model.StudyGuideModel;
import cs3500.pa02.quizquestions.Question;
import cs3500.pa02.quizquestions.QuestionBank;
import cs3500.pa02.studyfiles.NotesFile;
import cs3500.pa02.studyfiles.QuizFile;
import cs3500.pa02.viewers.QuizViewer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a tool for running the program
 */
public class RunProgram {
  /**
   * Creates a study guide and a quiz questions file, or just runs a quiz
   *
   * @param args - path to the directory of notes to summarize,
   *             - an ordering flag (filename, creation, or modified),
   *             - and an output path for where the study guide should be created
   * @throws IOException if path to directory of notes cannot be found or accessed
   * @throws IllegalArgumentException if the number of arguments is more than 3
   */
  public static void run(String[] args) throws IOException {
    if (args.length == 3) {
      // generate a study guide and SR file
      genFiles(args[0], args[1], args[2]);

    } else if (args.length == 0) {
      // run quiz session
      QuizController controller = new QuizController();
      ArrayList<String> startingInfo = controller.startingQuizInput();
      runQuiz(startingInfo, controller);

    } else {
      // invalid arguments
      throw new IllegalArgumentException("You may only pass zero or three arguments");
    }
  }


  /**
   * Start, and run, a spaced-repetition quiz session
   *
   * @param startingInfo a .sr filepath and the number of questions to use in this quiz
   * @param controller the controller for the quiz
   * @throws IOException if path to directory of notes cannot be found or accessed
   */
  public static void runQuiz(ArrayList<String> startingInfo, QuizController controller)
      throws IOException {
    QuizViewer viewer = new QuizViewer();

    // turn .sr file into questions
    String filePath = startingInfo.get(0);
    FileReader reader = new FileReader(filePath);
    ArrayList<Question> questions = reader.readToQuestions();
    int numQstns = Integer.parseInt(startingInfo.get(1));

    // turn questions into a QuizModel
    QuestionBank qb = new QuestionBank(questions);
    QuizModel model = new QuizModel(qb, numQstns);

    // run quiz
    while (!model.endQuiz) {
      Question currQuestion = model.getCurrQuestion();
      viewer.viewQuestion(currQuestion);

      try {
        String userResponse = controller.getInput();
        if (userResponse.equals("3")) {
          viewer.viewAnswer(currQuestion);
        }
        model.chooseOption(userResponse);

      } catch (NoSuchElementException e) {
        throw new NoSuchElementException("Could not get user input.");
      }
    }

    // end quiz: update file and print out statistics
    filePath = filePath.substring(0, filePath.length() - 3); // removing the ".sr"
    model.endQuiz(filePath);
    viewer.viewEnd(model.getQuestionBank().getStats());
  }


  /**
   * Generates a study guide file and a quiz file
   *
   * @param dirPath the path to the directory of notes files
   * @param sortMethod the method with which to sort the notes
   * @param destFile the file to output the study guide to
   * @throws IOException if the directory cannot be found/accessed
   */
  public static void genFiles(String dirPath, String sortMethod, String destFile)
      throws IOException {
    // gather files
    FileAggregator fileAggregator = new FileAggregator(dirPath);
    Files.walkFileTree(fileAggregator.path, fileAggregator);
    ArrayList<File> files = fileAggregator.getValidFiles();

    // create StudyGuide
    NotesFile notesFile = new NotesFile(files, destFile, ".md");
    StudyGuideModel studyModel = new StudyGuideModel(notesFile, sortMethod);
    studyModel.makeStudyGuide();

    // create QuizFile
    QuizFile quizFile = new QuizFile(files, destFile, ".sr");
    quizFile.makeFile();
  }
}

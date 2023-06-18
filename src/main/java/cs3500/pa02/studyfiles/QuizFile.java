package cs3500.pa02.studyfiles;

import cs3500.pa02.filehandling.FileWriter;
import cs3500.pa02.summarizers.QuizSummarizer;
import cs3500.pa02.summarizers.Summarizer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a file to be studied with (specifically a file of quiz questions)
 */
public class QuizFile extends StudyFile {
  private final ArrayList<File> files;
  private final String destFile;
  private final String ext;
  private final FileWriter fileWriter;

  /**
   * Instantiates a StudyFile with file-related information
   *
   * @param listOfFiles     the list of files to be 'studied' from
   * @param destinationFile where the later summarized study information should be outputted
   * @param fileExtension   the file extension for the destination file
   */
  public QuizFile(ArrayList<File> listOfFiles, String destinationFile,
                     String fileExtension) {
    super(listOfFiles, destinationFile, fileExtension);
    files = listOfFiles;
    destFile = destinationFile;
    ext = fileExtension;
    fileWriter = new FileWriter(new File(destFile), ext);
  }


  /**
   * Makes an output file of questions to be practiced/quizzed
   *
   * @throws IOException if the destination file cannot be found/accessed
   */
  @Override
  public void makeFile() throws IOException {
    StringBuilder sb = new StringBuilder();

    // summarize all the files
    for (File f : files) {
      Summarizer s = new QuizSummarizer(f.toPath());
      sb.append(s);
    }

    // write the summary to the destination file
    fileWriter.writeToFile(sb.toString());
  }
}

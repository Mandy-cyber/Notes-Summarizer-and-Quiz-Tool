package cs3500.pa02.studyfiles;

import cs3500.pa02.filehandling.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a file to be studied with (either a file of notes, or a file of quiz questions)
 */
public abstract class StudyFile {
  private final ArrayList<File> files;
  private final String destFile;
  private final String ext;
  private final FileWriter fileWriter;

  /**
   * Instantiates a StudyFile with file-related information
   *
   * @param listOfFiles the list of files to be 'studied' from
   * @param destinationFile where the later summarized study information should be outputted
   * @param fileExtension the file extension for the destination file
   */
  protected StudyFile(ArrayList<File> listOfFiles, String destinationFile, String fileExtension) {
    files = listOfFiles;
    destFile = destinationFile;
    ext = fileExtension;
    fileWriter = new FileWriter(new File(destFile), ext);
  }

  /**
   * Makes an output file with the desired study information (i.e. notes or questions)
   *
   * @throws IOException if the destination file cannot be found/accessed
   */
  public abstract void makeFile() throws IOException;
}

package cs3500.pa02.studyfiles;

import cs3500.pa02.comparators.CdComparator;
import cs3500.pa02.comparators.FnComparator;
import cs3500.pa02.comparators.MoComparator;
import cs3500.pa02.filehandling.FileWriter;
import cs3500.pa02.summarizers.NotesSummarizer;
import cs3500.pa02.summarizers.Summarizer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a file to be studied with (specifically a file of notes)
 */
public class NotesFile extends StudyFile {
  private ArrayList<File> files;
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
  public NotesFile(ArrayList<File> listOfFiles, String destinationFile,
                   String fileExtension) {
    super(listOfFiles, destinationFile, fileExtension);
    files = listOfFiles;
    destFile = destinationFile;
    ext = fileExtension;
    fileWriter = new FileWriter(new File(destFile), ext);
  }

  /**
   * Makes an output file of study notes (excluding questions)
   *
   * @throws IOException if the destination file cannot be found/accessed
   */
  @Override
  public void makeFile() throws IOException {
    StringBuilder sb = new StringBuilder();
    FileWriter fw = new FileWriter(new File(destFile), ext);

    // summarize all the files
    for (File f : files) {
      Summarizer s = new NotesSummarizer(f.toPath());
      sb.append(s);
    }

    // write the summary to the destination file
    fw.writeToFile(sb.toString());
  }

  /**
   * Sorts the list of files by the given ordering flag
   *
   * @param method the method by which to order the flags
   *               (by filename, creation date, or last-modified date)
   * @return the ordered list of files
   * @throws IllegalArgumentException if an invalid ordering method is provided
   */
  public ArrayList<File> sortFilesBy(String method) {
    method = method.toLowerCase();

    if (method.equals("filename")) {
      // SORT BY FILENAME
      Collections.sort(files, new FnComparator());

    } else if (method.equals("created")) {
      // SORT BY CREATION DATE/TIME
      Collections.sort(files, new CdComparator());

    } else if (method.equals("modified")) {
      // SORT BY LAST MODIFIED DATE/TIME
      Collections.sort(files, new MoComparator());
      Collections.reverse(files);

    } else {
      // INVALID ORDERING METHOD
      throw new IllegalArgumentException("Invalid ordering flag. Can only order by filename,"
          + " created, or modified. Given " + method);
    }

    return files;
  }
}

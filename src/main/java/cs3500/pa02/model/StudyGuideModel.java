package cs3500.pa02.model;

import cs3500.pa02.studyfiles.NotesFile;
import java.io.IOException;

/**
 * Represents the controls (possible actions) of a study session
 */
public class StudyGuideModel {
  private final NotesFile notes;
  private final String sortingMethod;

  /**
   * Instantiates a StudyFile to be used in this study session
   *
   * @param notesFile a markdown file of study notes
   * @param sortMethod the method the files should be sorted by before making the study guide
   */
  public StudyGuideModel(NotesFile notesFile, String sortMethod) {
    notes = notesFile;
    sortingMethod = sortMethod;
  }

  /**
   * Makes an output file of study notes (excluding questions) which constitutes a
   * study guide
   *
   * @throws IOException if the file cannot be created
   */
  public void makeStudyGuide() throws IOException {
    notes.sortFilesBy(sortingMethod);
    notes.makeFile();
  }
}

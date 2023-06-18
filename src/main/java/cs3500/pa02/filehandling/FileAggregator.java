package cs3500.pa02.filehandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Represents a tool for collecting all markdown files in a directory
 */
public class FileAggregator implements FileVisitor<Path> {
  /**
   * the path to the directory or file being aggregated
   */
  public Path path;
  private final ArrayList<File> validFiles;


  /**
   * Instantiates the path for the directory to be visited, and an empty
   * list of validFiles which will later be accumulated onto.
   *
   * @param dirPath the path to the directory in String format
   */
  public FileAggregator(String dirPath) {
    path = Path.of(dirPath);
    validFiles = new ArrayList<>();
  }


  /**
   * Determines if a file is a markdown file (i.e. ends in .md)
   *
   * @param file the file to determine if its markdown or not
   * @return whether, or not, the file is a markdown file
   */
  private boolean isMd(File file) {
    return file.getName().endsWith(".md");
  }


  /**
   * Returns the list of markdown files in the directory
   *
   * @return a copy of the list of valid files
   */
  public ArrayList<File> getValidFiles() {
    return validFiles;
  }


  /**
   * Adds the file to the list of validFiles if it is a markdown file.
   *
   * @param path a reference to the file
   * @param attrs the file's basic attributes
   * @return the visit result
   */
  @Override
  public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
    File file = path.toFile();
    if (isMd(file)) {
      validFiles.add(file);
    }
    return FileVisitResult.CONTINUE;
  }


  /**
   * Invoked for a directory before entries in the directory are visited.
   *
   * @param dir a reference to the directory
   * @param attrs the directory's basic attributes
   * @return the visit result
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    return FileVisitResult.CONTINUE;
  }

  /**
   * Invoked for a file that could not be visited.
   *
   * @param file a reference to the file
   * @param exc the I/O exception that prevented the file from being visited
   * @return the visit result
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    return FileVisitResult.CONTINUE;
  }

  /**
   * Invoked for a directory after entries in the directory, and all of their descendants,
   * have been visited.
   *
   * @param dir a reference to the directory
   * @param exc null if the iteration of the directory completes without an error;
   *            otherwise the I/O exception that caused the iteration of the directory
   *            to complete prematurely.
   * @return the visit result
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    return FileVisitResult.CONTINUE;
  }
}

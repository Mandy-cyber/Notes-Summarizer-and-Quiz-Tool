package cs3500.pa02.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Represents a way of comparing files by their names lexicographically
 */
public class FnComparator implements Comparator<File> {

  /**
   * Compares two files by their filename
   *
   * @param f1 the first file
   * @param f2 the second file
   * @return a negative number, zero, or a positive number if the first file comes before,
   *         same position, or after the second file alphabetically
   */
  @Override
  public int compare(File f1, File f2) {
    return f1.getName().compareTo(f2.getName());
  }
}

package cs3500.pa02.comparators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Represents a way of comparing files by their last-modified date/time
 */
public class MoComparator implements Comparator<File> {

  /**
   * Compares two files by the dates they were each last modified
   *
   * @param f1 the first file
   * @param f2 the second file
   * @return a negative number, zero, or a positive number if the first file comes before,
   *         same time, or after the second file in terms of last modified time
   * @throws RuntimeException if either file is null or cannot be found
   */
  @Override
  public int compare(File f1, File f2) {
    try {
      FileTime f1Attrs = Files.readAttributes(
          f1.toPath(), BasicFileAttributes.class).lastModifiedTime();
      FileTime f2Attrs = Files.readAttributes(
          f2.toPath(), BasicFileAttributes.class).lastModifiedTime();
      return f1Attrs.compareTo(f2Attrs);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

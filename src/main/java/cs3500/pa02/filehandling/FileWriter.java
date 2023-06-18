package cs3500.pa02.filehandling;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a tool for writing text to a file
 */
public class FileWriter {
  private final File file;
  private final String ext;

  /**
   * Instantiates the file being written to
   *
   * @param f the file being written to
   * @param extension the extension the file should have
   */
  public FileWriter(File f, String extension) {
    file = f;
    ext = extension;
  }


  /**
   * Writes the given String to this FileWriter's file
   *
   * @param contents the content to write to the file
   * @throws RuntimeException if the file cannot be found or written to.
   */
  public void writeToFile(String contents) {
    Path p = Path.of(file.toPath() + ext);
    byte[] data = contents.getBytes();

    try {
      Files.write(p, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}










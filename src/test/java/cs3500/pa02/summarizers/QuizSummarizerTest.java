package cs3500.pa02.summarizers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for summarizing the quiz contents of markdown files
 */
class QuizSummarizerTest {
  Path path1;
  Path path2;
  QuizSummarizer qs1;
  QuizSummarizer qs2;

  /**
   * Initializes paths and QuizSummarizers for testing
   *
   * @throws IOException if path cannot be found when instantiating a QuizSummarizer
   */
  @BeforeEach
  public void setup() throws IOException {
    path1 = Path.of("src/test/resources/questionFiles/apples.md");
    path2 = Path.of("src/test/resources/questionFiles/bananas.md");
    qs1 = new QuizSummarizer(path1);
    qs2 = new QuizSummarizer(path2);
  }

  /**
   * Tests dividing markdown into non-header text, and getting said text when requested
   */
  @Test
  void contentDivider() {
    assertEquals(qs1.getContent(), "- [[**Apples** are a popular fruit enjoyed all around "
        + "the world.]] They are grown on trees and come in many different varieties,  each with "
        + "their own unique flavor and texture.- [[What type of plants do apples grow on?:::Trees]]"
        + "- Some common apple varieties include:- Red Delicious- Granny Smith- Honeycrisp- Gala- "
        + "Fuji- [[Name one apple variety:::Red Delicious, Granny Smith, Honeycrisp, Gala, "
        + "or Fuji]]- Apples are not only delicious, but they also offer numerous health benefits."
        + " [[They are a good source of fiber  and vitamin C]], and may help to lower the risk"
        + " of heart disease and certain types of cancer [[Can apples lower the risk of heart "
        + "disease?:::Yes]].- [[One medium-sized apple contains about 4 grams of fiber]],"
        + " which is important for maintaining healthy digestion.- [[How much fiber is in a "
        + "medium-sized apple?:::4 grams]]- A medium-sized apple also provides about 14% of the "
        + "daily recommended intake of vitamin C- important for a strong immune system.- nth- "
        + "[[What can you cook with apples?:::nth]]");
  }

  /**
   * Tests summarizing text to only include double-bracketed, question-block, text
   */
  @Test
  void summarize() {
    assertEquals(qs1.summarize(qs1.getContent()), """
        What type of plants do apples grow on?:::Trees
        Name one apple variety:::Red Delicious, Granny Smith, Honeycrisp, Gala, or Fuji
        Can apples lower the risk of heart disease?:::Yes
        How much fiber is in a medium-sized apple?:::4 grams
        What can you cook with apples?:::nth
        """);

    assertEquals(qs2.summarize(qs2.getContent()), """
        What is the most popular fruit in the world?:::Bananas
        Name the vitamin that bananas are a good source of:::Vitamin C
        Can bananas cure cancer?:::No
        How should you choose a banana?:::by looking for the firm ones
        """);
  }

  /**
   * Tests getting a list of questions, in string format, from a markdown file
   */
  @Test
  void testToString() {
    assertEquals(qs1.toString(), """
        Hard  ||  What type of plants do apples grow on?  ||  Trees
        Hard  ||  Name one apple variety  ||  Red Delicious, Granny Smith, Honeycrisp, Gala, or Fuji
        Hard  ||  Can apples lower the risk of heart disease?  ||  Yes
        Hard  ||  How much fiber is in a medium-sized apple?  ||  4 grams
        Hard  ||  What can you cook with apples?  ||  nth
        """);

    assertEquals(qs2.toString(), """
        Hard  ||  What is the most popular fruit in the world?  ||  Bananas
        Hard  ||  Name the vitamin that bananas are a good source of  ||  Vitamin C
        Hard  ||  Can bananas cure cancer?  ||  No
        Hard  ||  How should you choose a banana?  ||  by looking for the firm ones
        """);
  }

  @Test
  void testGetQuestions() {
    assertEquals(qs1.getQuestions().size(), 5);
    assertEquals(qs2.getQuestions().size(), 4);
  }

}
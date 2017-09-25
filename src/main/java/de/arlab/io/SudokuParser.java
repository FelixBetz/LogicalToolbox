package de.arlab.io;

import de.arlab.sudoku.Sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A Sudoku parser.
 */
public class SudokuParser {

  private int currentLine;
  private Sudoku sudoku;
  private boolean problemLineFound = false;

  /**
   * Initializes the parser after the problem line was read.
   */
  private void init(final int base) {
    currentLine = 0;
    sudoku = new Sudoku(base);
  }

  /**
   * Parses a given sudoku file.
   * @param file the sudoku file
   * @return the sudoku
   * @throws IOException if there was a problem reading the file
   */
  public static Sudoku parse(final String file) throws IOException {
    final SudokuParser parser = new SudokuParser();
    final BufferedReader reader = new BufferedReader(new FileReader(file));
    while (reader.ready())
      parser.processLine(reader.readLine().trim());
    reader.close();
    return parser.sudoku;
  }

  /**
   * Processes a line of a sudoku file.
   * @param line the line
   * @throws IOException if there is a problem in the file
   */
  private void processLine(final String line) throws IOException {
    if (line.length() == 0)
      return;
    switch (line.charAt(0)) {
      case 'c': // Comment line
        break;
      case 'p': // Problem line
        if (problemLineFound)
          throw new IOException("The file contains more than one problem lines.");
        problemLineFound = true;
        processProblemLine(line);
        break;
      default: // Sudoku line
        if (!problemLineFound)
          throw new IOException("The problem line must be the first line in the file.");
        processNumberLine(line);
    }
  }

  /**
   * Processes the problem line of a sudoku file.
   * @param line the problem line
   * @throws IOException if the problem line has a wrong format
   */
  private void processProblemLine(final String line) throws IOException {
    final String[] parts = line.split("\\s+");
    if (parts.length < 3 || !parts[0].equals("p") || !parts[1].equals("sudoku"))
      throw new IOException("Illegally formatted problem line found: " + line);
    init(Integer.parseInt(parts[2]));
  }

  /**
   * Processes a number line of a sudoku file.
   * @param line the line
   * @throws IOException if there is a problem with the line
   */
  private void processNumberLine(final String line) throws IOException {
    final String[] parts = line.split("\\s+");
    if (parts.length == 0)
      return;
    if (parts.length < sudoku.length())
      throw new IOException("Found a line that is too short: " + line);
    for (int index = 0; index < sudoku.length(); index++)
      if (!parts[index].equals("-"))
        sudoku.set(currentLine, index, Integer.valueOf(parts[index]));
    currentLine++;
  }
}
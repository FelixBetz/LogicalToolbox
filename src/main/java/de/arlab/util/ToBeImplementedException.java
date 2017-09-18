package de.arlab.util;

/**
 * Exception for methods that have to be implemented by the students.
 */
public class ToBeImplementedException extends RuntimeException {

  /**
   * Constructs a new Exception indicating that the methods needs to be implemented.
   */
  public ToBeImplementedException() {
    super("Please implement this method before using it");
  }
}

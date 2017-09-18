package de.arlab.formulas.parser;

import de.arlab.formulas.And;
import de.arlab.formulas.Formula;
import de.arlab.formulas.Not;
import de.arlab.formulas.Or;
import de.arlab.formulas.Variable;

/**
 * A simple formula parser.
 */
public class Parser {

  private int pos = 0;

  /**
   * Parses a string and converts it into a Formula object. Variables are
   * arbitrary strings beginning with a character and containing only a-z,
   * A-Z, 0-9 and _ . AND is represented by + OR by / NOT by -.
   * @param s formula as string
   * @return Formula object of the input string
   * @throws IllegalArgumentException if the input string is no valid formula
   */
  public Formula parseFormula(final String s) throws IllegalArgumentException {
    pos = 0;
    Formula res = parseFormula2(s);
    if (pos < s.length())
      throw new IllegalArgumentException("Unerwartetes Ende der Eingabe. pos=" + pos + "s.length" + s.length());
    return res;
  }

  private Formula parseFormula2(final String s) throws IllegalArgumentException {
    final int end = s.length();
    if (end == 0)
      throw new IllegalArgumentException("Keine Formel eingegeben!");
    return parseDisjunction(s);
  }

  private Formula parseDisjunction(final String s) throws IllegalArgumentException {
    Formula res = parseConjunction(s);
    char current = currentChar(s);
    if (current == '/') {
      nextChar(s);
      Formula g = parseDisjunction(s);
      if (!(g instanceof Or))
        res = new Or(res, g);
      else
        res = new Or(new Or(res, ((Or) g).getLeft()), ((Or) g).getRight());
    }
    return res;
  }

  private Formula parseConjunction(final String s) throws IllegalArgumentException {
    Formula res = parseAtom(s);
    char current = currentChar(s);
    if (current == '+') {
      nextChar(s);
      Formula g = parseConjunction(s);
      if (!(g instanceof And))
        res = new And(res, g);
      else
        res = new And(new And(res, ((And) g).getLeft()), ((And) g).getRight());
    }
    return res;
  }

  private Formula parseAtom(final String s) throws IllegalArgumentException {
    Formula res, g;
    char current = currentChar(s);
    switch (current) {
      case '-':
        nextChar(s);
        g = parseAtom(s);
        res = new Not(g);
        break;
      case '(':
        nextChar(s);
        g = parseFormula2(s);
        if (currentChar(s) == ')') {
          nextChar(s);
          res = g;
        } else
          throw new IllegalArgumentException("Fehlende schließende Klammer in Formel " + s + ".");
        break;
      case '#':
        throw new IllegalArgumentException("Unerwartetes Ende der Eingabe bei Atom.");
      case '0':
        nextChar(s);
        res = Formula.FALSUM;
        break;
      case '1':
        nextChar(s);
        res = Formula.VERUM;
        break;
      default:
        String variable = varName(s);
        pos += variable.length();
        if (variable.isEmpty())
          throw new IllegalArgumentException("Ungültiger Beginn eines Variablenbezeichners '" + current + "' in Formel " + s + ".");
        if (!isVariableName(variable))
          throw new IllegalArgumentException("Ungültiger Variablenbezeichner '" + variable + "' in Formel " + s + ".");
        res = new Variable(variable);
        break;
    }
    return res;
  }

  private String varName(final String s) {
    int i = pos;
    while (i < s.length()
            && (Character.isLetter(s.charAt(i)) || Character.isDigit(s.charAt(i))))
      i++;
    return s.substring(pos, i);
  }

  private boolean isVariableName(final String s) {
    return Character.isLetter(s.charAt(0));
  }

  private boolean isValidChar(char c) {
    return Character.isLetter(c) || c == '/' || c == '+' || c == '-'
            || c == '(' || c == ')' || c == ' ' || c == '1' || c == '0';
  }

  private char nextChar(String s) throws IllegalArgumentException {
    if (++pos < s.length()) {
      char c = s.charAt(pos);
      if (isValidChar(c))
        return c;
      else
        throw new IllegalArgumentException("Ungültiges Zeichen " + c + " !");
    } else
      return '#';
  }

  private char currentChar(String s) throws IllegalArgumentException {
    while (pos < s.length() && s.charAt(pos) == ' ')
      pos++;
    if (pos < s.length()) {
      char c = s.charAt(pos);
      if (isValidChar(c))
        return c;
      else
        throw new IllegalArgumentException("Ungültiges Zeichen " + c + " !");
    } else
      return '#';
  }
}

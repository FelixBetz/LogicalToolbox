package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for the constant false.
 */
public final class Falsum extends Formula {

  private static final Falsum INSTANCE = new Falsum();

  /**
   * Private constructor for singleton pattern.
   */
  private Falsum() {
  }

  /**
   * Returns the single instance of the Falsum class.
   * @return the Falsum object
   */
  public static Falsum mk() {
    return INSTANCE;
  }

  @Override
  public boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean syntEqual(final Formula other) {
	  return other instanceof Falsum;
  }

  @Override
  public Formula simplify() {
    return this;
  }

  @Override
  public Formula substitute(final Variable var, final Formula formula) {
	  return this;
  }
// Falsum is atomic, literal, clause, minterm, nnf, cnf and dnf
  @Override
  public boolean isAtomicFormula() {
    return true;
  }

  @Override
  public boolean isLiteral() {
    return true;
  }

  @Override
  public boolean isClause() {
    return true;
  }

  @Override
  public boolean isMinterm() {
    return true;
  }

  @Override
  public boolean isNNF() {
	    return true;
  }

  @Override
  public boolean isCNF() {
	    return true;
  }

  @Override
  public boolean isDNF() {
	    return true;
  }

  @Override
  public Formula nnf() {
    return this;
  }

  @Override
  public Formula cnf() {
	    return this;
  }

  @Override
  public Formula dnf() {
	    return this;
  }

  @Override
  public int hashCode() {
    return 42;
  }

  @Override
  public String toString() {
    return "0";
  }
}

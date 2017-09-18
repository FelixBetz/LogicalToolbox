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
    throw new ToBeImplementedException();
  }

  @Override
  public Formula substitute(final Variable var, final Formula formula) {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isAtomicFormula() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isLiteral() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isClause() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isMinterm() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isNNF() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isCNF() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isDNF() {
    throw new ToBeImplementedException();
  }

  @Override
  public Formula nnf() {
    throw new ToBeImplementedException();
  }

  @Override
  public Formula cnf() {
    throw new ToBeImplementedException();
  }

  @Override
  public Formula dnf() {
    throw new ToBeImplementedException();
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

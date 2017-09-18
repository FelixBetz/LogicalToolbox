package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for the constant true.
 */
public final class Verum extends Formula {

  private static final Verum instance = new Verum();

  /**
   * Private constructor for singleton pattern.
   */
  private Verum() {
  }

  /**
   * Returns the single instance of the Verum class.
   * @return Verum object
   */
  public static Verum mk() {
    return instance;
  }

  @Override
  public boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException {
    return true;
  }

  @Override
  public boolean syntEqual(final Formula other) {
	  return other instanceof Verum;
  }

  @Override
  public Formula simplify() {
    return this;
  }

  @Override
  public Formula substitute(final Variable var, final Formula formula) {
    return this;
  }

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
    return 0x42;
  }

  @Override
  public String toString() {
    return "1";
  }
}

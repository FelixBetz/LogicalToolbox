package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for an OR-formula.
 */
public final class Or extends BinaryFormula {

  /**
   * Constructor for OR-formula.
   * @param left  left formula in infix notation
   * @param right right formula in infix notation
   */
  public Or(final Formula left, final Formula right) {
    super(left, right);
  }

  @Override
  public boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException {
    return left.evaluate(assignment) || right.evaluate(assignment);
  }

  @Override
  public boolean syntEqual(final Formula other) {
    throw new ToBeImplementedException();
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
  public Formula nnf() {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isDNF() {
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
    return 17 * left.hashCode() + 19 * right.hashCode();
  }

  @Override
  public String toString() {
    return "(" + left + " OR " + right + ")";
  }
}

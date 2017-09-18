package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for a NOT-formula.
 */
public final class Not extends Formula {

  private final Formula operand;

  /**
   * Constructor for NOT-formula.
   * @param operand operand of the not formula
   */
  public Not(final Formula operand) {
    this.operand = operand;
  }

  /**
   * Returns the operand of this Not formula.
   * @return operand of this Not formula
   */
  public Formula getOperand() {
    return operand;
  }

  @Override
  public boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException {
    return !operand.evaluate(assignment);
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
    return 13 * operand.hashCode();
  }

  @Override
  public String toString() {
    return "(" + "NOT " + operand + ")";
  }
}
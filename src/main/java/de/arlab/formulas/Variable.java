package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for a Variable.
 */
public final class Variable extends Formula implements Comparable<Variable> {

  private String name;

  /**
   * Constructor for a Variable.
   * @param name the name of the variable
   */
  public Variable(String name) {
    this.name = name;
  }

  /**
   * Returns the name of this variable.
   * @return the name of this variable
   */
  public String getName() {
    return name;
  }

  @Override
  public boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException {
    if (!assignment.containsKey(this))
      throw new IllegalArgumentException("Variable " + this + " was not assigned.");
    return assignment.get(this);
  }

  @Override
  public boolean syntEqual(final Formula other) {
    return (other instanceof Variable) && (name.equals(((Variable) other).getName()));
  }

  @Override
  public Formula simplify() {
    return this;
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
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(final Variable v) {
    return this.getName().compareTo(v.getName());
  }
}

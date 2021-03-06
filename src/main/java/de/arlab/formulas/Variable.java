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
    if (this.equals(var)) {
    	return formula;
    } else {
    	return this;
    }
  }
// a variable is atomic, literal, clause, minterm, nnf, cnf and dnf
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

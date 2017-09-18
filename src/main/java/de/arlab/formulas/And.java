package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for an AND-formula.
 */
public final class And extends BinaryFormula {

  /**
   * Constructor for an AND-formula.
   * @param left  left formula in infix notation
   * @param right right formula in infix notation
   */
  public And(final Formula left, final Formula right) {
    super(left, right);
  }

  @Override
  public boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException {
    return left.evaluate(assignment) && right.evaluate(assignment);
  }

  @Override
  public boolean syntEqual(final Formula other) {
	    return (other instanceof And) && left.syntEqual(((And) other).getLeft()) && right.syntEqual(((And) other).getRight());
	  }

  @Override
  public Formula simplify() {
    if (left.equals(right)) {
    	return left.simplify();
    }
    if ((left instanceof Falsum) || (right instanceof Falsum)) {
    	return Formula.FALSUM;
    }
    if (left instanceof Verum) {
    	return right.simplify();
    }
    if (right instanceof Verum) {
    	return left.simplify();
    }
    return new And(left.simplify(), right.simplify()).simplify();
  }

  @Override
  public Formula substitute(final Variable var, final Formula formula) {
    return new And(left.substitute(var, formula), right.substitute(var, formula));
  }

  @Override
  public boolean isAtomicFormula() {
    return false;
  }

  @Override
  public boolean isLiteral() {
    return false;
  }

  @Override
  public boolean isClause() {
    return false;
  }

  @Override
  public boolean isMinterm() {
    return left.isMinterm() && right.isMinterm();
  }

  @Override
  public boolean isNNF() {
    return left.isNNF() && right.isNNF();
  }

  @Override
  public boolean isCNF() {
    return left.isCNF() && right.isCNF();
  }

  @Override
  public boolean isDNF() {
    return left.isMinterm() && right.isMinterm();
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
    return 23 * left.hashCode() + 29 * right.hashCode();
  }

  @Override
  public String toString() {
    return "(" + left + " AND " + right + ")";
  }
}
package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for an AND-formula.
 */
public final class And extends BinaryFormula {
	/**
	 * Constructor for an AND-formula.
	 * 
	 * @param left
	 *            left formula in infix notation
	 * @param right
	 *            right formula in infix notation
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
		return (other instanceof And) && left.syntEqual(((And) other).getLeft())
				&& right.syntEqual(((And) other).getRight());
	}

	@Override
	public Formula simplify() {
		Formula newLeft = left.simplify();
		Formula newRight = right.simplify();
		if (newLeft.equals(newRight)) {
			return newLeft; // a and a is a
		}
		if ((newLeft instanceof Falsum) || (newRight instanceof Falsum)) {
			return Formula.FALSUM; // False and anything is always false
		}
		// True and anything is anything
		if (newLeft instanceof Verum) {
			return newRight;
		}
		if (newRight instanceof Verum) {
			return newLeft;
		}
		return new And(newLeft, newRight);
	}

	@Override
	public Formula substitute(final Variable var, final Formula formula) {
		return new And(left.substitute(var, formula), right.substitute(var, formula));
	}

	// Conjunction is not atomic, not literal, no clause but a minterm. It is in
	// nnf, cnf if lft and right are in cnf, dnf if left and right are minterms
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
		return new And(left.nnf(), right.nnf()).simplify();
	}

	@Override
	public Formula cnf() {
		return new And(left.cnf(), right.cnf());
	}

	@Override
	public Formula dnf() {
		Formula lft = left.cnf();
		Formula rgt = right.cnf();
		// if left or right cnf are an Or, distribute
		if (lft instanceof Or) {
			Or a = (Or) lft;
			return new Or(new And(a.getLeft(), rgt).cnf(), new And(a.getRight(), rgt).cnf()).simplify();
		}
		if (rgt instanceof Or) {
			Or a = (Or) rgt;
			return new Or(new And(lft, a.getLeft()).cnf(), new And(lft, a.getRight()).cnf()).simplify();
		}
		// otherwise we have a dnf
		return new And(lft, rgt).simplify();
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
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
			return newLeft;
		}
		if ((newLeft instanceof Falsum) || (newRight instanceof Falsum)) {
			return Formula.FALSUM;
		}
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
		if (this.isNNF()) {
			return new And(left.nnf(), right.nnf()).simplify();
		} else {
			return this.nnf().cnf();
		}
	}

	@Override
	public Formula cnf() {
		return new And(left.cnf(), right.cnf());
	}

	@Override
	public Formula dnf() {
		if (this.isNNF()) {
			if (this.isDNF()) {
				return this;
			}
			if (right instanceof Or) {
				Or a = (Or) right;
				return new Or(new And(left, a.getLeft()).dnf(), new And(left, a.getRight()).dnf());
			}
			Or a = (Or) left;
			return new Or(new And(a.getLeft(), right).dnf(), new And(a.getRight(), right).dnf());
		}
		return this.nnf().dnf();
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
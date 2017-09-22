package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Class for an OR-formula.
 */
public final class Or extends BinaryFormula {


	/**
	 * Constructor for OR-formula.
	 * 
	 * @param left
	 *            left formula in infix notation
	 * @param right
	 *            right formula in infix notation
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
		return (other instanceof Or) && left.syntEqual(((Or) other).getLeft())
				&& right.syntEqual(((Or) other).getRight());
	}

	@Override
	public Formula simplify() {
		// simplify left, right
		Formula newLeft = left.simplify();
		Formula newRight = right.simplify();
		if (newLeft.equals(newRight)) {
			return newLeft; // a or a is a
		}
		if ((newLeft instanceof Verum) || (newRight instanceof Verum)) {
			return Formula.VERUM; // True or anyThing is always true
		}
		if (newLeft instanceof Falsum) {
			return newRight; // False of Anything is always anyThing
		}
		if (newRight instanceof Falsum) {
			return newLeft;
		}
		return new Or(newLeft, newRight); 
	}

	@Override
	public Formula substitute(final Variable var, final Formula formula) {
		return new Or(left.substitute(var, formula), right.substitute(var, formula));
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
		return left.isClause() && right.isClause();
	}

	@Override
	public boolean isMinterm() {
		return false;
	}

	@Override
	public boolean isNNF() {
		return left.isNNF() && right.isNNF();
	}

	@Override
	public boolean isCNF() {
		return left.isClause() && right.isClause();
	}

	@Override
	public Formula nnf() {
		return new Or(left.nnf(), right.nnf()).simplify();
	}

	@Override
	public boolean isDNF() {
		return left.isDNF() && right.isDNF();
	}

	@Override
	public Formula cnf() {
		if (this.isNNF()) { // if the formula is in nnf work with it
			if (this.isCNF()) {
				return this;
			}
			// if left or right contains an and, use D
			if (right instanceof And) {
				And a = (And) right;
				return new And(new Or(left, a.getLeft()).cnf(), new Or(left, a.getRight()).cnf());
			}
			And a = (And) left;
			return new And(new Or(a.getLeft(), right).cnf(), new Or(a.getRight(), right).cnf());
		}
		// otherwise make the formula nnf first
		return this.nnf().cnf();
	}

	@Override
	public Formula dnf() {
		if (this.isNNF()) {
			return new Or(left.nnf(), right.nnf()).simplify();
		} else {
			return this.nnf().dnf();
		}
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

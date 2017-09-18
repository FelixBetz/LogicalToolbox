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
	 * 
	 * @param operand
	 *            operand of the not formula
	 */
	public Not(final Formula operand) {
		this.operand = operand;
	}

	/**
	 * Returns the operand of this Not formula.
	 * 
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
		return (other instanceof Not) && operand.syntEqual(((Not) other).getOperand());
	}

	@Override
	public Formula simplify() {
		if (operand instanceof Verum) {
			return Formula.FALSUM;
		}
		if (operand instanceof Falsum) {
			return Formula.VERUM;
		}
		if (operand instanceof Not) {
			return ((Not) operand).getOperand().simplify();
		} else {
			return new Not(operand.simplify());
		}

	}

	@Override
	public Formula substitute(final Variable var, final Formula formula) {
		return new Not(operand.substitute(var, formula));
	}

	@Override
	public boolean isAtomicFormula() {
		return false;
	}

	@Override
	public boolean isLiteral() {
		return operand.isAtomicFormula();
	}

	@Override
	public boolean isClause() {
		return operand.isAtomicFormula();
	}

	@Override
	public boolean isMinterm() {
		return operand.isAtomicFormula();
	}

	@Override
	public boolean isNNF() {
		return operand.isAtomicFormula();
	}

	@Override
	public boolean isCNF() {
		return operand.isAtomicFormula();
	}

	@Override
	public boolean isDNF() {
		return operand.isAtomicFormula();
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
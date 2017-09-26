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
		Formula o = operand.simplify();
		if (o instanceof Verum) {
			return Formula.FALSUM;
		}
		if (o instanceof Falsum) {
			return Formula.VERUM;
		}
		if (o instanceof Not) {
			return ((Not) operand).getOperand().simplify();
		} else {
			return new Not(o);
		}

	}

	@Override
	public Formula substitute(final Variable var, final Formula formula) {
		return new Not(operand.substitute(var, formula));
	}
// Negation isnt atomic, is literal, clause, minterm, nnf, cnf, dnf if operand is atomic, 
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
		// Negation is allowed only in front of variables
	   if (operand instanceof Variable) {
		   return this;
	   } 
	   if (operand instanceof Falsum) {
		   return Formula.VERUM;
	   }
	   if (operand instanceof Verum) {
		   return Formula.FALSUM;
	   }
	   // negation of negation
	   if (operand instanceof Not) {
		   return ((Not) operand).getOperand().nnf();
	   }
	   return Formula.applyDeMorgan(this).nnf();
	}

	@Override
	public Formula cnf() {
		if (this.isNNF()) {
			return this;
		} 
		return this.nnf().cnf();
	}

	@Override
	public Formula dnf() {
		if (this.isNNF()) {
			return this;
		} 
		return this.nnf().dnf();
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
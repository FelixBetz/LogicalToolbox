package de.arlab.formulas;

import de.arlab.util.ToBeImplementedException;

import java.util.Map;

/**
 * Abstract class for formulas in propositional logic.
 */
public abstract class Formula {

	/**
	 * Shortcut for the Verum-singleton.
	 */
	public static final Formula VERUM = Verum.mk();

	/**
	 * Shortcut for the Falsum-singleton.
	 */
	public static final Formula FALSUM = Falsum.mk();

	/**
	 * Evaluates a formula with the given complete variable assignment.
	 * 
	 * @param assignment
	 *            mapping between variables and truth values
	 * @return {@code true} if the formula evaluates to {@link #VERUM},
	 *         {@code false} if it evaluates to {@link #FALSUM}
	 * @throws IllegalArgumentException
	 *             if the given assignment is not complete
	 */
	public abstract boolean evaluate(final Map<Variable, Boolean> assignment) throws IllegalArgumentException;

	/**
	 * Test if two formulas are syntactically equal. Note: This is not a test for
	 * semantical equality.
	 * 
	 * @param other
	 *            formula to compare with
	 * @return {@code true} if the given formula is syntactically equal to this
	 *         formula, {@code false} otherwise
	 */
	public abstract boolean syntEqual(final Formula other);

	/**
	 * Simplifies the formula.
	 * 
	 * @return the simplified formula
	 */
	public abstract Formula simplify();

	/**
	 * Substitutes a variable with a formula.
	 * 
	 * @param var
	 *            the variable to substitute
	 * @param formula
	 *            the formula to replace the variable
	 * @return the substituted formula
	 */
	public abstract Formula substitute(final Variable var, final Formula formula);

	/**
	 * Tests if the formula is an atomic formula. An atomic formula is a variable,
	 * verum or falsum.
	 * 
	 * @return {@code true} if formula is atomic, {@code false} otherwise
	 */
	public abstract boolean isAtomicFormula();

	/**
	 * Tests if the formula is a literal. A literal is an atomic formula or the
	 * negation of an atomic formula.
	 * 
	 * @return {@code true} if the formula is a literal, {@code false} otherwise
	 */
	public abstract boolean isLiteral();

	/**
	 * Tests if the formula is a clause.
	 * 
	 * @return {@code true} if formula is a clause, {@code false} otherwise
	 */
	public abstract boolean isClause();

	/**
	 * Tests if the formula is a minterm.
	 * 
	 * @return {@code true} if formula is a minterm, {@code false} otherwise
	 */
	public abstract boolean isMinterm();

	/**
	 * Tests if the formula is in NNF (Negation Normal Form).
	 * 
	 * @return {@code true} if formula is in NNF, {@code false} otherwise
	 */
	public abstract boolean isNNF();

	/**
	 * Tests if the formula is in CNF (Conjunctive Normal Form).
	 * 
	 * @return {@code true} if formula is in CNF, {@code false} otherwise
	 */
	public abstract boolean isCNF();

	/**
	 * Tests if the formula is in DNF (Disjunctive Normal Form).
	 * 
	 * @return {@code true} if formula is in DNF, {@code false} otherwise
	 */
	public abstract boolean isDNF();

	/**
	 * Computes an equivalent formula in NNF.
	 * 
	 * @return an equivalent formula in NNF
	 */
	public abstract Formula nnf();

	/**
	 * Computes an equivalent formula in CNF.
	 * 
	 * @return an equivalent formula in CNF
	 */
	public abstract Formula cnf();

	/**
	 * Computes an equivalent formula in DNF.
	 * 
	 * @return an equivalent formula in DNF
	 */
	public abstract Formula dnf();

	/**
	 * Apply De Morgan laws on the formula. Note: This function will have an effect
	 * if and only if the formula has the form (NOT (s1 OR s2)) or (NOT (s1 AND
	 * s2)). For every other formula, an IllegalArgumentException will be thrown.
	 * 
	 * @param f
	 *            The Formula on which to apply the De Morgan rule
	 * @return the formula with De Morgan applied
	 */
	public static Formula applyDeMorgan(final Not f) throws IllegalArgumentException {
		Formula op = f.getOperand();
		if (op instanceof And) {
			And a = (And) op;
			return new Or(new Not(a.getLeft()), new Not(a.getRight()));
		}
		if (op instanceof Or) {
			Or o = (Or) op;
			return new And(new Not(o.getLeft()), new Not(o.getRight()));
		}
		throw new IllegalArgumentException();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || !(o instanceof Formula))
			return false;
		return ((Formula) o).syntEqual(this);
	}

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();
}

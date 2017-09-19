package de.arlab.sat;

import de.arlab.formulas.Formula;
import de.arlab.formulas.Not;
import de.arlab.formulas.Or;
import de.arlab.formulas.Variable;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for a Solver.
 */
public abstract class Solver {

	/**
	 * Tests if a given formula is satisfiable. Note: If you have a proper
	 * implementation of {@link #isSAT(Set)} for clause sets, it might be enough to
	 * call that method with the given formula transformed into a clause set.
	 * 
	 * @param formula
	 *            the formula to test
	 * @return {@code true} if the formula is satisfiable, otherwise {@code false}
	 */
	public abstract boolean isSAT(final Formula formula);

	/**
	 * Tests if a given clause set is satisfiable. Note: If you have a proper
	 * implementation of {@link #isSAT(Formula)}, it might be enough to call that
	 * method with the given clause set transformed into a formula.
	 * 
	 * @param clauseSet
	 *            the clause set to test
	 * @return {@code true} if the clause set is satisfiable, otherwise
	 *         {@code false}
	 */
	public abstract boolean isSAT(final Set<Clause> clauseSet);

	/**
	 * Returns a (potentially partial) mapping which satisfies the input. If the
	 * formula is unsatisfiable {@code null} is returned. Note: If you have a proper
	 * implementation of {@link #getModel(Set)} for clause sets, it might be enough
	 * to call that method with the given formula transformed into a clause set.
	 * 
	 * @param formula
	 *            the formula
	 * @return a satisfying variable mapping or {@code null} if none exists
	 */
	public abstract Map<Variable, Boolean> getModel(final Formula formula);

	/**
	 * Returns a (potentially partial) mapping which satisfies the input. If the
	 * clause set is unsatisfiable {@code null} is returned. Note: If you have a
	 * proper implementation of {@link #getModel(Formula)}, it might be enough to
	 * call that method with the given clause set transformed into a formula.
	 * 
	 * @param clauseSet
	 *            the set of clauses
	 * @return a satisfying variable mapping or {@code null} if none exists
	 */
	public abstract Map<Variable, Boolean> getModel(final Set<Clause> clauseSet);

	/**
	 * Tests if a given formula is a contradiction.
	 * 
	 * @param formula
	 *            the formula to test
	 * @return {@code true} if the formula is a contradiction, otherwise
	 *         {@code false}
	 */
	public boolean isContradiction(final Formula formula) {
		return !isSAT(formula);
	}

	/**
	 * Tests if a given formula is a tautology.
	 * 
	 * @param formula
	 *            the formula to test
	 * @return {@code true} if the formula is a tautology, otherwise {@code false}
	 */
	public boolean isTautology(final Formula formula) {
		return isContradiction(new Not(formula));
	}

	/**
	 * Tests if a given formula entails (implies) another given formula.
	 * 
	 * @param f
	 *            the first formula
	 * @param g
	 *            the second formula
	 * @return {@code true} if the first formula entails the second formula (f =>
	 *         g), otherwise {@code false}
	 */
	public boolean entails(final Formula f, final Formula g) {
		return isTautology(new Or(new Not(f), g));
	}

	/**
	 * Tests if two formulas are semantically equivalent.
	 * 
	 * @param f
	 *            the first formula
	 * @param g
	 *            the second formula
	 * @return {@code true} if the formulas are semantically equivalent (f <=> g),
	 *         otherwise {@code false}
	 */
	public boolean isEquivalent(final Formula f, final Formula g) {
		return entails (f,g) && entails(g,f);
	}
}

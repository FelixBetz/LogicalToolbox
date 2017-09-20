package de.arlab.sat;

import de.arlab.formulas.Formula;
import de.arlab.formulas.Variable;
import de.arlab.sat.heuristics.ChoiceHeuristic;
import de.arlab.util.ToBeImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for a DPLL Solver.
 */
public class DPLLSolver extends Solver {

	private final ChoiceHeuristic heuristic;
	private final Map<Variable, Boolean> model;

	/**
	 * Constructor for a DPLL solver.
	 * 
	 * @param heuristics
	 *            the search heuristics for this solver
	 */
	public DPLLSolver(final ChoiceHeuristic heuristics) {
		this.heuristic = heuristics;
		this.model = new HashMap<>();
	}

	@Override
	public boolean isSAT(final Set<Clause> clauseSet) {
		this.model.clear();
		List<Clause> clauseList = new ArrayList<>();
		clauseList.addAll(clauseSet);
		return dpll(clauseList);
	}

	@Override
	public boolean isSAT(final Formula formula) {
		return isSAT(Clause.formula2Clauses(formula));
	}

	@Override
	public Map<Variable, Boolean> getModel(final Set<Clause> clauseSet) {
		if (isSAT(clauseSet))
			return model;
		else
			return null;
	}

	@Override
	public Map<Variable, Boolean> getModel(final Formula formula) {
		return getModel(Clause.formula2Clauses(formula));
	}

	/**
	 * The main DPLL algorithm. Returns {@code true} if a given set of clauses is
	 * satisfiable.
	 * 
	 * @param clauseSet
	 *            the clause set to test
	 * @return {@code true} if the clause set is satisfiable, otherwise
	 *         {@code false}
	 */
	private boolean dpll(final List<Clause> clauseSet) {
		Literal lit = containsUnitClause(clauseSet);
		while (lit != null) {
			this.model.put(lit.getVar(), lit.getPhase());
			unitSubsumption(clauseSet, lit);
			unitResolution(clauseSet, lit.negate());
			lit = containsUnitClause(clauseSet);
		}
		if (containsEmptyClause(clauseSet))
			return false;
		if (clauseSet.isEmpty())
			return true;
		Literal nextChoice = heuristic.chooseLiteral(clauseSet);
		if (dpll(unionWithUnitClause(clauseSet, nextChoice)))
			return true;
		if (dpll(unionWithUnitClause(clauseSet, nextChoice.negate())))
			return true;

		return false;

	}
	/**
	 * Returns the literal of a unit clause, if the clause set contains a unit
	 * clause. Otherwise {@code null} will be returned.
	 * 
	 * @param clauseSet
	 *            the clause set
	 * @return the literal of a unit clause or {@code null} if there is no unit
	 *         clause
	 */
	private Literal containsUnitClause(final List<Clause> clauseSet) {
		Literal lit = null;
		Iterator<Clause> it = clauseSet.iterator();
		while (it.hasNext()) {
			lit = it.next().getUnitLit();
			if (lit != null) {
				break;
			}
		}
		return lit;
	}

	/**
	 * Deletes all clauses in a clause set containing a certain literal.
	 * 
	 * @param clauseSet
	 *            the clause set
	 * @param literal
	 *            the literal
	 */
	private void unitSubsumption(final List<Clause> clauseSet, final Literal literal) {
		Iterator<Clause> it = clauseSet.iterator();
		while (it.hasNext()) {
			Clause c = it.next();
			if (c.contains(literal)) {
				it.remove();
			}
		}

	}

	/**
	 * Deletes a literal from all clauses of a clause set.
	 * 
	 * @param clauseSet
	 *            the clause set
	 * @param literal
	 *            the literal
	 */
	private void unitResolution(final List<Clause> clauseSet, final Literal literal) {
		Iterator<Clause> it = clauseSet.iterator();
		while (it.hasNext()) {
			Clause c = it.next();
			c.removeLiteral(literal);
		}
	}

	/**
	 * Returns {@code true} if a clause set contains an empty clause.
	 * 
	 * @param clauseSet
	 *            the clause set
	 * @return {@code true} if clause set contains an empty clause, otherwise
	 *         {@code false}
	 */
	private static boolean containsEmptyClause(final List<Clause> clauseSet) {
		Iterator<Clause> it = clauseSet.iterator();
		while (it.hasNext()) {
			Clause c = it.next();
			if (c.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates a new clause set consisting of the old clauses and a new unit
	 * clause of a given literal.
	 * 
	 * @param clauseSet
	 *            the clause set
	 * @param literal
	 *            the literal
	 * @return the union of the old clause set and the new unit clause
	 */
	private List<Clause> unionWithUnitClause(final List<Clause> clauseSet, final Literal literal) {
		clauseSet.add(new Clause(literal));
		return clauseSet;
	}
}

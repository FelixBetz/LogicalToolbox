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
   * @param heuristics the search heuristics for this solver
   */
  public DPLLSolver(final ChoiceHeuristic heuristics) {
    this.heuristic = heuristics;
    this.model = new HashMap<>();
  }

  @Override
  public boolean isSAT(final Set<Clause> clauseSet) {
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isSAT(final Formula formula) {
    throw new ToBeImplementedException();
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
    throw new ToBeImplementedException();
  }

  /**
   * The main DPLL algorithm.
   * Returns {@code true} if a given set of clauses is satisfiable.
   * @param clauseSet the clause set to test
   * @return {@code true} if the clause set is satisfiable, otherwise {@code false}
   */
  private boolean dpll(final List<Clause> clauseSet) {
    throw new ToBeImplementedException();
  }

  /**
   * Returns the literal of a unit clause, if the clause set contains a unit
   * clause. Otherwise {@code null} will be returned.
   * @param clauseSet the clause set
   * @return the literal of a unit clause or {@code null} if there is no unit clause
   */
  private Literal containsUnitClause(final List<Clause> clauseSet) {
    throw new ToBeImplementedException();
  }

  /**
   * Deletes all clauses in a clause set containing a certain literal.
   * @param clauseSet the clause set
   * @param literal   the literal
   */
  private void unitSubsumption(final List<Clause> clauseSet, final Literal literal) {
    throw new ToBeImplementedException();
  }

  /**
   * Deletes a literal from all clauses of a clause set.
   * @param clauseSet the clause set
   * @param literal   the literal
   */
  private void unitResolution(final List<Clause> clauseSet, final Literal literal) {
    throw new ToBeImplementedException();
  }

  /**
   * Returns {@code true} if a clause set contains an empty clause.
   * @param clauseSet the clause set
   * @return {@code true} if clause set contains an empty clause, otherwise {@code false}
   */
  private static boolean containsEmptyClause(final List<Clause> clauseSet) {
    throw new ToBeImplementedException();
  }

  /**
   * Generates a new clause set consisting of the old clauses and a new unit clause of a given literal.
   * @param clauseSet the clause set
   * @param literal   the literal
   * @return the union of the old clause set and the new unit clause
   */
  private List<Clause> unionWithUnitClause(final List<Clause> clauseSet, final Literal literal) {
    throw new ToBeImplementedException();
  }
}

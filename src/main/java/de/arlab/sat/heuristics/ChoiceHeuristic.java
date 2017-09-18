package de.arlab.sat.heuristics;

import de.arlab.sat.Clause;
import de.arlab.sat.Literal;

import java.util.List;

/**
 * Interface for a choice heuristic for literals.
 */
public interface ChoiceHeuristic {

  /**
   * Chooses a literal w.r.t. the implemented heuristic from the given clause set.
   * @param clauseSet clause set to choose a literal from
   * @return the chosen literal
   */
  Literal chooseLiteral(final List<Clause> clauseSet);
}

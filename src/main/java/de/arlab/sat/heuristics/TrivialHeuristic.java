package de.arlab.sat.heuristics;

import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.List;

/**
 * A trivial heuristics, which chooses the next available variable.
 */
public class TrivialHeuristic implements ChoiceHeuristic {

  @Override
  public Literal chooseLiteral(final List<Clause> clauseSet) {
	  Clause c = clauseSet.get(0);
    return c.getFirstLiteral();
  }
}
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
		Clause c = clauseSet.get(0); // we only choose from a non-empty set anyways, so we ignore the fact that c
										// could be null
		return c.getFirstLiteral();
	}
}
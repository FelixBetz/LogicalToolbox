package de.arlab.sat.heuristics;

import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A heuristic that chooses the literal that is least common in the set of
 * clauses
 */
public class LeastCommonLiteralHeuristic extends LiteralHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Literal, Integer> map = literalMap(clauseSet);
		// iterate over the map and keep the entry that has the lowest value
		Map.Entry<Literal, Integer> minEntry = null;
		for (Map.Entry<Literal, Integer> entry : map.entrySet()) {
			if (minEntry == null || minEntry.getValue() > entry.getValue())
				minEntry = entry;

		}
		return minEntry.getKey();
	}
}
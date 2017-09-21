package de.arlab.sat.heuristics;

import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A trivial heuristics, which chooses the next available variable.
 */
public class LeastCommonLiteralHeuristic extends LiteralHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Literal, Integer> map = literalMap(clauseSet);
		Map.Entry<Literal, Integer> minEntry = null;
		for (Map.Entry<Literal, Integer> entry : map.entrySet()) {
			if (minEntry == null || minEntry.getValue() > entry.getValue())
				minEntry = entry;

		}
		return minEntry.getKey();
	}
}
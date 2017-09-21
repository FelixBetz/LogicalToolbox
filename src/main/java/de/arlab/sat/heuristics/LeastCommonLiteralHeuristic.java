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
public class LeastCommonLiteralHeuristic implements ChoiceHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Literal, Integer> map = new HashMap<>();
		for (Clause clause : clauseSet) {
			for (Literal literal : clause.getLiterals()) {
				if (map.containsKey(literal)) {
					map.put(literal, map.get(literal) + 1);
				} else {
					map.put(literal, 1);
				}
			}
		}
		Map.Entry<Literal, Integer> minEntry = null;
		for (Map.Entry<Literal, Integer> entry : map.entrySet()) {
			if (minEntry == null || minEntry.getValue() > entry.getValue())
				minEntry = entry;

		}
		return minEntry.getKey();
	}
}
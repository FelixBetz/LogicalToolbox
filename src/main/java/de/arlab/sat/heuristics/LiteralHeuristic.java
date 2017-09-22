package de.arlab.sat.heuristics;

import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract class for heuristics that work on literals.
 */
public abstract class LiteralHeuristic implements ChoiceHeuristic {
	/**
	 * count occurance of literals
	 * @param clauseSet
	 *            a (non-empty) set of clauses
	 * @return map a Hashmap that tells us which literal occurs how often.
	 */
	public Map<Literal, Integer> literalMap(List<Clause> clauseSet) {
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
		return map;
	}
}
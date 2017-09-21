package de.arlab.sat.heuristics;

import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A trivial heuristics, which chooses the next available variable.
 */
public class LeastCommonVariableHeuristic implements ChoiceHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Variable, Integer> map = new HashMap<>();
		// Store the variables and the number of its occurence in a map
		for (Clause clause : clauseSet) {
			for (Literal literal : clause.getLiterals()) {
				Variable var = literal.getVar();
				if (map.containsKey(var)) {
					map.put(var, map.get(var) + 1);
				} else {
					map.put(var, 1);
				}
			}
		}
		// Iterate over the map to find the entry with the lowest occurence
		Map.Entry<Variable, Integer> minEntry = null;
		for (Map.Entry<Variable, Integer> entry : map.entrySet()) {
			if (minEntry == null || minEntry.getValue() > entry.getValue())
				minEntry = entry;

		}
		return new Literal(minEntry.getKey());
	}
}
package de.arlab.sat.heuristics;

import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A heuristic that chooses the variable that is least common in the set of
 * clauses
 */
public class LeastCommonVariableHeuristic extends VariableHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Variable, Integer> map = variableMap(clauseSet);
		// Iterate over the map to find the entry with the lowest occurence
		Map.Entry<Variable, Integer> minEntry = null;
		for (Map.Entry<Variable, Integer> entry : map.entrySet()) {
			if (minEntry == null || minEntry.getValue() > entry.getValue())
				minEntry = entry;

		}
		return new Literal(minEntry.getKey());
	}
}
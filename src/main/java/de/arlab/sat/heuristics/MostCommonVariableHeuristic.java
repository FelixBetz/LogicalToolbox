package de.arlab.sat.heuristics;

import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A heuristic that chooses the variable that is most common in the set of
 * clauses
 */
public class MostCommonVariableHeuristic extends VariableHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Variable, Integer> map = variableMap(clauseSet);
		// iterate over the map and keep the entry that has the highest value
		Map.Entry<Variable, Integer> maxEntry = null;
		for (Map.Entry<Variable, Integer> entry : map.entrySet()) {
			if (maxEntry == null || maxEntry.getValue() < entry.getValue())
				maxEntry = entry;

		}
		return new Literal(maxEntry.getKey());
	}
}
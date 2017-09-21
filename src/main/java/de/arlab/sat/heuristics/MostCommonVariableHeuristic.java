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
public class MostCommonVariableHeuristic extends VariableHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Variable, Integer> map = variableMap(clauseSet);
		Map.Entry<Variable, Integer> maxEntry = null;
		for (Map.Entry<Variable, Integer> entry : map.entrySet()) {
			if (maxEntry == null || maxEntry.getValue() > entry.getValue())
				maxEntry = entry;

		}
		return new Literal(maxEntry.getKey());
	}
}
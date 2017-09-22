package de.arlab.sat.heuristics;

import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract class for heuristics that work on variables.
 */
public abstract class VariableHeuristic implements ChoiceHeuristic {
	/**
	 * count occurance of variables
	 * 
	 * @param clauseSet
	 *            a (non-empty) set of clauses
	 * @return map a Hashmap that tells us which variable occurs how often.
	 */
	public static Map<Variable, Integer> variableMap(List<Clause> clauseSet) {
		Map<Variable, Integer> map = new HashMap<>();
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
		return map;
	}
}
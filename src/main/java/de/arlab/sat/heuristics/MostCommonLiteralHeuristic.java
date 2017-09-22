package de.arlab.sat.heuristics;

import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A heuristic that chooses the literal that is most common in the set of
 * clauses
 */
public class MostCommonLiteralHeuristic extends LiteralHeuristic {

	@Override
	public Literal chooseLiteral(List<Clause> clauseSet) {
		Map<Literal, Integer> map = literalMap(clauseSet);
		// iterate over the map and keep the entry that has the highest value
		Map.Entry<Literal, Integer> maxEntry = null;
		for (Map.Entry<Literal, Integer> entry : map.entrySet()) {
			if (maxEntry == null || maxEntry.getValue() < entry.getValue())
				maxEntry = entry;

		}
		return maxEntry.getKey();
	}
}
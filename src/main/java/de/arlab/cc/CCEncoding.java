package de.arlab.cc;

import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Methods for encoding cardinality constraints of different types.
 */
public class CCEncoding {
	// The way we use those methods we „feed“ them with literals with positive
	// phase, so we assume that the literals we get in here are positive

	/**
	 * Encodes an at-least-one constraint: {@code \sum_(i=1)^n l_i >= 1.}
	 *
	 * Note: Encoding contains no additional variables and has one clause.
	 * 
	 * @param literals
	 *            literals where at least one has to hold
	 * @return clauses of the cardinality constraint encoding
	 */
	public static Set<Clause> atLeastOne(final Collection<Literal> literals) {
		Set<Clause> set = new HashSet<>();
		set.add(new Clause(literals)); // "at least one" is a simple "or", so we just build one clause.
		Clause clause = new Clause(new Clause(literals));   //literal1 Or literal2 OR.....
		set.add(clause);
		return set;
	}

	/**
	 * Encodes an at-most-one constraint: {@code \sum_(i=1)^n l_i <= 1.}
	 *
	 * Note: Encoding contains no additional variables.
	 * 
	 * @param literals
	 *            literals where at most one has to hold
	 * @return clauses of the cardinality constraint encoding
	 */
	public static Set<Clause> atMostOne(final Collection<Literal> literals) {
		Set<Clause> clauses = new HashSet<>();
		List<Literal> list = new ArrayList<>();
		list.addAll(literals);
		int l = list.size();
		// combine the negated literal pairwise with the literals (again negated) that
		// follow in that list.
		for (int i = 0; i < l; i++) {
			Literal lit = list.get(i).negate();
			Iterator<Literal> it = list.subList(i + 1, l).iterator();
			while (it.hasNext()) {
				Literal next = it.next().negate();
				clauses.add(new Clause(lit, next));         //!(x_i And x_j) <=> !x_i OR !x_j
			}
		}
		return clauses;
	}

	/**
	 * Encodes an exactly-one constraint: {@code \sum_(i=1)^n l_i = 1.}
	 *
	 * Note: Encoding contains no additional variables.
	 * 
	 * @param literals
	 *            literals where exactly one has to hold
	 * @return clauses of the cardinality constraint encoding
	 */
	public static Set<Clause> exactlyOne(final Collection<Literal> literals) {
		// (if x>=1 and x<=1 then it must be x==1), so we use the two former constraints
		Set<Clause> set= atMostOne(literals);  //combine atMostOne and atLeastOne
		set.addAll(atLeastOne(literals));
		return set;
	}
}
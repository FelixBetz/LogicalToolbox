package de.arlab.cc;

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
		Clause clause = new Clause(new Clause(literals));
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
		Clause clause = new Clause(new Clause(literals),false);
		Clause clause2 = new Clause(new Clause(literals),false);
		Set<Clause> set = new HashSet<>();
		Iterator<Literal> it = clause.getLiterals().iterator();
		while(it.hasNext()) {
			Literal next = it.next();
			Clause add = new Clause(clause2);
			add.negateLiteral(next);
			set.add(add);
		}
		if(!(clause.getLiterals().size()<=1)) set.add(clause2);
	    return set;
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
		Set<Clause> set= atMostOne(literals);
		set.addAll(atLeastOne(literals));
		return set;
	}
}
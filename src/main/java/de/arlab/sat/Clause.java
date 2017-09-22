package de.arlab.sat;

import de.arlab.formulas.And;
import de.arlab.formulas.Falsum;
import de.arlab.formulas.Formula;
import de.arlab.formulas.Not;
import de.arlab.formulas.Or;
import de.arlab.formulas.Variable;
import de.arlab.formulas.Verum;
import de.arlab.util.ToBeImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A mutable Clause. A clause is a set of literals. In propositional logic it is
 * interpreted as a disjunction of the contained literals.
 */
public class Clause {

	private final Set<Literal> literals;

	/**
	 * Constructs an empty clause.
	 */
	public Clause() {
		literals = new HashSet<>();
	}

	/**
	 * Constructor for a unit clause.
	 * 
	 * @param lit
	 *            the single literal in this clause
	 */
	public Clause(final Clause other) {
		literals = new HashSet<>();
		for (Literal literal : other.getLiterals()) {
			literals.add(new Literal(literal));
		}
	}

	public Clause(final Literal lit) {
		literals = new HashSet<>();
		literals.add(lit);
	}

	/**
	 * Constructor for a clause with a given collection of literals.
	 * 
	 * @param lits
	 *            the literals of this clause
	 */
	public Clause(final Collection<Literal> lits) {
		literals = new HashSet<>(lits);
	}

	/**
	 * Constructor for a clause with given literals.
	 * 
	 * @param lits
	 *            the literals of this clause
	 */
	public Clause(final Literal... lits) {
		this.literals = new HashSet<>();
		Collections.addAll(literals, lits);
	}

	/**
	 * Returns the literals of this clause.
	 * 
	 * @return the literals of this clause
	 */
	public Set<Literal> getLiterals() {
		return literals;
	}
	/**
	 * Returns the first literal of this clause if it exists.
	 * 
	 * @return the first literal of this clause or null if the clause is empty
	 */
	public Literal getFirstLiteral() {
		if (this.literals.isEmpty()) {
			return null;
		}
		Iterator<Literal> it = literals.iterator();
		return it.next();
	}

	/**
	 * Adds a literal to this clause.
	 * 
	 * @param lit
	 *            the literal to add
	 */
	public void addLiteral(final Literal lit) {
		literals.add(lit);
	}

	/**
	 * Removes a literal from this clause.
	 * 
	 * @param lit
	 *            the literal to remove
	 */
	public void removeLiteral(final Literal lit) {
		literals.remove(lit);
	}

	/**
	 * Returns {@code true} if this clause is unit.
	 * 
	 * @return {@code true} if this clause is unit, otherwise {@code false}
	 */
	public boolean isUnit() {
		return literals.size() == 1;
	}

	/**
	 * Returns {@code true} if this clause is empty.
	 * 
	 * @return {@code true} if this clause is empty, otherwise {@code false}
	 */
	public boolean isEmpty() {
		return literals.isEmpty();
	}

	/**
	 * Returns the single literal of this clause, if this clause is a unit clause.
	 * Otherwise null will be returned.
	 * 
	 * @return the single literal of this clause, or null if this clause is not unit
	 */
	public Literal getUnitLit() {
		if (isUnit())
			return literals.iterator().next();
		else
			return null;
	}

	/**
	 * Returns {@code true} if this clause contains a given literal literal.
	 * 
	 * @param lit
	 *            the literal
	 * @return {@code true} if this clause contains lit, otherwise {@code false}
	 */
	public boolean contains(final Literal lit) {
		return literals.contains(lit);
	}

	/**
	 * Adds all literals of the given clause to this clause.
	 * 
	 * @param c
	 *            clause to union with
	 */
	public void unionWith(final Clause c) {
		literals.addAll(c.getLiterals());
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this)
			return true;
		if (other == null)
			return false;
		return other instanceof Clause && literals.equals(((Clause) other).literals);
	}

	@Override
	public int hashCode() {
		int sum = 0;
		for (Literal lit : literals)
			sum += lit.hashCode();
		return sum;
	}

	@Override
	public String toString() {
		return literals.toString();
	}

	/**
	 * Converts an arbitrary formula to a set of clauses.
	 * 
	 * @param f
	 *            the formula
	 * @return a set of clauses equivalent to the CNF of the formula
	 */
	public static Set<Clause> formula2Clauses(final Formula f) {
		Formula cnf = f.cnf();
		Set<Clause> set = new HashSet<>();
		// Literals are already clauses
		// if cnf instanceof Verum then the set is empty, no set manipulation needed
		if (cnf instanceof Falsum) {
			set.add(new Clause());
		} else if (cnf instanceof Variable) {
			set.add(new Clause(new Literal((Variable) cnf, true)));
		} else if (cnf instanceof Not) {
			Variable var = (Variable) ((Not) cnf).getOperand();
			set.add(new Clause(new Literal(var, false)));
		} else if (cnf instanceof Or) { // as our formula is in cnf left and right are clauses too, join them
			Set<Clause> left = Clause.formula2Clauses(((Or) cnf).getLeft());
			Set<Clause> right = Clause.formula2Clauses(((Or) cnf).getRight());
			Clause c = Clause.setOfClausesToClause(left);
			c.unionWith(Clause.setOfClausesToClause(right));
			set.add(c);
		} else if (cnf instanceof And) { // convert left and right to sets of clauses and join both sets
			set = Clause.formula2Clauses(((And) cnf).getLeft());
			Set<Clause> right = Clause.formula2Clauses(((And) cnf).getRight());
			set.addAll(right);
		}
		return set;
	}

	/**
	 * Iterate over a set of clauses and union all clauses together?
	 * 
	 * @param clauses
	 * @return a clause
	 */
	private static Clause setOfClausesToClause(final Set<Clause> clauses) {
		Iterator<Clause> it = clauses.iterator();
		Clause c = new Clause();
		while (it.hasNext()) {
			c.unionWith(it.next());
		}
		return c;
	}

	/**
	 * Converts a set of clauses into a corresponding formula in cnf.
	 * 
	 * @param clauses
	 *            the set of clauses
	 * @return the corresponding formula in cnf
	 */
	public static Formula clauses2Formula(final Set<Clause> clauses) {
		if (clauses.isEmpty()) { // Empty set represents verum
			return Verum.mk();
		}
		Iterator<Clause> it = clauses.iterator();
		// Variable that holds the complete formula
		Formula f = Clause.clause2Formula(it.next());
		// Join the temp formula (by and) with the next converted clause
		while (it.hasNext()) {
			Formula v = Clause.clause2Formula(it.next());
			f = new And(v, f);
		}
		return f;

	}

	/**
	 * Convert a set of literals in a formula
	 * 
	 * @param c
	 * @return the formula that is represented by the given clause
	 */
	public static Formula clause2Formula(final Clause c) {
		if (c.isEmpty()) { // Empty Clause represents falsum.
			return Falsum.mk();
		}
		Iterator<Literal> it = c.getLiterals().iterator();
		// f collects the formula
		Formula f = Clause.literal2Formula(it.next());
		// Join the formula and the next variable by or.
		while (it.hasNext()) {
			Formula v = Clause.literal2Formula(it.next());
			f = new Or(v, f);
		}
		return f;
	}

	/**
	 * Convert a literal to a variable
	 * 
	 * @param lit
	 * @return a variable (type formula) or its negation
	 */
	private static Formula literal2Formula(final Literal lit) {
		Formula v = lit.getVar();
		if (!lit.getPhase()) {
			v = new Not(v);
		}
		return v;
	}

	/**
	 * Returns all variables which are present in a given set of clauses.
	 * 
	 * @param clauses
	 *            the set of clauses
	 * @return all variables in the clauses
	 */
	public static Set<Variable> vars(final Set<Clause> clauses) {
		final Set<Variable> vars = new HashSet<Variable>();
		for (Clause c : clauses)
			vars.addAll(vars(c));
		return vars;
	}

	/**
	 * Returns a copy of a set of clauses
	 * 
	 * @param clauseSet
	 * @return a copy of the set
	 */
	public static Set<Clause> copyClauseSet(final Set<Clause> clauseSet) {
		Set<Clause> clauseList = new HashSet<>();
		for (Clause clause : clauseSet) {
			clauseList.add(new Clause(clause));
		}
		return clauseList;
	}

	/**
	 * Returns a copy of a list of clauses
	 * 
	 * @param clauseSet
	 * @return a copy of the list
	 */
	public static List<Clause> copyClauseSet(final List<Clause> clauseSet) {
		List<Clause> clauseList = new ArrayList<>();
		for (Clause clause : clauseSet) {
			clauseList.add(new Clause(clause));
		}
		return clauseList;
	}

	/**
	 * list all the variables used in a clause
	 * 
	 * @param c
	 * @return all variables in a set
	 */
	private static Set<Variable> vars(final Clause c) {
		final Set<Variable> vars = new HashSet<Variable>();
		for (Literal l : c.getLiterals())
			vars.add(l.getVar());
		return vars;
	}
}

package de.arlab.sat;

import de.arlab.formulas.And;
import de.arlab.formulas.Falsum;
import de.arlab.formulas.Formula;
import de.arlab.formulas.Not;
import de.arlab.formulas.Or;
import de.arlab.formulas.Variable;
import de.arlab.formulas.Verum;
import de.arlab.util.ToBeImplementedException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A mutable Clause.
 * A clause is a set of literals.
 * In propositional logic it is interpreted as a disjunction of the contained literals.
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
   * @param lit the single literal in this clause
   */
  public Clause(final Literal lit) {
    literals = new HashSet<>();
    literals.add(lit);
  }

  /**
   * Constructor for a clause with a given collection of literals.
   * @param lits the literals of this clause
   */
  public Clause(final Collection<Literal> lits) {
    literals = new HashSet<>(lits);
  }

  /**
   * Constructor for a clause with given literals.
   * @param lits the literals of this clause
   */
  public Clause(final Literal... lits) {
    this.literals = new HashSet<>();
    Collections.addAll(literals, lits);
  }

  /**
   * Returns the literals of this clause.
   * @return the literals of this clause
   */
  public Set<Literal> getLiterals() {
    return literals;
  }


  /**
   * Adds a literal to this clause.
   * @param lit the literal to add
   */
  public void addLiteral(final Literal lit) {
    throw new ToBeImplementedException();
  }

  /**
   * Removes a literal from this clause.
   * @param lit the literal to remove
   */
  public void removeLiteral(final Literal lit) {
    throw new ToBeImplementedException();
  }

  /**
   * Returns {@code true} if this clause is unit.
   * @return {@code true} if this clause is unit, otherwise {@code false}
   */
  public boolean isUnit() {
    throw new ToBeImplementedException();
  }

  /**
   * Returns {@code true} if this clause is empty.
   * @return {@code true} if this clause is empty, otherwise {@code false}
   */
  public boolean isEmpty() {
    throw new ToBeImplementedException();
  }

  /**
   * Returns the single literal of this clause, if this clause is a unit clause. Otherwise null will be returned.
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
   * @param lit the literal
   * @return {@code true} if this clause contains lit, otherwise {@code false}
   */
  public boolean contains(final Literal lit) {
    throw new ToBeImplementedException();
  }

  /**
   * Adds all literals of the given clause to this clause.
   * @param c clause to union with
   */
  public void unionWith(final Clause c) {
    throw new ToBeImplementedException();
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
   * @param f the formula
   * @return a set of clauses equivalent to the CNF of the formula
   */
  public static Set<Clause> formula2Clauses(final Formula f) {
    throw new ToBeImplementedException();
  }

  /**
   * Converts a set of clauses into a corresponding formula in cnf.
   * @param clauses the set of clauses
   * @return the corresponding formula in cnf
   */
  public static Formula clauses2Formula(final Set<Clause> clauses) {
    throw new ToBeImplementedException();
  }

  /**
   * Returns all variables which are present in a given set of clauses.
   * @param clauses the set of clauses
   * @return all variables in the clauses
   */
  public static Set<Variable> vars(final Set<Clause> clauses) {
    final Set<Variable> vars = new HashSet<Variable>();
    for (Clause c : clauses)
      vars.addAll(vars(c));
    return vars;
  }

  private static Set<Variable> vars(final Clause c) {
    final Set<Variable> vars = new HashSet<Variable>();
    for (Literal l : c.getLiterals())
      vars.add(l.getVar());
    return vars;
  }
}

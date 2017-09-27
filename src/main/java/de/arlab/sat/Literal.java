package de.arlab.sat;

import de.arlab.formulas.Variable;

/**
 * Class for a literal. A literal is a Variable with a phase.
 */
public class Literal {
	private final Variable var;
	private final boolean phase;

	/**
	 * Constructor for a literal.
	 * 
	 * @param var
	 *            the variable of this literal
	 * @param phase
	 *            the phase of this literal
	 */
	public Literal(final Variable var, final boolean phase) {
		this.var = var;
		this.phase = phase;
	}

	/**
	 * Constructor to copy a literal.
	 * 
	 * @param other
	 *            the literal to be copied
	 */
	public Literal(final Literal other) {
		this.var = other.getVar();
		this.phase = other.getPhase();
	}

	/**
	 * Alternative constructor for a literal with a positive phase.
	 * 
	 * @param var
	 *            the variable of this literal
	 */
	public Literal(final Variable var) {
		this.var = var;
		this.phase = true;
	}

	/**
	 * alternative Constructor for a literal .
	 * 
	 * @param i
	 *            an integer to determine the name of the variable and phase of this
	 *            literal
	 */
	public Literal(final Integer i) {
		if (i > 0) {
			this.var = new Variable(Integer.toString(i));
			this.phase = true;
		} else {
			this.var = new Variable(Integer.toString(Math.abs(i)));
			this.phase = false;
		}
	}

	public Literal(Literal next, boolean phase2) {
		this.var = next.getVar();
		this.phase = phase2;
	}

	/**
	 * Returns the variable of this literal.
	 * 
	 * @return the variable of this literal
	 */
	public Variable getVar() {
		return var;
	}

	/**
	 * Returns the phase of this literal.
	 * 
	 * @return the phase of this literal
	 */
	public boolean getPhase() {
		return phase;
	}

	/**
	 * Returns a negated copy of this literal.
	 * 
	 * @return a negated copy of this literal
	 */
	public Literal negate() {
		return new Literal(var, !phase);
	}

	@Override
	public boolean equals(final Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Literal))
			return false;
		Literal o = (Literal) other;
		return var.equals(o.var) && phase == o.phase;
	}

	@Override
	public int hashCode() {
		return 2 * var.getName().hashCode() + (phase ? 1 : 0);
	}

	@Override
	public String toString() {
		return phase ? var.toString() : "-" + var;
	}
}

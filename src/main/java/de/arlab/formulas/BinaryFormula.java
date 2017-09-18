package de.arlab.formulas;

/**
 * Abstract class for binary formulas.
 */
public abstract class BinaryFormula extends Formula {

  protected final Formula left;
  protected final Formula right;

  /**
   * Constructor for BinaryFormula.
   * @param left  left formula in infix notation
   * @param right right formula in infix notation
   */
  protected BinaryFormula(final Formula left, final Formula right) {
    this.left = left;
    this.right = right;
  }

  /**
   * Returns the left operand of this binary formula.
   * @return the left operand of this binary formula
   */
  public Formula getLeft() {
    return left;
  }

  /**
   * Returns the right operand of this binary formula.
   * @return the right operand of this binary formula
   */
  public Formula getRight() {
    return right;
  }
}

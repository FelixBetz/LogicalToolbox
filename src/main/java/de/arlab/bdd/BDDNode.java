package de.arlab.bdd;

import de.arlab.formulas.Variable;

/**
 * Representation of a BDD node.
 */
public final class BDDNode {

  private final Variable var;
  private final int left;
  private final int right;

  /**
   * Constructs a new BDD node.
   * @param var   the variable of this node
   * @param left  the index of the left node (path to true)
   * @param right the index of the right node (path to false)
   */
  public BDDNode(final Variable var, final int left, final int right) {
    this.var = var;
    this.left = left;
    this.right = right;
  }

  /**
   * Returns the variable of this node.
   * @return the variable of this node
   */
  public Variable getVar() {
    return var;
  }

  /**
   * Returns the index the the left node.
   * This is the path where the variable of this node is assigned to true.
   * @return the index the the left node
   */
  public int getLeft() {
    return left;
  }

  /**
   * Returns the index the the right node.
   * This is the path where the variable of this node is assigned to false.
   * @return the index the the right node
   */
  public int getRight() {
    return right;
  }

  /**
   * Returns the complement of this node.
   * @return the complement of this node
   */
  public BDDNode complement() {
    return new BDDNode(var, -left, -right);
  }

  @Override
  public String toString() {
    return "[" + var + " | left: " + left + " , right: " + right + "]";
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this)
      return true;
    if (other == null || !(other instanceof BDDNode))
      return false;
    BDDNode o = (BDDNode) other;
    return o.var.equals(var) && o.left == left && o.right == right;
  }

  @Override
  public int hashCode() {
    return var.getName().hashCode() + left + right;
  }
}
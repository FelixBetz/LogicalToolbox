package de.arlab.tableaus;

import de.arlab.formulas.Formula;
import de.arlab.formulas.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Class for a semantic tableau.
 */
public class Tableau {

  private final TableauNode root;

  /**
   * Constructor for a tableau.
   * @param formula the formula for the root node of the tableau
   */
  public Tableau(final Formula formula) {
    final List<Formula> label = new LinkedList<>();
    label.add(formula.nnf());
    this.root = new TableauNode(label);
  }

  /**
   * Tests if this tableau is closed.
   * @return {@code true} if this tableau is closed, otherwise {@code false}
   */
  public boolean isClosed() {
    return root.isClosed();
  }

  /**
   * Returns a model of this tableau or {@code null} if it is closed.
   * @return a model of this tableau or {@code null} if it is closed
   */
  public Map<Variable, Boolean> getModel() {
    return root.getModel();
  }

  @Override
  public String toString() {
    return root.toString() + "-----------------------------";
  }
}

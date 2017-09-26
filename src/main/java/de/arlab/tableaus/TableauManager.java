package de.arlab.tableaus;

import de.arlab.formulas.Formula;
import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Solver;
import de.arlab.util.ToBeImplementedException;

import java.util.Map;
import java.util.Set;

/**
 * Manager for Tableaus.
 */
public class TableauManager extends Solver {

  @Override
  public boolean isSAT(Formula formula) {
	  return ! new Tableau(formula).isClosed();
  }

  @Override
  public boolean isSAT(final Set<Clause> clauseSet) {
	  return ! new Tableau(Clause.clauses2Formula(clauseSet)).isClosed();
  }

  @Override
  public Map<Variable, Boolean> getModel(Formula formula) {
    return new Tableau(formula).getModel();
  }

  @Override
  public Map<Variable, Boolean> getModel(final Set<Clause> clauseSet) {
    return new Tableau(Clause.clauses2Formula(clauseSet)).getModel();
  }
}

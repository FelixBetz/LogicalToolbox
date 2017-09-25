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
    throw new ToBeImplementedException();
  }

  @Override
  public boolean isSAT(final Set<Clause> clauseSet) {
    throw new ToBeImplementedException();
  }

  @Override
  public Map<Variable, Boolean> getModel(Formula formula) {
    throw new ToBeImplementedException();
  }

  @Override
  public Map<Variable, Boolean> getModel(final Set<Clause> clauseSet) {
    throw new ToBeImplementedException();
  }
}

package sudoku;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.formulas.*;
import de.arlab.io.DIMACSParser;
import de.arlab.sat.Clause;

public class BDDTest {
	private static BDDManager manager = new BDDManager();

	private static Formula f1;
	private static Formula f1cnf;
	private static Formula f2;
	private static Formula f3;
	private static Formula f2cnf;
	private static Formula f2dnf;
	private static Map<Variable, Boolean> map1;
	private static Formula x1 = F.var1;
	private static Formula nx1 = new Not(F.var1);
	private static Formula x2 = F.var2;
	private static Formula x3 = F.var3;
	private static Formula x4 = F.var4;
	private static Formula x5 = F.var5;
	@BeforeClass
	public static void initialize() {
		f1 = new And(nx1, x2);
		f1cnf = new And(nx1, new Or(x1,x2));
		f2 = new Or(x3, f1);
		f2cnf = new And(new Or(nx1,x3),new Or(x1, new Or(x2,x3)));
		map1 = new HashMap<>();
		map1.put(F.var1, false);
		map1.put(F.var2, true);
	}

	@Test
	public void testBdd() throws IOException {
		assertTrue(manager.isSAT(Formula.VERUM));
		assertTrue(manager.isTautology(Formula.VERUM));
		assertFalse(manager.isSAT(Formula.FALSUM));
		assertFalse(manager.isTautology(Formula.FALSUM));
		assertTrue(manager.isSAT(new Or(x1, nx1)));
		assertFalse(manager.isSAT(new And(x1, nx1)));
		assertTrue(manager.isTautology(new Or(x1, nx1)));
		assertTrue(manager.isContradiction(new And(x1, nx1)));
		// Not Satisfiable
		Set<Clause> parsed1 = new DIMACSParser().parse("src/test/resources/profiling/formel1.cnf");
		// Satisfiable
		Set<Clause> parsed2 = new DIMACSParser().parse("src/test/resources/profiling/formel2.cnf");
		assertFalse(manager.isSAT(parsed1));
		assertTrue(manager.isSAT(parsed2));
		assertTrue(manager.isContradiction(Clause.clauses2Formula(parsed1)));
		assertFalse(manager.isContradiction(Clause.clauses2Formula(parsed2)));
		assertTrue(manager.isTautology(new Not(Clause.clauses2Formula(parsed1))));
		assertFalse(manager.isTautology(new Not(Clause.clauses2Formula(parsed2))));
		assertEquals(f1,manager.toDNF(f1));
		assertEquals(f1cnf,manager.toCNF(f1));
		// model of a formula and its cnf should be the same.
		assertEquals(map1, manager.getModel(f1));
		assertEquals(map1, manager.getModel(f1cnf));
		// the model is empty if the formula is Contradiction or tautology
		assertEquals(new HashMap<>(), manager.getModel(F.verum));
		assertEquals(new HashMap<>(), manager.getModel(F.falsum));
		assertEquals(new HashMap<>(), manager.getModel(new Or(x1, nx1)));
		assertEquals(new HashMap<>(), manager.getModel(new And(x1, nx1)));
		System.out.println(manager.toCNF(f2).isCNF());

	}
}

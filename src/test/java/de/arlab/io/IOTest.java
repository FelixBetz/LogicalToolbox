package de.arlab.io;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.io.DIMACSParser;
import de.arlab.sat.*;
import de.arlab.sat.heuristics.TrivialHeuristic;
import de.arlab.formulas.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IOTest {

	private static Map<Variable, Boolean> assignment = new HashMap<Variable, Boolean>();

	private static Formula nnf;
	private static Formula cnf;
	private static Formula nonCnf1;
	private static Formula nonCnf2;
	private static Formula dnf;
	private static Formula nonDnf1;
	private static Formula nonDnf2;

	private static Formula x1 = F.var1;
	private static Formula x2 = F.var2;
	private static Formula x3 = F.var3;
	private static Formula x4 = F.var4;
	private static Formula x5 = F.var5;

	private Formula t = Formula.VERUM;
	private Formula f = Formula.FALSUM;

	private static Variable v1 = new Variable("v1");
	private static Variable v2 = new Variable("v2");
	private static Variable v3 = new Variable("v3");

	private static Literal lit1 = new Literal(v1, true);
	private static Literal lit2 = new Literal(v2, true);
	private static Literal lit3 = new Literal(v3, true);
	private static Literal notlit1 = new Literal(v1, false);
	private static Clause clause1 = new Clause();
	private static Clause clause2 = new Clause();
	private static Clause clause3 = new Clause(lit2);
	private static DPLLSolver solver = new DPLLSolver(new TrivialHeuristic());
	private static DIMACSParser dm1 = new DIMACSParser();
	private static DIMACSParser dm2 = new DIMACSParser();
	private static DIMACSParser dm3 = new DIMACSParser();
	private static DIMACSParser dm4 = new DIMACSParser();

	@BeforeClass
	public static void initialize() {
		assignment.put(F.var1, true);
		assignment.put(F.var2, false);
		assignment.put(F.var3, true);
		assignment.put(F.var4, false);

		nnf = new And(new Not(F.var3), new Or(F.var1, F.var2));
		cnf = new And(new Or(new Not(x1), x2), new Or(new Or(x1, x2), x3));
		nonCnf1 = new And(new Or(x1, x2), new Not(new Or(new Or(x1, x2), x3)));
		nonCnf2 = new And(new Or(new Not(x1), x2), new And(new Or(x1, new And(x2, x1)), x3));

		dnf = new Or(new And(new Not(x1), x2), new And(new And(x1, x2), x3));
		nonDnf1 = new Or(new And(new Not(new Not(x1)), x2), new And(new And(x1, x2), x3));
		nonDnf2 = new Or(new And(new Not(x1), x2), new Or(new And(x1, new Or(x2, x1)), x3));
	}

	@Test
	public void testGettersAndSetters() {
		assertEquals(F.var1, new And(F.var1, F.var2).getLeft());
		assertEquals(F.var2, new And(F.var1, F.var2).getRight());
		assertEquals(F.var1, new Or(F.var1, F.var2).getLeft());
		assertEquals(F.var2, new Or(F.var1, F.var2).getRight());
	}

	@Test
	public void testDIMACSL() throws IOException {
		String file = "src/test/java/de/arlab/io/test.cnf";
		Set<Clause> c = new HashSet<>();
		Collections.addAll(c, 
				new Clause(new Literal(new Variable("1")), new Literal(new Variable("2")),new Literal(new Variable("3"))),
				new Clause(new Literal(new Variable("2")), new Literal(new Variable("3")),new Literal(new Variable("4"))),
				new Clause(new Literal(new Variable("5")), new Literal(new Variable("3")),new Literal(new Variable("4"),false)));
		assertEquals(dm1.parse(file), c);
		assertFalse(solver.isSAT(dm2.parse("src/test/resources/dimacs/no/aim-50-1_6-no-1.cnf")));
		assertFalse(solver.isSAT(dm3.parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		assertFalse(solver.isSAT(dm4.parse("src/test/resources/dimacs/no/uuf50-02.cnf")));

	}
}

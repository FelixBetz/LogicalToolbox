package de.arlab.io;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.io.DIMACSParser;
import de.arlab.sat.*;
import de.arlab.sat.heuristics.LeastCommonLiteralHeuristic;
import de.arlab.sat.heuristics.LeastCommonVariableHeuristic;
import de.arlab.sat.heuristics.MostCommonLiteralHeuristic;
import de.arlab.sat.heuristics.MostCommonVariableHeuristic;
import de.arlab.sat.heuristics.TrivialHeuristic;
import de.arlab.sat.heuristics.VariableHeuristic;
import de.arlab.formulas.*;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
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
	private static DPLLSolver lcvsolver = new DPLLSolver(new LeastCommonVariableHeuristic());
	private static DPLLSolver lclsolver = new DPLLSolver(new LeastCommonLiteralHeuristic());
	private static DPLLSolver mcvsolver = new DPLLSolver(new MostCommonVariableHeuristic());
	private static DPLLSolver mclsolver = new DPLLSolver(new MostCommonLiteralHeuristic());
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
	public void testTrivial1() throws IOException {
		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-021.cnf")));

	}
	@Test
	public void testMostLit1() throws IOException {
		assertFalse(mclsolver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-021.cnf")));

	}

	@Test
	public void testMostVar1() throws IOException {
		assertFalse(mcvsolver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-021.cnf")));
	}
	@Test
	public void testTrivial2() throws IOException {
		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-03.cnf")));

	}
	@Test
	public void testMostLit2() throws IOException {
		assertFalse(mclsolver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-03.cnf")));

	}
	@Test
	public void testMostVar2() throws IOException {
		assertFalse(mcvsolver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-03.cnf")));
	}
}
 
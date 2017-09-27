package de.arlab.sat;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.formulas.F;
import de.arlab.cc.CCEncoding;
import de.arlab.formulas.*;
import de.arlab.io.DIMACSParser;
import de.arlab.sat.Clause;
import de.arlab.sat.DPLLSolver;
import de.arlab.sat.Literal;
import de.arlab.sat.heuristics.TrivialHeuristic;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SATTest {

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
	private static Clause clause35 = new Clause(lit2);
	private static Clause clause4 = new Clause();
	private static DPLLSolver solver = new DPLLSolver(new TrivialHeuristic());

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
	public void testClause() {
		clause35.addLiteral(lit3);
		clause35.addLiteral(lit1);
		System.out.println(CCEncoding.atMostOne(clause35.getLiterals()));
		assertTrue(new DPLLSolver(new TrivialHeuristic()).isSAT(Formula.VERUM));
		assertEquals(clause1.getFirstLiteral(), null);	
		clause1.addLiteral(lit1);
		assertEquals(clause1.getFirstLiteral(), lit1);	
		clause1.addLiteral(lit1);
		
		
		clause4.unionWith(clause4);
		assertEquals(clause4, new Clause());
		
		clause4.addLiteral(lit1);
		clause4.addLiteral(lit2);
		assertEquals(clause4.getFirstLiteral(),lit1);
		
		
		clause3.unionWith(clause1);
		assertTrue(clause1.isUnit());
		assertFalse(clause2.isUnit());
		assertFalse(clause1.isEmpty());
		assertTrue(clause2.isEmpty());
		assertTrue(clause3.contains(lit1));
		clause3.removeLiteral(lit2);
		assertFalse(clause3.contains(lit2));
	}

	@Test
	public void testFormula2Clause() {
		Set<Clause> testSet = new HashSet<>();
		assertEquals(Clause.formula2Clauses(t), testSet);
		testSet.add(new Clause());
		assertEquals(Clause.formula2Clauses(f), testSet);
		testSet.clear();
		testSet.add(clause1);
		assertEquals(Clause.formula2Clauses(v1), testSet);
		testSet.clear();
		testSet.add(new Clause(notlit1));
		assertEquals(Clause.formula2Clauses(new Not(v1)), testSet);
		testSet.clear();
		Clause c1 = new Clause();
		Clause c2 = new Clause();
		c1.addLiteral(new Literal(F.var1).negate());
		c1.addLiteral(new Literal(F.var2));
		c2.addLiteral(new Literal(F.var1));
		c2.addLiteral(new Literal(F.var2));
		c2.addLiteral(new Literal(F.var3));
		testSet.add(c1);
		testSet.add(c2);
		assertEquals(Clause.formula2Clauses(cnf), testSet);
	}

	@Test
	public void testClause2Formula() {
		Clause c1 = new Clause();
		Clause c2 = new Clause();
		assertEquals(Clause.clause2Formula(c1), f);
		c1.addLiteral(new Literal(F.var1).negate());
		c1.addLiteral(new Literal(F.var2));
		c2.addLiteral(new Literal(F.var1));
		c2.addLiteral(new Literal(F.var2));
		c2.addLiteral(new Literal(F.var3));
		Set<Clause> testSet = new HashSet<>();
		testSet.add(c1);
		testSet.add(c2);
		assertEquals(Clause.clause2Formula(c1), new Or(F.var2, new Not(F.var1)));
		assertEquals(Clause.clause2Formula(c2), new Or(F.var3, new Or(F.var2, F.var1)));
		assertEquals(Clause.clauses2Formula(testSet),
				new And(new Or(F.var3, new Or(F.var2, F.var1)), new Or(F.var2, new Not(F.var1))));
		assertTrue(solver.isSAT(testSet));
	}

	@Test
	public void testDPLL() {
		Map<Variable, Boolean> m = new HashMap<>();
		assertEquals(solver.getModel(t), m);
		assertEquals(solver.getModel(f), null);
		m.put(F.var1, false);
		m.put(F.var2, true);
		assertEquals(solver.getModel(cnf), m);
		Map<Variable, Boolean> m2 = new HashMap<>();
		assertEquals(solver.getModel(t), m2);
		assertTrue(solver.isSAT(cnf));
		assertFalse(solver.isContradiction(cnf));
		assertTrue(solver.isSAT(cnf));
		assertTrue(solver.isEquivalent(dnf, dnf));
		assertFalse(solver.isContradiction(new Or(x1,new Not(x1))));
		assertTrue(solver.isTautology(new Or(x1,new Not(x1))));
		assertTrue(solver.isTautology(new Or(x1,new Not(x1))));
	}

	@Test
	public void testDIMACSL() throws IOException {

		assertTrue(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/yes/aim-50-1_6-yes1-1.cnf")));
		assertTrue(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/yes/aim-50-1_6-yes1-2.cnf")));
		assertTrue(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/yes/aim-50-1_6-yes1-3.cnf")));
		assertTrue(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/yes/aim-50-1_6-yes1-4.cnf")));

		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/aim-50-1_6-no-1.cnf")));
		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-02.cnf")));
		
//		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-01.cnf")));
//		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-02.cnf")));
//		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-021.cnf")));
//		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-03.cnf")));
//		assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-04.cnf")));
	}
}

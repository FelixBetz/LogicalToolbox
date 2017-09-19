package de.arlab.formulas;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormulaTest {

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

	private Variable v1 = new Variable("v1");
	private Variable v2 = new Variable("v2");
	private Variable v3 = new Variable("v3");

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
	public void testCompare() {
		assertTrue(F.var1.compareTo(F.var2) != 0);
		assertTrue(F.var1.compareTo(F.var1) == 0);
	}

	@Test
	public void testEvaluate() {
		assertTrue(F.verum.evaluate(assignment));
		assertFalse(F.falsum.evaluate(assignment));

		assertTrue(F.var1.evaluate(assignment));
		assertFalse(F.var2.evaluate(assignment));
		assertTrue(F.var3.evaluate(assignment));
		assertFalse(F.var4.evaluate(assignment));
		assertFalse(F.f1.evaluate(assignment));
		assertFalse(F.f2.evaluate(assignment));
		assertTrue(F.f3.evaluate(assignment));
		assertTrue(F.f4.evaluate(assignment));
		assertTrue(F.f5.evaluate(assignment));
		assertFalse(F.f6.evaluate(assignment));
		assertTrue(F.f7.evaluate(assignment));
		assertFalse(F.f8.evaluate(assignment));
		assertTrue(F.f9.evaluate(assignment));
		assertTrue(F.f10.evaluate(assignment));
		assertTrue(F.f11.evaluate(assignment));
		assertFalse(F.f12.evaluate(assignment));
		assertTrue(F.f13.evaluate(assignment));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEvaluateException() {
		F.f14.evaluate(assignment);
	}

	@Test
	public void testSimp() {
		// not
		assertEquals(new Not(t).simplify(), f);
		assertEquals(new Not(f).simplify(), t);
		assertEquals(new Not(new Not(f)).simplify(), f);
		assertEquals(new Not(new Not(t)).simplify(), t);

		// and
		assertEquals(new And(t, t).simplify(), t);
		assertEquals(new And(f, f).simplify(), f);
		assertEquals(new And(x1, x1).simplify(), x1);
		assertEquals(new And(x1, t).simplify(), x1);
		assertEquals(new And(t, x1).simplify(), x1);
		assertEquals(new And(x1, f).simplify(), f);
		assertEquals(new And(f, x1).simplify(), f);
		assertEquals(new And(x1, new Not(f)).simplify(), x1);
		assertEquals(new And(new Not(f), x1).simplify(), x1);
		// or
		assertEquals(new Or(t, t).simplify(), t);
		assertEquals(new Or(f, f).simplify(), f);
		assertEquals(new Or(x1, x1).simplify(), x1);
		assertEquals(new Or(x1, t).simplify(), t);
		assertEquals(new Or(t, x1).simplify(), t);
		assertEquals(new Or(x1, f).simplify(), x1);
		assertEquals(new Or(f, x1).simplify(), x1);
		assertEquals(new Or(x1, new Not(t)).simplify(), x1);
		assertEquals(new Or(new Not(t), x1).simplify(), x1);
		// Variable
		assertEquals(x1.simplify(), x1);
	}

	@Test
	public void testSubstitue() {
		assertEquals(v1.substitute(v1, new Or(x1, x2)), new Or(x1, x2));
		assertEquals(new And(v1, v1).substitute(v1, x1), new And(x1, x1));
		assertEquals(new Or(v1, v1).substitute(v1, x1), new Or(x1, x1));
		assertEquals(new Not(v1).substitute(v1, x1), new Not(x1));
		assertEquals(t.substitute(v1, x1), t);
		assertEquals(f.substitute(v1, x1), f);
	}

	@Test
	public void testSynt() {
		assertTrue(t.syntEqual(t));
		assertFalse(t.syntEqual(f));
		assertTrue(f.syntEqual(f));
		assertFalse(f.syntEqual(t));
		assertTrue(new Not(t).syntEqual(new Not(t)));
		assertFalse(new Not(t).syntEqual(t));
		assertTrue(new And(t, f).syntEqual(new And(t, f)));
		assertFalse(new And(t, f).syntEqual(new And(t, t)));
		assertFalse(new And(t, f).syntEqual(new And(f, f)));
		assertFalse(new And(t, f).syntEqual(t));
		assertTrue(new Or(t, f).syntEqual(new Or(t, f)));
		assertFalse(new Or(t, f).syntEqual(new And(t, f)));
		assertFalse(new Or(t, f).syntEqual(new Or(f, f)));
		assertFalse(new Or(t, f).syntEqual(t));
		assertTrue(x1.syntEqual(x1));
		assertFalse(x1.syntEqual(x2));
	}

	@Test
	public void test24() {
		assertTrue(t.isAtomicFormula());
		assertTrue(f.isAtomicFormula());
		assertFalse(new Not(t).isAtomicFormula());
		assertFalse(nnf.isAtomicFormula());
		assertTrue(t.isLiteral());
		assertTrue(f.isLiteral());
		assertTrue(new Not(t).isLiteral());
		assertFalse(dnf.isLiteral());
		assertTrue(t.isClause());
		assertTrue(f.isClause());
		assertTrue(new Not(t).isClause());
		assertFalse(dnf.isClause());
		assertTrue(new Or(x1, new Or(x2, x3)).isClause());
		assertTrue(t.isMinterm());
		assertTrue(f.isMinterm());
		assertTrue(new Not(t).isMinterm());
		assertFalse(dnf.isMinterm());
		assertTrue(new And(x1, new And(x2, new And(x3, x4))).isMinterm());
	}

	@Test
	public void test25() {
		assertTrue(dnf.isDNF());
		assertTrue(cnf.isCNF());
		assertTrue(nnf.isNNF());
		assertFalse(nonCnf1.isCNF());
		assertFalse(nonCnf2.isCNF());
		assertFalse(nonDnf1.isDNF());
		assertFalse(nonDnf2.isDNF());
		assertFalse(new Not(new Not(t)).isNNF());
	}

	@Test
	public void testNNF() {
		
		assertEquals(t.nnf(), t);
		assertEquals(f.nnf(), f);
		assertEquals(new Not(t).nnf(), f);
		assertEquals(new Not(f).nnf(), t);
		assertEquals(x3.nnf(), x3);
		assertEquals(new Not(x2).nnf(), new Not(x2));
		assertEquals(new Not(new Not(x1)).nnf(), x1);
		assertEquals(new And(v1, v1).nnf(), v1);
		assertEquals(new And(v1, new Not(v3)).nnf(), new And(v1, new Not(v3)));
		assertEquals(nnf.nnf(), nnf);
		assertEquals(new Not(nnf).nnf(), new Or(F.var3, new And(new Not(F.var1), new Not(F.var2))));
	}
	
	@Test
	public void testCNF() {
		assertEquals(cnf.cnf(),cnf);
		assertEquals(t.cnf(),t);
		assertEquals(v1.cnf(),v1);
		assertEquals(new And(v1,v2).cnf(), new And(v1,v2));
		assertEquals(new Or(v1,v2).cnf(), new Or(v1,v2));
		assertEquals(new Or(v1,new And(v2,v3)).cnf(), new And(new Or(v1,v2),new Or(v1,v3)));
		assertEquals(new Or(new And(v1,v2),v3).cnf(), new And(new Or(v1,v3),new Or(v2,v3)));
		assertEquals(new Not(v1).cnf(), new Not(v1));
		assertEquals(new Not(new Or(v1, v2)).cnf(), new And(new Not(v1),new Not(v2)));
	}
	
	@Test
	public void testDNF() {
		assertEquals(dnf.dnf(),dnf);
		assertEquals(t.dnf(),t);
		assertEquals(v1.dnf(),v1);
		assertEquals(new And(v1,v2).dnf(), new And(v1,v2));
		assertEquals(new Or(v1,v2).dnf(), new Or(v1,v2));
		assertEquals(new Or(v1,new And(v2,v3)).dnf(), new Or(v1, new And(v2,v3)));
		assertEquals(new And(v1,new Or(v2,v3)).dnf(), new Or(new And(v1,v2),new And(v1,v3)));
		assertEquals(new And(new Or(v1,v2),v3).dnf(), new Or(new And(v1,v3),new And(v2,v3)));
		assertEquals(new Not(v1).dnf(), new Not(v1));
		assertEquals(new Not(new And(v1, v2)).dnf(), new Or(new Not(v1),new Not(v2)));
		assertEquals(new And(new Not(v1), new Or(v1,v2)).dnf(),new Or(new And(new Not(v1),v1), new And(new Not(v1),v2)));
		

	}

}
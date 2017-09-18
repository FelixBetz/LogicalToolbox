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

  @BeforeClass
  public static void initialize() {
    assignment.put(F.var1, true);
    assignment.put(F.var2, false);
    assignment.put(F.var3, true);
    assignment.put(F.var4, false);

    nnf = new And(new Not(F.var3), new Or(F.var1, F.var2));
    cnf = new And(new Or(new Not(x1), x2), new Or(new Or(x1, x2), x3));
    nonCnf1 = new And(
            new Or(x1, x2),
            new Not(new Or(new Or(x1, x2), x3)));
    nonCnf2 = new And(
            new Or(new Not(x1), x2),
            new And(new Or(x1, new And(x2, x1)), x3));

    dnf = new Or(new And(new Not(x1), x2), new And(new And(x1, x2), x3));
    nonDnf1 = new Or(
            new And(new Not(new Not(x1)), x2),
            new And(new And(x1, x2), x3));
    nonDnf2 = new Or(
            new And(new Not(x1), x2),
            new Or(new And(x1, new Or(x2, x1)), x3));
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
	  assertEquals (new Not(t).simplify(), f);
	  assertEquals (new Not(f).simplify(), t);
	  assertEquals (new Not(new Not(f)).simplify(), f);
	  assertEquals (new Not(new Not(t)).simplify(), t);
  }
  
  @Test
  public void testSynt() {
	  assertTrue (t.syntEqual(t));
	  assertFalse (t.syntEqual(f));
	  assertTrue (f.syntEqual(f));
	  assertFalse (f.syntEqual(t));
	  assertTrue (new Not (t).syntEqual(new Not (t)));
	  assertFalse (new Not (t).syntEqual(t));
	  assertTrue (new And (t, f).syntEqual(new And (t, f)));
	  assertFalse (new And (t, f).syntEqual(new And (t, t)));
	  assertFalse (new And (t, f).syntEqual(new And (f, f)));
	  assertFalse (new And (t, f).syntEqual(t));
	  assertTrue (new Or (t, f).syntEqual(new Or (t, f)));
	  assertFalse (new Or (t, f).syntEqual(new And (t, f)));
	  assertFalse (new Or (t, f).syntEqual(new Or (f, f)));
	  assertFalse (new Or (t, f).syntEqual(t));
	  assertTrue (x1.syntEqual(x1));
	  assertFalse (x1.syntEqual(x2));
  }
  
}
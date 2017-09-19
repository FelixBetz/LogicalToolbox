package de.arlab.sat;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.formulas.And;
import de.arlab.formulas.Formula;
import de.arlab.formulas.Or;
import de.arlab.formulas.Variable;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SATTest {

	private static Literal nnf;
	private static Formula cnf;
	private static Formula nonCnf1;
	private static Formula nonCnf2;
	private static Formula dnf;
	private static Formula nonDnf1;
	private static Formula nonDnf2;

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

	@BeforeClass
	public static void initialize() {
		clause1.addLiteral(lit1);
		clause3.unionWith(clause1);
	}

	@Test
	public void testGettersAndSetters() {
		assertTrue(clause1.isUnit());
		assertFalse(clause2.isUnit());
		assertFalse(clause1.isEmpty());
		assertTrue(clause2.isEmpty());
		assertTrue(clause3.contains(lit1));
		clause3.removeLiteral(lit2);
		assertFalse(clause3.contains(lit2));
	}

}
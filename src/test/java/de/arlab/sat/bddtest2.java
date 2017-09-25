package de.arlab.sat;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.formulas.*;



public class bddtest2 {
	private static BDDManager manager = new BDDManager();
	private static BDDManager manager2 = new BDDManager();
	private static BDDNode n1 = new BDDNode(new Variable("t"), 1, -1);
	private static BDDNode n2 = new BDDNode(new Variable("s"), 2, -1);
	
	private static Variable v1 = new Variable("v1");
	private static Variable v2 = new Variable("v2");
	private static Variable v3 = new Variable("v3");
	
	
	@Test
	public void testBdd() {
		assertEquals(manager.lookupUnique(n1), 2);
		assertEquals(manager.lookupUnique(n2), 3);
		assertEquals(manager.expandNode(2), n1);
		assertEquals(manager.expandNode(3), n2);
		assertTrue(manager2.isSAT(new Or(F.var1, (new Not(F.var2)))));
		
		assertTrue(new BDDManager().isSAT(new And(v1,v2)));
		assertFalse(new BDDManager().isSAT(new And(v1,new Not(v1))));
		
		assertFalse(new BDDManager().isSAT(new And(new Or(v1,v2), new And(v1, new Not(v1)))));
		assertTrue(new BDDManager().isSAT(new And(new Or(v1,v2), new Or(v1, new Not(v1)))));
		assertFalse(new BDDManager().isSAT(new And(new Or(new And(v1, new Not(v1)),v2), new Or(new And(v3, new Not(v3)),new Not(v2)))));
		
		
		BDDManager man1 = new BDDManager();
		//man1.mkBDD(new Not(new And(new Not(v1),v2)));
		//assertEquals(man1.toFormula(new Not(new And(new Not(v1),v2))),new Not(new And(new Not(v1),v2)));
	
		//man1.mkBDD(new Or(v1,v2));
		//assertEquals(man1.toFormula(new Or(v1,v2)),new Or(v1,v2));
		//System.out.println(man1.getModel(new Not(new And(new Not(v1),v2))));
		//System.out.println(man1.getModel(new And(new Not(v1),v2)));
		//System.out.println(man1.getModel(new Not(v1)));
		System.out.println(man1.getModel(new And(v1,new Not(v2))));
		System.out.println(man1.getModel(new And(new Not(v1),v2)));
	}
}

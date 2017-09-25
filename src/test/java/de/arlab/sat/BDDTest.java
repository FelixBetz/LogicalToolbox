package de.arlab.sat;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.formulas.*;

public class BDDTest {
	private static BDDManager manager = new BDDManager();
	private static BDDManager manager2 = new BDDManager();
	private static BDDNode n1 = new BDDNode(new Variable("t"), 1, -1);
	private static BDDNode n2 = new BDDNode(new Variable("s"), 2, -1);
	@Test
	public void testClause() {
		assertEquals(manager.lookupUnique(n1), 2);
		assertEquals(manager.lookupUnique(n2), 3);
		assertEquals(manager.expandNode(2), n1);
		assertEquals(manager.expandNode(3), n2);
		int a = manager2.mkBDD(new Not(new Variable("c")));
		System.out.println(a);
	}
}

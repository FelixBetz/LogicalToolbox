package de.arlab.sat;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.formulas.*;
import de.arlab.formulas.parser.Parser;

public class BDDTest {
	private static BDDManager manager = new BDDManager();
	private static BDDManager manager2 = new BDDManager();
	private static BDDNode n1 = new BDDNode(new Variable("t"), 1, -1);
	private static BDDNode n2 = new BDDNode(new Variable("s"), 2, -1);
	
	
	
	@Test
	public void testClause() {
		System.out.println(manager2.getModel(new Or(new Not(F.var1), F.var2)));
	}
}

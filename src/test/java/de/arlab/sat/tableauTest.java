package de.arlab.sat;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


import de.arlab.formulas.*;
import de.arlab.formulas.parser.Parser;
import de.arlab.tableaus.Tableau;
import de.arlab.tableaus.TableauNode;

public class tableauTest {
	private static Variable v1 = new Variable("v1");
	private static Variable v2 = new Variable("v2");
	private static Variable v3 = new Variable("v3");
	
	
	
	@Test
	public void testTableau() {
		System.out.println(new Tableau(new And(v1,v2)).toString());
		System.out.println(new Tableau(v1));
		System.out.println(new Tableau(new Or(v1,new Or(v2, v3))).toString());
		System.out.println(new Tableau(new Or(v1 , new And(v1,v2))).toString());
		
		assertFalse(new Tableau(v1).isClosed());
		assertTrue(new Tableau(new And(v1, new Not(v1))).isClosed());
		assertFalse(new Tableau(new And(v1, new Not(v2))).isClosed());
	}
}

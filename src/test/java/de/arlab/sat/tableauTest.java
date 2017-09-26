package de.arlab.sat;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;


import de.arlab.formulas.*;
import de.arlab.formulas.parser.Parser;
import de.arlab.tableaus.Tableau;
import de.arlab.tableaus.TableauManager;
import de.arlab.tableaus.TableauNode;

public class tableauTest {
	private static Variable v1 = new Variable("v1");
	private static Variable v2 = new Variable("v2");
	private static Variable v3 = new Variable("v3");
	
	
	
	@Test
	public void testTableau() {
		/*System.out.println(new Tableau(new And(v1,v2)).toString());
		System.out.println(new Tableau(v1));
		System.out.println(new Tableau(new Or(v1,new Or(v2, v3))).toString());
		System.out.println(new Tableau(new Or(v1 , new And(v1,v2))).toString());*/
		
		assertFalse(new Tableau(v1).isClosed());
		assertTrue(new Tableau(new And(v1, new Not(v1))).isClosed());
		assertFalse(new Tableau(new And(v1, new Not(v2))).isClosed());
		
		System.out.println(new Tableau(new And(v1, new Not(v2))).getModel());
		System.out.println(new Tableau(new Or(v1, new Not(v2))).getModel());
		
		assertTrue(new TableauManager().isSAT(Formula.VERUM));
		assertFalse(new TableauManager().isSAT(Formula.FALSUM));
		assertTrue(new TableauManager().isSAT(new And(v1,v2)));
		assertFalse(new TableauManager().isSAT(new And(v1,new Not(v1))));
		
		Map<Variable, Boolean> model = new HashMap<>();
		
		assertEquals(new TableauManager().getModel(Formula.VERUM), model);
		assertEquals(new TableauManager().getModel(Formula.FALSUM), model);
		model.put(v1, true);
		model.put(v2, true);
		assertEquals(new TableauManager().getModel(new And(v1,v2)),model);
		model.clear();
		assertEquals(new TableauManager().getModel(new And(v1,new Not(v1))),model);
		model.clear();
		model.put(v1, true);
		model.put(v2, false);
		assertEquals(new TableauManager().getModel(new And(v1,new Not(v2))),model);
		model.clear();
		model.put(v1, true);
		assertEquals(new TableauManager().getModel(new Or(v1,v2)),model);
		model.clear();
		model.put(v1,true);
		assertEquals(new TableauManager().getModel(new Or(v1,new Not(v1))),model);
		model.clear();
		model.put(v1, false);
		assertEquals(new TableauManager().getModel(new Or(new Not(v1), v2)),model);
		model.clear();
		model.put(v1, false);
		assertEquals(new TableauManager().getModel(new Not(v1)),model);
		model.clear();
		model.put(v1, true);
		model.put(v2, true);
		model.put(v3, true);
		assertEquals(new TableauManager().getModel(new And(v1, new And(v2,v3))),model);
		model.clear();
		model.put(v2, true);
		assertEquals(new TableauManager().getModel(new Or( new And(v1,new Not(v1)), v2)),model);
	}
}

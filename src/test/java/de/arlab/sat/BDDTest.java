package de.arlab.sat;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.formulas.*;
import de.arlab.formulas.parser.Parser;
import de.arlab.io.DIMACSParser;

public class BDDTest {
	private static BDDManager manager = new BDDManager();
	
	
	
	@Test
	public void testClause() throws IOException {
		assertTrue(manager.isSAT(new DIMACSParser().parse("test.cnf")));
	}
}

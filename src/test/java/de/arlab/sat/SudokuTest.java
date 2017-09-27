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
import de.arlab.sudoku.SudokuEncoding;

public class SudokuTest {
	private static BDDManager manager = new BDDManager();
	
	
	
	@Test
	public void testClause() throws IOException {
		System.out.println(SudokuEncoding.atLeastOneNumEachEntry(2));
		System.out.println(SudokuEncoding.eachNumAtMostOnceEachRow(2));
	}
}

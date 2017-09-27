package de.arlab.sat;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.formulas.*;
import de.arlab.formulas.parser.Parser;
import de.arlab.io.DIMACSParser;
import de.arlab.io.SudokuParser;
import de.arlab.sat.heuristics.MostCommonLiteralHeuristic;
import de.arlab.sat.heuristics.MostCommonVariableHeuristic;
import de.arlab.sudoku.Sudoku;
import de.arlab.sudoku.SudokuEncoding;
import de.arlab.tableaus.Tableau;

public class SudokuTest {
	private static BDDManager manager = new BDDManager();
	
	
	
	@Test
	public void testClause() throws IOException {
		Sudoku spar = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
//		SudokuEncoding.print(spar);
		Set<Clause> set = SudokuEncoding.encode(spar);
		System.out.println(set);
		Map<Variable, Boolean> map = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set);
		System.out.println(map);
		Sudoku out = SudokuEncoding.decode(map, 1);
		SudokuEncoding.print(out);
	}
}

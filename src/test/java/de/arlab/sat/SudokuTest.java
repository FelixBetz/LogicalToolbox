package de.arlab.sat;

import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import de.arlab.bdd.BDDManager;
import de.arlab.bdd.BDDNode;
import de.arlab.cc.CCEncoding;
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
	//	Sudoku spar = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
	//	System.out.println(SudokuEncoding.eachNumAtMostOnceEachRow(1));
//		Set<Clause> set = SudokuEncoding.encode(spar);
//		System.out.println(set);
//		Map<Variable, Boolean> map = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set);
//		System.out.println(map);
//		Sudoku out = SudokuEncoding.decode(map, 2);
//		SudokuEncoding.print(out);
		Clause c = new Clause();
		Set<Literal> set = new HashSet<>();
		set.add(new Literal(new Variable("1"),false));
		set.add(new Literal(new Variable("2"),false));
		set.add(new Literal(new Variable("3"),false));
		System.out.println(CCEncoding.atMostOne(set));

	}
}

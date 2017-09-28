package de.arlab.sat;

import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import de.arlab.sat.heuristics.LeastCommonLiteralHeuristic;
import de.arlab.sat.heuristics.LeastCommonVariableHeuristic;
import de.arlab.sat.heuristics.MostCommonLiteralHeuristic;
import de.arlab.sat.heuristics.MostCommonVariableHeuristic;
import de.arlab.sudoku.Sudoku;
import de.arlab.sudoku.SudokuEncoding;
import de.arlab.tableaus.Tableau;
import de.arlab.tableaus.TableauManager;

public class SudokuTest {
	private static BDDManager manager = new BDDManager();

	@Test
	public void testDpllMostCommonLiteralHeuristic() throws IOException {
		// Sudoku1
		Sudoku spar1 = new SudokuParser().parse("src/test/resources/sudoku/example01.txt");
		Sudoku spar1Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample01.txt");
		Set<Clause> set1 = SudokuEncoding.encode(spar1);
		Map<Variable, Boolean> map1 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set1);
		assertEquals(SudokuEncoding.decode(map1, 3), spar1Sol);

		// Sudoku2
		Sudoku spar2 = new SudokuParser().parse("src/test/resources/sudoku/example02.txt");
		Sudoku spar2Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample02.txt");
		Set<Clause> set2 = SudokuEncoding.encode(spar2);
		Map<Variable, Boolean> map2 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set2);
		assertEquals(SudokuEncoding.decode(map2, 3), spar2Sol);

		// Sudoku3
		Sudoku spar3 = new SudokuParser().parse("src/test/resources/sudoku/example03.txt");
		Sudoku spar3Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample03.txt");
		Set<Clause> set3 = SudokuEncoding.encode(spar3);
		Map<Variable, Boolean> map3 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set3);
		assertEquals(SudokuEncoding.decode(map3, 3), spar3Sol);

		// Sudoku4
		Sudoku spar4 = new SudokuParser().parse("src/test/resources/sudoku/example04.txt");
		Sudoku spar4Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample04.txt");
		Set<Clause> set4 = SudokuEncoding.encode(spar4);
		Map<Variable, Boolean> map4 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set4);
		assertEquals(SudokuEncoding.decode(map4, 3), spar4Sol);

		// Sudoku5
		Sudoku spar5 = new SudokuParser().parse("src/test/resources/sudoku/example05.txt");
		Sudoku spar5Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample05.txt");
		Set<Clause> set5 = SudokuEncoding.encode(spar5);
		Map<Variable, Boolean> map5 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set5);
		assertEquals(SudokuEncoding.decode(map5, 3), spar5Sol);

		// Sudoku6
		Sudoku spar6 = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
		Sudoku spar6Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample06.txt");
		Set<Clause> set6 = SudokuEncoding.encode(spar6);
		Map<Variable, Boolean> map6 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set6);
		assertEquals(SudokuEncoding.decode(map6, 2), spar6Sol);

		// Sudoku7
		Sudoku spar7 = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
		Sudoku spar7Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample07.txt");
		Set<Clause> set7 = SudokuEncoding.encode(spar7);
		Map<Variable, Boolean> map7 = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set7);
		assertEquals(SudokuEncoding.decode(map7, 1), spar7Sol);
	}
	
	@Test
	public void testDpllMostCommonVariableHeuristic() throws IOException {
		// Sudoku1
		Sudoku spar1 = new SudokuParser().parse("src/test/resources/sudoku/example01.txt");
		Sudoku spar1Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample01.txt");
		Set<Clause> set1 = SudokuEncoding.encode(spar1);
		Map<Variable, Boolean> map1 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set1);
		assertEquals(SudokuEncoding.decode(map1, 3), spar1Sol);

		// Sudoku2
		Sudoku spar2 = new SudokuParser().parse("src/test/resources/sudoku/example02.txt");
		Sudoku spar2Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample02.txt");
		Set<Clause> set2 = SudokuEncoding.encode(spar2);
		Map<Variable, Boolean> map2 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set2);
		assertEquals(SudokuEncoding.decode(map2, 3), spar2Sol);

		// Sudoku3
		Sudoku spar3 = new SudokuParser().parse("src/test/resources/sudoku/example03.txt");
		Sudoku spar3Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample03.txt");
		Set<Clause> set3 = SudokuEncoding.encode(spar3);
		Map<Variable, Boolean> map3 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set3);
		assertEquals(SudokuEncoding.decode(map3, 3), spar3Sol);

		// Sudoku4
		Sudoku spar4 = new SudokuParser().parse("src/test/resources/sudoku/example04.txt");
		Sudoku spar4Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample04.txt");
		Set<Clause> set4 = SudokuEncoding.encode(spar4);
		Map<Variable, Boolean> map4 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set4);
		assertEquals(SudokuEncoding.decode(map4, 3), spar4Sol);

		// Sudoku5
		Sudoku spar5 = new SudokuParser().parse("src/test/resources/sudoku/example05.txt");
		Sudoku spar5Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample05.txt");
		Set<Clause> set5 = SudokuEncoding.encode(spar5);
		Map<Variable, Boolean> map5 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set5);
		assertEquals(SudokuEncoding.decode(map5, 3), spar5Sol);

		// Sudoku6
		Sudoku spar6 = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
		Sudoku spar6Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample06.txt");
		Set<Clause> set6 = SudokuEncoding.encode(spar6);
		Map<Variable, Boolean> map6 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set6);
		assertEquals(SudokuEncoding.decode(map6, 2), spar6Sol);

		// Sudoku7
		Sudoku spar7 = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
		Sudoku spar7Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample07.txt");
		Set<Clause> set7 = SudokuEncoding.encode(spar7);
		Map<Variable, Boolean> map7 = new DPLLSolver(new MostCommonVariableHeuristic()).getModel(set7);
		assertEquals(SudokuEncoding.decode(map7, 1), spar7Sol);

	}
	
	@Test
	public void testDpllLeastCommonVariableHeuristic() throws IOException {
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		//  Sudokus 1-5 (base == 3) können nicht in anehmbarer Zeit gelöst werden
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		/* 
		// Sudoku1
		Sudoku spar1 = new SudokuParser().parse("src/test/resources/sudoku/example01.txt");
		Sudoku spar1Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample01.txt");
		Set<Clause> set1 = SudokuEncoding.encode(spar1);
		Map<Variable, Boolean> map1 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set1);
		assertEquals(SudokuEncoding.decode(map1, 3), spar1Sol);

		// Sudoku2
		Sudoku spar2 = new SudokuParser().parse("src/test/resources/sudoku/example02.txt");
		Sudoku spar2Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample02.txt");
		Set<Clause> set2 = SudokuEncoding.encode(spar2);
		Map<Variable, Boolean> map2 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set2);
		assertEquals(SudokuEncoding.decode(map2, 3), spar2Sol);

		// Sudoku3
		Sudoku spar3 = new SudokuParser().parse("src/test/resources/sudoku/example03.txt");
		Sudoku spar3Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample03.txt");
		Set<Clause> set3 = SudokuEncoding.encode(spar3);
		Map<Variable, Boolean> map3 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set3);
		assertEquals(SudokuEncoding.decode(map3, 3), spar3Sol);

		// Sudoku4
		Sudoku spar4 = new SudokuParser().parse("src/test/resources/sudoku/example04.txt");
		Sudoku spar4Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample04.txt");
		Set<Clause> set4 = SudokuEncoding.encode(spar4);
		Map<Variable, Boolean> map4 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set4);
		assertEquals(SudokuEncoding.decode(map4, 3), spar4Sol);

		// Sudoku5
		Sudoku spar5 = new SudokuParser().parse("src/test/resources/sudoku/example05.txt");
		Sudoku spar5Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample05.txt");
		Set<Clause> set5 = SudokuEncoding.encode(spar5);
		Map<Variable, Boolean> map5 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set5);
		assertEquals(SudokuEncoding.decode(map5, 3), spar5Sol);
		*/
		// Sudoku6
		Sudoku spar6 = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
		Sudoku spar6Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample06.txt");
		Set<Clause> set6 = SudokuEncoding.encode(spar6);
		Map<Variable, Boolean> map6 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set6);
		assertEquals(SudokuEncoding.decode(map6, 2), spar6Sol);

		// Sudoku7
		Sudoku spar7 = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
		Sudoku spar7Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample07.txt");
		Set<Clause> set7 = SudokuEncoding.encode(spar7);
		Map<Variable, Boolean> map7 = new DPLLSolver(new LeastCommonVariableHeuristic()).getModel(set7);
		assertEquals(SudokuEncoding.decode(map7, 1), spar7Sol);

	}
	@Test
	public void testDpllLeastCommonLiteralHeuristic() throws IOException {
		// Sudoku1
		Sudoku spar1 = new SudokuParser().parse("src/test/resources/sudoku/example01.txt");
		Sudoku spar1Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample01.txt");
		Set<Clause> set1 = SudokuEncoding.encode(spar1);
		Map<Variable, Boolean> map1 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set1);
		assertEquals(SudokuEncoding.decode(map1, 3), spar1Sol);

		// Sudoku2
		Sudoku spar2 = new SudokuParser().parse("src/test/resources/sudoku/example02.txt");
		Sudoku spar2Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample02.txt");
		Set<Clause> set2 = SudokuEncoding.encode(spar2);
		Map<Variable, Boolean> map2 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set2);
		assertEquals(SudokuEncoding.decode(map2, 3), spar2Sol);

		// Sudoku3
		Sudoku spar3 = new SudokuParser().parse("src/test/resources/sudoku/example03.txt");
		Sudoku spar3Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample03.txt");
		Set<Clause> set3 = SudokuEncoding.encode(spar3);
		Map<Variable, Boolean> map3 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set3);
		assertEquals(SudokuEncoding.decode(map3, 3), spar3Sol);

		// Sudoku4
		Sudoku spar4 = new SudokuParser().parse("src/test/resources/sudoku/example04.txt");
		Sudoku spar4Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample04.txt");
		Set<Clause> set4 = SudokuEncoding.encode(spar4);
		Map<Variable, Boolean> map4 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set4);
		assertEquals(SudokuEncoding.decode(map4, 3), spar4Sol);

		// Sudoku5
		Sudoku spar5 = new SudokuParser().parse("src/test/resources/sudoku/example05.txt");
		Sudoku spar5Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample05.txt");
		Set<Clause> set5 = SudokuEncoding.encode(spar5);
		Map<Variable, Boolean> map5 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set5);
		assertEquals(SudokuEncoding.decode(map5, 3), spar5Sol);

		// Sudoku6
		Sudoku spar6 = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
		Sudoku spar6Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample06.txt");
		Set<Clause> set6 = SudokuEncoding.encode(spar6);
		Map<Variable, Boolean> map6 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set6);
		assertEquals(SudokuEncoding.decode(map6, 2), spar6Sol);

		// Sudoku7
		Sudoku spar7 = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
		Sudoku spar7Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample07.txt");
		Set<Clause> set7 = SudokuEncoding.encode(spar7);
		Map<Variable, Boolean> map7 = new DPLLSolver(new LeastCommonLiteralHeuristic()).getModel(set7);
		assertEquals(SudokuEncoding.decode(map7, 1), spar7Sol);

	}
	@Test
	public void testTableau() throws IOException {
		
		/*
		// Sudoku1
		Sudoku spar1 = new SudokuParser().parse("src/test/resources/sudoku/example01.txt");
		Sudoku spar1Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample01.txt");
		Set<Clause> set1 = SudokuEncoding.encode(spar1);
		Map<Variable, Boolean> map1 = new TableauManager().getModel(set1);
		assertEquals(SudokuEncoding.decode(map1, 3), spar1Sol);

		// Sudoku2
		Sudoku spar2 = new SudokuParser().parse("src/test/resources/sudoku/example02.txt");
		Sudoku spar2Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample02.txt");
		Set<Clause> set2 = SudokuEncoding.encode(spar2);
		Map<Variable, Boolean> map2 = new TableauManager().getModel(set2);
		assertEquals(SudokuEncoding.decode(map2, 3), spar2Sol);

		// Sudoku3
		Sudoku spar3 = new SudokuParser().parse("src/test/resources/sudoku/example03.txt");
		Sudoku spar3Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample03.txt");
		Set<Clause> set3 = SudokuEncoding.encode(spar3);
		Map<Variable, Boolean> map3 = new TableauManager().getModel(set3);
		assertEquals(SudokuEncoding.decode(map3, 3), spar3Sol);

		// Sudoku4
		Sudoku spar4 = new SudokuParser().parse("src/test/resources/sudoku/example04.txt");
		Sudoku spar4Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample04.txt");
		Set<Clause> set4 = SudokuEncoding.encode(spar4);
		Map<Variable, Boolean> map4 = new TableauManager().getModel(set4);
		assertEquals(SudokuEncoding.decode(map4, 3), spar4Sol);

		// Sudoku5
		Sudoku spar5 = new SudokuParser().parse("src/test/resources/sudoku/example05.txt");
		Sudoku spar5Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample05.txt");
		Set<Clause> set5 = SudokuEncoding.encode(spar5);
		Map<Variable, Boolean> map5 = new TableauManager().getModel(set5);
		assertEquals(SudokuEncoding.decode(map5, 3), spar5Sol);

		// Sudoku6
		Sudoku spar6 = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
		Sudoku spar6Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample06.txt");
		Set<Clause> set6 = SudokuEncoding.encode(spar6);
		Map<Variable, Boolean> map6 = new TableauManager().getModel(set6);
		assertEquals(SudokuEncoding.decode(map6, 2), spar6Sol);
*/
		// Sudoku7
		Sudoku spar7 = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
		Sudoku spar7Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample07.txt");
		Set<Clause> set7 = SudokuEncoding.encode(spar7);
		Map<Variable, Boolean> map7 = new TableauManager().getModel(set7);
		assertEquals(SudokuEncoding.decode(map7, 1), spar7Sol);

	}
	
	@Test
	public void testBdd() throws IOException {
		
		/*
		// Sudoku1
		Sudoku spar1 = new SudokuParser().parse("src/test/resources/sudoku/example01.txt");
		Sudoku spar1Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample01.txt");
		Set<Clause> set1 = SudokuEncoding.encode(spar1);
		Map<Variable, Boolean> map1 = new BDDManager().getModel(set1);
		assertEquals(SudokuEncoding.decode(map1, 3), spar1Sol);

		// Sudoku2
		Sudoku spar2 = new SudokuParser().parse("src/test/resources/sudoku/example02.txt");
		Sudoku spar2Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample02.txt");
		Set<Clause> set2 = SudokuEncoding.encode(spar2);
		Map<Variable, Boolean> map2 = new BDDManager().getModel(set2);
		assertEquals(SudokuEncoding.decode(map2, 3), spar2Sol);

		// Sudoku3
		Sudoku spar3 = new SudokuParser().parse("src/test/resources/sudoku/example03.txt");
		Sudoku spar3Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample03.txt");
		Set<Clause> set3 = SudokuEncoding.encode(spar3);
		Map<Variable, Boolean> map3 = new BDDManager().getModel(set3);
		assertEquals(SudokuEncoding.decode(map3, 3), spar3Sol);

		// Sudoku4
		Sudoku spar4 = new SudokuParser().parse("src/test/resources/sudoku/example04.txt");
		Sudoku spar4Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample04.txt");
		Set<Clause> set4 = SudokuEncoding.encode(spar4);
		Map<Variable, Boolean> map4 = new BDDManager().getModel(set4);
		assertEquals(SudokuEncoding.decode(map4, 3), spar4Sol);

		// Sudoku5
		Sudoku spar5 = new SudokuParser().parse("src/test/resources/sudoku/example05.txt");
		Sudoku spar5Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample05.txt");
		Set<Clause> set5 = SudokuEncoding.encode(spar5);
		Map<Variable, Boolean> map5 = new BDDManager().getModel(set5);
		assertEquals(SudokuEncoding.decode(map5, 3), spar5Sol);
*/
		// Sudoku6
		Sudoku spar6 = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
		Sudoku spar6Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample06.txt");
		Set<Clause> set6 = SudokuEncoding.encode(spar6);
		Map<Variable, Boolean> map6 = new BDDManager().getModel(set6);
		assertEquals(SudokuEncoding.decode(map6, 2), spar6Sol);

		// Sudoku7
		Sudoku spar7 = new SudokuParser().parse("src/test/resources/sudoku/example07.txt");
		Sudoku spar7Sol = new SudokuParser().parse("src/test/resources/sudoku/solutionExample07.txt");
		Set<Clause> set7 = SudokuEncoding.encode(spar7);
		Map<Variable, Boolean> map7 = new BDDManager().getModel(set7);
		assertEquals(SudokuEncoding.decode(map7, 1), spar7Sol);

	}
	
}

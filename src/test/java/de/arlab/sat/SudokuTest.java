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
import de.arlab.sat.heuristics.MostCommonLiteralHeuristic;
import de.arlab.sat.heuristics.MostCommonVariableHeuristic;
import de.arlab.sudoku.Sudoku;
import de.arlab.sudoku.SudokuEncoding;
import de.arlab.tableaus.Tableau;
import de.arlab.tableaus.TableauManager;

public class SudokuTest {
	private static BDDManager manager = new BDDManager();
	
	
	
	@Test
	public void testClause() throws IOException {
//		System.out.println(SudokuEncoding.atLeastOneNumEachEntry(2));
	Sudoku spar = new SudokuParser().parse("src/test/resources/sudoku/example06.txt");
	///System.out.println(SudokuEncoding.eachNumAtMostOnceEachRow(1));
		Set<Clause> set = SudokuEncoding.encode(spar);
//		System.out.println(set.size());
		Map<Variable, Boolean>  map = new DPLLSolver(new MostCommonLiteralHeuristic()).getModel(set);
		Map<Variable, Boolean>  map2 = new BDDManager().getModel(set);
//		System.out.println(map);
		Sudoku out = SudokuEncoding.decode(map, 2);
		SudokuEncoding.print(out);
//		Clause c = new Clause();
//		Set<Literal> set = new HashSet<>();
//		set.add(new Literal(new Variable("1"),true));
//		List<Literal> list = new ArrayList<>();
//		list.add(new Literal(1));
//		list.add(new Literal(2));
//		list.add(new Literal(3));
//		list.add(new Literal(4));
//		System.out.println(list);
//		System.out.println(list.subList(, list.size()));
//		System.out.println(CCEncoding.atMostOne(set));

	}
}

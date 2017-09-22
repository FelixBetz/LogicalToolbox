package de.arlab.formulas;
import org.junit.Test;
import de.arlab.io.DIMACSParser;
import de.arlab.sat.DPLLSolver;
import de.arlab.sat.heuristics.LeastCommonLiteralHeuristic;
import de.arlab.sat.heuristics.LeastCommonVariableHeuristic;
import de.arlab.sat.heuristics.MostCommonLiteralHeuristic;
import de.arlab.sat.heuristics.MostCommonVariableHeuristic;
import de.arlab.sat.heuristics.TrivialHeuristic;


import static org.junit.Assert.assertFalse;

import java.io.IOException;

public class benchmark {
	private static DPLLSolver solver1 = new DPLLSolver(new TrivialHeuristic());
	private static DPLLSolver solver2 = new DPLLSolver(new LeastCommonLiteralHeuristic());
	private static DPLLSolver solver3 = new DPLLSolver(new LeastCommonVariableHeuristic());
	private static DPLLSolver solver4 = new DPLLSolver(new MostCommonLiteralHeuristic());
	private static DPLLSolver solver5 = new DPLLSolver(new MostCommonVariableHeuristic());
	
	@Test
	public void benchTrivial() throws IOException {
		assertFalse(solver1.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
	}
	@Test
	public void benchLeastCommonLiteralHeuristic() throws IOException {
		assertFalse(solver2.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
	}
	@Test
	public void LeastCommonVariableHeuristic() throws IOException {
		assertFalse(solver3.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
	}
	@Test
	public void benchMostCommonLiteralHueristic() throws IOException {
		assertFalse(solver4.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
	}
	@Test
	public void MostCommonVariableHeuristic() throws IOException {
		assertFalse(solver5.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
	}
	
	
	//@Test
	//public void testDIMACSL() throws IOException {

		
		//assertFalse(solver1.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		//assertFalse(solver2.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		//assertFalse(solver3.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		//assertFalse(solver4.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		//assertFalse(solver5.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/no/uuf50-01.cnf")));
		
		//assertFalse(solver1.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-01.cnf")));
		//assertFalse(solver2.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-01.cnf")));
		//assertFalse(solver3.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-01.cnf")));
		//assertFalse(solver4.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-01.cnf")));
		//assertFalse(solver5.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-01.cnf")));
		
		
		
		
		//assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-02.cnf")));
		//assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-021.cnf")));
		//assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-03.cnf")));
		//assertFalse(solver.isSAT(new DIMACSParser().parse("src/test/resources/dimacs/benchmarks/uuf100-04.cnf")));
	//}
}

package de.arlab.formulas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import de.arlab.io.DIMACSParser;
import de.arlab.sat.Clause;
import de.arlab.sat.DPLLSolver;
import de.arlab.sat.Literal;
import de.arlab.sat.Solver;
import de.arlab.sat.heuristics.TrivialHeuristic;

public class Temp {
	public static void main(String[] args) throws IOException {
		String fileName = "test.cnf";
		Solver solver = new DPLLSolver(new TrivialHeuristic());
		DIMACSParser dm2 = new DIMACSParser();
		System.out.println(solver.isSAT(dm2.parse("src/test/resources/dimacs/no/aim-50-1_6-no-1.cnf")));
	}
}

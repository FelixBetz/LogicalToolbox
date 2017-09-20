package de.arlab.formulas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import de.arlab.io.DIMACSParser;
import de.arlab.sat.Clause;
import de.arlab.sat.DPLLSolver;
import de.arlab.sat.heuristics.TrivialHeuristic;

public class Temp {
	public static void main(String[] args) throws IOException {
		String fileName = "aim-50-1_6-yes1-1.cnf";
		DIMACSParser p = new DIMACSParser();
		Set<Clause> c = p.parse(fileName);
		Iterator<Clause> it = c.iterator();
		DPLLSolver solver = new DPLLSolver(new TrivialHeuristic());
		System.out.println(solver.getModel(c));
	}
}

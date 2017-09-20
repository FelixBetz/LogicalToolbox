package de.arlab.io;

import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Parser for SAT instances in the DIMACS format.
 *
 * Each instance will allow only one call to parse, so you should create a new
 * Parser for each file you want to parse.
 *
 * Reference: "Satisfiability Suggested Format"
 */
public final class DIMACSParser {
	private int numVars = -1;
	private int numClauses = -1;
	private final Set<Clause> clauses = new HashSet<>();
	private boolean parsed = false;

	/**
	 * Returns the number of variables from the latest parse.
	 * 
	 * @return the number of variables
	 * @throws IllegalStateException
	 *             if {@link #parse(String)} has not been called before
	 */
	public int getNumVars() {
		if (!parsed)
			throw new IllegalStateException("Please call 'parse' first.");
		return numVars;
	}

	/**
	 * Returns the number of clauses from the latest parse.
	 * 
	 * @return the number of clauses
	 * @throws IllegalStateException
	 *             if {@link #parse(String)} has not been called before
	 */
	public int getNumClauses() {
		if (!parsed)
			throw new IllegalStateException("Please call 'parse' first.");
		return numClauses;
	}

	/**
	 * Returns the set of clauses from the latest parse.
	 * 
	 * @return the parsed set of clauses
	 * @throws IllegalStateException
	 *             if {@link #parse(String)} has not been called before
	 */
	public Set<Clause> getClauses() {
		if (!parsed)
			throw new IllegalStateException("Please call 'parse' first.");
		return clauses;
	}

	/**
	 * Parses a given DIMACS file and returns the corresponding set of clauses.
	 * 
	 * @param file
	 *            the DIMACS file
	 * @return the set of clauses parsed from the given DIMACS file
	 * @throws IllegalStateException
	 *             if the method was already called
	 * @throws IOException
	 *             if there was a problem with the file
	 */
	public Set<Clause> parse(final String file) throws IOException {
		if (parsed)
			throw new IllegalStateException("Please create a new DIMACSParser for each file you want to parse.");
		parsed = true;
		Scanner scan = new Scanner(new File(file));
		while (scan.hasNextLine()) {
			String[] line = scan.nextLine().split("\\s");
			if (!line[0].equals("c") && !line[0].equals("p")) {
				Clause c = line2clause(line);
				clauses.add(c);
			}
		}
		return clauses;
	}

	private Clause line2clause(String[] line) {
		Clause c = new Clause();
		Boolean phase = true;
		for (String string : line) {
			if (!string.equals("0")) {
				if (string.substring(0, 1).equals("-")) {
					phase = false;
					string = string.substring(1);
				}
				c.addLiteral(new Literal(new Variable(string), phase));
			}
		}
		return c;
	}
}
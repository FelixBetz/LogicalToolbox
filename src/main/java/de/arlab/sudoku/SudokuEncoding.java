package de.arlab.sudoku;

import de.arlab.cc.CCEncoding;
import de.arlab.formulas.Variable;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.util.ToBeImplementedException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Encoding of Sudoku as SAT instance.
 */
public class SudokuEncoding {

	/**
	 * Encodes a given sudoku instance as SAT instance.
	 * 
	 * @param sudoku
	 *            the sudoku instance
	 * @return a SAT instance representing the given sudoku instance
	 */
	public static Set<Clause> encode(final Sudoku sudoku) {
		final Set<Clause> clauses = new HashSet<>();
		final int base = sudoku.base();
		clauses.addAll(atLeastOneNumEachEntry(base));
		clauses.addAll(eachNumAtMostOnceEachRow(base));
		clauses.addAll(eachNumAtMostOnceEachCol(base));
		clauses.addAll(eachNumAtMostOnceEachSubGrid(base));
		clauses.addAll(presetEntries(sudoku));
		return clauses;
	}

	/**
	 * Creates a variable name for a given position and the number of the entry.
	 * 
	 * @param row
	 *            row of the entry
	 * @param col
	 *            column of the entry
	 * @param val
	 *            number of the entry
	 * @return variable name in the form "x_y_z"
	 */
	private static Variable var(final int row, final int col, final int val) {
		return new Variable(String.format("%s_%s_%s", String.valueOf(row), String.valueOf(col), String.valueOf(val)));
	}

	/**
	 * Creates a positive literal for a given position and the number of the entry.
	 * 
	 * @param row
	 *            row of the entry
	 * @param col
	 *            column of the entry
	 * @param val
	 *            number of the entry
	 * @return positive literal for the entry
	 */
	private static Literal posLit(int row, int col, int val) {
		return new Literal(var(row, col, val), true);
	}

	/**
	 * Creates a negative literal for a given position and the number of the entry.
	 * 
	 * @param row
	 *            row of the entry
	 * @param col
	 *            column of the entry
	 * @param val
	 *            number of the entry
	 * @return negative literal for the entry
	 */
	private static Literal negLit(int row, int col, int val) {
		return new Literal(var(row, col, val), false);
	}

	/**
	 * Ensures that for each entry at least one number (1 to length) is chosen.
	 * 
	 * @param base
	 *            the base of the sudoku
	 * @return the respective constraints as a set of clauses
	 */
	public static Set<Clause> atLeastOneNumEachEntry(final int base) {
<<<<<<< HEAD
		int length = base * base;
		Set<Clause> clauses = new HashSet<>();
		// loop through the rows then through the cols and then work with each number.
		for (int row = 1; row <= length; row++) {
			for (int col = 1; col <= length; col++) {
				Set<Literal> set = new HashSet<>();
				for (int n = 1; n <= length; n++) {
=======
		int b = base * base; // amount of numbers => sudoku width and height
		Set<Clause> clauses = new HashSet<>();

		for (int row = 1; row <= b; row++) { // for each entry in sudoku (base*base)*(base*base=) entries
			for (int col = 1; col <= b; col++) { // width height
				Set<Literal> set = new HashSet<>();
				for (int n = 1; n <= b; n++) { // possible assignments for each field: 1...n
>>>>>>> 5bde11b233fe9b3f9a5cc4fd4f57dcb15e44e5e5
					set.add(posLit(row, col, n));
				}
				clauses.addAll(CCEncoding.atLeastOne(set));
			}
		}
		return clauses;
	}

	/**
	 * Ensures that for each row a number is chosen at most once.
	 * 
	 * @param base
	 *            the base of the sudoku
	 * @return the respective constraints as a set of clauses
	 */
	public static Set<Clause> eachNumAtMostOnceEachRow(final int base) {
		int length = base * base;
		Set<Clause> clauses = new HashSet<>();
<<<<<<< HEAD
		// loop through the rows, then work with each number by looping through the cols
		// for each number
		for (int row = 1; row <= length; row++) {
			for (int n = 1; n <= length; n++) {
				Set<Literal> set = new HashSet<>();
				for (int col = 1; col <= length; col++) {
=======

		for (int row = 1; row <= b; row++) {    //for each row: base*base rows
			for (int n = 1; n <= b; n++) {
				Set<Literal> set = new HashSet<>();
				for (int col = 1; col <= b; col++) {   //each field can contain values from 1...n in each row
>>>>>>> 5bde11b233fe9b3f9a5cc4fd4f57dcb15e44e5e5
					set.add(posLit(row, col, n));
				}
				clauses.addAll(CCEncoding.atMostOne(set));
			}
		}
		return clauses;
	}

	/**
	 * Ensures that for each column a number is chosen at most once.
	 * 
	 * @param base
	 *            the base of the sudoku
	 * @return the respective constraints as a set of clauses
	 */
	private static Set<Clause> eachNumAtMostOnceEachCol(final int base) {
		int length = base * base;
		Set<Clause> clauses = new HashSet<>();
<<<<<<< HEAD
		// loop through the cols, then work with each number by looping through the rows
		// for each number
		for (int col = 1; col <= length; col++) {
			for (int n = 1; n <= length; n++) {
				Set<Literal> set = new HashSet<>();
				for (int row = 1; row <= length; row++) {
=======

		for (int col = 1; col <= b; col++) {   //for each column: base*base
			for (int n = 1; n <= b; n++) {
				Set<Literal> set = new HashSet<>();
				for (int row = 1; row <= b; row++) {   //each field can contain values from 1...n in each column
>>>>>>> 5bde11b233fe9b3f9a5cc4fd4f57dcb15e44e5e5
					set.add(posLit(row, col, n));
				}
				clauses.addAll(CCEncoding.atMostOne(set));
			}
		}
		return clauses;
	}

	/**
	 * Ensures that for each subgrid a number is chosen at most once.
	 * 
	 * @param base
	 *            the base of the sudoku
	 * @return the respective constraints as a set of clauses
	 */
	private static Set<Clause> eachNumAtMostOnceEachSubGrid(final int base) {
		int length = base * base;
		Set<Clause> clauses = new HashSet<>();
<<<<<<< HEAD
		// we loop down and right through the rows and then for each number through the
		// rows and cols of each grid
		for (int rowGrid = 0; rowGrid < base; rowGrid++) {
			for (int colGrid = 0; colGrid < base; colGrid++) {
				for (int n = 1; n <= base * base; n++) {
					Set<Literal> set = new HashSet<>();
					for (int row = 1; row <= base; row++) {
						for (int col = 1; col <= base; col++) {
							set.add(posLit(row + rowGrid * base, col + colGrid * base, n));
=======
		for (int rgrid = 0; rgrid < base; rgrid++) {     //each row contains base subgrids
			for (int cgrid = 0; cgrid < base; cgrid++) { //each column contains base subgrids
				for (int n = 1; n <= base * base; n++) { //each subgrid contains base*base(amount of numbers) values
					Set<Literal> set = new HashSet<>();
					for (int r = 1; r <= base; r++) {	 //add clausle for each field in subgrid
						for (int c = 1; c <= base; c++) {
							set.add(posLit(r + rgrid * base, c + cgrid * base, n)); 
>>>>>>> 5bde11b233fe9b3f9a5cc4fd4f57dcb15e44e5e5
						}
					}
					clauses.addAll(CCEncoding.atMostOne(set));
				}
			}
		}
		return clauses;
	}

	/**
	 * Creates unit clauses for preset entries of the sudoku system.
	 *
	 * Note: entry = -1 means no number is set
	 * 
	 * @param sudoku
	 *            sudoku instance
	 * @return unit clauses for preset entries
	 */
	private static Set<Clause> presetEntries(final Sudoku sudoku) {
		final Set<Clause> presetVars = new HashSet<>();
		final int length = sudoku.length();
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++)
				if (sudoku.get(row, col) != -1)
					presetVars.add(new Clause(posLit(row + 1, col + 1, sudoku.get(row, col))));
		return presetVars;
	}

	/**
	 * Decodes a given variable mapping as sudoku instance. Variables are expected
	 * in the form `row`_`col`_`val`, where row, col and val are Integer-parsable
	 * substrings.
	 * 
	 * @param model
	 *            the variable mapping
	 * @param base
	 *            the base of the sudoku
	 * @return the sudoku instance resulting from the mapping
	 */
	public static Sudoku decode(final Map<Variable, Boolean> model, final int base) {
		final Sudoku sudoku = new Sudoku(base);
		for (final Map.Entry<Variable, Boolean> entry : model.entrySet()) {
			if (entry.getValue()) {
				final String var = entry.getKey().getName();
				final String[] tokens = var.trim().split("_");
				if (tokens.length != 3)
					throw new IllegalArgumentException("Invalid variable name: " + var);
				final int row = Integer.valueOf(tokens[0]);
				final int col = Integer.valueOf(tokens[1]);
				final int val = Integer.valueOf(tokens[2]);
				sudoku.set(row - 1, col - 1, val);
			}
		}
		return sudoku;
	}

	/**
	 * Prints a given sudoku instance to standard out.
	 * 
	 * @param sudoku
	 *            the sudoku instance
	 */
	public static void print(final Sudoku sudoku) {
		final int length = sudoku.length();
		final int base = sudoku.base();
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				String num = sudoku.get(row, col) == -1 ? "-" : String.valueOf(sudoku.get(row, col));
				if ((col + 1) == length)
					System.out.print(num + "\n");
				else
					System.out.print(num + " ");
				if ((col + 1) % base == 0 && (col + 1) != length)
					System.out.print(" ");
			}
			if ((row + 1) % base == 0 && (row + 1) != length)
				System.out.print("\n");
		}
	}
}
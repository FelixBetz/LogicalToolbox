package de.arlab.bdd;

import de.arlab.formulas.*;
import de.arlab.sat.Clause;
import de.arlab.sat.Literal;
import de.arlab.sat.Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A BDD Manager.
 */
public class BDDManager extends Solver {

	private final Map<Integer, BDDNode> unique;
	private final Map<BDDNode, Integer> uback;
	private final Map<Set<Integer>, Integer> computeTable;
	private int nextIndex = 2;
	private final List<Variable> ord;

	private static final int BDD_TRUE = 1;
	private static final int BDD_FALSE = -1;

	/**
	 * Constructs a new BDDManager.
	 */
	public BDDManager() {
		unique = new HashMap<>();
		uback = new HashMap<>();
		computeTable = new HashMap<>();
		ord = new LinkedList<>();
	}

	/**
	 * Computes the BDD for a given formula.
	 * 
	 * @param formula
	 *            the formula
	 * @return the corresponding BDD (index of root node)
	 */
	public int mkBDD(final Formula formula) {
		if (formula instanceof Verum)
			return BDD_TRUE;
		if (formula instanceof Falsum)
			return BDD_FALSE;
		if (formula instanceof Variable) {
			int i = bddVar((Variable) formula);
			return i;
		}
		if (formula instanceof And) {
			And a = (And) formula;
			return bddAnd(mkBDD(a.getLeft()), mkBDD(a.getRight()));
		}
		if (formula instanceof Or) {
			Or a = (Or) formula;
			return bddOr(mkBDD(a.getLeft()), mkBDD(a.getRight()));
		}
		Not n = (Not) formula;
		int i = mkBDD(n.getOperand());
		return -i;
	}

	/**
	 * Computes the BDD for a given literal
	 * 
	 * @param literal
	 *            the literal
	 * @return the corresponding BDD (index of root node)
	 */
	private int mkBDD(final Literal literal) {
		if (literal.getPhase()) // positive literal leads to just a variable
			return bddVar(literal.getVar());
		return -bddVar(literal.getVar());
	}

	/**
	 * Computes the BDD for a given clause .
	 * 
	 * @param clause
	 *            the clause
	 * @return the corresponding BDD (index of root node)
	 */
	private int mkBDD(final Clause clause) {
		if (clause.isEmpty())
			return -1;
		Iterator<Literal> it = clause.getLiterals().iterator();
		Literal lit = it.next();
		int i = mkBDD(lit);
		while (it.hasNext()) {
			i = bddOr(i, mkBDD(it.next()));
		}
		return i;
	}

	/**
	 * Computes the BDD for a given clause set.
	 * 
	 * @param clauses
	 *            the set of clauses
	 * @return the corresponding BDD (index of root node)
	 */
	public int mkBDD(final Set<Clause> clauses) {
		if (clauses.isEmpty())
			return -1;
		Iterator<Clause> it = clauses.iterator();
		int i = mkBDD(it.next());
		while (it.hasNext())
			i = bddAnd(i, mkBDD(it.next()));
		return i;
	}

	/**
	 * Returns the corresponding BDD node for a given index.
	 * 
	 * @param n
	 *            the index
	 * @return the corresponding BDD node
	 * @throws IllegalArgumentException
	 *             if no BDD node was found for the given index, this indicates a
	 *             faulty implementation
	 */
	private BDDNode expandNode(final int n) {
		int m = Math.abs(n);
		if (!unique.containsKey(m)) {
			throw new IllegalArgumentException();
		}
		BDDNode node = unique.get(m);
		if (n < 0)
			return node.complement();
		return node;
	}

	/**
	 * Returns the index of a node. If the node is unknown, it is added with a new
	 * index.
	 * 
	 * @param node
	 *            the node to lookup and possibly add
	 * @return the index of the node
	 */
	private int lookupUnique(final BDDNode node) {
		if (uback.containsKey(node))
			return uback.get(node);
		uback.put(node, nextIndex);
		unique.put(nextIndex, node);
		nextIndex++;
		return uback.get(node);
	}

	/**
	 * Returns the order of two variables in this BDD.
	 * 
	 * @param v1
	 *            the first variable for comparison
	 * @param v2
	 *            the second variable for comparison
	 * @return {@code true} if v1 < v2 wrt. the current ordering, otherwise
	 *         {@code false}
	 */
	private boolean order(final Variable v1, final Variable v2) {
		return ord.indexOf(v1) < ord.indexOf(v2);
	}

	/**
	 * Creates a new BDD node and returns its index.
	 * 
	 * @param v
	 *            the variable at this node
	 * @param left
	 *            the index of the left child
	 * @param right
	 *            the index of the right child
	 * @return the index of the new node
	 */
	private int mkNode(final Variable v, final int left, final int right) {
		if (!ord.contains(v)) // if the variable isnt already in the variable order, add it
			ord.add(v);
		if (left == right)
			return left;
		BDDNode node = new BDDNode(v, left, right);
		if (left < 0) {
			return -lookupUnique(node.complement());
		}
		return lookupUnique(node);
	}

	/**
	 * Returns a BDD representing the given variable.
	 * 
	 * @param v
	 *            the variable
	 * @return a BDD representing the given variable
	 */
	private int bddVar(final Variable v) {
		return mkNode(v, BDD_TRUE, BDD_FALSE);
	}

	/**
	 * Computes the conjunction of two BDDs.
	 * 
	 * @param m1
	 *            the first BDD (index of root node)
	 * @param m2
	 *            the second BDD (index of root node)
	 * @return the conjunction of m1 and m2 (index of the root node)
	 */
	private int bddAnd(final int m1, final int m2) {
		if ((m1 == BDD_FALSE) || (m2 == BDD_FALSE))
			return BDD_FALSE;
		if (m1 == BDD_TRUE)
			return m2;
		if (m2 == BDD_TRUE)
			return m1;
		Set<Integer> set = new HashSet<>();
		set.add(m1);
		set.add(m2);
		if (computeTable.containsKey(set)) // if the conjunction already happened just use the result of that.
			return computeTable.get(set);
		BDDNode node1 = expandNode(m1);
		BDDNode node2 = expandNode(m2);
		int i = 0;
		if (order(node1.getVar(), node2.getVar()))
			i = mkNode(node1.getVar(), bddAnd(node1.getLeft(), m2), bddAnd(node1.getRight(), m2));
		else if (order(node2.getVar(), node1.getVar()))
			i = mkNode(node2.getVar(), bddAnd(m1, node2.getLeft()), bddAnd(m1, node2.getRight()));
		else // both vars equal.
			i = mkNode(node1.getVar(), bddAnd(node1.getLeft(), node2.getLeft()),
					bddAnd(node1.getRight(), node2.getRight()));
		computeTable.put(set, i);
		return i;
	}

	/**
	 * Computes the disjunction of two BDDs.
	 * 
	 * @param m1
	 *            the first BDD (index of root node)
	 * @param m2
	 *            the second BDD (index of root node)
	 * @return the disjunction of m1 and m2 (index of root node)
	 */
	private int bddOr(final int m1, final int m2) {
		return -bddAnd(-m1, -m2); // m1 or m2 == not (not m1 and not m2)
	}

	/**
	 * Returns a (potentially partial) mapping which satisfies the BDD. If the BDD
	 * represents {@code Falsum} {@code null} is returned.
	 * 
	 * @param root
	 *            the BDD
	 * @return a satisfying variable mapping or {@code null} if none exists
	 */
	private Map<Variable, Boolean> getModel(final int root) {
		Map<Variable, Boolean> model = new HashMap<>();
		if ((root == BDD_TRUE) || (root == BDD_FALSE))
			return model;
		BDDNode node = expandNode(root);
		if (node.getLeft() == BDD_TRUE) {
			model.put(node.getVar(), true);
			return model;
		}
		if (node.getRight() == BDD_TRUE) {
			model.put(node.getVar(), false);
			return model;
		}

		model = getModel(node.getLeft());
		if (!model.isEmpty()) {
			model.put(node.getVar(), true);
			return model;
		}
		model = getModel(node.getRight());
		if (!model.isEmpty()) {
			model.put(node.getVar(), false);
		}
		return model;

	}

	/**
	 * Create the DNF of a formula
	 * 
	 * @param root
	 *            the root of a formula in a BDD
	 * @return the dnf of the formula
	 */
	private Formula toDNF(final int root) {
		List<List<Formula>> liste = paths(root);
		Iterator<List<Formula>> it = liste.iterator();
		Formula f = listConjunction(it.next());
		while(it.hasNext()) {
			f = new Or(f,listConjunction(it.next()));
		}
		return f.simplify();
	}
	
	private Formula listConjunction(List<Formula> next) {
		Iterator<Formula> it = next.iterator();
		Formula f = it.next();
		if(f instanceof Falsum)
			return Formula.FALSUM;
		while(it.hasNext()) {
			f = new And(f,it.next());
		}
		return f.simplify();
	}
	
	private Formula listDisjunction(List<Formula> next) {
		Iterator<Formula> it = next.iterator();
		Formula f = it.next();
		if(f instanceof Verum)
			return Formula.VERUM;
		while(it.hasNext()) {
			f = new Or(f,it.next());
		}
		return f.simplify();
	}

	
	public List<List<Formula>> paths(final int root) {
		List<List<Formula>> gesamtListe = new ArrayList<>();
		if (root == 1) {
			List<Formula> path = new ArrayList<>();
			path.add(Formula.VERUM);
			gesamtListe.add(path);
			return gesamtListe;
		}
		if (root == -1) {
			List<Formula> path = new ArrayList<>();
			path.add(Formula.FALSUM);
			gesamtListe.add(path);
			return gesamtListe;
		}
		BDDNode node = expandNode(root);
		List<List<Formula>> left = paths(node.getLeft());
		List<List<Formula>> right = paths(node.getRight());
		Iterator<List<Formula>> it = left.iterator();
		while(it.hasNext()) {
			it.next().add(node.getVar());
		}
		gesamtListe.addAll(left);
		it = right.iterator();
		while(it.hasNext()) {
			it.next().add(new Not(node.getVar()));
		}
		gesamtListe.addAll(right);
		return gesamtListe;		
	}

	/**
	 * Create the CNF of a formula
	 * 
	 * @param root
	 *            the root of a formula in a BDD
	 * @return the cnf of the formula
	 */
	private Formula toCNF(final int root) {
		List<List<Formula>> liste = paths(root);
		Iterator<List<Formula>> it = liste.iterator();
		Formula f = listDisjunction(it.next());
		while(it.hasNext()) {
			f = new And(f,listDisjunction(it.next()));
		}
		return f.simplify();
	}

	/**
	 * Convert a formula into its CNF
	 * 
	 * @param formula
	 *            a random formula
	 * @return the dnf of the formula
	 */
	public Formula toCNF(final Formula formula) {
		return toCNF(mkBDD(formula));
	}

	/**
	 * Convert a formula into its DNF
	 * 
	 * @param formula
	 *            a random formula
	 * @return the dnf of the formula
	 */
	public Formula toDNF(final Formula formula) {
		return toDNF(mkBDD(formula));
	}

	/**
	 * Tests if a given BDD is satisfiable.
	 * 
	 * @param root
	 *            the BDD
	 * @return {@code true} if the BDD is satisfiable, otherwise {@code false}
	 */
	private boolean isSAT(final int root) {
		if (root == BDD_TRUE)
			return true;
		if (root == BDD_FALSE)
			return false;
		BDDNode node = expandNode(root);
		return isSAT(node.getLeft()) || isSAT(node.getRight());
	}

	/**
	 * Tests if a given BDD is a tautology.
	 * 
	 * @param root
	 *            the BDD
	 * @return {@code true} if the BDD is a tautology, otherwise {@code false}
	 */
	private boolean isTautology(final int root) {
		return !isSAT(-root);
	}

	/**
	 * Tests if a given BDD node is a contradiction.
	 * 
	 * @param root
	 *            the BDD
	 * @return {@code true} if the BDD is a contradiction, otherwise {@code false}
	 */
	private boolean isContradiction(final int root) {
		return !isSAT(root);
	}

	@Override
	public boolean isSAT(final Formula formula) {
		return isSAT(mkBDD(formula));
	}

	@Override
	public boolean isSAT(final Set<Clause> clauseSet) {
		return isSAT(Clause.clauses2Formula(clauseSet));
	}

	@Override
	public Map<Variable, Boolean> getModel(final Formula formula) {
		return getModel(mkBDD(formula));
	}

	@Override
	public Map<Variable, Boolean> getModel(final Set<Clause> clauseSet) {
		return getModel(mkBDD(clauseSet));
	}

	@Override
	public boolean isTautology(final Formula formula) {
		return isTautology(mkBDD(formula));
	}

	@Override
	public boolean isContradiction(final Formula formula) {
		return isContradiction(mkBDD(formula));
	}

	/**
	 * Returns a string with debugging information.
	 * 
	 * @return debugging information string
	 */
	public String debugInfo() {
		String init = "Unique Table\n------------------\n";
		for (int nodeNum : unique.keySet())
			init += "Node " + nodeNum + ": " + unique.get(nodeNum) + "\n";
		return init;
	}

	@Override
	public String toString() {
		return "<BDD with " + nextIndex + " nodes>";
	}
}
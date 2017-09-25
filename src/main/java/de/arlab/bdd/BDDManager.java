package de.arlab.bdd;

import de.arlab.formulas.*;
import de.arlab.formulas.Variable;
import de.arlab.formulas.Verum;
import de.arlab.sat.Clause;
import de.arlab.sat.Solver;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.HashSet;
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
		if (formula instanceof Falsum)
			return -1;
		if (formula instanceof Variable)
			return bddVar((Variable) formula);
		if (formula instanceof Not)
			return -mkBDD(((Not) formula).getOperand());
		if (formula instanceof And)
			return bddAnd(mkBDD(((And) formula).getLeft()), mkBDD(((And) formula).getRight()));
		if (formula instanceof Or)
			return bddOr(mkBDD(((Or) formula).getLeft()), mkBDD(((Or) formula).getRight()));
		return 1;

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
	public BDDNode expandNode(final int n) {
		if (!unique.containsKey(n))
			throw new IllegalArgumentException();
		BDDNode node = unique.get(n);
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
	public int lookupUnique(final BDDNode node) {
		if (uback.containsKey(node))
			return uback.get(node);

		this.unique.put(this.nextIndex, node);
		this.uback.put(node, this.nextIndex);
		this.nextIndex++;
		return this.uback.get(node);
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
		if (!this.ord.contains(v)) {
			this.ord.add(v);
		}
		if (left == right)
			return left;
		BDDNode node = new BDDNode(v, left, right);
		int i = lookupUnique(node);
		if (left < 0)
			return -i;
		return i;
	}

	/**
	 * Returns a BDD representing the given variable.
	 * 
	 * @param v
	 *            the variable
	 * @return a BDD representing the given variable
	 */
	private int bddVar(final Variable v) {
		return mkNode(v, 1, -1);
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
		if (m1 == BDD_FALSE || m2 == BDD_FALSE)
			return BDD_FALSE;
		if (m1 == BDD_TRUE)
			return m2;
		if (m2 == BDD_TRUE)
			return m1;
		Set<Integer> set = new HashSet<>(m1, m2);
		if (computeTable.containsKey(set))
			return computeTable.get(set);

		BDDNode node1 = expandNode(m1);
		BDDNode node2 = expandNode(m2);
		int i;
		if (node1.getVar().equals(node2.getVar())) {
			i = mkNode(node1.getVar(), bddAnd(node1.getLeft(), node2.getLeft()),
					bddAnd(node1.getRight(), node2.getRight()));
		} else if (order(node1.getVar(), node2.getVar())) {
			i = mkNode(node1.getVar(), bddAnd(node1.getLeft(), m2), bddAnd(node1.getRight(), m2));
		} else {
			i = mkNode(node2.getVar(), bddAnd(m1, node2.getLeft()), bddAnd(m1, node2.getRight()));
		}
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
		int i = bddAnd(-m1, -m2);
		return -i;
	}

	/**
	 * Returns a (potentially partial) mapping which satisfies the BDD. If the BDD
	 * represents {@code Falsum} {@code null} is returned.
	 * 
	 * @param root
	 *            the BDD
	 * @return a satisfying variable mapping or {@code null} if none exists
	 */
//	private Map<Variable, Boolean> getModel(final int root) {
//		Map<Variable, Boolean> map = new HashMap<>();
//		int n = 1;
//		boolean b = true;
//		while (b) {
//			b = false;
//			for (Map.Entry<BDDNode, Integer> entry : uback.entrySet()) {
//				if (entry.getKey().getLeft() == n) {
//					map.put(entry.getKey().getVar(), true);
//					n = entry.getValue();
//					b = true;
//					break;
//				} else if (entry.getKey().getRight() == n) {
//					map.put(entry.getKey().getVar(), false);
//					n = entry.getValue();
//					b = true;
//					break;
//				}
//			}
//		}
//		return map;
//	}
	private Map<Variable, Boolean> getModel(final int root) {
		Map<Variable, Boolean> map = new HashMap<>();
		if (root == 1 || root == -1)
			return map;
		BDDNode node = expandNode(root);
		if(node.getLeft()==1) {
			map.put(node.getVar(), true);
			return map;
		}
		else if (node.getRight() == 1) {
			map.put(node.getVar(),false);
			return map;
		}
		map = getModel (node.getLeft());
		if (!map.isEmpty()) {
			map.put(node.getVar(), true);
			return map;
		}
		map = getModel (node.getRight());
		if (!map.isEmpty()) {
			map.put(node.getVar(), false);
			return map;
		}
		map.clear();
		return map;
		
		
	}
	/**
	 * Tests if a given BDD is satisfiable.
	 * 
	 * @param root
	 *            the BDD
	 * @return {@code true} if the BDD is satisfiable, otherwise {@code false}
	 */
	private boolean isSAT(final int root) {
		if(root == 1)
			return true;
		if(root==-1)
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
		return getModel(Clause.clauses2Formula(clauseSet));
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
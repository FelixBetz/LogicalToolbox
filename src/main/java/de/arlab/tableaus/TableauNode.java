package de.arlab.tableaus;

import de.arlab.formulas.And;
import de.arlab.formulas.Falsum;
import de.arlab.formulas.Formula;
import de.arlab.formulas.Not;
import de.arlab.formulas.Or;
import de.arlab.formulas.Variable;
import de.arlab.util.ToBeImplementedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Class for a tableau node.
 */
public class TableauNode {

	private final List<Formula> label;
	private TableauNode left;
	private TableauNode right;

	/**
	 * Constructor for a tableau node.
	 * 
	 * @param label
	 *            a list of formulas
	 */
	public TableauNode(final List<Formula> label) {
		this.label = label;
		createChildren();
	}

	/**
	 * Creates the children for this node.
	 */
	private void createChildren() {
		ListIterator<Formula> it = label.listIterator();

		while (it.hasNext()) {
			Formula f = it.next();
			if (f instanceof And) {
				List<Formula> leftLabel = new LinkedList<Formula>();
				leftLabel.addAll(label);
				leftLabel.remove(f);
				leftLabel.add(((And) f).getLeft());
				leftLabel.add(((And) f).getRight());		
				left = new TableauNode(leftLabel);
				break;
			}
			if (f instanceof Or) {
				List<Formula> leftLabel = new LinkedList<Formula>();
				leftLabel.addAll(label);
				leftLabel.remove(f);
				leftLabel.add(((Or) f).getLeft());
				left = new TableauNode(leftLabel);
				
				List<Formula> rightLabel = new LinkedList<Formula>();
				rightLabel.addAll(label);
				rightLabel.remove(f);
				rightLabel.add(((Or) f).getRight());
				right = new TableauNode(rightLabel);
				break;
			}
		}
	}

	/**
	 * Tests if this node is a leaf.
	 * 
	 * @return {@code true} if this node is a leaf, otherwise {@code false}
	 */
	private boolean isLeaf() {
		if (left == null && right == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tests if this node is closed.
	 * 
	 * @return {@code true} if this node is closed, otherwise {@code false}
	 */
	public boolean isClosed() {
		if(this.left != null && this.right == null) {
			return  this.left.isClosed();
		}
		else if(this.left !=null && this.right != null) {
			return this.left.isClosed() && this.right.isClosed();
		}
		if(this.isLeaf()) {
			ListIterator<Formula> it = label.listIterator();
			while(it.hasNext()) {
				Formula f = it.next();
				if(f instanceof Falsum) {
					return true;
				}
				if(label.contains(new Not(f).simplify())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns a satisfying assignment. If this node is closed, {@code null} is
	 * returned.
	 * 
	 * @return a satisfying assignment or {@code null} if the node is closed
	 */
	public Map<Variable, Boolean> getModel() {
		throw new ToBeImplementedException();
	}

	@Override
	public String toString() {
		return toString(0);
	}

	/**
	 * Returns a string representation of this node with the given initial indent.
	 * 
	 * @param indent
	 *            the indent for this node
	 * @return a string representation of this node
	 */
	private String toString(final int indent) {
		String result = "";
		for (int i = 0; i < indent; ++i)
			result += "    ";
		for (Formula f : label)
			result += f + ";";
		result += "\n";
		if (left != null)
			result += left.toString(indent + 1);
		if (right != null)
			result += right.toString(indent + 1);
		return result;
	}
}
package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;
import edu.isu.cs.cs3308.structures.impl.LinkedQueue;

import java.util.LinkedList;

public class BreadthFirstTraversal<E> extends AbstractTraversal<E>
{
	/**
	 * Tree traversal - Constructor
	 *
	 * @author: Dylan Lasher
	 * @param tree traverse
	 */
	public BreadthFirstTraversal(Tree<E> tree) {
		super(tree);
	}

	@Override
	public Iterable<Node<E>> traverse()
	{
		if (tree.root() == null)
		{
			return null;
		}
		else {
			return traverseFrom(tree.root());
		}
	}

	@Override
	public Iterable<Node<E>> traverseFrom(Node<E> node)
	{
		tree.validate(node);

		LinkedQueue<BinaryTreeNode<E>> nodeQue = new LinkedQueue<>();
		LinkedList<Node<E>> listNode = new LinkedList<>();

		nodeQue.offer((BinaryTreeNode<E>) node);

		while (!nodeQue.isEmpty())
		{
			BinaryTreeNode<E> tempNode = nodeQue.poll();
			listNode.addLast(tempNode);

			command.execute(tree, tempNode);

			if (tempNode.getLeft() != null)
			{
				nodeQue.offer(tempNode.getLeft());
			}
			if (tempNode.getRight() != null)
			{
				nodeQue.offer(tempNode.getRight());
			}
		}
		return listNode;
	}
}
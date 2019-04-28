package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;

import java.util.List;

public class PreOrderTraversal<E> extends DepthFirstTraversal<E>
{

	public PreOrderTraversal(Tree<E> tree) {
		super(tree);
	}

	@Override
	public void subtree(Node<E> p, List<Node<E>> snapshot)
	{
		if (snapshot == null)
		{
			throw new IllegalArgumentException("List is null");
		}
		BinaryTreeNode<E> btn = (BinaryTreeNode<E>)tree.validate(p);

		snapshot.add(btn);
		if (command != null)
		{
			command.execute(tree, btn);
		}

		if (btn.getLeft() != null)
		{
			subtree(btn.getLeft(), snapshot);
		}
		if (btn.getRight() != null)
		{
			subtree(btn.getRight(), snapshot);
		}
	}
}
package edu.isu.cs.cs3308.traversals;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;

import java.util.List;

public class PostOrderTraversal<E> extends DepthFirstTraversal<E>
{

	public PostOrderTraversal(Tree<E> tree) {
		super(tree);
	}

	@Override
	public void subtree(Node<E> p, List<Node<E>> snapshot)
	{
		if (snapshot == null)
		{
			throw new IllegalArgumentException("Null List");
		}
		BinaryTreeNode<E> btn = (BinaryTreeNode<E>)tree.validate(p);

		if (btn.getLeft() != null)
		{
			subtree(btn.getLeft(), snapshot);
		}
		if (btn.getRight() != null)
		{
			subtree(btn.getRight(), snapshot);
		}

		snapshot.add(btn);
		if (command != null)
		{
			command.execute(tree, btn);
		}
	}
}
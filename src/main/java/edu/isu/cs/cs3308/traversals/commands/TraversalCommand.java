package edu.isu.cs.cs3308.traversals.commands;

import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.Tree;

/**
 *
 * @author fate
 */
public abstract class TraversalCommand<E> {

    public abstract void execute(Tree<E> tree, Node<E> node);
}

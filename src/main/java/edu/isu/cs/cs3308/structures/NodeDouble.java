package edu.isu.cs.cs3308.structures;

/**
 * Node class needed for the Double List
 *
 * @author Dylan Lasher
 */
public class NodeDouble<E> extends NodeSingle<E>
{

	private NodeDouble<E> prev;

	public NodeDouble(E data) {
		super(data);
	}

	public NodeDouble<E> getPrev() {
		return prev;
	}

	public void setPrev(NodeDouble<E> prev) {
		this.prev = prev;
	}
}
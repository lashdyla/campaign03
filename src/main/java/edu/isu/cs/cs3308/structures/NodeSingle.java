package edu.isu.cs.cs3308.structures;

/**
 * Class for List - NodeSingle
 *
 * @author Dylan Lasher
 */
public class NodeSingle<E>
{

	protected E data;
	protected NodeSingle<E> next;

	public NodeSingle(E data) {
		this.data = data;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public NodeSingle<E> getNext() {
		return next;
	}

	public void setNext(NodeSingle<E> next) {
		this.next = next;
	}
}

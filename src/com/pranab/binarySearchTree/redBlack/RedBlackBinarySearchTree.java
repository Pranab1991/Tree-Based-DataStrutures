package com.pranab.binarySearchTree.redBlack;

import java.util.function.Consumer;

/*
 * UNSTABLE !!!!! work pending use RedBlackTree instead
 * 
 */
@Deprecated
public class RedBlackBinarySearchTree<K extends Comparable<K>, V> extends BaseRedBlackTreeOperations<K, V> {

	
	 
	public boolean insert(K key, V value) {
		NodeRB<K, V> newLeaf = new NodeRB<>(key, sentinal, sentinal, sentinal, value, false);
		NodeRB<K, V> searchNode = (NodeRB<K, V>) this.root;
		NodeRB<K, V> parent = null;
		boolean modify = false;
		if (searchNode == null) {
			this.root = newLeaf;
			size++;
			restructureTree(newLeaf);
			return true;
		}
		while (searchNode != sentinal) {
			parent = searchNode;
			if (key.compareTo(searchNode.getKey()) < 0) {
				searchNode = (NodeRB<K, V>) searchNode.getLeftChild();
			} else if (key.compareTo(searchNode.getKey()) > 0) {
				searchNode = (NodeRB<K, V>) searchNode.getRightChild();
			} else {
				searchNode.setValue(value);
				modify = true;
				break;
			}
		}
		if (!modify) {
			newLeaf.setParent(parent);
			if (parent.getKey().compareTo(key) > 0) {
				parent.setLeftChild(newLeaf);
			} else {
				parent.setRightChild(newLeaf);
			}
			size++;
			restructureTree(newLeaf);
		}
		return true;
	}

	private void restructureTree(NodeRB<K, V> validateNode) {
		while ((validateNode.getParent() != sentinal) && (!((NodeRB<K, V>) validateNode.getParent()).isBlack())) {
			if (validateNode.getParent().getParent() != sentinal) {
				NodeRB<K, V> grandParent = ((NodeRB<K, V>) validateNode.getParent().getParent());
				NodeRB<K, V> parent = ((NodeRB<K, V>) validateNode.getParent());
				if (validateNode.getParent() == validateNode.getParent().getParent().getLeftChild()) {
					NodeRB<K, V> parentRightSibling = (NodeRB<K, V>) validateNode.getParent().getParent()
							.getRightChild();
					if ((parentRightSibling != sentinal) && (!parentRightSibling.isBlack())) {
						parentRightSibling.setBlack(true);
						parent.setBlack(true);
						grandParent.setBlack(false);
						validateNode = grandParent;
					} else {
						if (parent.getRightChild() == validateNode) {
							validateNode = validateNode.getParent();
							leftRotate(validateNode);
						}
						((NodeRB<K, V>) validateNode.getParent()).setBlack(true);
						grandParent.setBlack(false);
						rightRotate(grandParent);
					}
				} else {
					NodeRB<K, V> parentLeftSibling = (NodeRB<K, V>) validateNode.getParent().getParent().getLeftChild();
					if ((parentLeftSibling != sentinal) && (!parentLeftSibling.isBlack())) {
						parentLeftSibling.setBlack(true);
						parent.setBlack(true);
						grandParent.setBlack(false);
						validateNode = grandParent;
					} else {
						if (parent.getLeftChild() == validateNode) {
							validateNode = validateNode.getParent();
							rightRotate(validateNode);
						}
						((NodeRB<K, V>) validateNode.getParent()).setBlack(true);
						grandParent.setBlack(false);
						leftRotate(grandParent);
					}
				}
			}
		}
		((NodeRB<K, V>) this.root).setBlack(true);
	}

	 
	public boolean delete(K key) {
		NodeRB<K, V> delete_Node = (NodeRB<K, V>) search(key);
		if (delete_Node == sentinal) {
			return false;
		}
		NodeRB<K, V> incoming_Node = null;
		NodeRB<K, V> remove_Replacing_Node = delete_Node;
		boolean remove_Replacing_Node_is_black = remove_Replacing_Node.isBlack();
		if (delete_Node.getLeftChild() == sentinal) {
			incoming_Node = (NodeRB<K, V>) delete_Node.getRightChild();
			transplant(delete_Node, incoming_Node);
		} else if (delete_Node.getRightChild() == sentinal) {
			incoming_Node = (NodeRB<K, V>) delete_Node.getLeftChild();
			transplant(delete_Node, incoming_Node);
		} else {
			remove_Replacing_Node = (NodeRB<K, V>) getMinimum(delete_Node.getRightChild());
			remove_Replacing_Node_is_black = remove_Replacing_Node.isBlack();
			incoming_Node = (NodeRB<K, V>) remove_Replacing_Node.getRightChild();
			if ((remove_Replacing_Node.getParent() == delete_Node)) {
					incoming_Node.setParent(remove_Replacing_Node);
			} else {
				transplant(remove_Replacing_Node, incoming_Node);
				remove_Replacing_Node.setRightChild(delete_Node.getRightChild());
				remove_Replacing_Node.getRightChild().setParent(incoming_Node);
			}
			transplant(delete_Node, remove_Replacing_Node);
			remove_Replacing_Node.setLeftChild(delete_Node.getLeftChild());
			delete_Node.getLeftChild().setParent(remove_Replacing_Node);
			remove_Replacing_Node.setBlack(delete_Node.isBlack());
			delete_Node.setLeftChild(null);
			delete_Node.setRightChild(null);
			delete_Node.setParent(null);
		}
		if (remove_Replacing_Node_is_black) {
			restructure(incoming_Node);
		}
		return true;
	}

	private void restructure(NodeRB<K, V> incoming_Node) {
		while (incoming_Node != this.root && incoming_Node.isBlack()) {
			if (incoming_Node == incoming_Node.getParent().getLeftChild()) {
				NodeRB<K, V> sibling = (NodeRB<K, V>) incoming_Node.getParent().getRightChild();
				if ((!sibling.isBlack())) {
					sibling.setBlack(true);
					((NodeRB<K, V>) incoming_Node.getParent()).setBlack(false);
					leftRotate(incoming_Node.getParent());
					sibling = (NodeRB<K, V>) incoming_Node.getParent().getRightChild();
				}
				if ( ((NodeRB<K, V>) sibling.getLeftChild()).isBlack()
						&& ((NodeRB<K, V>) sibling.getRightChild()).isBlack()) {
					sibling.setBlack(false);
					incoming_Node = (NodeRB<K, V>) incoming_Node.getParent();
				} else {
					if (((NodeRB<K, V>) sibling.getRightChild()).isBlack()) {
						((NodeRB<K, V>) sibling.getLeftChild()).setBlack(true);
						sibling.setBlack(false);
						rightRotate(sibling);
						sibling = (NodeRB<K, V>) incoming_Node.getParent().getRightChild();
					}
					sibling.setBlack(((NodeRB<K, V>) incoming_Node.getParent()).isBlack());
					((NodeRB<K, V>) incoming_Node.getParent()).setBlack(true);
					((NodeRB<K, V>) sibling.getRightChild()).setBlack(true);
					leftRotate(incoming_Node.getParent());
					incoming_Node = (NodeRB<K, V>) this.root;
				}
			} else {
				NodeRB<K, V> sibling = (NodeRB<K, V>) incoming_Node.getParent().getLeftChild();
				if ((!sibling.isBlack())) {
					sibling.setBlack(true);
					((NodeRB<K, V>) incoming_Node.getParent()).setBlack(false);
					rightRotate(incoming_Node.getParent());
					sibling = (NodeRB<K, V>) incoming_Node.getParent().getLeftChild();
				}
				if (((NodeRB<K, V>) sibling.getLeftChild()).isBlack()
						&& ((NodeRB<K, V>) sibling.getRightChild()).isBlack()) {
					sibling.setBlack(false);
					incoming_Node = (NodeRB<K, V>) incoming_Node.getParent();
				} else {
					if (((NodeRB<K, V>) sibling.getLeftChild()).isBlack()) {
						((NodeRB<K, V>) sibling.getRightChild()).setBlack(true);
						sibling.setBlack(false);
						leftRotate(sibling);
						sibling = (NodeRB<K, V>) incoming_Node.getParent().getLeftChild();
					}
					sibling.setBlack(((NodeRB<K, V>) incoming_Node.getParent()).isBlack());
					((NodeRB<K, V>) incoming_Node.getParent()).setBlack(true);
					((NodeRB<K, V>) sibling.getLeftChild()).setBlack(true);
					rightRotate(incoming_Node.getParent());
					incoming_Node = (NodeRB<K, V>) this.root;
				}
			}
			((NodeRB<K, V>) incoming_Node).setBlack(true);
		}
	}

	private void leftRotate(NodeRB<K, V> NodeRB) {
		NodeRB<K, V> parent = NodeRB.getParent();
		NodeRB<K, V> new_pivoit = NodeRB.getRightChild();
		if (new_pivoit != sentinal) {
			NodeRB.setRightChild(new_pivoit.getLeftChild());
			if (new_pivoit.getLeftChild() != sentinal) {
				new_pivoit.getLeftChild().setParent(NodeRB);
			}
			setParentPivoit(NodeRB, parent, new_pivoit);
			NodeRB.setParent(new_pivoit);
			new_pivoit.setLeftChild(NodeRB);
		}
	}

	private void rightRotate(NodeRB<K, V> NodeRB) {
		NodeRB<K, V> parent = NodeRB.getParent();
		NodeRB<K, V> new_pivoit = NodeRB.getLeftChild();
		if (new_pivoit != sentinal) {
			NodeRB.setLeftChild(new_pivoit.getRightChild());
			if (new_pivoit.getRightChild() != sentinal) {
				new_pivoit.getRightChild().setParent(NodeRB);
			}
			setParentPivoit(NodeRB, parent, new_pivoit);
			NodeRB.setParent(new_pivoit);
			new_pivoit.setRightChild(NodeRB);
		}
	}

	private void setParentPivoit(NodeRB<K, V> NodeRB, NodeRB<K, V> parent, NodeRB<K, V> new_pivoit) {
		new_pivoit.setParent(parent);
		if (parent != sentinal) {
			if (parent.getLeftChild() == NodeRB) {
				parent.setLeftChild(new_pivoit);
			} else {
				parent.setRightChild(new_pivoit);
			}
		} else {
			this.root = new_pivoit;
		}
	}

	 
	public V getValue(K key) {
		readlocker.lock();
		try {
			NodeRB<K, V> value = search(key);
			return value == sentinal ? null : value.getValue();
		} finally {
			readlocker.unlock();
		}
	}

	 
	public K getMinimumKey() {
		readlocker.lock();
		try {
			NodeRB<K, V> value = getMinimum(this.root);
			return value == sentinal ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	 
	public K getMaximumKey() {
		readlocker.lock();
		try {
			NodeRB<K, V> value = getMaximum(this.root);
			return value == sentinal ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	 
	public K getSuccessorKey(K key) {
		readlocker.lock();
		try {
			NodeRB<K, V> value = getSuccessor(key);
			return value == null ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	 
	public K getPredecessorKey(K key) {
		readlocker.lock();
		try {
			NodeRB<K, V> value = getPredecessor(key);
			return value == null ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	 
	public void iterator(K key, Consumer<NodeRB<K, V>> work) {
		readlocker.lock();
		try {
			walk(search(key), work);
		} finally {
			readlocker.unlock();
		}
	}

	public K getRootKey() {
		return root!=null?root.getKey():null;
	}

	 
	public int size() {
		return size;
	}

	 
	public boolean contains(K key) {
		readlocker.lock();
		try {
			NodeRB<K, V> value = getMinimum(this.root);
			return value == null ? false : true;
		} finally {
			readlocker.unlock();
		}
	}
}

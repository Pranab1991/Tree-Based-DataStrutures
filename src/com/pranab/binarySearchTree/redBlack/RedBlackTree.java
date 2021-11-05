package com.pranab.binarySearchTree.redBlack;

import java.util.Optional;

public class RedBlackTree<K extends Comparable<K>, V> implements RebBlackTree<K, V> {

	private NodeRB<K, V> sentinal = new NodeRB<>(null, null, null, null, null, true);
	private NodeRB<K, V> root = sentinal;
	private int size = 0;

	@Override
	public Optional<NodeRB<K, V>> getRoot() {
		return Optional.ofNullable(root);
	}

	@Override
	public void insert(K key, V value) {
		NodeRB<K, V> newNode = new NodeRB<>(key, sentinal, sentinal, sentinal, value, false);
		if (root == sentinal) {
			root = newNode;
		} else {
			NodeRB<K, V> temp = root;
			NodeRB<K, V> parent = sentinal;
			while (temp != sentinal) {
				parent = temp;
				if (key.compareTo(temp.getKey()) < 0) {
					temp = temp.getLeftChild();
				} else {
					temp = temp.getRightChild();
				}
			}
			newNode.setParent(parent);
			if (key.compareTo(parent.getKey()) < 0) {
				parent.setLeftChild(newNode);
			} else {
				parent.setRightChild(newNode);
			}
		}
		size++;
		insertRBFixUp(newNode);
	}

	@Override
	public void delete(K key) {
		NodeRB<K, V> deleteNode = searchNode(key);
		NodeRB<K, V> replacingNode = null;
		boolean isBlack = deleteNode.isBlack();
		NodeRB<K, V> incomingNode;
		if (deleteNode.getLeftChild() == sentinal) {
			incomingNode = deleteNode.getRightChild();
			transplant(deleteNode, deleteNode.getRightChild());
		} else if (deleteNode.getRightChild() == sentinal) {
			incomingNode = deleteNode.getLeftChild();
			transplant(deleteNode, deleteNode.getLeftChild());
		} else {
			replacingNode = minimumNode(deleteNode.getRightChild());
			isBlack = replacingNode.isBlack();
			incomingNode = replacingNode.getRightChild();
			if (replacingNode.getParent() == deleteNode) {
				incomingNode.setParent(replacingNode);
			} else {
				transplant(replacingNode, incomingNode);
				replacingNode.setRightChild(deleteNode.getRightChild());
				deleteNode.getRightChild().setParent(replacingNode);
			}
			transplant(deleteNode, replacingNode);
			replacingNode.setLeftChild(deleteNode.getLeftChild());
			deleteNode.getLeftChild().setParent(replacingNode);
			replacingNode.setBlack(deleteNode.isBlack());
		}
		size--;
		if (isBlack) {
			deleteRBFixUp(incomingNode);
		}

	}

	@Override
	public Optional<V> search(K key) {
		V value = searchNode(key).getValue();
		return Optional.ofNullable(value);
	}

	@Override
	public Optional<V> minimum() {
		NodeRB<K, V> minimumNode = minimumNode(sentinal);
		return Optional.ofNullable(minimumNode.getValue());
	}

	@Override
	public Optional<V> maximum() {
		NodeRB<K, V> maximumNode = maximumNode(sentinal);
		return Optional.ofNullable(maximumNode.getValue());
	}

	@Override
	public Optional<V> minimum(K key) {
		NodeRB<K, V> minimumNode = minimumNode(searchNode(key));
		return Optional.ofNullable(minimumNode.getValue());
	}

	@Override
	public Optional<V> maximum(K key) {
		NodeRB<K, V> maximumNode = maximumNode(searchNode(key));
		return Optional.ofNullable(maximumNode.getValue());
	}

	@Override
	public Optional<V> successor(K key) {
		NodeRB<K, V> successorNode = successorNode(searchNode(key));
		return Optional.ofNullable(successorNode.getValue());
	}

	@Override
	public Optional<V> predecessor(K key) {
		NodeRB<K, V> predecessorNode = predecessorNode(searchNode(key));
		return Optional.ofNullable(predecessorNode.getValue());
	}

	public void inOrderWalk(NodeRB<K, V> node) {
		if (node == sentinal) {
			return;
		}
		inOrderWalk(node.getLeftChild());
		System.out.print(node.getValue()+" , ");
		inOrderWalk(node.getRightChild());
	}

	private NodeRB<K, V> searchNode(K key) {
		NodeRB<K, V> temp = root;
		NodeRB<K, V> nodeFound = sentinal;
		while (temp != sentinal) {
			if (temp.getKey() == key) {
				nodeFound = temp;
				break;
			} else {
				if (key.compareTo(temp.getKey()) < 0) {
					temp = temp.getLeftChild();
				} else {
					temp = temp.getRightChild();
				}
			}
		}
		return nodeFound;
	}

	private NodeRB<K, V> minimumNode(NodeRB<K, V> keyNode) {
		NodeRB<K, V> temp;
		if (keyNode == sentinal) {
			temp = root;
		} else {
			temp = keyNode;
		}
		if (temp != sentinal) {
			while (temp.getLeftChild() != sentinal) {
				temp = temp.getLeftChild();
			}
		}
		return temp;
	}

	private NodeRB<K, V> maximumNode(NodeRB<K, V> keyNode) {
		NodeRB<K, V> temp;
		if (keyNode == sentinal) {
			temp = root;
		} else {
			temp = keyNode;
		}
		if (temp != sentinal) {
			while (temp.getRightChild() != sentinal) {
				temp = temp.getRightChild();
			}
		}
		return temp;
	}

	private NodeRB<K, V> successorNode(NodeRB<K, V> keyNode) {
		NodeRB<K, V> node = keyNode;
		if (node.getRightChild() != sentinal) {
			return minimumNode(node.getRightChild());
		}
		NodeRB<K, V> parent = node.getParent();
		while (parent != sentinal && parent.getRightChild() == node) {
			node = parent;
			parent = node.getParent();
		}
		return parent;
	}

	private NodeRB<K, V> predecessorNode(NodeRB<K, V> keyNode) {
		NodeRB<K, V> node = keyNode;
		if (node.getLeftChild() != sentinal) {
			return maximumNode(node.getLeftChild());
		}
		NodeRB<K, V> parent = node.getParent();
		while (parent != sentinal && parent.getLeftChild() == node) {
			node = parent;
			parent = node.getParent();
		}
		return parent;
	}

	private void leftRotate(NodeRB<K, V> pivoit) {
		NodeRB<K, V> piviotRight = pivoit.getRightChild();
		pivoit.setRightChild(piviotRight.getLeftChild());
		if (piviotRight.getLeftChild() != sentinal) {
			piviotRight.getLeftChild().setParent(pivoit);
		}
		piviotRight.setParent(pivoit.getParent());
		if (pivoit.getParent() != sentinal) {
			if (pivoit.getParent().getLeftChild() == pivoit) {
				pivoit.getParent().setLeftChild(piviotRight);
			} else {
				pivoit.getParent().setRightChild(piviotRight);
			}
		} else {
			root = piviotRight;
		}
		piviotRight.setLeftChild(pivoit);
		pivoit.setParent(piviotRight);
	}

	private void rightRotate(NodeRB<K, V> pivoit) {
		NodeRB<K, V> piviotLeft = pivoit.getLeftChild();
		pivoit.setLeftChild(piviotLeft.getRightChild());
		if (piviotLeft.getRightChild() != sentinal) {
			piviotLeft.getRightChild().setParent(pivoit);
		}
		piviotLeft.setParent(pivoit.getParent());
		if (pivoit.getParent() != sentinal) {
			if (pivoit.getParent().getLeftChild() == pivoit) {
				pivoit.getParent().setLeftChild(piviotLeft);
			} else {
				pivoit.getParent().setRightChild(piviotLeft);
			}
		} else {
			root = piviotLeft;
		}
		piviotLeft.setRightChild(pivoit);
		pivoit.setParent(piviotLeft);
	}

	private void insertRBFixUp(NodeRB<K, V> newNode) {
		while (!newNode.getParent().isBlack()) {
			if (newNode.getParent() == newNode.getParent().getParent().getLeftChild()) {
				if (!newNode.getParent().getParent().getRightChild().isBlack()) {
					newNode.getParent().setBlack(true);
					newNode.getParent().getParent().getRightChild().setBlack(true);
					newNode.getParent().getParent().setBlack(false);
					newNode = newNode.getParent().getParent();
				} else {
					if (newNode.getParent() == newNode.getParent().getParent().getLeftChild()) {
						newNode = newNode.getParent();
						leftRotate(newNode);
					}
					newNode.getParent().setBlack(true);
					newNode.getParent().getParent().setBlack(false);
					rightRotate(newNode.getParent().getParent());
				}
			} else {
				if (!newNode.getParent().getParent().getLeftChild().isBlack()) {
					newNode.getParent().setBlack(true);
					newNode.getParent().getParent().getLeftChild().setBlack(true);
					newNode.getParent().getParent().setBlack(false);
					newNode = newNode.getParent().getParent();
				} else {
					if (newNode.getParent() == newNode.getParent().getParent().getRightChild()) {
						newNode = newNode.getParent();
						rightRotate(newNode);
					}
					newNode.getParent().setBlack(true);
					newNode.getParent().getParent().setBlack(false);
					leftRotate(newNode.getParent().getParent());
				}
			}
		}
		root.setBlack(true);
	}

	private void transplant(NodeRB<K, V> outgoing, NodeRB<K, V> incoming) {
		if (outgoing.getParent() == sentinal) {
			root = incoming;
		} else {
			if (outgoing.getParent().getRightChild() == outgoing) {
				outgoing.getParent().setRightChild(incoming);
			} else {
				outgoing.getParent().setLeftChild(incoming);
			}
		}
		incoming.setParent(outgoing.getParent());
	}

	private void deleteRBFixUp(NodeRB<K, V> incomingNode) {
		while(incomingNode!=root&&incomingNode.isBlack()) {
			if(incomingNode.getParent().getLeftChild()==incomingNode) {
				NodeRB<K,V> sibilingNode=incomingNode.getParent().getRightChild();
				if(!sibilingNode.isBlack()) {
					sibilingNode.setBlack(true);
					incomingNode.getParent().setBlack(false);
					leftRotate(incomingNode.getParent());
					sibilingNode=incomingNode.getParent().getRightChild();
				}
				if(sibilingNode.getLeftChild().isBlack()&&sibilingNode.getRightChild().isBlack()) {
					sibilingNode.setBlack(false);
					incomingNode=incomingNode.getParent();
				}else {
					if(sibilingNode.getRightChild().isBlack()) {
						sibilingNode.setBlack(false);
						sibilingNode.getLeftChild().setBlack(true);
						leftRotate(sibilingNode);
						sibilingNode=incomingNode.getParent().getRightChild();
					}
					sibilingNode.setBlack(incomingNode.isBlack());
					incomingNode.getParent().setBlack(true);
					sibilingNode.getRightChild().getParent().setBlack(true);
					leftRotate(incomingNode.getParent());
					incomingNode=root;
				}
			}else {
				NodeRB<K,V> sibilingNode=incomingNode.getParent().getLeftChild();
				if(!sibilingNode.isBlack()) {
					sibilingNode.setBlack(true);
					incomingNode.getParent().setBlack(false);
					rightRotate(incomingNode.getParent());
					sibilingNode=incomingNode.getParent().getLeftChild();
				}
				if(sibilingNode.getLeftChild().isBlack()&&sibilingNode.getRightChild().isBlack()) {
					sibilingNode.setBlack(false);
					incomingNode=incomingNode.getParent();
				}else {
					if(sibilingNode.getLeftChild().isBlack()) {
						sibilingNode.setBlack(false);
						sibilingNode.getRightChild().setBlack(true);
						rightRotate(sibilingNode);
						sibilingNode=incomingNode.getParent().getLeftChild();
					}
					sibilingNode.setBlack(incomingNode.getParent().isBlack());
					incomingNode.getParent().setBlack(true);
					sibilingNode.getLeftChild().setBlack(true);
					rightRotate(incomingNode.getParent());
					incomingNode=root;
				}
			}
		}
		incomingNode.setBlack(true);
	}

	public static void main(String[] args) {
		RedBlackTree<Integer, String> tree = new RedBlackTree<>();
		tree.insert(1, "One");
		tree.insert(10, "Ten");
		tree.insert(8, "Eight");
		tree.insert(5, "Five");
		tree.insert(3, "Three");
		tree.insert(2, "Two");
		tree.insert(9, "Nine");
		tree.insert(7, "Seven");
		tree.insert(0, "Zero");
		tree.insert(4, "Four");
		tree.insert(6, "Six");
		tree.inOrderWalk(tree.getRoot().get());
		System.out.println();
		tree.delete(5);
		tree.inOrderWalk(tree.getRoot().get());
		System.out.println();
		tree.delete(10);
		tree.inOrderWalk(tree.getRoot().get());
		System.out.println();
		tree.insert(5, "Five");
		tree.inOrderWalk(tree.getRoot().get());
		System.out.println();
	}

}

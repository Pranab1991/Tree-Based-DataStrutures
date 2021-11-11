package com.pranab.binarySearchTree.avlTree;

import java.util.function.Consumer;

import com.pranab.binarySearchTree.BaseTreeOperations;
import com.pranab.binarySearchTree.Node;

public class AvlTree<K extends Comparable<K>, V> extends BaseTreeOperations<K, V> {

	@Override
	public V getValue(K key) {
		readlocker.lock();
		try {
			Node<K, V> value = search(key);
			return value == null ? null : value.getValue();
		} finally {
			readlocker.unlock();
		}
	}

	@Override
	public K getMinimumKey() {
		readlocker.lock();
		try {
			Node<K, V> value = getMinimum(this.root);
			return value == null ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	@Override
	public K getMaximumKey() {
		readlocker.lock();
		try {
			Node<K, V> value = getMaximum(this.root);
			return value == null ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	@Override
	public K getSuccessorKey(K key) {
		readlocker.lock();
		try {
			Node<K, V> value = getSuccessor(key);
			return value == null ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	@Override
	public K getPredecessorKey(K key) {
		readlocker.lock();
		try {
			Node<K, V> value = getPredecessor(key);
			return value == null ? null : value.getKey();
		} finally {
			readlocker.unlock();
		}
	}

	@Override
	public void iterator(K key, Consumer<Node<K, V>> work) {
		readlocker.lock();
		try {
			walk(search(key), work);
		} finally {
			readlocker.unlock();
		}
	}

	public K getRootKey() {
		return root != null ? root.getKey() : null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(K key) {
		readlocker.lock();
		try {
			Node<K, V> value = search(key);
			return value == null ? false : true;
		} finally {
			readlocker.unlock();
		}
	}

	@Override
	public boolean insert(K key, V value) {
		Node<K, V> temp = root;
		Node<K, V> parent = null;
		while (temp != null) {
			parent = temp;
			if (key.compareTo(temp.getKey()) < 0) {
				temp = temp.getLeftChild();
			} else {
				temp = temp.getRightChild();
			}
		}
		Node<K, V> newNode = new Node<>(key, null, null, null, value);
		if (parent == null) {
			root = newNode;
		} else {
			newNode.setParent(parent);
			if (key.compareTo(parent.getKey()) < 0) {
				parent.setLeftChild(newNode);
			} else {
				parent.setRightChild(newNode);
			}
		}
		balanceAvlTree(parent);
		size++;
		return true;
	}
	
	private void balanceAvlTree(Node<K, V> node) {
		if(node==null) {
			return;
		}
		int diffInHeight = nodeHeightDiff(node);
		if(diffInHeight<-1||diffInHeight>1) {
			if(diffInHeight>0) {				
				Node<K, V> rightchild=node.getRightChild();
				int subDiffInHeight=nodeHeightDiff(rightchild);
				if(subDiffInHeight>0) {
					leftRotate(node);
				}else {
					rightRotate(rightchild);
					leftRotate(node);
				}
			}else {
				Node<K, V> leftChild=node.getLeftChild();
				int subDiffInHeight=nodeHeightDiff(leftChild);
				if(subDiffInHeight<0) {
					rightRotate(node);
				}else {
					leftRotate(leftChild);
					rightRotate(node);
				}
			}
		}
		if(node.getParent()!=null) {
			balanceAvlTree(node.getParent());
		}
	}
	
	private void leftRotate(Node<K, V> node) {
		Node<K, V> rightChild = node.getRightChild();
			node.setRightChild(rightChild.getLeftChild());
			if(rightChild.getLeftChild()!=null) {
				rightChild.getLeftChild().setParent(node);
			}
		rightChild.setParent(node.getParent());
		if (node.getParent() != null) {
			if (node.getParent().getLeftChild() == node) {
				node.getParent().setLeftChild(rightChild);
			} else {
				node.getParent().setRightChild(rightChild);
			}			
		}else {
			root=rightChild;
		}
		node.setParent(rightChild);
		rightChild.setLeftChild(node);
	}
	
	private void rightRotate(Node<K, V> node) {
		Node<K, V> leftChild = node.getLeftChild();
		node.setLeftChild(leftChild.getRightChild());
		if (leftChild.getRightChild() != null) {
			leftChild.getRightChild().setParent(node);
		}
		leftChild.setParent(node.getParent());
		if (node.getParent() != null) {
			if (node.getParent().getLeftChild() == node) {
				node.getParent().setLeftChild(leftChild);
			} else {
				node.getParent().setRightChild(leftChild);
			}
		}else {
			root=leftChild;
		}
		node.setParent(leftChild);
		leftChild.setRightChild(node);
	}

	private int nodeHeightDiff(Node<K, V> node) {
		int leftHeight=0;
		int rightHeight=0;
		if(node.getLeftChild()!=null) {
			leftHeight=heightOfTree(node.getLeftChild(), 0)+1;
		}
		if(node.getRightChild()!=null) {
			rightHeight=heightOfTree(node.getRightChild(), 0)+1;
		}
		return rightHeight-leftHeight;
	}

	@Override
	public boolean delete(K key) {
		try {
			Node<K, V> found = search(key);
			if (found == null) {
				return false;
			}
			if (found.getLeftChild() == null) {
				transplant(found, found.getRightChild());
			} else if (found.getRightChild() == null) {
				transplant(found, found.getLeftChild());
			} else {
				Node<K, V> to_be_replaced = getMinimum(found.getRightChild());
				if (to_be_replaced.getParent() != found) {
					transplant(to_be_replaced, to_be_replaced.getRightChild());
					to_be_replaced.setRightChild(found.getRightChild());
					found.getRightChild().setParent(to_be_replaced);
				}
				transplant(found, to_be_replaced);
				to_be_replaced.setLeftChild(found.getLeftChild());
				found.getLeftChild().setParent(to_be_replaced);				
			}
			found.setLeftChild(null);
			found.setRightChild(null);
			balanceAvlTree(found.getParent());
			found.setParent(null);
			size--;
			return true;
		} catch (Exception e) {
			return false;
		} 
	}

	private int heightOfTree(Node<K, V> node, int height) {
		if (node == null) {
			return height - 1;
		}
		int leftHeight = heightOfTree(node.getLeftChild(), height + 1);
		int rightHeight = heightOfTree(node.getRightChild(), height + 1);
		if (leftHeight < rightHeight) {
			return rightHeight;
		} else {
			return leftHeight;
		}
	}

	public int heightOfTreeBalancedTree(K key) {
		return heightOfTree(search(key), 0);
	}

	public static void main(String[] args) {
		AvlTree<Integer, Integer> tree = new AvlTree<>();
		
		
		tree.insert(14,14);
		tree.insert(17,17);
		tree.insert(11,11);
		tree.insert(7,7);
		tree.insert(53,53);
		tree.insert(4,4);
		tree.insert(13,13);
		tree.insert(12,12);
		tree.insert(8,8);
		tree.insert(60,60);
		tree.insert(19,19);
		tree.insert(16,16);
		tree.insert(20,20);
		System.out.println(tree.heightOfTreeBalancedTree(tree.getRootKey()));
		tree.delete(8);
		tree.delete(7);
		tree.delete(11);
		tree.delete(14);
		tree.delete(17);
		tree.delete(20);
		tree.delete(60);
		System.out.println(tree.heightOfTreeBalancedTree(tree.getRootKey()));
	}

}

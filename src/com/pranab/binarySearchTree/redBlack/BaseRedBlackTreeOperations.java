package com.pranab.binarySearchTree.redBlack;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Consumer;

import com.pranab.binarySearchTree.Node;
import com.pranab.binarySearchTree.TreeOperations;

public abstract class BaseRedBlackTreeOperations<K extends Comparable<? super K>, V> implements TreeOperations<K, V> {
	protected Node<K, V> root;
	protected int size;
	protected ReentrantReadWriteLock locker = new ReentrantReadWriteLock();
	protected ReadLock readlocker = locker.readLock();
	protected WriteLock writelocker = locker.writeLock();
	NodeRB<K, V> sentinal=new NodeRB<K, V>(null,null,null,null,null,true);
	
	protected Node<K, V> search(K key) {
		readlocker.lock();
		Node<K, V> found=sentinal;
		try {
			found = root;
			while (found != sentinal) {
				if (found.getKey().equals(key)) {
					break;
				}
				if (key.compareTo(found.getKey()) < 0) {
					found = found.getLeftChild();
				} else {
					found = found.getRightChild();
				}
			}
			return found;
		} finally {
			readlocker.unlock();
		}
	}

	protected Node<K, V> getMinimum(Node<K, V> node) {
		readlocker.lock();
		try {
			Node<K, V> minimun = node;
			if (minimun != sentinal) {
				while (minimun.getLeftChild() != sentinal) {
					minimun = minimun.getLeftChild();
				}
			}
			return minimun;
		} finally {
			readlocker.unlock();
		}
	}

	protected Node<K, V> getMaximum(Node<K, V> node) {
		readlocker.lock();
		try {
			Node<K, V> maximum = node;
			if (maximum != sentinal) {
				while (maximum.getRightChild() != sentinal) {
					maximum = maximum.getRightChild();
				}
			}
			return maximum;
		} finally {
			readlocker.unlock();
		}
	}

	protected Node<K, V> getSuccessor(K key) {
		readlocker.lock();
		try {
			Node<K, V> successor = null;
			Node<K, V> found = search(key);
			if (found != sentinal) {
				if (found.getRightChild() != sentinal) {
					successor = getMinimum(found.getRightChild());
				} else {
					Node<K, V> parent = found.getParent();
					while ((parent != sentinal) && (found == parent.getRightChild())) {
						found = parent;
						parent = found.getParent();
					}
					successor = parent;
				}
			}
			return successor;
		} finally {
			readlocker.unlock();
		}
	}

	protected Node<K, V> getPredecessor(K key) {
		readlocker.lock();
		try {
			Node<K, V> predecessor = null;
			Node<K, V> found = search(key);
			if (found != sentinal) {
				if (found.getLeftChild() != sentinal) {
					predecessor = getMaximum(found.getLeftChild());
				} else {
					Node<K, V> parent = found.getParent();
					while ((parent != sentinal) && (found == parent.getLeftChild())) {
						found = parent;
						parent = found.getParent();
					}
					predecessor = parent;
				}
			}
			return predecessor;
		} finally {
			readlocker.unlock();
		}
	}

	protected void transplant(Node<K, V> replaced, Node<K, V> replacing) {
		writelocker.lock();
		try {
			Node<K, V> parent = replaced.getParent();
			if (null != replacing) {
				replacing.setParent(parent);
			}
			if (parent == sentinal) {
				root = replacing;				
				return;
			}
			if (replaced == parent.getLeftChild()) {
				parent.setLeftChild(replacing);
			} else {
				parent.setRightChild(replacing);
			}
		} finally {
			writelocker.unlock();
		}
	}

	protected void walk(Node<K, V> startNode, Consumer<Node<K, V>> work) {
		readlocker.lock();
		try {
			if (startNode != sentinal) {
				walk(startNode.getLeftChild(), work);
				work.accept(startNode);
				walk(startNode.getRightChild(), work);
			}
		} finally {
			readlocker.unlock();
		}
	}

}

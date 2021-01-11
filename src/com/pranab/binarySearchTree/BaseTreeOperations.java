package com.pranab.binarySearchTree;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Consumer;

public abstract class BaseTreeOperations<K extends Comparable<? super K>, V> implements TreeOperations<K, V> {
	protected Node<K, V> root;
	protected int size;
	protected ReentrantReadWriteLock locker = new ReentrantReadWriteLock();
	protected ReadLock readlocker = locker.readLock();
	protected WriteLock writelocker = locker.writeLock();

	protected Node<K, V> search(K key) {
		readlocker.lock();
		try {
			Node<K, V> found = root;
			while (found != null) {
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
			if (minimun != null) {
				while (minimun.getLeftChild() != null) {
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
			if (maximum != null) {
				while (maximum.getRightChild() != null) {
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
			if (found != null) {
				if (found.getRightChild() != null) {
					successor = getMinimum(found.getRightChild());
				} else {
					Node<K, V> parent = found.getParent();
					while ((parent != null) && (found == parent.getRightChild())) {
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
			if (found != null) {
				if (found.getLeftChild() != null) {
					predecessor = getMaximum(found.getLeftChild());
				} else {
					Node<K, V> parent = found.getParent();
					while ((parent != null) && (found == parent.getLeftChild())) {
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
			if (parent == null) {
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
			if (startNode != null) {
				walk(startNode.getLeftChild(), work);
				work.accept(startNode);
				walk(startNode.getRightChild(), work);
			}
		} finally {
			readlocker.unlock();
		}
	}

}
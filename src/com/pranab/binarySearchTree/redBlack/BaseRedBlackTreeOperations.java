package com.pranab.binarySearchTree.redBlack;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Consumer;

import com.pranab.binarySearchTree.TreeOperations;

@Deprecated
public abstract class BaseRedBlackTreeOperations<K extends Comparable<K>, V>  {
	protected NodeRB<K, V> root;
	protected int size;
	protected ReentrantReadWriteLock locker = new ReentrantReadWriteLock();
	protected ReadLock readlocker = locker.readLock();
	protected WriteLock writelocker = locker.writeLock();
	NodeRB<K, V> sentinal=new NodeRB<K, V>(null,null,null,null,null,true);
	
	protected NodeRB<K, V> search(K key) {
		readlocker.lock();
		NodeRB<K, V> found=sentinal;
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

	protected NodeRB<K, V> getMinimum(NodeRB<K, V> NodeRB) {
		readlocker.lock();
		try {
			NodeRB<K, V> minimun = NodeRB;
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

	protected NodeRB<K, V> getMaximum(NodeRB<K, V> NodeRB) {
		readlocker.lock();
		try {
			NodeRB<K, V> maximum = NodeRB;
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

	protected NodeRB<K, V> getSuccessor(K key) {
		readlocker.lock();
		try {
			NodeRB<K, V> successor = null;
			NodeRB<K, V> found = search(key);
			if (found != sentinal) {
				if (found.getRightChild() != sentinal) {
					successor = getMinimum(found.getRightChild());
				} else {
					NodeRB<K, V> parent = found.getParent();
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

	protected NodeRB<K, V> getPredecessor(K key) {
		readlocker.lock();
		try {
			NodeRB<K, V> predecessor = null;
			NodeRB<K, V> found = search(key);
			if (found != sentinal) {
				if (found.getLeftChild() != sentinal) {
					predecessor = getMaximum(found.getLeftChild());
				} else {
					NodeRB<K, V> parent = found.getParent();
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

	protected void transplant(NodeRB<K, V> replaced, NodeRB<K, V> replacing) {
		writelocker.lock();
		try {
			NodeRB<K, V> parent = replaced.getParent();
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

	protected void walk(NodeRB<K, V> startNode, Consumer<NodeRB<K, V>> work) {
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

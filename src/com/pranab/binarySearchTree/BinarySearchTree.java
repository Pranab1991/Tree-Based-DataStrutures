package com.pranab.binarySearchTree;

import java.util.function.Consumer;

public class BinarySearchTree<K extends Comparable<K>, V> extends BaseTreeOperations<K, V> {

	@Override
	public boolean insert(K key, V value) {
		writelocker.lock();
		try {
			Node<K, V> newLeaf = new Node<>(key, null, null, null, value);
			Node<K, V> searchNode = this.root;
			Node<K, V> parent = null;
			boolean modify = false;
			if (searchNode == null) {
				this.root = newLeaf;
				size++;
				return true;
			}
			while (searchNode != null) {
				parent = searchNode;
				if (searchNode.getKey().compareTo(key) > 0) {
					searchNode = searchNode.getLeftChild();
				} else if (searchNode.getKey().compareTo(key) < 0) {
					searchNode = searchNode.getRightChild();
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
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			writelocker.unlock();
		}
	}

	@Override
	public boolean delete(K key) {
		writelocker.lock();
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
			found.setParent(null);
			size--;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			writelocker.unlock();
		}
	}

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
		return root!=null?root.getKey():null;
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

}

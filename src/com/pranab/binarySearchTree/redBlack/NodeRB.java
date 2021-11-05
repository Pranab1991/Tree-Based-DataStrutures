package com.pranab.binarySearchTree.redBlack;

import com.pranab.binarySearchTree.Node;

public class NodeRB<K extends Comparable<K>, V> {
	private K key;
	private NodeRB<K, V> leftChild;
	private NodeRB<K, V> rightChild;
	private NodeRB<K, V> parent;
	private V value;
	private boolean black;
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public NodeRB<K, V> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(NodeRB<K, V> leftChild) {
		this.leftChild = leftChild;
	}

	public NodeRB<K, V> getRightChild() {
		return rightChild;
	}

	public void setRightChild(NodeRB<K, V> rightChild) {
		this.rightChild = rightChild;
	}

	public NodeRB<K, V> getParent() {
		return parent;
	}

	public void setParent(NodeRB<K, V> parent) {
		this.parent = parent;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

	public NodeRB() {
		super();
	}

	public NodeRB(K key, NodeRB<K, V> leftChild, NodeRB<K, V> rightChild, NodeRB<K, V> parent, V value, boolean black) {
		super();
		this.key = key;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.parent = parent;
		this.value = value;
		this.black = black;
	}

	
}

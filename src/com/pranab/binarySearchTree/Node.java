package com.pranab.binarySearchTree;

public class Node<K extends Comparable<K>, V> {
	private K key;
	private Node<K, V> leftChild;
	private Node<K, V> rightChild;
	private Node<K, V> parent;
	private V value;

	public Node(K key, Node<K, V> leftChild, Node<K, V> rightChild, Node<K, V> parent, V value) {
		super();
		this.key = key;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.parent = parent;
		this.value = value;
	}

	public Node<K, V> getParent() {
		return parent;
	}

	public void setParent(Node<K, V> parent) {
		this.parent = parent;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public Node<K, V> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node<K, V> leftChild) {
		this.leftChild = leftChild;
	}

	public Node<K, V> getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node<K, V> rightChild) {
		this.rightChild = rightChild;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}

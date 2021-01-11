package com.pranab.binarySearchTree.redBlack;

import com.pranab.binarySearchTree.Node;

public class NodeRB<K extends Comparable<? super K>, V> extends Node<K, V> {

	public NodeRB(K key, Node<K, V> leftChild, Node<K, V> rightChild, Node<K, V> parent, V value, boolean black) {
		super(key, leftChild, rightChild, parent, value);
		this.black = black;
	}

	private boolean black;

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

}

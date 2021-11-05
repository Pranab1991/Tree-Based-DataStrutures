package com.pranab.binarySearchTree.redBlack;

import java.util.Optional;

public interface RebBlackTree<K extends Comparable<K>,V> {

	void insert(K key,V value);
	void delete(K key);
	Optional<V> search(K key);
	Optional<V> minimum();
	Optional<V> maximum();
	Optional<V> minimum(K key);
	Optional<V> maximum(K key);
	Optional<V> successor(K key);
	Optional<V> predecessor(K key);
	Optional<NodeRB<K, V>> getRoot();
}

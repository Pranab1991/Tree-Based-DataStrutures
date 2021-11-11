package com.pranab.binarySearchTree;

import java.util.function.Consumer;

public interface TreeOperations<K extends Comparable<K> ,V> {

V getValue(K key) ;
K getMinimumKey() ;
K getMaximumKey() ;
K getSuccessorKey(K key);
K getPredecessorKey(K key);
void iterator(K key,Consumer<Node<K, V>> work) ;
boolean insert(K key,V value);
boolean delete(K key);
int size();
boolean contains(K key);
}


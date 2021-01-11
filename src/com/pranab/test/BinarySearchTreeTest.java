package com.pranab.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.pranab.binarySearchTree.BinarySearchTree;

class BinarySearchTreeTest {

	private static BinarySearchTree<Integer, String> tree;
	private static final Integer MAX_VALUE = 11;
	private static final Integer MIN_VALUE = 0;

	@BeforeEach
	public void initialize() {
		tree = new BinarySearchTree<>();
		tree.insert(5, "Five");
		tree.insert(2, "Two");
		tree.insert(4, "Four");
		tree.insert(10, "Ten");
		tree.insert(8, "Eight");
		tree.insert(3, "Three");
		tree.insert(1, "One");
		tree.insert(6, "Six");
		tree.insert(7, "Seven");
		tree.insert(0, "Zero");
		tree.insert(11, "Eleven");
	}

	@Test
	public void insert_test() {
		assertTrue(tree.insert(9, "nine"));
		System.out.println();
//tree.walk(tree.getRoot());
	}

	@ParameterizedTest
	@ValueSource(ints = { 5, 3, 10, 1, 4, 2, 6, 7, 8, 11 })
	public void delete_test(int key) {
		assertTrue(tree.delete(key));
		iterateTree();
	}

	@Test
	public void recursive_delete_test() {
		int[] numbers = {5,2,4,10,8,3,1,6,7,0,11};
		assertAll("numbers",
		         () -> assertExtracted_delete(numbers[0]),
		         () -> assertExtracted_delete(numbers[1]),
		         () -> assertExtracted_delete(numbers[2]),
		         () -> assertExtracted_delete(numbers[3]),
		         () -> assertExtracted_delete(numbers[4]),
		         () -> assertExtracted_delete(numbers[5]),
		         () -> assertExtracted_delete(numbers[6]),
		         () -> assertExtracted_delete(numbers[7]),
		         () -> assertExtracted_delete(numbers[8]),
		         () -> assertExtracted_delete(numbers[9]),
		         () -> assertExtracted_delete(numbers[10])
		     );
	}
	
	
	@Test
	public void recursive_delete_insert_test() {
		int[] delete_num_array = {5,2,4,10,8,3,1,6,7,0,11};
		int[] insert_key= {99,22,100,50,70};
		String[] insert_value= {"NintyNine","TwentyTwo","Hundred","Fifty","Seventy"};
		assertAll("numbers",
		         () -> assertExtracted_delete(delete_num_array[0]),
		         () -> assertExtracted_delete(delete_num_array[1]),
		         () -> assertExtracted_insert(insert_key[0],insert_value[0]),
		         () -> assertExtracted_delete(delete_num_array[2]),
		         () -> assertExtracted_insert(insert_key[1],insert_value[1]),
		         () -> assertExtracted_delete(delete_num_array[3]),
		         () -> assertExtracted_insert(insert_key[2],insert_value[2]),
		         () -> assertExtracted_delete(delete_num_array[4]),
		         () -> assertExtracted_delete(delete_num_array[5]),
		         () -> assertExtracted_delete(insert_key[2]),
		         () -> assertExtracted_delete(delete_num_array[6]),
		         () -> assertExtracted_delete(insert_key[1]),
		         () -> assertExtracted_delete(delete_num_array[7]),
		         () -> assertExtracted_delete(delete_num_array[8]),
		         () -> assertExtracted_delete(insert_key[0]),
		         () -> assertExtracted_delete(delete_num_array[9]),
		         () -> assertExtracted_delete(delete_num_array[10]),
		         () -> assertExtracted_insert(insert_key[3],insert_value[3]),
		         () -> assertExtracted_insert(insert_key[4],insert_value[4]),
		         () -> assertExtracted_delete(insert_key[3]),
		         () -> assertExtracted_delete(insert_key[4]),
		         () -> assertNull(tree.getMaximumKey()),
		         () -> assertNull(tree.getMinimumKey()),
		         () -> assertNull(tree.getPredecessorKey(5)),
		         () -> assertNull(tree.getSuccessorKey(2)),
		         () -> assertFalse(tree.contains(3))
		     );
	}
	
	@Test
	public void search_test() {
		assertEquals("Four", tree.getValue(4));
	}

	@Test
	public void minimum_test() {
		assertEquals(MIN_VALUE, tree.getMinimumKey());
	}

	@Test
	public void maximum_test() {
		assertEquals(MAX_VALUE, tree.getMaximumKey());
	}

	@ParameterizedTest
	@ValueSource(ints = { 7, 5, 0 })
	public void successor_test(int key) {
		assertEquals(Integer.valueOf(key + 1), tree.getSuccessorKey(key));
	}

	@ParameterizedTest
	@ValueSource(ints = { 7, 5, 1 })
	public void predecessor_test(int key) {
		assertEquals(Integer.valueOf(key - 1), tree.getPredecessorKey(key));
	}

	@Test
	public void walk_tree() {
		tree.iterator(tree.getRootKey(), (node) -> {
			node.setValue(node.getValue() + "s");
			System.out.print(node.getValue() + ",");
		});
		System.out.println();
	}

	@ParameterizedTest
	@ValueSource(ints = { 7, 5, 1 })
	public void contains_test(int key) {
		assertEquals(true, tree.contains(key));
	}

	@Test
	public void size_test() {
		assertEquals(11, tree.size());
	}
	
	private void assertExtracted_delete(int key) {
		assertTrue(tree.delete(key));
		iterateTree();
	}
	
	private void assertExtracted_insert(int key,String value) {
		assertTrue(tree.insert(key,value));
		iterateTree();
	}

	private void iterateTree() {
		System.out.println();
		tree.iterator(tree.getRootKey(), (node) -> {
			System.out.print(node.getValue() + ",");
		});
		System.out.println();
	}
}

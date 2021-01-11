package com.pranab.binarySearchTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DeleteA {

	public static void main(String[] args) {
		List<Integer> arr=Arrays.asList(1,2,3,4,5);
		arr.forEach(index->System.out.println(index));
		System.out.println("ABC".substring(0,1));
	}
}


interface Abc{
	default void abc() {};
	void aa();
}

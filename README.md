# Tree



## Introduction:



<p> **Tree** In computer science, a tree is a widely used abstract data type that simulates a hierarchical tree structure, with a root value and subtrees of children with a parent node, represented as a set of linked nodes.<br><br>



A tree data structure can be defined recursively as a collection of nodes, where each node is a data structure consisting of a value and a list of references to nodes. The start of the tree is the "root node" and the reference nodes are the "children". No reference is duplicated and none points to the root</p>



## Getting Started:

<p>This project contains the implementation of two kinds of tree data structure, Binary Search Tree and Red-Black Tree(which is also a BST but with mechanisms to make it a balanced BST ) </p>

<pre> - <b>Binary Search Tree</b> : A node-based binary tree data structure that has the following properties:

			1. The left subtree of a node contains only nodes with keys lesser than the node’s key.

			2. The right subtree of a node contains only nodes with keys greater than the node’s key.

			3. The left and right subtree each must also be a binary search tree.</pre>

<pre> - <b>Undirected Graph</b>  :  A red-black tree is a kind of self-balancing binary search tree where each node has an extra bit, and that bit is often interpreted as the color

			Rules That Every Red-Black Tree Follows: 

			1. Every node has a colour either red or black.

			2. The root of the tree is always black.

			3. There are no two adjacent red nodes (A red node cannot have a red parent or red child).

			4. Every path from a node (including root) to any of its descendants NULL nodes has the same number of black nodes.</pre>



The APIs exposed for these two data structures are segregated in two interfaces __TreeOperations.java__ and __RebBlackTree.java__ . As promised by the design of data structure to expose operations with time complexity of **O(log n)** , the same has been achieved in implementation.<br><br>



**BinarySearchTree.java**: is the __Key - Value__ implementation of Binary Search Tree data structure. The implementation uses Generics to achieve Template class design meaning, custom objects can be used just as in case of a Map collection in java. The implementation is Thread-safe.<br><br>

Below code snippet depicts usage of the BST:

'''java:
BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();  //creation of a BST
		tree.insert(1, "One");  //creation of a node 
		tree.insert(10, "Ten");
		tree.insert(8, "Eight");
		tree.delete(10); //deletion of a node
'''

 **RedBlackTree.java**  is the __Key - Value__ implementation of Red-Black Tree data structure. The implementation uses Generics to achieve Template class design meaning, custom objects can be used just as in case of a Map collection in java. It's not Thread-safe yet.<br><br>

Below code snippet depicts usage of the Red Black:

'''java:

RedBlackTree<Integer, String> tree = new RedBlackTree<>(); //creation of a RB Tree
		tree.insert(1, "One"); //creation of a node 
		tree.insert(10, "Ten");
		tree.insert(8, "Eight");
		tree.delete(10); //deletion of a node
'''




## Pending Work:

- Documentation

- Unit Testing

- Thread safety for Red Black Tree

- More APIs to make it user friendly

- More focus on Error Handling



### Thanks

Please feel free to fork/ download the project. There are a lot of areas to improve on, including extensive testing. Help is always appreciated



Connect me on linked in : **[https://www.linkedin.com/in/pranab-bharadwaj-237887176/](https://www.linkedin.com/in/pranab-bharadwaj-237887176/)**



Regards

Pranab Bharadwaj

pranabharadwaj@gmail.com,

pranabbharadwaj@gmail.com


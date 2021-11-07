# Tree

## Introduction:

<p>**Tree** In computer science, a tree is a widely used abstract data type that simulates a hierarchical tree structure, with a root value and subtrees of children with a parent node, represented as a set of linked nodes.<br><br>

A tree data structure can be defined recursively as a collection of nodes, where each node is a data structure consisting of a value and a list of references to nodes. The start of the tree is the "root node" and the reference nodes are the "children". No reference is duplicated and none points to the root</p>

## Getting Started:
<p>This project contains implementation of two kind of tree datastructure, Binary Search Tree and Red Black Tree(which is also a BST but with mechanismes to make it a balamced BST ) <br>
<pre>- **Binary Search Tree** : A node-based binary tree data structure which has the following properties:
								1. The left subtree of a node contains only nodes with keys lesser than the node’s key.
								2. The right subtree of a node contains only nodes with keys greater than the node’s key.
								3. The left and right subtree each must also be a binary search tree.</pre>
<pre>- **Undirected Graph**  :  A red-black tree is a kind of self-balancing binary search tree where each node has an extra bit, and that bit is often interpreted as the colour
							Rules That Every Red-Black Tree Follows: 
								1. Every node has a colour either red or black.
								2. The root of the tree is always black.
								3. There are no two adjacent red nodes (A red node cannot have a red parent or red child).
								4. Every path from a node (including root) to any of its descendants NULL nodes has the same number of black nodes.</pre>

The APIs exposed for these two datastructures are segregated in two interfaces __TreeOperations.java__ and __RebBlackTree.java__ . As promisimed by the designe of datastructure to expose operations with time complexity of **O(log n)** , same has been achived in implementation.<br><br>

**BinarySearchTree.java**: is the __Key - Value__ implementation of Binary Search Tree datastructure. The implementation uses Generics to achive Template class designe meaning, custome objects can be used just as incase of a Map collection in java. The implementation is Thread-safe.<br><br>

Below code snippet depicts usage of the BST:
'''java:
BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
		tree.insert(1, "One");
		tree.insert(10, "Ten");
		tree.insert(8, "Eight");
		tree.delete(10);
'''
</p>

**RedBlackTree.java**  is the __Key - Value__ implementation of Red Black Tree datastructure. The implementation uses Generics to achive Template class designe meaning, custome objects can be used just as incase of a Map collection in java. It's not Thread-safe yet.<br><br>

Below code snippet depicts usage of the BST:
'''java:
RedBlackTree<Integer, String> tree = new RedBlackTree<>();
		tree.insert(1, "One");
		tree.insert(10, "Ten");
		tree.insert(8, "Eight");
		tree.delete(10);
'''
</p>

## Pending Work:
- Documentation
- Unit Testing
- Thread safty for Red Black Tree
- More APIs to make it user friendly
- More focus on Error Handling

### Thanks
Please feel free to fork/ download the project. There are a lot of areas to improve on, including extensive testing. Help is always appreciated

Connect me on linked in : **[https://www.linkedin.com/in/pranab-bharadwaj-237887176/](https://www.linkedin.com/in/pranab-bharadwaj-237887176/)**

Regards
Pranab Bharadwaj
pranabharadwaj@gmail.com,
pranabbharadwaj@gmail.com

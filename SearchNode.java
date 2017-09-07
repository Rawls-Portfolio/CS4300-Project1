/*
 * File: SearchNode.java
 * Author: Amanda Rawls
 * Date: 2/11/2017
 * Project: Missionaries and Cannibals
 * Class: CS4300
 * Professor: Dr. Mark Hauschild
 * 
 * This file contains the class SearchNode, to be used with the TreeSearch class and an
 * implementation of the ProblemFormulation interface.
 */

public class SearchNode<T> {
	
	private T state;  		// the state in the state space to which the node corresponds
	private SearchNode<?> parent; 	// the node in the search tree that generated this node
	private T action;		// the action that was applied to the parent to generate the node
	private Integer pathCost;	// the cost, initial value inherited from the parents
	
	public SearchNode(ProblemFormulation<T> problem, T state){
		this.state = state;
		this.parent = null;
		this.action = null;
		this.pathCost = 0;
	} // end root constructor
	
	private SearchNode(ProblemFormulation<T> problem, SearchNode<?> parent, T action){
		this.parent = parent;
		this.action = action;
		state = problem.result( (T) getParent().getState(), action);
		pathCost = getParent().pathCost + problem.pathCost((T) getParent().getState(), action);
	} // end child constructor

	public SearchNode<T> childNode(ProblemFormulation<T> problem, T action){
		SearchNode<T> childNode = new SearchNode<T>(problem, this, action);
		return childNode;
	} // end childNode
	
	// getters
	public SearchNode<?> getParent() { return parent; }
	public Integer getPathCost(){ return pathCost; }
	public T getState() { return state; }
	public T getAction() { return action; }
}

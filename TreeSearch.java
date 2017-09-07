/*
 * File: TreeSearch.java
 * Author: Amanda Rawls
 * Date: 2/11/2017
 * Project: Missionaries and Cannibals
 * Class: CS4300
 * Professor: Dr. Mark Hauschild
 * 
 * This file contains the class TreeSearch, which initiates a SearchNode as a root and
 * then uses methods from a class which implements the ProblemFormulation interface to determine
 * the solution.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TreeSearch<T>{
	private static final boolean enableMappedStates = false;
	private SearchNode<T> root;
	private Deque<SearchNode<T>> frontier;
	private Integer maxDepth;
	private ProblemFormulation<T> problem;
	private List<T> mappedStates;	
	
	public TreeSearch(ProblemFormulation<T> problem, T state, Integer maxDepth){
		this.problem = problem;
		root = new SearchNode<T>(problem, state);
		this.maxDepth = maxDepth;
		this.frontier = new LinkedList<SearchNode<T>>();
		
		if (enableMappedStates)
			this.mappedStates = new ArrayList<T>();
	} // end constructor
	
	// returns the searchNode that contains the goal if successful
	// and null if unsuccessful
	public SearchNode<T> treeSearch(){
		SearchNode<T> exploreNode = null;
		List<T> actions;

		// initialize the frontier
		frontier.add(root);
		
		// search for the goal
		boolean goal = false;
		while  (frontier.peekLast() != null){
			exploreNode = frontier.removeLast();
			
			if(problem.goalTest((T) exploreNode.getState())){
				goal = true;
				break; }
			
			// if exceeded maxDepth, skip expanding this node (treat it like a leaf)
			 if (exploreNode.getPathCost() >= maxDepth)
				 continue;
			
			// retrieve all valid actions for the current state
			 actions = problem.action((T) exploreNode.getState());
			
			if (enableMappedStates){
				// System.out.println("Examining state: " + exploreNode.getState());
				if (! (mappedStates.contains(exploreNode.getState())) ){ // expand unseen state and it to the record
					// for each valid action, create new SearchNodes and add them to the frontier
					for (int a = 0; a < actions.size(); a++){
					frontier.add(exploreNode.childNode(problem, actions.get(a)));
					// System.out.println("Adding state: " + exploreNode.getState() + " and action: " + actions.get(a) + " combination to frontier");
					mappedStates.add(exploreNode.getState()); } }
			} else {
				for (int a = 0; a < actions.size(); a++)
					frontier.add(exploreNode.childNode(problem, actions.get(a))); } } //end while
		if (goal)
			return exploreNode;
		else
			return null; 
	} // end treeSearch
	
	// return a deque containing the history of states
	public Deque<List<T>> traceSolution(SearchNode<?> traceNode){
		// return the history of states and actions in a deque
		Deque<List<T>> trace = new ArrayDeque();
		
		// if traceNode is null, return empty Deque
		if (traceNode == null)
			return trace;
				
		// walk from the traceNode to the root
		while (traceNode.getParent() != null){
			// System.out.println("node at depth: " + traceNode.getPathCost());
			List<T> snapshot = new ArrayList(2); 	// holds the action and state for each node in the trace
			snapshot.add((T) traceNode.getAction());
			snapshot.add((T) traceNode.getState());
			trace.add(snapshot);
			traceNode = traceNode.getParent(); } // end while
		
		// add root to trace
		List<T> snapshot = new ArrayList(2); 	// holds the action and state for each node in the trace
		snapshot.add((T) root.getAction());
		snapshot.add((T) root.getState());
		trace.add(snapshot);
		
		return trace;
	} // end traceSolution	
} // end TreeSearch
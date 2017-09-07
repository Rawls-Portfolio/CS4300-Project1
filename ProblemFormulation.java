/*
 * File: ProblemFormulation.java
 * Author: Amanda Rawls
 * Date: 2/11/2017
 * Project: Missionaries and Cannibals
 * Class: CS4300
 * Professor: Dr. Mark Hauschild
 * 
 * This file contains the interface ProblemFormulation which is designed to work with
 * the TreeSearch class.
 */

import java.util.List;

public interface ProblemFormulation<T> {
	
	// given a state, return a set of valid actions
	public List<T> action(T state);
	
	// the transition function, given a state and action, return the resulting state
	public T result(T state, T action);
	
	// check whether the state matches the goal configuration
	public boolean goalTest(T state);
	
	// given a state and an action, return a cost
	public Integer pathCost(T state, T action);
} // end ProblemFormulation

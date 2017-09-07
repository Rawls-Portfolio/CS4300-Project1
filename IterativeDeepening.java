/*
 * File: IterativeDeepening.java
 * Author: Amanda Rawls
 * Date: 2/11/2017
 * Project: Missionaries and Cannibals
 * Class: CS4300
 * Professor: Dr. Mark Hauschild
 * 
 * This file contains the class IterativeDeepening, which contains the main method.
 * This method uses iterative deepening to progressively search a tree for the 
 * solution, and provides the TreeSearch with the the Cannibals implementation 
 * of the ProblemFormulation interface.
 */

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class IterativeDeepening {

	public static void main(String[] args) {
		// this is a more interesting and relevant scenario...
		System.out.println("3 humans and 3 zombies have 1 boat, and they all want to cross the river..\n" +
							"No more than two can get in a boat at a time, but the zombies are hungry.\n" +
							"The humans can fend off the zombies if they outnumber them,\n" +
							"so don't let the zombies outnumber the humans!\n"+
							"... unless you prefer zombies. BRAINZ.\n");
		// get maxDepth from user
		Scanner scanner = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a max depth (integer) for iterative deepening: ");
		Integer maxDepth = scanner.nextInt(); 
		scanner.close();
		
		// initialize the Missionaries and Cannibals problem formulation:
		List<Integer> initial = Arrays.asList(3, 3, 1);
		List<Integer> goal = Arrays.asList(0, 0, 0);
		ProblemFormulation<List<Integer>> problem = new Cannibals(goal);
		
		// prepare storage for search data
		SearchNode<?> solution = null;
		TreeSearch<List<Integer>> solveProblem = null;
		
		// iterative deepening
		for (int i = 0; i <= maxDepth; i++){
			solveProblem = new TreeSearch<List<Integer>>(problem, initial, i);
			solution = solveProblem.treeSearch();
			if (solution != null){
				System.out.println("\nFound solution in Max Depth: " + i);
				break; } //end if
			System.out.println("Finished Searching Max Depth: " + i); } //end for
		
		printTrace(solveProblem.traceSolution(solution));
	} // end main

	//  trace is a Deque, containing a List of the action, then the state
	// the action and state are in the form of List<Integer>
	private static void printTrace(Deque<List<List<Integer>>> trace) {
		if (trace.isEmpty())
			System.out.println("No solution was found.");
		else {
			List<List<Integer>> snapshot = null;
			List<Integer> action = null;
			List<Integer> state = null;
			while ((snapshot = trace.pollLast()) != null){ //iterate through deque
				// retrieve action
				action = snapshot.get(0);
				
				//retrieve state
				state = snapshot.get(1);
				
				// print out report
				// handle null action (for root)
				if (action != null){
					System.out.print("Action: ");
					for (int a = 0; a < action.size() - 1; a++) // print first two values
						System.out.print(action.get(a) + ", ");
					// print last value
					System.out.println(action.get(action.size()-1)); } // end if

				System.out.print("Resulting state: ");
				for (int s = 0; s < state.size() - 1; s++) // print first two values
					System.out.print(state.get(s) + ", ");
				// print last value
				System.out.println(state.get(state.size()-1) + "\n"); } // end while
			} //end else
	} // end printTrace
} // end IterativeDeepening
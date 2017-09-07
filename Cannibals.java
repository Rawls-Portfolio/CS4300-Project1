import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cannibals implements ProblemFormulation<List<Integer>> { 
	private List<Integer> goalState;
	
	public Cannibals(List<Integer> goal){
		goalState = goal;
	} // end constructor

	@Override
	// given a state, return a set of valid actions
	public List<List<Integer>> action(List<Integer> state) {
		List<List<Integer>> possibleActions = new ArrayList<>(5);
		List<List<Integer>> validActions = new ArrayList<>(5);
		List<Integer> result = new ArrayList<>(state.size());
		
		// list the five possible actions
		List<Integer> ccb = Arrays.asList(0, 2, 1);
		List<Integer> mmb = Arrays.asList(2, 0, 1);
		List<Integer> mcb = Arrays.asList(1, 1, 1);
		List<Integer> mb = Arrays.asList(1, 0, 1);
		List<Integer> cb = Arrays.asList(0, 1, 1);
		
		// assemble actions into a list
		possibleActions.add(ccb);
		possibleActions.add(mmb);
		possibleActions.add(mcb);
		possibleActions.add(mb);		
		possibleActions.add(cb);
		
		Integer m; // placeholder for missionaries
		Integer c; // placeholder for cannibals
		
		// iterate through possibleActions, 
		// verify validity of action
		// if valid, add action to validActions
		for (List<Integer>a : possibleActions){
			result = result(state, a);
			m = result.get(0);
			c = result.get(1);
			if ( 
				// value is valid
				( (m >= 0 && m <= 3) && (c >= 0 && c <= 3) )
				// no conflicts if all the missionaries are together
				&& ( ((m == 3) || (m == 0))
				// if the missionaries are split, there are no conflicts 
				// if there are the same number of cannibals with them
				|| ((m < 3 && m > 0) && m == c ) ) ) {
					validActions.add(a); } /*end if*/ } // end for
		
		return validActions;
	} // end action

	@Override
	public List<Integer> result(List<Integer> state, List<Integer> action) {
		List<Integer> s = state;
		List<Integer> a = action;
		Integer boat = s.size() - 1;
		List<Integer> result = new ArrayList<Integer>(s.size());
		
		// evaluate state 
		if ( s.get(boat) == 1){ // if boat is present, remove action from state
			for(int i = 0; i < s.size(); i++) 
			    result.add(s.get(i) - a.get(i)); } // end if
		else if (s.get(boat) == 0){ // if boat is missing, add action to state
			for(int i = 0; i < s.size(); i++) 
				result.add(s.get(i) + a.get(i)); } // end else if
		
		return result;
	} // end result

	@Override
	public boolean goalTest(List<Integer> state) {
		boolean equal = true;
		for (int i = 0; i < state.size(); i++){
			if (state.get(i) != goalState.get(i))
				equal = false;	} // end for
		
		return equal;
	} // end goalTest

	@Override
	// Cannibals is a uniform cost problem, so 1 is returned, 
	// regardless of state or action.
	// use this to track tree depth.
	public Integer pathCost(List<Integer> state, List<Integer> action) { return 1; }
}// end Cannibals
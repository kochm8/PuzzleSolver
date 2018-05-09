import java.util.Stack;

public class Solver {

	//Comparator<State> comp = new StateComparator();
	//PriorityQueue<State> queue = new PriorityQueue<State>(comp);
	
	private Stack<State> stack = new Stack<State>();
	
	private int statesExpanded = 0;
	private int deep = 0;
	private boolean finished = false;
	
	private int row;
	private int col;

	
	public Solver(int row, int col) {
		this.row = row;
		this.col = col;
	}


	public void solve(int[] puzzle){
	
		long startTime = System.currentTimeMillis();
		
		checkLength(puzzle.length);

		State root = new State( puzzle, row, col);
		deep = root.getHeuristic();

		while(!finished){
			stack.push(root);
			idaStar(deep);
			
			System.out.println("Deep: " + deep);	
			System.out.println("Total States expanded: " + statesExpanded);
			
			deep+=2;
			//statesExpanded = 0;
		}
		
		long duration = (System.currentTimeMillis()-startTime) / 1;
		
		System.out.println("");
		System.out.println("Solution found. Elapsed time: " + Long.toString(duration) + "ms");
	}


	
	public void idaStar(int deeplimit){
		
		int minDeep = deeplimit;
		
		while(!stack.isEmpty()){

			State state = stack.pop();

			if(state.checkSolution()){
				finished = true;
				stack.clear();
			}	

			if(deeplimit >= state.getHeuristic()){

				if(state.isParentParent()){
					stack = state.getChilds(stack);
					statesExpanded++;
				}
				
				//if(minDeep >= state.getHeuristic() && state.deep == deeplimit ){
				//	minDeep = state.getHeuristic();
				//} 
			}
		}
		//return minDeep;
	}
	
	private void checkLength(int puzzleLength){
		if(row*col != puzzleLength){
			System.out.println("puzzleLength/row/col doesn't match! - Exit");
			System.exit(0);
		}
	}

}

import java.util.Stack;

public class Solver {

	private Stack<State> stack = new Stack<State>();
	private int statesExpanded = 0;
	private boolean finished = false;
	private State solutionState;
	private int row;
	private int col;

	
	public Solver(int row, int col) {
		this.row = row;
		this.col = col;
	}


	public void solve(int[] puzzle){
	
		int deep = 0;
		long startTime = System.currentTimeMillis();
		
		checkLength(puzzle.length);

		State root = new State( puzzle, this.row, this.col);
		deep = root.getHeuristic();

		while(!this.finished){
			stack.push(root);
			idaStar(deep);
			
			System.out.println("Deep: " + deep);	
			System.out.println("Total States expanded: " + this.statesExpanded);
			
			deep+=2;
		}
		
		long duration = (System.currentTimeMillis()-startTime) / 1;
		
		System.out.println("");
		System.out.println("Solution found. Elapsed time: " + Long.toString(duration) + "ms");
	}


	
	public void idaStar(int deeplimit){
		
		//int minDeep = deeplimit;
		
		while(!stack.isEmpty()){

			State state = stack.pop();

			if(state.checkSolution()){
				this.finished = true;
				this.solutionState = state;
				stack.clear();
			}	

			if(deeplimit >= state.getHeuristic()){

				if(state.isParentParent()){
					stack = state.getChilds(stack);
					this.statesExpanded++;
				}
				
				//if(minDeep >= state.getHeuristic() && state.deep == deeplimit ){
				//	minDeep = state.getHeuristic();
				//} 
			}
		}
		//return minDeep;
	}
	
	private void checkLength(int puzzleLength){
		if(this.row*this.col != puzzleLength){
			System.out.println("puzzleLength/row/col doesn't match! - Exit");
			System.exit(0);
		}
	}
	
	public void printSolution() {
		
		Stack<State> solutionStack = new Stack<State>();
		State state = this.solutionState;
		int count = 0;
		
		while(state != null) {
			solutionStack.push(state);
			state = state.getParent();
		}
		
		while(!solutionStack.isEmpty()) {
			
			State sSate = solutionStack.pop();
			
			System.out.println(" ");
			System.out.print("(" + count + ".) ");
			
			if( sSate.getMoveDirection() == State.MoveDirection.LEFT ) {
				System.out.println("move left");
			}else if( sSate.getMoveDirection() == State.MoveDirection.RIGHT ) {
				System.out.println("move right");
			}else if( sSate.getMoveDirection() == State.MoveDirection.UP ) {
				System.out.println("move up");
			}else if( sSate.getMoveDirection() == State.MoveDirection.DOWN ) {
				System.out.println("move down");
			}else if( sSate.getMoveDirection() == State.MoveDirection.ROOT ) {
				System.out.println("initial puzzle");
			}
			
			System.out.println(" ");
					
			int[] puzzle = sSate.getPuzzle();
			
			if(this.col == 3 && this.row == 3) {
				System.out.println( puzzle[0] + "," + puzzle[1] + "," + puzzle[2] + "," );
				System.out.println( puzzle[3] + "," + puzzle[4] + "," + puzzle[5] + "," );
				System.out.println( puzzle[6] + "," + puzzle[7] + "," + puzzle[8] );
			}
			
			if(this.col == 4 && this.row == 4) {
				System.out.println( puzzle[0] +  "," + puzzle[1] +  "," + puzzle[2] +  "," + puzzle[3]  + "," );
				System.out.println( puzzle[4] +  "," + puzzle[5] +  "," + puzzle[6] +  "," + puzzle[7]  + "," );
				System.out.println( puzzle[8] +  "," + puzzle[9] +  "," + puzzle[10] + "," + puzzle[11] + "," );
				System.out.println( puzzle[12] + "," + puzzle[13] + "," + puzzle[14] + "," + puzzle[15] );
			}
			
			count++;
			
		}
		
	}
}

import java.util.Stack;

public class State{

	private int[] puzzle;
	private int heuristic;
	private int row;
	private int col;
	private int deep;
	private State parent;


	public State(int[] puzzle, int row, int col){
		this.puzzle = new int[row*col];
		this.col = col;
		this.row = row;
		this.puzzle = puzzle;
		this.heuristic = calcTotalManhattanDistance(this);
	}


	private State(State parentState){
		this.puzzle = parentState.puzzle.clone();
		this.col = parentState.col;
		this.row = parentState.row;
		this.deep = parentState.deep + 1;
	}


	public boolean checkSolution(){

		boolean success = true;

		for(int i=0; i<puzzle.length-1; i++){
			if(puzzle[i] != i+1){
				success = false;
				break;
			}
		}
		return success;
	}



	public Stack<State> getChilds(Stack<State> queue){

		int x = getX( getBlankPos() );
		int y = getY( getBlankPos() );


		//blank is left top
		if((x == 0) && (y == 0)){
			queue.add(moveRight(x, y));
			queue.add(moveDown(x, y));


			//blank is right top
		} else if((x == row-1) && (y == 0)){
			queue.add(moveLeft(x, y));
			queue.add(moveDown(x, y));


			//blank is top
		} else if(y == 0){
			queue.add(moveLeft(x, y));
			queue.add(moveRight(x, y));
			queue.add(moveDown(x, y));


			//blank is left bottom
		} else if((x == 0) && (y == col-1)){
			queue.add(moveRight(x, y));
			queue.add(moveUp(x, y));


			//blank is right bottom
		} else if((x == row-1) && (y == col-1)){
			queue.add(moveLeft(x, y));
			queue.add(moveUp(x, y));


			//blank is bottom
		} else if(y == col-1){
			queue.add(moveLeft(x, y));
			queue.add(moveRight(x, y));
			queue.add(moveUp(x, y));


			//blank is left
		} else if(x == 0){
			queue.add(moveDown(x, y));
			queue.add(moveUp(x, y));
			queue.add(moveRight(x, y));


			//blank is right
		} else if(x == row-1){
			queue.add(moveDown(x, y));
			queue.add(moveUp(x, y));
			queue.add(moveLeft(x, y));


			// blank is in the middle
		} else{
			queue.add(moveDown(x, y));
			queue.add(moveUp(x, y));
			queue.add(moveRight(x, y));
			queue.add(moveLeft(x, y));
		}
		return queue;
	}


	public int getHeuristic() {
		return heuristic;
	}

	public boolean isParentParent(){
		boolean expand = false;
		if(this.deep > 1){
			if(!Util.areArraysEqual(this.parent.parent.puzzle,this.puzzle)){
				expand = true;
			}
		}else{
			expand = true;
		}
		return expand;
	}

	/*
	public void print(){
		System.out.println( this.puzzle[0].getId() + " (" + this.puzzle[0].getHeuristic() + ")," +  this.puzzle[1].getId() + " (" + this.puzzle[1].getHeuristic() + ")," +  this.puzzle[2].getId() + " (" + this.puzzle[2].getHeuristic() + ")," );
		System.out.println( this.puzzle[3].getId() + " (" + this.puzzle[3].getHeuristic() + ")," +  this.puzzle[4].getId() + " (" + this.puzzle[4].getHeuristic() + ")," +  this.puzzle[5].getId() + " (" + this.puzzle[5].getHeuristic() + ")," );
		System.out.println( this.puzzle[6].getId() + " (" + this.puzzle[6].getHeuristic() + ")," +  this.puzzle[7].getId() + " (" + this.puzzle[7].getHeuristic() + ")," +  this.puzzle[8].getId() + " (" + this.puzzle[8].getHeuristic() + ")," );
		System.out.println( "Heuristic: " + this.getHeuristic());
	}
	 */

	private State moveRight(int x, int y){		
		return swapNodesAndCreateChild(x, y, x+1, y);
	}

	private State moveLeft(int x, int y){
		return swapNodesAndCreateChild(x, y, x-1, y);
	}

	private State moveDown(int x, int y){
		return swapNodesAndCreateChild(x, y, x, y+1);
	}

	private State moveUp(int x, int y){
		return swapNodesAndCreateChild(x, y, x, y-1);
	}

	private int getX(int bufferPos){
		return bufferPos % row;
	}

	private int getY(int bufferPos){
		return bufferPos / col;
	}

	private int getPos(int x, int y){
		return y*row + x;
	}

	private int getBlankPos(){
		int pos = 0;
		for(int i=0; i<puzzle.length; i++){
			if(puzzle[i] == 0){
				pos = i;
				break;
			}
		}
		return pos;
	}


	private State swapNodesAndCreateChild(int fromX, int fromY, int toX, int toY){

		State child = new State(this);
		child.parent = this;

		int toN = child.puzzle[getPos(toX, toY)];
		int fromN = child.puzzle[getPos(fromX, fromY)];

		child.puzzle[getPos(toX, toY)] = fromN;
		child.puzzle[getPos(fromX, fromY)] = toN;

		child.heuristic = calcTotalManhattanDistance(child);

		return child;
	}


	private int getNodePos(int node, int[] buffer){
		int pos = -1;
		for(int i=0; i<buffer.length; i++){
			if(node == buffer[i]){
				pos = i;
				break;
			}
		}
		return pos;
	}


	private int calcManhanttanDistance(int node, int[] buffer){

		int heuristic = 0;

		//Keine heuristic beim blank
		if(node != 0){
			int bufferPos = getNodePos(node, buffer);

			int xIst = getX(bufferPos);
			int yIst = getY(bufferPos);

			int xSoll = getX(node-1);
			int ySoll = getY(node-1);

			heuristic = Math.abs(xIst - xSoll) + Math.abs(yIst - ySoll);
		}
		return heuristic;
	}

	private int calcTotalManhattanDistance(State state){

		int childHeuristic = 0;

		for(int i=0; i<state.puzzle.length; i++){
			childHeuristic = childHeuristic + calcManhanttanDistance(state.puzzle[i], state.puzzle);
		}

		return childHeuristic + state.deep;
	}

	/*
	 implements Comparable<State> 
	@Override
	public int compareTo(State o) {
		return Integer.compare(this.getHeuristic(), o.getHeuristic());
	}
	 */

}

public class Start {

	public static void main(String[] args) {
		
		int[] puzzle = {
				
				/*
				6,3,4,
				1,0,8,
				5,7,2

				6,2,3,
				0,7,8,
				4,1,5
				
				3,0,6,
				1,2,8,
				7,5,4
				 
				8,6,7,
				2,5,4,
				3,0,1
				
				11,0,15,2,
				6,13,1,14,
				3,9,4,12,
				5,10,7,8
				*/
				
				1,10,3,0,
				5,8,12,4,
				13,14,2,6,
				11,15,9,7		

		};

		Solver solver = new Solver(4,4);
		solver.solve(puzzle);
		solver.printSolution();
	}

}

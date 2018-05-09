import java.util.Comparator;

public class StateComparator implements Comparator<State>{

	@Override
	public int compare(State o1, State o2) {
		return Integer.compare(o1.getHeuristic(), o2.getHeuristic());
	}

}

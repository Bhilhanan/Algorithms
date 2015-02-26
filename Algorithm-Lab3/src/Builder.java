/*
 * @author: Bhilhanan A Jeyaram
 * This class stores the input file in the form of graph. It detects for parseException
 * as it creates the graph. Once the graph is created, it checks for cycle and unknown
 * targets. If no exception is found then it creates the topological order of the
 * string that is passed as input to makeTarget()
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Builder
{
	private HashMap<String,ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	private HashMap<String, String> tsort = new HashMap<String, String>();
	private HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

	/** 
	 * @param makefile the incoming file
	 * @throws ParseException
	 * @throws UnknownTargetException
	 * @throws CycleDetectedException
	 */
	public Builder(String makefile) throws ParseException, UnknownTargetException, CycleDetectedException {
		if(makefile == null)
			throw new ParseException();
		for(String line : makefile.split("\n")) {
			String[] vertex = line.split(":");
			if(vertex.length!=3)
				throw new ParseException();
			vertex[0]=vertex[0].trim();
			//if duplicate target definitions is present then throw parseException
			if(tsort.containsKey(vertex[0]))
				throw new ParseException();
			tsort.put(vertex[0], vertex[2].trim());
			if(!vertex[1].trim().equals(""))//new definition
				graph.put(vertex[0], new ArrayList<String>(Arrays.asList(vertex[1].trim().split("\\s"))));
			else//may lead to another target
				graph.put(vertex[0], new ArrayList<String>());
		}
		testUnknownTarget();
		testCycle();
	}

	/**
	 * Detects presence of undefined targets
	 * @throws UnknownTargetException
	 */
	private void testUnknownTarget() throws UnknownTargetException {
		
		for(String key : graph.keySet()) {
			visited.put(key, false);
			for(String dependency : graph.get(key)) {
				if(!graph.containsKey(dependency))
					throw new UnknownTargetException();
			}
		}
	}
	
	/**
	 * Detects presence of cycle in the graph
	 * @throws CycleDetectedException
	 */
	private void testCycle() throws CycleDetectedException{

		Map<String, Boolean> completed=new HashMap<String, Boolean>(visited);
		for(String key:graph.keySet()) {
			if(!visited.get(key))
				dfs_detectCycle(key,visited,completed);
		}
	}

	/** 
	 * This is three colour DFS implementation to detect cycle
	 * @param visited: nodes that are visited
	 * @param target: string to be processed
	 * @param completed: nodes that are visited and completed
	 * @throws CycleDetectedException
	 */
	private void dfs_detectCycle(String target, Map<String, Boolean> visited, Map<String, Boolean> completed) throws CycleDetectedException {
		visited.put(target, true);
		for(String s:graph.get(target)) {
			if(!visited.get(s))
				dfs_detectCycle(s,visited,completed);
			else if(!completed.get(s))//visited but not completed then throw exception
				throw new CycleDetectedException();
		}
		completed.put(target, true);
	}

	/**
	 * 
	 * @param targetName the target
	 * @return result the made target
	 */
	public ArrayList<String> makeTarget(String targetName) {
		ArrayList<String> sorted=new ArrayList<String>();
		if(graph.containsKey(targetName))
			dfsTSort(targetName, sorted);
		return sorted;
	}
	
	private void dfsTSort(String t, ArrayList<String> sorted) {
		for(String s:graph.get(t)) {
			if(!sorted.contains(tsort.get(t)))
				dfsTSort(s, sorted);
		}
		if(!sorted.contains(tsort.get(t)))
			sorted.add(tsort.get(t));
	}

}

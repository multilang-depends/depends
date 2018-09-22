package depends.format.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import depends.entity.repo.EntityRepo;
import depends.util.Tuple;

public class DependencyMatrix {
    private Map<Integer, Map<Integer, Map<String, Integer>>> relations = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();
    private Map<Integer, Map<Integer, Map<String, Integer>>> reMappedRelations = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();

    private ArrayList<String> nodes;
	private Integer relationCount=0;
    public DependencyMatrix() {
    }

    /**
     * 优先返回重映射的Id
     * @return
     */
	public Map<Integer, Map<Integer, Map<String, Integer>>> getRelations() {
		if (reMappedRelations.size()>0) 
			return reMappedRelations;
        return relations;
    }

    public void addDependencies(ArrayList<Tuple<Integer, Integer>> pairs, String depType) {
        for(Tuple<Integer, Integer> dep : pairs) {
        	addDependency(depType, dep);
        }
    }


    public void addDependency(String depType, Tuple<Integer, Integer> pair) {
		Integer from = pair.x;
		Integer to = pair.y;

		if(from.equals(to) || from == -1 || to == -1) {
		    return;
		}
		if(!relations.containsKey(from)) {
		    relations.put(from, new HashMap<Integer, Map<String, Integer>>());
		}
		if(!relations.get(from).containsKey(to)) {
		    relations.get(from).put(to, new HashMap<String, Integer>());
		}
		if(!relations.get(from).get(to).containsKey(depType)) {
		    relations.get(from).get(to).put(depType, 0);
		}

		int weight = relations.get(from).get(to).get(depType) + 1;
		relations.get(from).get(to).put(depType, weight);
		relationCount++;
	}

	public ArrayList<String> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<String> nodes) {
		this.nodes = nodes;
	}


	private Integer translateToNewId(EntityRepo repo, HashMap<String, Integer> nodesMap, Integer key) {
		return nodesMap.get(repo.getEntity(key).getFullName());
	}

	public void remapIds(EntityRepo repo) {
		HashMap<String, Integer> nodesMap = new HashMap<>();
		for (int i=0;i<nodes.size();i++) {
			nodesMap.put(nodes.get(i), i);
		}
		
		for (Integer key:relations.keySet()) {
			Map<Integer, Map<String, Integer>> entry = relations.get(key);
			HashMap<Integer, Map<String, Integer>> newEntry = new HashMap<>();
			reMappedRelations.put(translateToNewId(repo, nodesMap, key), newEntry);
			for (Integer idsTo:entry.keySet()) {
				Map<String, Integer> value = entry.get(idsTo);
				newEntry.put(translateToNewId(repo, nodesMap, idsTo), value);
			}
		}		
	}

	public Integer relationCount() {
		return relationCount;
	}
}
